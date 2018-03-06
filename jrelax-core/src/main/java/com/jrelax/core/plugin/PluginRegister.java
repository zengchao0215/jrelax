package com.jrelax.core.plugin;

import com.jrelax.core.plugin.annotation.Plugin;
import com.jrelax.core.plugin.annotation.PluginAfterAdvice;
import com.jrelax.core.plugin.annotation.PluginBeforeAdvice;
import com.jrelax.core.support.ApplicationContextHelper;
import com.jrelax.kit.ClassKit;
import com.jrelax.kit.ObjectKit;
import com.jrelax.kit.StringKit;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.*;

/**
 * 插件注册类
 *
 * @author zengchao
 */
public class PluginRegister implements Comparator<Class<?>> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void init() {
        Set<Class<?>> classes = getAllPluginClasses();

        //排序
        List<Class<?>> sortedClasses = sort(classes);
        for (Class<?> cls : sortedClasses) {
            if (PluginConfig.getInstance().isLoadOnStartUp(cls))
                register(cls);
        }
        logger.info("当前系统安装插件数：" + PluginPool.getAllPlugins().size());
    }

    /**
     * 排序
     *
     * @param classes
     */
    public List<Class<?>> sort(Set<Class<?>> classes) {
        List<Class<?>> list = new ArrayList<>(classes);
        list.sort(this);
        return list;
    }

    /**
     * 排序
     *
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(Class<?> o1, Class<?> o2) {
        Integer order1 = PluginConfig.getInstance().getOrder(o1);
        Integer order2 = PluginConfig.getInstance().getOrder(o2);
        return order1.compareTo(order2);
    }

    /**
     * 获取目录中的所有插件
     * 包含系统加载和系统未加载的
     *
     * @return
     */
    public Set<Class<?>> getAllPluginClasses() {
        String pack = this.getClass().getPackage().getName().replace(".core.plugin", "");
        return ClassKit.getClassesByAnnotation(pack, Plugin.class);
    }

    /**
     * 注销
     */
    public void destroy() {
        Map<String, PluginBean> map = PluginPool.getAllPlugins();
        for (String s : map.keySet()) {
            unregister(map.get(s).getBean());
        }
    }

    /**
     * 插件加载器
     *
     * @param clazz
     * @return
     */
    public boolean register(Class<?> clazz) {
        Plugin plu = clazz.getAnnotation(Plugin.class);
        if (StringKit.isEmpty(plu.value())) {
            logger.error("插件未加载:插件名称不可为空");
            return false;
        }
        //判断插件池中是否有相同的插件,如已存在，不重新加载
        if (ObjectKit.isNotNull(PluginPool.getPlugin(clazz.getName()))) {
            logger.error("[" + clazz.getName() + "] 插件系统中已存在，请勿重复注册!");
            return false;
        }

        if (!IPlugin.class.isAssignableFrom(clazz)) {
            logger.error(plu.value() + "插件未加载:必须实现" + IPlugin.class.getName() + "接口!");
            return false;
        }
        PluginBean pluginBean = null;
        try {
            Object object = PluginLoader.load(plu.value(), clazz);
            IPlugin plugin = (IPlugin) object;
            if (ObjectKit.isNotNull(plugin)) {
                pluginBean = new PluginBean(clazz);
                pluginBean.setBean(plugin);
                pluginBean.setLoadOnStartup(PluginConfig.getInstance().isLoadOnStartUp(clazz));
                PluginPool.addPlugin(pluginBean.getClassName(), pluginBean);
            } else {
                logger.error(plu.value() + "插件未加载:执行初始化方法返回false!");
                return false;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        //设置属性
        Properties properties = PluginConfig.getInstance().getProperties(clazz.getName());
        if (properties != null && pluginBean.hasProperty()) {
            IPluginProperty pluginProperty = (IPluginProperty) pluginBean.getBean();
            pluginProperty.setProperties(properties);
            pluginProperty.afterPropertiesSet();
        }

        //拦截规则  - before
        PluginBeforeAdvice beforeAdvice = clazz.getAnnotation(PluginBeforeAdvice.class);
        if (ObjectKit.isNotNull(beforeAdvice) && pluginBean.hasBeforeAdvice()) {
            if (!StringKit.isEmpty(beforeAdvice.expression())) {
                PluginAdvice pAdvice = ApplicationContextHelper.getInstance().getBean(PluginAdvice.class);
                pAdvice.addBeforeAdvices((IPluginBefore) pluginBean.getBean(), beforeAdvice.expression());
            }
        }
        //拦截规则  - after
        PluginAfterAdvice afterAdvice = clazz.getAnnotation(PluginAfterAdvice.class);
        if (ObjectKit.isNotNull(afterAdvice) && pluginBean.hasAfterAdvice()) {
            if (!StringKit.isEmpty(afterAdvice.expression())) {
                PluginAdvice pAdvice = ApplicationContextHelper.getInstance().getBean(PluginAdvice.class);
                pAdvice.addAfterAdvices((IPluginAfter) pluginBean.getBean(), afterAdvice.expression());
            }
        }
        logger.info("Plugin Registered : {name:" + plu.value() + ",class:" + clazz.getName() + "}");
        return true;
    }

    /**
     * 插件解注册
     *
     * @param plugin 插件
     * @return
     */
    public boolean unregister(IPlugin plugin) {
        if (ObjectKit.isNotNull(plugin)) {
            try {
                plugin.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //移除拦截
            PluginAdvice pAdvice = ApplicationContextHelper.getInstance().getBean(PluginAdvice.class);
            pAdvice.removeAfterAdvices(plugin);
            pAdvice.removeBeforeAdvices(plugin);
            String name = plugin.getClass().getName();
            if (AopUtils.isAopProxy(plugin)) {
                name = AopUtils.getTargetClass(plugin).getName();
            }
            //从插件池中移除
            PluginPool.getAllPlugins().remove(name);
            logger.info("Plugin UnRegistered : {class:" + name + "}");
        }
        return true;
    }

    /**
     * 插件解注册
     *
     * @param cls
     * @return
     */
    public boolean unregister(Class<?> cls) {
        return !ObjectKit.isNotNull(cls) || unregister(PluginPool.getPlugin(cls).getBean());
    }

}
