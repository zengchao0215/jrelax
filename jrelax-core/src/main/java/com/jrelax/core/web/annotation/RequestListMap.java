package com.jrelax.core.web.annotation;

import java.lang.annotation.*;

/**
 * List<Map>解析，用于多行相同数据映射成Map的情况
 * Created by zengchao on 2017-02-17.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestListMap {
    /**
     * 排除空值
     * @return
     */
    boolean ignoreEmpty() default false;
}
