package com.jrelax.cache.provider;

import com.jrelax.cache.ICacheProvider;
import com.jrelax.cache.memcached.JRelaxMemCachedManager;
import com.jrelax.kit.DateKit;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * 系统缓存，MemCached版
 * Created by zengc on 2017-01-03.
 */
public class MemCachedProvider implements ICacheProvider {
    private Set<String> keys = new HashSet<>();

    @Override
    public <T> T get(String key) {
        return (T) JRelaxMemCachedManager.getClient().get(key);
    }

    @Override
    public void put(String key, Object obj) {
        JRelaxMemCachedManager.getClient().set(key, obj);
        keys.add(key);
    }

    @Override
    public void put(String key, Object obj, long expireTime) {
        JRelaxMemCachedManager.getClient().add(key, obj, DateKit.calcOfMilliSecond(new Date(), Integer.parseInt(expireTime + "")));
        keys.add(key);
    }

    @Override
    public boolean has(String key) {
        return JRelaxMemCachedManager.getClient().get(key) != null;
    }

    @Override
    public void remove(String key) {
        JRelaxMemCachedManager.getClient().delete(key);
        keys.remove(key);
    }

    @Override
    public void clear() {
        JRelaxMemCachedManager.getClient().flushAll();
        keys.clear();
    }

    @Override
    public Set<String> keys() {
        //如果是空的，则从服务器获取
        if (keys.isEmpty()) {
            //遍历statsItems 获取items:2:number=14
            Map<String, Map<String, String>> statsItems = JRelaxMemCachedManager.getClient().statsItems();
            Map<String, String> statsItems_sub = null;
            String statsItems_sub_key = null;
            int items_number = 0;
            String server = null;
            //根据items:2:number=14，调用statsCacheDump，获取每个item中的key
            Map<String, Map<String, String>> statsCacheDump = null;
            Map<String, String> statsCacheDump_sub = null;
            String statsCacheDumpsub_key = null;
            for (Iterator iterator = statsItems.keySet().iterator(); iterator.hasNext(); ) {
                server = (String) iterator.next();
                statsItems_sub = statsItems.get(server);
                for (Iterator iterator_item = statsItems_sub.keySet().iterator(); iterator_item.hasNext(); ) {
                    statsItems_sub_key = (String) iterator_item.next();
                    //items:2:number=14
                    if (statsItems_sub_key.toUpperCase().startsWith("items:".toUpperCase()) && statsItems_sub_key.toUpperCase().endsWith(":number".toUpperCase())) {
                        items_number = Integer.parseInt(statsItems_sub.get(statsItems_sub_key).trim());
                        statsCacheDump = JRelaxMemCachedManager.getClient().statsCacheDump(new String[]{server}, Integer.parseInt(statsItems_sub_key.split(":")[1].trim()), items_number);

                        for (Iterator statsCacheDump_iterator = statsCacheDump.keySet().iterator(); statsCacheDump_iterator.hasNext(); ) {
                            statsCacheDump_sub = statsCacheDump.get(statsCacheDump_iterator.next());
                            for (Iterator iterator_keys = statsCacheDump_sub.keySet().iterator(); iterator_keys.hasNext(); ) {
                                statsCacheDumpsub_key = (String) iterator_keys.next();
                                try {
                                    keys.add(URLDecoder.decode(statsCacheDumpsub_key, "UTF-8"));
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                }
            }
        }
        return keys;
    }

    @Override
    public void removeByPrefix(String prefix) {
        Set<String> keys = this.keys();
        for (String key : keys) {
            if (key.startsWith(prefix)) {
                remove(key);
            }
        }
    }
}
