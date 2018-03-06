package com.jrelax.core.web.transform.tree;

/**
 * 节点适配器
 * Created by zengchao on 2017/6/30.
 */
public interface TreeNodeAdapter<T> {
    void adapter(T t, TreeNode treeNode);
}
