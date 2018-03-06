package com.jrelax.plugins.mail;

import java.lang.reflect.Method;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.core.plugin.IPluginAfter;
import com.jrelax.core.web.support.http.HandlerRequest;
import com.jrelax.orm.query.Condition;
import com.jrelax.web.system.entity.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jrelax.core.plugin.IPlugin;
import com.jrelax.core.plugin.annotation.Plugin;
import com.jrelax.core.plugin.annotation.PluginAfterAdvice;
import com.jrelax.kit.DateKit;
import com.jrelax.web.common.entity.notify.NotifyMessage;
import com.jrelax.web.system.entity.Email;
import com.jrelax.web.system.entity.User;
import com.jrelax.web.system.service.EmailService;
import com.jrelax.web.system.service.LogService;
import com.jrelax.web.system.service.NotifyService;

@Plugin(value="邮件发送插件",  group = "通知",loadOnStartup=false)
@PluginAfterAdvice(expression="execution(* com.jrelax.com.jrelax.web.system.service.EmailService.save(..)) ")
public class MailSenderPlugin extends TimerTask implements IPlugin, IPluginAfter{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private Properties props;//邮件参数
	private long period = 0;//检查邮件的周期
	private Timer timer = null;
	@Autowired
	private EmailService emailService;
	@Autowired
	private LogService logService;
	@Autowired
	private NotifyService notifyService;

	public boolean init() {
		//初始化参数
		props = new Properties();
		props.setProperty("mail.smtp.host", JRelaxSystemConfigHelper.get("mail.smtp.host"));
		props.setProperty("mail.smtp.port", JRelaxSystemConfigHelper.get("mail.smtp.port"));
		props.setProperty("mail.smtp.socketFactory.port", JRelaxSystemConfigHelper.get("mail.smtp.socketFactory.port"));
		props.setProperty("mail.smtp.auth", JRelaxSystemConfigHelper.get("mail.smtp.auth"));
		props.setProperty("mail.smtp.socketFactory.class", JRelaxSystemConfigHelper.get("mail.smtp.socketFactory.class"));
		props.setProperty("mail.smtp.socketFactory.fallback", JRelaxSystemConfigHelper.get("mail.smtp.socketFactory.fallback"));
		props.setProperty("mail.smtp.username", JRelaxSystemConfigHelper.get("mail.smtp.username"));
		props.setProperty("mail.smtp.password", JRelaxSystemConfigHelper.get("mail.smtp.password"));
		props.setProperty("mail.period", JRelaxSystemConfigHelper.get("mail.period"));
		if(props.size() > 0){
			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider()); 
			
			timer = new Timer("MailSender");
			period = Long.parseLong(props.getProperty("mail.period"));
			timer.schedule(this, period, period);

			logger.info("启动邮件发送程序。。。");
			return true;
		}
		return false;
	}

	public void destroy() {
		super.cancel();//清除任务
	}

	public void afterReturning(Object returnValue, Method method,
			Object[] params, Object obj) {
		//重置邮件发送休眠时间
		period = Long.parseLong(props.getProperty("mail.period"));
		synchronized (this) {
			notify();//激活线程
		}
	}

	@Override
	public void run() {
		try {
			//查询待发送邮件
			List<Email> list = emailService.list(Condition.NEW().eq("state", 1).desc("weight").asc("createTime"));
			//如果没有待发送邮件，那么处理发送失败的邮件
			if(list.size() == 0){
				emailService.list(Condition.NEW().eq("state", 3).desc("weight").asc("createTime"));
			}
			//如果没有邮件，则延长刷新周期
			if(list.size() == 0){
				synchronized (this) {
					wait(period+=period);
				}
				return;
			}
			final String username = props.getProperty("mail.smtp.username");
			final String password = props.getProperty("mail.smtp.password");
			  
			Session session = Session.getDefaultInstance(props, new Authenticator(){  
			      protected PasswordAuthentication getPasswordAuthentication() {  
			          return new PasswordAuthentication(username, password);  
			      }
		      }); 
			List<User> senders = new ArrayList<User>();//获取所有邮件发送人
			for(Email email : list){
				Message msg = new MimeMessage(session);
				msg.addFrom(InternetAddress.parse(username,false));//发件人
				for(String recipient : email.getRecipients()){//增加收件人
					msg.addRecipients(RecipientType.TO, InternetAddress.parse(recipient,false));
				}
				msg.setSubject(email.getTitle());
				msg.setText(email.getContent());
				msg.setSentDate(DateKit.parse(email.getCreateTime()));
				try {
					//执行发送
					Transport.send(msg);
					
					email.setState(2);
					
					if(!senders.contains(email.getCreateUser())){
						senders.add(email.getCreateUser());
					}
				} catch (Exception e) {
					email.setState(3);//标记为发送失败
				}
				email.setSendTime(DateKit.now());
				emailService.merge(email);
			}
			
			//发送到通知中心
			NotifyMessage notify = new NotifyMessage();
			for(User user : senders){//增加通知接收人
				notify.addRecipient(user);
			}
			notify.setTitle("邮件发送成功!");
			notify.setContent("您的邮件已经全部发送！");
			notify.setLogo("ti-email");
			notify.setLogoBackgroundClass("bg-primary");
			notify.setCreateUser("邮件发送插件");
			notify.setCreateTime(DateKit.now());
			
			notifyService.save(notify);
			
		} catch (Exception e) {
			e.printStackTrace();
			logService.error("邮件发送插件", "邮件发送失败！", Log.DEFAULT_USER, new HandlerRequest(Log.DEFAULT_IP, "", ""));
		}
	}

}
