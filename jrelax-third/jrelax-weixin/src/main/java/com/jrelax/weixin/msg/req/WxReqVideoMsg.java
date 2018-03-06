package com.jrelax.weixin.msg.req;

/**
 * 视频消息
 */
public class WxReqVideoMsg extends WxReqMsg {
    {
        super.setMsgType(WxReqMsgType.VIDEO);
    }

    private String mediaId;
    private String thumbMediaId;//缩略图

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }
}
