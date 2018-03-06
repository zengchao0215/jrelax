package com.jrelax.weixin.msg;

/**
 * 发送消息类型
 */
public enum WxMsgType {
    TEXT("text"),
    IMAGE("image"),
    VOICE("voice"),
    VIDEO("video"),
    MUSIC("music"),
    NEWS("news");

    private String name;

    WxMsgType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        return this.name;
    }

    public static WxMsgType valueOf2(String name) {
        for (WxMsgType type : values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant " + WxMsgType.class.getName() + "." + name);
    }
}
