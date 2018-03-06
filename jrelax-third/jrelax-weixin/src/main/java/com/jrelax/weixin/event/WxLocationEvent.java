package com.jrelax.weixin.event;

/**
 * 位置上报事件
 * 此事件取决于：
 *  公众账号是否开启要求用户位置上报
 *  用户是否允许位置上报
 */
public class WxLocationEvent extends WxEvent {
    {
        super.setEventType(WxEventType.LOCATION);
    }

    private double lon;//维度
    private double lat;//经度
    private double precision;//精度

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

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }
}
