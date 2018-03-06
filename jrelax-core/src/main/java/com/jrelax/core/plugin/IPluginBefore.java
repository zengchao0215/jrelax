package com.jrelax.core.plugin;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/6/30.
 */
public interface IPluginBefore {
    /**
     * 拦截方法 - before
     * @param method
     * @param params
     * @param obj
     */
    void before(Method method, Object[] params, Object obj);
}
