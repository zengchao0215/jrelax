package com.jrelax.cache.provider;

import com.jrelax.cache.ICacheProvider;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * 默认系统缓存类
 */
public class DefaultCacheProvider implements ICacheProvider {
    private Map<String, Long> expireMap = new HashMap<>();
    private Map<String, Long> timeMap = new HashMap<>();
    private Map<String, Object> data = new HashMap<>();

    public <T> T get(String key) {
        //判断是否过期
        T t = (T) data.get(key);
        if(t != null){
            //判断是否过期
            Long expireTime = expireMap.get(key);
            Long lastTime = timeMap.get(key);

            if(expireTime != null && lastTime != null){
                if(expireTime != -1){//-1表示无限时间
                    if(System.currentTimeMillis() - lastTime > expireTime){//过期
                        t = null;
                        remove(key);
                    }
                }
            }
        }
        return t;
    }

    public void put(String key, Object value) {
        put(key, value, -1);
    }

    @Override
    public void put(String key, Object obj, long expireTime) {
        expireMap.put(key, expireTime);
        timeMap.put(key, System.currentTimeMillis());
        data.put(key, obj);
    }

    @Override
    public boolean has(String key) {
        return data.containsKey(key);
    }

    public void remove(String key) {
        data.remove(key);
        expireMap.remove(key);
        timeMap.remove(key);
    }

    public void clear() {
        data.clear();
        expireMap.clear();
        timeMap.clear();
    }

    @Override
    public Set<String> keys() {
        return data.keySet();
    }

    @Override
    public void removeByPrefix(String prefix) {
        Set<String> keys = this.keys();
        Set<String> removeKeys = new HashSet<>();
        for(String key : keys){
            if(key.startsWith(prefix)){
                removeKeys.add(key);
            }
        }

        //删除
        for(String k : removeKeys){
            remove(k);
        }
    }
}
