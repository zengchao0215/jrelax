package com.jrelax.core.web.support.http;

import com.jrelax.kit.HttpKit;

import javax.servlet.http.HttpServletRequest;

/**
 * HandlerRequest
 * Created by zengchao on 2017-02-27.
 */
public class HandlerRequest {
    //远程地址
    public String remoteAddr;
    //浏览器型号
    public String broswer;
    //平台型号
    public String platform;

    public String getRemoteAddr() {
        return remoteAddr;
    }

    private void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getBroswer() {
        return broswer;
    }

    private void setBroswer(String broswer) {
        this.broswer = broswer;
    }

    public String getPlatform() {
        return platform;
    }

    private void setPlatform(String platform) {
        this.platform = platform;
    }

    public HandlerRequest(){

    }

    public HandlerRequest(String remoteAddr, String broswer, String platform) {
        this.remoteAddr = remoteAddr;
        this.broswer = broswer;
        this.platform = platform;
    }

    /**
     * 获得HandlerRequest
     * @param request
     * @return
     */
    public static HandlerRequest fromWebRequest(HttpServletRequest request){
        HandlerRequest handlerRequest = new HandlerRequest();
        handlerRequest.setRemoteAddr(HttpKit.getRequestAddr(request));
        handlerRequest.setBroswer(HttpKit.getBrowser(request));
        handlerRequest.setPlatform(HttpKit.getPlatform(request));
        return handlerRequest;
    }
}
