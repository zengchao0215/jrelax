package com.jrelax.config;

import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.kit.ObjectKit;
import com.jrelax.core.web.session.SessionAttributeManager;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户配置
 * 基于SessionAttributeManager
 */
public final class JRelaxUserConfigHelper {
    /**
     * 获取系统配置
     *
     * @return
     */
    public static Map<String, String> getConfigMap() {
        Map<String, String> configMap = SessionAttributeManager.get(ApplicationCommon.CACHE_USER_CONFIG);
        if (ObjectKit.isNull(configMap)) {
            configMap = new HashMap<>();
            SessionAttributeManager.put(ApplicationCommon.CACHE_USER_CONFIG, configMap);
        }
        return configMap;
    }

    /**
     * 获取系统配置
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        return getConfigMap().get(key);
    }

    /**
     * 获取系统配置，如不存在则使用默认值
     *
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public static String get(String key, String defaultValue) {
        return getConfigMap().getOrDefault(key, defaultValue);
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.parseBoolean(get(key, defaultValue + ""));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static int getInt(String key, int defaultValue) {
        return Integer.parseInt(get(key, defaultValue + ""));
    }

    /**
     * 设置系统配置，重启失效
     *
     * @param key
     * @param value
     */
    public static void put(String key, String value) {
        getConfigMap().put(key, value);
    }
}
