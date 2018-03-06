package com.jrelax.weixin.msg.req;

/**
 * 地理位置消息
 */
public class WxReqLocationMsg extends WxReqMsg {
    {
        super.setMsgType(WxReqMsgType.LOCATION);
    }


    private double lon;//纬度
    private double lat;//经度
    private double scale;//缩放大小
    private String label;//地理位置信息

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
