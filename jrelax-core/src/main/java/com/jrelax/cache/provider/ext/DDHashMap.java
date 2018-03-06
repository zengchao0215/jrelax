package com.jrelax.cache.provider.ext;

import java.util.LinkedHashMap;

/**
 * Created by zengc on 2016/7/19.
 */
public class DDHashMap<K,V> extends LinkedHashMap<K,V> {
    @Override
    public V get(Object key) {
        return super.get(key + "");
    }

}
