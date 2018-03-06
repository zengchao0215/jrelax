package com.jrelax.weixin.event;

public class WxClickEvent extends WxEvent {
    {
        super.setEventType(WxEventType.CLICK);
    }
    private String eventKey = "";

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }
}
