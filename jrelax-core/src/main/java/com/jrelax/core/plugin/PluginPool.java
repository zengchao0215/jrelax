package com.jrelax.core.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginPool {
    private PluginPool() {

    }

    private static Map<String, PluginBean> plugins = new HashMap<String, PluginBean>();

    /**
     * 获取所有已加载的插件
     *
     * @return
     */
    public static Map<String, PluginBean> getAllPlugins() {
        return plugins;
    }

    /**
     * 根据插件名称获取插件
     *
     * @param clazz 类名
     * @return
     */
    public static PluginBean getPlugin(String clazz) {
        return plugins.get(clazz);
    }

    /**
     * 根据插件类获取插件
     *
     * @param clazz
     * @return
     */
    public static PluginBean getPlugin(Class<?> clazz) {
        PluginBean pluginBean = null;
        for (PluginBean p : plugins.values()) {
            if (p.getBean().getClass().equals(clazz)) {
                pluginBean = p;
                break;
            }
        }
        return pluginBean;
    }

    /**
     * 增加插件
     *
     * @param clazz 类名称
     * @param pluginBean
     */
    public static void addPlugin(String clazz, PluginBean pluginBean) {
        plugins.put(clazz, pluginBean);
    }

    /**
     * 根据插件组别获取插件列表
     * @param group
     * @return
     */
    public static List<PluginBean> getPluginByGroup(String group){
        List<PluginBean> list = new ArrayList<>();
        for (PluginBean p : plugins.values()) {
            if (p.getGroup().equals(group)) {
               list.add(p);
            }
        }
        return list;
    }
}
