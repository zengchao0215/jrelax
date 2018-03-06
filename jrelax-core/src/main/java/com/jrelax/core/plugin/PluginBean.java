package com.jrelax.core.plugin;

import com.jrelax.core.plugin.annotation.Plugin;
import com.jrelax.core.plugin.annotation.PluginBeforeAdvice;
import org.springframework.aop.support.AopUtils;

/**
 * 插件Bean
 * Created by zengchao on 2017-03-07.
 */
public class PluginBean {
    private IPlugin bean = null;
    private boolean hasBeforeAdvice = false;
    private boolean hasAfterAdvice = false;
    private boolean hasProperty = false;
    private String name = "";//插件名称
    private String group = "";//插件组
    private String className = "";//类名
    private boolean loadOnStartup = false;//启动机加载

    public PluginBean(IPlugin bean) {
        if(AopUtils.isAopProxy(bean)){
            init(AopUtils.getTargetClass(bean));
        }else{
            init(bean.getClass());
        }
        this.bean = bean;
    }

    public PluginBean(Class<?> cls){
        init(cls);
    }

    private void init(Class<?> cls){
        Plugin plugin = cls.getAnnotation(Plugin.class);
        if(plugin != null){
            this.group = plugin.group();
            this.name = plugin.value();
            this.className = cls.getName();
            this.loadOnStartup = plugin.loadOnStartup();
            this.hasBeforeAdvice = IPluginBefore.class.isAssignableFrom(cls);
            this.hasAfterAdvice = IPluginAfter.class.isAssignableFrom(cls);
            this.hasProperty = IPluginProperty.class.isAssignableFrom(cls);
        }else{
            throw new RuntimeException("插件不可用");
        }
    }

    public IPlugin getBean() {
        return bean;
    }

    public void setBean(IPlugin bean) {
        this.bean = bean;
    }

    public boolean hasBeforeAdvice() {
        return hasBeforeAdvice;
    }

    public void setHasBeforeAdvice(boolean hasBeforeAdvice) {
        this.hasBeforeAdvice = hasBeforeAdvice;
    }

    public boolean hasAfterAdvice() {
        return hasAfterAdvice;
    }

    public void setHasAfterAdvice(boolean hasAfterAdvice) {
        this.hasAfterAdvice = hasAfterAdvice;
    }

    public boolean hasProperty() {
        return hasProperty;
    }

    public void setHasProperty(boolean hasProperty) {
        this.hasProperty = hasProperty;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public boolean isLoadOnStartup() {
        return loadOnStartup;
    }

    public void setLoadOnStartup(boolean loadOnStartup) {
        this.loadOnStartup = loadOnStartup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
