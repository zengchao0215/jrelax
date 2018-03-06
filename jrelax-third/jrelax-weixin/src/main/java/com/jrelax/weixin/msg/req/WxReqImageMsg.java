package com.jrelax.weixin.msg.req;

/**
 * 图片消息
 */
public class WxReqImageMsg extends WxReqMsg {
    {
        super.setMsgType(WxReqMsgType.IMAGE);
    }

    private String picUrl;
    private String mediaId;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
