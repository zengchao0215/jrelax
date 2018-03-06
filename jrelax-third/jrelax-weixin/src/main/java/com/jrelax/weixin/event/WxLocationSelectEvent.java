package com.jrelax.weixin.event;

/**
 * 位置选择
 */
public class WxLocationSelectEvent extends WxEvent{
    {
        super.setEventType(WxEventType.LOCATION_SELECT);
    }
    private String eventKey;
    private double lon;
    private double lat;
    private double scale;
    private String label;
    private String poiname;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

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

    public String getPoiname() {
        return poiname;
    }

    public void setPoiname(String poiname) {
        this.poiname = poiname;
    }
}
