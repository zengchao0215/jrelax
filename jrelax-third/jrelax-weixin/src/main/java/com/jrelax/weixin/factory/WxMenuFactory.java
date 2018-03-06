package com.jrelax.weixin.factory;

import com.jrelax.weixin.menu.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信菜单工厂
 */
public class WxMenuFactory {
    /**
     * 创建点击菜单
     * @param name
     * @param key
     * @return
     */
    public static WxClickMenu createClickMenu(String name, String key){
        WxClickMenu menu = new WxClickMenu();
        menu.setName(name);
        menu.setKey(key);

        return menu;
    }

    /**
     * 创建位置通用菜单
     * @param name
     * @return
     */
    public static <T extends WxMenu> T createMenu(Class<T> clazz, String name){
        try {
            T menu = clazz.newInstance();
            menu.setName(name);

            return menu;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 创建位置通用菜单
     * @param name
     * @return
     */
    public static <T extends WxEventMenu> T createEventMenu(Class<T> clazz, String name, String key){
        try {
            T menu = clazz.newInstance();
            menu.setName(name);
            menu.setKey(key);

            return menu;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 创建媒体ID菜单
     * @param name
     * @param mediaId
     * @return
     */
    public static WxMediaIdMenu createMediaIdMenu(String name, String mediaId){
        WxMediaIdMenu menu = new WxMediaIdMenu();
        menu.setName(name);
        menu.setMediaId(mediaId);

        return menu;
    }

    /**
     * 创建父级菜单
     * @param name
     * @param subMenuList
     * @return
     */
    public static WxParentMenu createParentMenu(String name, List<WxMenu> subMenuList){
        WxParentMenu menu = new WxParentMenu();
        menu.setName(name);
        menu.setSubMenus(subMenuList);

        return menu;
    }

    /**
     * 创建父级菜单
     * @param name
     * @return
     */
    public static WxParentMenu createParentMenu(String name){
        WxParentMenu menu = new WxParentMenu();
        menu.setName(name);
        menu.setSubMenus(new ArrayList<>());

        return menu;
    }

    /**
     * 创建图文菜单，跳转到mediaId
     * @param name
     * @param mediaId
     * @return
     */
    public static WxViewLimitedMenu createViewLimitedMenu(String name, String mediaId){
        WxViewLimitedMenu menu = new WxViewLimitedMenu();
        menu.setName(name);
        menu.setMediaId(mediaId);

        return menu;
    }

    /**
     * 创建图文菜单，跳转到url
     * @param name
     * @param url
     * @return
     */
    public static WxViewMenu createViewMenu(String name, String url){
        WxViewMenu menu = new WxViewMenu();
        menu.setName(name);
        menu.setUrl(url);

        return menu;
    }
}
