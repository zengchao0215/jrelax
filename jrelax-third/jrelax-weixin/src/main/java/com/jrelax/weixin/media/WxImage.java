package com.jrelax.weixin.media;

/**
 * 图片
 */
public class WxImage extends WxMedia{
    private String mediaId;

    public WxImage(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
