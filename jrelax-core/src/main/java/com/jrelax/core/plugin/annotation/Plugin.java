package com.jrelax.core.plugin.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 插件注解类
 * 2017-01-05 增加group
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Plugin {
    String value();

    String group() default "";

    /**
     * 系统系统时加载
     *
     * @return
     */
    boolean loadOnStartup() default false;

    /**
     * 排序，值越小 越优先
     *
     * @return
     */
    int order() default 99;//排序
}
