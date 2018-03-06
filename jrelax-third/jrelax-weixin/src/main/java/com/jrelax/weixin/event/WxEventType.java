package com.jrelax.weixin.event;

import com.jrelax.weixin.msg.WxMsgType;

/**
 * 微信事件类型
 */
public enum WxEventType {
    SUBSCRIBE("subscribe"),//订阅
    UNSUBSCRIBE("unsubscribe"),//取消订阅
    SCAN("SCAN"),//扫描带场景的二维码
    SCAN_CODE_PUSH("scancode_push"),
    SCAN_CODE_WAITMSG("scancode_waitmsg"),
    CLICK("CLICK"),//点击
    PIC_SYS_PHOTO("pic_sysphoto"),//拍照
    PIC_PHOTO_OR_ALBUM("pic_photo_or_album"),//拍照或从相册选择
    PIC_WEIXIN("pic_weixin"),//微信相册
    LOCATION("LOCATION"),//位置上报
    LOCATION_SELECT("location_select"),//发送位置事件
    VIEW("VIEW");//View事件

    private String name;

    WxEventType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static WxEventType valueOf2(String name) {
        for (WxEventType type : values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant " + WxMsgType.class.getName() + "." + name);
    }
}
