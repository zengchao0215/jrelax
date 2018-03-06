package com.jrelax.core.plugin;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/6/30.
 */
public interface IPluginAfter {
    /**
     * 拦截方法 - after
     * @param returnValue
     * @param method
     * @param params
     * @param obj
     */
    void afterReturning(Object returnValue, Method method, Object[] params, Object obj);
}
