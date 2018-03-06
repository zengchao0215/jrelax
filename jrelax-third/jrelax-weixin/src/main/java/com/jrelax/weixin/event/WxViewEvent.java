package com.jrelax.weixin.event;

public class WxViewEvent extends WxEvent {
    {
        super.setEventType(WxEventType.VIEW);
    }
    private String eventKey;
    private String menuId;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
