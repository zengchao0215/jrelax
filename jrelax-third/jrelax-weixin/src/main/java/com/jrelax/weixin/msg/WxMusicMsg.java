package com.jrelax.weixin.msg;

import com.jrelax.weixin.media.WxMusic;

import java.util.ArrayList;
import java.util.List;

/**
 * 音乐消息
 */
public class WxMusicMsg extends WxMsg {
    {
        super.setMsgType(WxMsgType.MUSIC);
    }
    private List<WxMusic> musics = new ArrayList<>();

    public List<WxMusic> getMusics() {
        return musics;
    }

    public void setMusics(List<WxMusic> musics) {
        this.musics = musics;
    }

    public WxMusicMsg addMusic(WxMusic music){
        this.musics.add(music);

        return this;
    }

    @Override
    public String toXml() {
        StringBuilder xml = new StringBuilder(super.toXml());
        xml.append("<Music>").append(System.lineSeparator());
        for (WxMusic music : musics) {
            xml.append("<Title><![CDATA[").append(music.getTitle()).append("]]></Title>").append(System.lineSeparator());
            xml.append("<Description><![CDATA[").append(music.getDescription()).append("]]></Description>").append(System.lineSeparator());
            xml.append("<MusicUrl><![CDATA[").append(music.getMusicUrl()).append("]]></MusicUrl>").append(System.lineSeparator());
            xml.append("<HQMusicUrl><![CDATA[").append(music.getHQMusicUrl()).append("]]></HQMusicUrl>").append(System.lineSeparator());
            xml.append("<ThumbMediaId><![CDATA[").append(music.getThumbMediaId()).append("]]></ThumbMediaId>").append(System.lineSeparator());
        }
        xml.append("</Music>").append(System.lineSeparator());

        return super.getXmlStart() + System.lineSeparator() + xml.toString() + super.getXmlEnd();
    }
}
