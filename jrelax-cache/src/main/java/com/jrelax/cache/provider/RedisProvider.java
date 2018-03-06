package com.jrelax.cache.provider;

import com.jrelax.cache.ICacheProvider;
import com.jrelax.cache.redis.JRelaxRedisManager;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 系统缓存 Redis版本
 * Created by zengc on 2017-01-03.
 */
public class RedisProvider implements ICacheProvider {
    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) getValueStore().get(key);
    }

    @Override
    public void put(String key, Object obj) {
        getValueStore().set(key, obj);
    }

    @Override
    public void put(String key, Object obj, long expireTime) {
        getValueStore().set(key, obj, expireTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public void remove(String key) {
        getTemplate().delete(key);
    }

    @Override
    public boolean has(String key) {
        return getValueStore().get(key) != null;
    }

    @Override
    public void clear() {
        getTemplate().execute((RedisCallback<Object>) connection -> {
            connection.flushDb();
            return "ok";
        });
    }

    @Override
    public Set<String> keys() {
        return getTemplate().keys("*");
    }

    @Override
    public void removeByPrefix(String prefix) {
        Set<String> keys = getTemplate().keys(prefix + "*");
        for(String key : keys){
            if(key.startsWith(prefix)){
                remove(key);
            }
        }
    }

    private ValueOperations<String, Object> getValueStore(){
        return JRelaxRedisManager.getValueStore();
    }

    private RedisTemplate<String, Object> getTemplate(){
        return JRelaxRedisManager.getTemplate();
    }
}
