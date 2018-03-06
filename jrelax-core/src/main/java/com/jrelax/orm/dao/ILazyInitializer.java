package com.jrelax.orm.dao;

/**
 * 对于延迟加载属性，需要在controller层立即加载时使用此接口
 * 接口中直接编写初始化代码
 * Created by zengc on 2016-05-30.
 */
@FunctionalInterface
public interface ILazyInitializer<M> {
    void init(M m);
}
