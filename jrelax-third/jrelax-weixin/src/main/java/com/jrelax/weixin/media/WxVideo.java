package com.jrelax.weixin.media;

public class WxVideo extends WxMedia {
    private String title;
    private String description;
    private String mediaId;

    public WxVideo(String title, String description, String mediaId) {
        this.title = title;
        this.description = description;
        this.mediaId = mediaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
