package com.jrelax.weixin.event;

/**
 * 扫码事件
 */
public class WxScanCodeWaitMsgEvent extends WxEvent {
    {
        super.setEventType(WxEventType.SCAN_CODE_WAITMSG);
    }

    private String eventKey;
    private String scanType;
    private String scanResult;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getScanType() {
        return scanType;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType;
    }

    public String getScanResult() {
        return scanResult;
    }

    public void setScanResult(String scanResult) {
        this.scanResult = scanResult;
    }
}
