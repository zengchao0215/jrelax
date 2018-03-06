package com.jrelax.core.plugin;

import java.util.LinkedHashMap;
import java.util.Properties;

/**
 * 插件参数接口
 * Created by zengchao on 2017-03-07.
 */
public interface IPluginProperty {
    /**
     * 设置插件参数
     *
     * @param properties
     */
    void setProperties(Properties properties);

    /**
     * 获取插件参数
     * @return
     */
    Properties getProperties();

    /**
     * 设置插件参数后触发
     */
    void afterPropertiesSet();

    /**
     * 获取插件参数映射
     *
     * @return
     */
    LinkedHashMap<String, String> getPropertyMapping();
}
