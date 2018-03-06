package com.jrelax.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.core.support.ApplicationContextHelper;
import com.jrelax.kit.ObjectKit;

/**
 * 系统配置帮助类
 * 配置存在本地配置文件中
 */
public final class JRelaxCoreConfigHelper {
	private static Properties cacheProperties = null;
	public static InputStream getInputstream(){
		InputStream in = null;
		try {
			in = ApplicationContextHelper.getInstance().getApplicationContext().getResource("classpath:core-config.properties").getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return in;
	}
	
	/**
	 * 刷新缓存
	 * @return
	 */
	public static Properties reload(){
		return getProperties();
	}
	/**
	 * 获取所有配置项
	 * @return
	 */
	public static Properties getProperties(){
		Properties prop = new Properties();
		try {
			InputStream is = getInputstream();
			prop.load(is);
			is.close();
			
			cacheProperties = prop;
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
	public static String getProperty(String key){
		if(ObjectKit.isNull(cacheProperties) || ApplicationCommon.DEBUG)
			getProperties();
		
		return cacheProperties.getProperty(key);
	}

	/**
	 * 获取配置只
	 * @param key 键
	 * @param defaultValue 默认值
	 * @return
	 */
	public static String getProperty(String key, String defaultValue){
		String val = getProperty(key);
		return val == null ? defaultValue : val;
	}
	
	/**
	 * 获取配置组
	 * 匹配前缀， 如：mail.
	 * @param group
	 * @return
	 */
	public static Properties getPropertyGroup(String group){
		Properties allProp = null;
		if (cacheProperties == null || ApplicationCommon.DEBUG)
			allProp = getProperties();
		else
			allProp = cacheProperties;
		Properties prop = new Properties();
		Enumeration<?> keys = allProp.keys();
		while(keys.hasMoreElements()){
			String key = keys.nextElement().toString();
			if(key.startsWith(group)){
				prop.put(key, allProp.get(key));
			}
		}
		return prop;
	}
}
