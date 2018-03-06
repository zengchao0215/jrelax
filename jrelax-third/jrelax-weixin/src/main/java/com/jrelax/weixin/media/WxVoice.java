package com.jrelax.weixin.media;

/**
 * 语音
 */
public class WxVoice extends WxMedia {
    private String mediaId;

    public WxVoice(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
