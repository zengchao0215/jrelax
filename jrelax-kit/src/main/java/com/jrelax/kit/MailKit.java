package com.jrelax.kit;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

/**
 * 邮件工具类
 * @author zengchao
 *
 */
public class MailKit {
	/**
	 * 获取邮件数量
	 * @param type 收件服务器类型 pop imap
	 * @param host 收件服务器地址
	 * @param port 端口
	 * @param user 用户名
	 * @param pass 密码
	 * @param ssl 是否使用SSL
	 * @return
	 * @throws MessagingException 
	 */
	public static int getMessageCount(String type, String host, int port, String user, String pass, boolean ssl) throws MessagingException{
		int result = -1;
		if("pop".equals(type)){
			
			
			Store store = getStore(type, host, port, user, pass, ssl);
			
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);
			result = inbox.getMessageCount();
			
			inbox.close(true);
			store.close();
		}else if("imap".equals(type)){
			Store store = getStore(type, host, port, user, pass, ssl);
			
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_WRITE);
			result = inbox.getMessageCount();
			
			inbox.close(true);
			store.close();
		}
		return result;
	}
	
	public static Store getStore(String type, String host, int port, String user, String pass, boolean ssl) throws MessagingException{
		if("pop".equals(type)){
			Properties props = new Properties();
			props.put("mail.store.protocol", "pop3");
			props.put("mail.pop3.port", port);
			props.put("mail.pop3.host", host);
			
			Session session = Session.getInstance(props);
			
			Store store = session.getStore("pop3");
			store.connect(user, pass);
			
			return store;
		}else if("imap".equals(type)){
			Properties props = new Properties();
			props.put("mail.store.protocol", "imap");
			props.put("mail.imap.port", port);
			props.put("mail.imap.host", host);
			if(ssl){
				//props.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.setProperty("mail.imap.useStartTLS", "true");
			}
			
			Session session = Session.getInstance(props);
			Store store = session.getStore("imap");
			store.connect(user, pass);
			
			return store;
		}
		
		return null;
	}
	
	public static List<String[]> getMessageList(String type, String host, int port, String user, String pass, boolean ssl) throws MessagingException{
		List<String[]> list = new ArrayList<String[]>();
		
		Store store = getStore(type, host, port, user, pass, ssl);
		
		Folder inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_WRITE);
		int size = inbox.getMessageCount()>15?15:inbox.getMessageCount();
		Message[] messages = inbox.getMessages(1, size);
		for(Message msg : messages){
			String[] info = new String[4];
			info[0] = ((InternetAddress)msg.getFrom()[0]).getPersonal();
			info[1] = ((InternetAddress)msg.getFrom()[0]).getAddress();
			info[2] = msg.getSubject();
			info[3] = DateKit.format(msg.getReceivedDate());
			list.add(info);
		}
		
		inbox.close(true);
		store.close();
		
		
		return list;
	}
}
