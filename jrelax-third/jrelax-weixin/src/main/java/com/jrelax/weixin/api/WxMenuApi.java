package com.jrelax.weixin.api;

import com.jrelax.weixin.WxKit;
import com.jrelax.weixin.WxToken;
import com.jrelax.weixin.menu.WxMenu;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * 微信菜单API
 */
public class WxMenuApi {
    private static final String URL_GET = "https://api.weixin.qq.com/cgi-bin/menu/get";
    private static final String URL_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create";
    private static final String URL_DELETE = "https://api.weixin.qq.com/cgi-bin/menu/delete";

    /**
     * 获取菜单
     *
     * @param wxToken
     * @return
     */
    public static JSONObject get(WxToken wxToken) {
        return WxKit.request(wxToken, URL_GET, null);
    }

    /**
     * 创建菜单
     *
     * @param wxToken
     * @param menuList
     * @return
     */
    public static boolean create(WxToken wxToken, List<WxMenu> menuList) {
        JSONObject json = new JSONObject();
        JSONArray buttons = new JSONArray();
        for(WxMenu menu : menuList){
            buttons.add(menu.toString());
        }
        json.element("button", buttons);
        return WxKit.isSuccess(WxKit.post(wxToken, URL_CREATE, json.toString()));
    }

    /**
     * 删除菜单
     *
     * @param wxToken
     * @return
     */
    public static boolean delete(WxToken wxToken) {
        return WxKit.isSuccess(WxKit.request(wxToken, URL_DELETE, null));
    }
}
