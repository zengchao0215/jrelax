package com.jrelax.cache;

import java.util.Set;

public interface ICacheProvider {
	/**
	 * 根据key获取数据字典中的数据
	 * @param key
	 * @return
	 */
	<T> T get(String key);
	/**
	 * 存放数据
	 * @param key
	 * @param obj
	 */
	void put(String key,Object obj);

	/**
	 *
	 * @param key 键
	 * @param obj 值
	 * @param expireTime 过期时间，单位毫秒
	 */
	void put(String key, Object obj, long expireTime);

	/**
	 * 是否存在
	 * @param key
	 * @return
	 */
	boolean has(String key);

	/**
	 * 移除数据
	 * @param key
	 */
	void remove(String key);

	/**
	 * 根据前缀来移除
	 * @param prefix
	 */
	void removeByPrefix(String prefix);

	/**
	 * 清空缓存
	 */
	void clear();

	/**
	 * 获取缓存所有键
	 * @return
	 */
	Set keys();
}
