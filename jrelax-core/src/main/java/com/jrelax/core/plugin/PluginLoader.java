package com.jrelax.core.plugin;

import com.jrelax.core.support.ApplicationContextHelper;
import com.jrelax.kit.ObjectKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 插件加载器
 *
 * @author zengchao
 */
public class PluginLoader {
    private static Logger logger = LoggerFactory.getLogger(PluginLoader.class);

    /**
     * @param name  插件名称
     * @param clazz 插件类
     * @return
     */
    public static Object load(String name, Class<?> clazz) throws Exception {
        try {
            Object object = ApplicationContextHelper.getInstance().getApplicationContext().getAutowireCapableBeanFactory().createBean(clazz);

            //调用插件的init方法对插件进行初始化
            if (ObjectKit.isNotNull(object)) {
                IPlugin plugin = (IPlugin) object;
                if (plugin.init()) {
                    return plugin;
                }
            }
            return null;
        } catch (Exception e) {
            logger.error("Plugin Load Failed(" + name + ") : " + clazz.getName(), e);
            throw new Exception("Plugin Load Failed(" + name + ") : " + clazz.getName());
        }
    }
}
