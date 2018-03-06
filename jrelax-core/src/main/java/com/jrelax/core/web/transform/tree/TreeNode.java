package com.jrelax.core.web.transform.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 树节点
 * Created by zengchao on 2017/6/30.
 */
public class TreeNode {
    private String id = null;
    private String parent = null;
    private String text = null;
    private String icon = null;
    private boolean opened = false;//是否打开
    private boolean selected = false;//是否选中
    private boolean disabled = false;//是否禁用
    private boolean leaf = true;//是否是叶节点，叶节点表示无下级节点
    private List children = null;//子节点
    private Map<String, Object> attrMap = new HashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }

    public TreeNode attr(String key, Object value){
        attrMap.put(key, value);
        return this;
    }

    public Map<String, Object> getAttrMap(){
        return this.attrMap;
    }
}
