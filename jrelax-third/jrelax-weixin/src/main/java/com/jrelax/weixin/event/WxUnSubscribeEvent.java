package com.jrelax.weixin.event;

/**
 * 取消订阅事件
 */
public class WxUnSubscribeEvent extends WxEvent {
    {
        super.setEventType(WxEventType.UNSUBSCRIBE);
    }

    private String eventKey;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }
}
