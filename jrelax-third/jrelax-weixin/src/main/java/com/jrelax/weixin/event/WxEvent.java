package com.jrelax.weixin.event;

import java.util.Date;

/**
 * 微信事件
 */
public abstract class WxEvent {
    private String toUserName;
    private String fromUserName;
    private Date createTime;
    private WxEventType eventType;

    public WxEvent() {
    }

    public WxEvent(String toUserName, String fromUserName, Date createTime, WxEventType eventType) {
        this.toUserName = toUserName;
        this.fromUserName = fromUserName;
        this.createTime = createTime;
        this.eventType = eventType;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public WxEventType getEventType() {
        return eventType;
    }

    protected void setEventType(WxEventType eventType) {
        this.eventType = eventType;
    }
}
