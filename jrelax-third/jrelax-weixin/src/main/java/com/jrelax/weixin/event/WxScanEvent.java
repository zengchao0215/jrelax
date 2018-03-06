package com.jrelax.weixin.event;

public class WxScanEvent extends WxEvent{
    {
        super.setEventType(WxEventType.SCAN);
    }

    private boolean subscribe = false;//是否订阅公众号
    private String eventKey;
    private String ticket;

    public boolean isSubscribe() {
        return subscribe;
    }

    public void setSubscribe(boolean subscribe) {
        this.subscribe = subscribe;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
