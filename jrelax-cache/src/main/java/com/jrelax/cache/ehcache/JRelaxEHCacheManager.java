package com.jrelax.cache.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.Configuration;

/**
 * EHCache管理器
 * Created by zengchao on 2016-11-21.
 */
public class JRelaxEHCacheManager {
    private static JRelaxEHCacheManager instance;
    private CacheManager cacheManager;
    private Cache cache;
    private final String DEFAULT_CACHE_NAME = "JRELAX_EHCACHE";

    private void init() {
        cacheManager = new CacheManager();
        cacheManager.addCache(DEFAULT_CACHE_NAME);
        cache = cacheManager.getCache(DEFAULT_CACHE_NAME);
    }

    /**
     * 获取缓存模板
     * @return
     */
    public static Cache getTemplate(){
        if(instance == null){
            instance = new JRelaxEHCacheManager();
            instance.init();
        }
        return instance.cache;
    }

    /**
     * 获取缓存
     * @param key
     * @return
     */
    public Element get(String key){
        return this.cache.get(key);
    }

    //添加缓存
    public void add(String key, Object value){
        Element element = new Element(key, value);
        this.cache.put(element);
    }
}
