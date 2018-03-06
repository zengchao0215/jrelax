package com.jrelax.weixin.msg.req;

/**
 * 语音消息
 */
public class WxReqVoiceMsg extends WxReqMsg {
    {
        super.setMsgType(WxReqMsgType.VOICE);
    }

    private String mediaId;//媒体id，需要通过媒体接口下载媒体
    private String format;//语音格式
    private String recognition;//语音识别结果

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getRecognition() {
        return recognition;
    }

    public void setRecognition(String recognition) {
        this.recognition = recognition;
    }
}
