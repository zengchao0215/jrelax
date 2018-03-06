package com.jrelax.plugins.sms;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.jrelax.core.plugin.IPluginAfter;
import com.jrelax.core.web.support.http.HandlerRequest;
import com.jrelax.kit.DateKit;
import com.jrelax.kit.HttpKit;
import com.jrelax.orm.query.Condition;
import com.jrelax.web.system.entity.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jrelax.core.plugin.IPlugin;
import com.jrelax.core.plugin.annotation.Plugin;
import com.jrelax.core.plugin.annotation.PluginAfterAdvice;
import com.jrelax.web.system.entity.SMS;
import com.jrelax.web.system.service.ConfigService;
import com.jrelax.web.system.service.LogService;
import com.jrelax.web.system.service.SMSService;

@Plugin(value="短信发送插件", group = "通知", loadOnStartup=false)
@PluginAfterAdvice(expression="execution(* com.jrelax.com.jrelax.web.system.service.SMSService.save(..)) ")
public class SMSSenderPlugin extends TimerTask implements IPlugin, IPluginAfter{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private Map<String, String> props;
	private Timer timer = null;
	@Autowired
	private SMSService smsService;
	@Autowired
	private LogService logService;
	@Autowired
	private ConfigService configService;
	
	public boolean init() {
		//初始化参数
		try {
			props = configService.getMapByGroup("sms.");
			timer = new Timer("SMSSender");
			timer.schedule(this, 0, 1000*60*30);
			
			logger.info("启动短信发送程序。。。");
		} catch (Exception e) {
			logService.error("短信发送插件", "短信发送插件初始化失败！", Log.DEFAULT_USER, new HandlerRequest(Log.DEFAULT_IP, "", ""));
		}
					
		return true;
	}

	public void destroy() {
		super.cancel();//清除任务
	}

	public void afterReturning(Object returnValue, Method method,
			Object[] params, Object obj) {
		SMS sms = new SMS();
		for(int i=0;i<params.length;i++){
			if(i==0){
				sms = (SMS)params[i];
			}
		}
		String url = props.get("sms.url");
		String requestMethod = props.get("sms.method").toUpperCase();
		String contentField = props.get("sms.field.content");
		String mobileField = props.get("sms.field.mobile");
		String encoding = props.get("sms.encoding");
		String requestParams = "";
		int idx = url.indexOf("?");
		if(idx >=0){
			requestParams = url.substring(idx+1);
			url = url.substring(0, idx);
		}
		try {
			requestParams += mobileField+"="+sms.getReceiver()+"&"+contentField+"="+sms.getContent();

			//发送请求
			String response = HttpKit.send(url, requestMethod, requestParams);
			sms.setState(2);
			sms.setRemarks(response);
		} catch (Exception e) {
			e.printStackTrace();
			sms.setState(3);//标记为发送失败
		}
		sms.setSendTime(DateKit.now());
		smsService.merge(sms);
	}

	@Override
	public void run() {
		try {
			List<SMS> smsList = smsService.list(Condition.NEW().eq("state", 1).desc("weight").asc("createTime"));
			if(smsList.size() == 0){
				smsList = smsService.list(Condition.NEW().eq("state", 3).desc("weight").asc("createTime"));
			}
			if(smsList.size() == 0){
				return ;
			}
			String url = props.get("sms.url");
			String requestMethod = props.get("sms.method").toUpperCase();
			String contentField = props.get("sms.field.content");
			String mobileField = props.get("sms.field.mobile");
			String encoding = props.get("sms.encoding");
			String requestParams = "";
			int idx = url.indexOf("?");
			if(idx >=0){
				requestParams = url.substring(idx+1);
				url = url.substring(0, idx);
			}
			
			for(SMS sms:smsList){
				try {
					requestParams += mobileField+"="+sms.getReceiver()+"&"+contentField+"="+sms.getContent();

					//发送请求
					String response = HttpKit.send(url, requestMethod, requestParams);
					sms.setState(2);
					sms.setRemarks(response);

				} catch (Exception e) {
					e.printStackTrace();
					sms.setState(3);//标记为发送失败
				}
				sms.setSendTime(DateKit.now());
				smsService.merge(sms);
			}

		} catch (Exception e) {
			logService.error("短信发送插件", "短信发送失败！", Log.DEFAULT_USER, new HandlerRequest(Log.DEFAULT_IP, "", ""));
		}
		
	}
}
