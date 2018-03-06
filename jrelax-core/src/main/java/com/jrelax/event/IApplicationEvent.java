package com.jrelax.event;

/**
 * 事件接口
 * Created by zengchao on 2017-03-09.
 */
public interface IApplicationEvent {
    /**
     * 当事件被触发时
     * @param source
     * @param params
     */
    void onTrigger(Object source, Object params);
}
