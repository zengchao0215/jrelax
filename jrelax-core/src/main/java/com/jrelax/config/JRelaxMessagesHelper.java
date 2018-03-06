package com.jrelax.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.core.support.ApplicationContextHelper;
import com.jrelax.kit.ObjectKit;

/**
 * 国际化文件读取工具
 * @author zengchao
 *
 */
public class JRelaxMessagesHelper {
	private static Map<String, Properties> messages = new HashMap<String, Properties>();

	public static File getFile(String locale){
		File file = null;
		try {
			file = ApplicationContextHelper.getInstance().getApplicationContext().getResource("classpath:lang/messages_"+locale+".properties").getFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	/**
	 * 获取所有配置项
	 * @return
	 */
	public static Properties getProperties(Locale locale){
		Properties prop = new Properties();
		try {
			File file = getFile(locale.getLanguage()+"_"+locale.getCountry());
			InputStream is = new FileInputStream(file);
			prop.load(is);
			is.close();
			
			messages.put(locale.getLanguage(), prop);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	/**
	 * 获取配置值
	 * @param key
	 * @return
	 */
	public static String getProperty(Locale locale, String key){
		if(ApplicationCommon.DEBUG) getProperties(locale);
		if(ObjectKit.isNull(messages.get(locale.getLanguage())))
			messages.put(locale.getLanguage(), getProperties(locale));
		
		return messages.get(locale.getLanguage()).getProperty(key);
	}
	
	/**
	 * 获取当前的国际化语言
	 * @return
	 */
	public static Locale getLocale(HttpSession session){
		Locale locale = (Locale)session.getAttribute(ApplicationCommon.SESSION_LOCALE);
		if(ObjectKit.isNull(locale))
			locale = Locale.getDefault();
		return locale;
	}
}
