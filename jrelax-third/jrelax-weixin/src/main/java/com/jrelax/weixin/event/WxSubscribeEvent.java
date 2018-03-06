package com.jrelax.weixin.event;

/**
 * 订阅事件
 */
public class WxSubscribeEvent extends WxEvent {
    {
        super.setEventType(WxEventType.SUBSCRIBE);
    }

    private String eventKey;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }
}
