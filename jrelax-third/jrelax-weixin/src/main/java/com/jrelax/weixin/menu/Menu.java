package com.jrelax.weixin.menu;

import java.util.List;

/**
 * 菜单按钮封装类
 */
public class Menu {

    private List<WxMenu> button;

    public List<WxMenu> getButton() {
        return button;
    }

    public void setButton(List<WxMenu> button) {
        this.button = button;
    }
}
