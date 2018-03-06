package com.jrelax.event.annotation;

import java.lang.annotation.*;

/**
 * 系统事件注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ApplicationEvent {
    String value(); //事件名称
}
