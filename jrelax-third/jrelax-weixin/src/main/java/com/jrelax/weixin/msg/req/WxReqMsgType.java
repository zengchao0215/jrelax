package com.jrelax.weixin.msg.req;

public enum WxReqMsgType {
    TEXT("text"),
    IMAGE("image"),
    LINK("link"),
    LOCATION("location"),
    SHORT_VIDEO("shortvideo"),
    VIDEO("video"),
    VOICE("voice");

    private String name;

    WxReqMsgType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static WxReqMsgType valueOf2(String name) {
        for (WxReqMsgType type : values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant " + WxReqMsgType.class.getName() + "." + name);
    }
}
