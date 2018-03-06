package com.jrelax.weixin.msg;

import com.jrelax.weixin.media.WxVoice;

import java.util.ArrayList;
import java.util.List;

/**
 * 语音消息
 */
public class WxVoiceMsg extends WxMsg {
    {
        super.setMsgType(WxMsgType.VOICE);
    }
    private List<WxVoice> voices = new ArrayList<>();

    public List<WxVoice> getVoices() {
        return voices;
    }

    public void setVoices(List<WxVoice> voices) {
        this.voices = voices;
    }

    public WxVoiceMsg addVoice(WxVoice voice) {
        this.voices.add(voice);
        return this;
    }

    @Override
    public String toXml() {
        StringBuilder xml = new StringBuilder(super.toXml());
        xml.append("<Voice>").append(System.lineSeparator());
        for (WxVoice voice : voices) {
            xml.append("<MediaId><![CDATA[").append(voice.getMediaId()).append("]]></MediaId>").append(System.lineSeparator());
        }
        xml.append("</Voice>").append(System.lineSeparator());

        return super.getXmlStart() + System.lineSeparator() + xml.toString() + super.getXmlEnd();
    }
}
