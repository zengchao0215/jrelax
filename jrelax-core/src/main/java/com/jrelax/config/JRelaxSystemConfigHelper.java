package com.jrelax.config;

import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.kit.ObjectKit;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统配置获取
 * 配置存储在相应的数据库中
 * Created by zengchao on 2016-11-11.
 */
public final class JRelaxSystemConfigHelper {
    /**
     * 获取系统配置
     * @return
     */
    public static Map<String, String> getConfigMap(){
        Map<String, String> configMap = ApplicationCommon.getCacheProvider().get(ApplicationCommon.CACHE_SYSTEM_CONFIG);
        if(ObjectKit.isNull(configMap)) {
            configMap = new HashMap<>();
            ApplicationCommon.getCacheProvider().put(ApplicationCommon.CACHE_SYSTEM_CONFIG, configMap);
        }
        return configMap;
    }
    /**
     * 获取系统配置
     * @param key
     * @return
     */
    public static String get(String key){
        return getConfigMap().get(key);
    }

    /**
     * 获取系统配置，如不存在则使用默认值
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public static String get(String key, String defaultValue){
        return getConfigMap().getOrDefault(key, defaultValue);
    }

    public static boolean getBoolean(String key){
        return Boolean.parseBoolean(get(key));
    }

    public static boolean getBoolean(String key, boolean defaultValue){
        return Boolean.parseBoolean(get(key, defaultValue+""));
    }

    public static int getInt(String key){
        return Integer.parseInt(get(key));
    }

    public static int getInt(String key, int defaultValue){
        return Integer.parseInt(get(key, defaultValue+""));
    }

    /**
     * 设置系统配置，重启失效
     * @param key
     * @param value
     */
    public static void put(String key, String value){
        getConfigMap().put(key, value);
    }
}
