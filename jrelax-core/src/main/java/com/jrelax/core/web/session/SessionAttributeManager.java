package com.jrelax.core.web.session;

import com.jrelax.cache.ICacheProvider;
import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.kit.ObjectKit;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于Session的存储
 * 基于系统缓存来实现
 * Created by Administrator on 2016-09-22.
 */
public class SessionAttributeManager {
    private static String KEY = "SESSION_ATTRIBUTE_";

    private static String getSessionId() {
        HttpSession session = WebApplicationCommon.getSession();
        if (session == null) throw new RuntimeException("No Session");
        return session.getId();
    }

    private static String getKey(String key) {
        String sid = getSessionId();
        return KEY + sid + "_" + key;
    }

    private static ICacheProvider getCacheProvider() {
        return ApplicationCommon.getCacheProvider();
    }

    /**
     * 增加
     *
     * @param key 键
     * @param value 值
     */
    public static void put(String key, Object value) {
        getCacheProvider().put(getKey(key), value);
    }

    /**
     * 增加
     * @param key 键
     * @param value 值
     * @param expireTime 过期时间，单位：毫秒
     */
    public static void put(String key, Object value, long expireTime){
        getCacheProvider().put(getKey(key), value, expireTime);
    }

    /**
     * 移除
     *
     * @param key 键
     */
    public static void remove(String key) {
        getCacheProvider().remove(getKey(key));
    }

    /**
     * 清空s所有
     */
    public static void clearAll() {
        getCacheProvider().removeByPrefix(KEY);
    }

    /**
     * 清空当前的session值
     *
     */
    public static void clear() {
        getCacheProvider().removeByPrefix(KEY + getSessionId());
    }

    /**
     * 清空指定session的值
     * @param sessionId Session ID
     */
    public static void clear(String sessionId) {
        getCacheProvider().removeByPrefix(KEY + sessionId);
    }

    /**
     * 是否存在
     *
     * @param key
     * @return
     */
    public static boolean has(String key) {
        return getCacheProvider().has(getKey(key));
    }

    /**
     * 获取
     *
     * @param key
     * @param <M>
     * @return
     */
    public static <M> M get(String key) {
        return getCacheProvider().get(getKey(key));
    }


}
