package com.jrelax.cache;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 数据字典
 */
public interface IDataDict{
	<T> T get(String key);
	
	List<String> getList(String key);
	
	Map<String,String> getMap(String key);

	JSONObject getJson(String key);
	
	void put(String key,Object data);

	/**
	 * 移除数据
	 * @param key
	 */
	void remove(String key);

	boolean sync();

	boolean has(String key);
}
