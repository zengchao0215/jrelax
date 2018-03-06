package com.jrelax.weixin.menu;

/**
 * 扫码推事件用户点击按钮后，
 * 微信客户端将调起扫一扫工具，完成扫码操作后显示扫描结果（如果是URL，将进入URL），
 * 且会将扫码的结果传给开发者，开发者可以下发消息。
 */
public class WxScanCodePushMenu extends WxEventMenu {
    {
        super.setType("scancode_push");
    }
}
