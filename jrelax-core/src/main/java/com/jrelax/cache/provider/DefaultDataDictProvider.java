package com.jrelax.cache.provider;

import com.jrelax.cache.IDataDict;
import com.jrelax.core.support.ApplicationCommon;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 数据字典默认实现类
 * @author zengchao
 *
 */
public class DefaultDataDictProvider implements IDataDict {
	private Map<String,Object> data = new HashMap<>();
	private boolean isLoaded = false;
	
	public Object get(String key) {
		if(!isLoaded || ApplicationCommon.DEBUG)
			this.sync();
		return data.get(key);
	}

	public synchronized boolean sync() {
		isLoaded = true;
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<String> getList(String key) {
		if(!isLoaded)
			this.sync();
		return (List<String>) data.get(key);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getMap(String key) {
		if(!isLoaded)
			this.sync();
		return (Map<String, String>) data.get(key);
	}

	public JSONObject getJson(String key) {
		return JSONObject.fromObject(getMap(key));
	}

	public void put(String key, Object data) {
		if(!isLoaded)
			this.sync();
	}

	@Override
	public boolean has(String key) {
		return data.containsKey(key);
	}

	public void remove(String key) {
		data.remove(key);
	}

	public void clear() {
		data.clear();
	}
}
