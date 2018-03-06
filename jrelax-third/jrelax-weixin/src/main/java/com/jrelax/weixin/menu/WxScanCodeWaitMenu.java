package com.jrelax.weixin.menu;

/**
 * 扫码推事件且弹出“消息接收中”提示框用户点击按钮后，
 * 微信客户端将调起扫一扫工具，完成扫码操作后，
 * 将扫码的结果传给开发者，同时收起扫一扫工具，
 * 然后弹出“消息接收中”提示框，随后可能会收到开发者下发的消息。
 */
public class WxScanCodeWaitMenu extends WxEventMenu {
    {
        super.setType("scancode_waitmsg");
    }
}
