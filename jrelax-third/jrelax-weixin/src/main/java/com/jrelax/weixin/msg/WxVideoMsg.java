package com.jrelax.weixin.msg;

import com.jrelax.weixin.media.WxVideo;

import java.util.ArrayList;
import java.util.List;

public class WxVideoMsg extends WxMsg {
    {
        super.setMsgType(WxMsgType.VIDEO);
    }
    private List<WxVideo> videos = new ArrayList<>();

    public List<WxVideo> getVideos() {
        return videos;
    }

    public void setVideos(List<WxVideo> videos) {
        this.videos = videos;
    }

    public WxVideoMsg addVideo(WxVideo video) {
        this.videos.add(video);

        return this;
    }

    @Override
    public String toXml() {
        StringBuilder xml = new StringBuilder(super.toXml());
        xml.append("<Video>").append(System.lineSeparator());
        for (WxVideo video : videos) {
            xml.append("<MediaId><![CDATA[").append(video.getMediaId()).append("]]></MediaId>").append(System.lineSeparator());
            xml.append("<Title><![CDATA[").append(video.getTitle()).append("]]></Title>").append(System.lineSeparator());
            xml.append("<Description><![CDATA[").append(video.getDescription()).append("]]></Description>").append(System.lineSeparator());
        }
        xml.append("</Video>").append(System.lineSeparator());

        return super.getXmlStart() + System.lineSeparator() + xml.toString() + super.getXmlEnd();
    }
}
