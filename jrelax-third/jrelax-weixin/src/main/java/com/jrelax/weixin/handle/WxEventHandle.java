package com.jrelax.weixin.handle;

import com.jrelax.weixin.event.WxEvent;
import com.jrelax.weixin.msg.WxMsg;

public interface WxEventHandle {
    /**
     * 事件处理
     * @param event
     * @return
     */
    WxMsg handle(WxEvent event);

    /**
     * 判断是否支持处理事件
     * @param event
     * @return 是否可以处理
     */
    boolean supported(WxEvent event);
}
