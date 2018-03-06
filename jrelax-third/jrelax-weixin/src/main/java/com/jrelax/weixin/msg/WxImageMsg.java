package com.jrelax.weixin.msg;

import com.jrelax.weixin.media.WxImage;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片消息
 */
public class WxImageMsg extends WxMsg {
    {
        super.setMsgType(WxMsgType.IMAGE);
    }
    private List<WxImage> images = new ArrayList<>();

    public List<WxImage> getImages() {
        return images;
    }

    public void setImages(List<WxImage> images) {
        this.images = images;
    }

    public WxImageMsg addImage(WxImage image) {
        this.images.add(image);
        return this;
    }

    @Override
    public String toXml() {
        StringBuilder xml = new StringBuilder(super.toXml());
        xml.append("<Image>").append(System.lineSeparator());
        for (WxImage image : images) {
            xml.append("<MediaId><![CDATA[").append(image.getMediaId()).append("]]></MediaId>").append(System.lineSeparator());
        }
        xml.append("</Image>").append(System.lineSeparator());

        return super.getXmlStart() + System.lineSeparator() + xml.toString() + super.getXmlEnd();
    }
}
