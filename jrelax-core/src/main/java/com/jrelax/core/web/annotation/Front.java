package com.jrelax.core.web.annotation;

import java.lang.annotation.*;

/**
 * 标记为前端访问，用于区分后端
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Front {
}
