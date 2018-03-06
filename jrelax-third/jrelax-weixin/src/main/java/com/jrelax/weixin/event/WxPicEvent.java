package com.jrelax.weixin.event;

public abstract class WxPicEvent extends WxEvent{
    private String eventKey;
    private int count = 0;
    private String picList;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPicList() {
        return picList;
    }

    public void setPicList(String picList) {
        this.picList = picList;
    }
}
