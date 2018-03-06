package com.jrelax.weixin.menu;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WxParentMenu extends WxMenu {
    {
        super.setType(null);
    }
    private List<WxMenu> subMenus = new ArrayList<>();
    public List<WxMenu> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<WxMenu> sub_button) {
        this.setType("");
        this.subMenus = sub_button;
    }

    public WxMenu addSubMenu(WxMenu subMenu){
        this.setType("");
        this.subMenus.add(subMenu);
        return this;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json =  super.toJson();
        json.element("sub_button", this.subMenus);

        return json;
    }
}
