package com.jrelax.core.web.transform.tree;

import com.jrelax.kit.ObjectKit;
import com.jrelax.kit.ReflectKit;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JSTree组件
 * Created by zengc on 2016-11-06.
 */
public class JSTreeTransform {
    public static JSTreeTransform INSTANCE = new JSTreeTransform();

    private JSTreeTransform() {

    }

    /**
     * 此方法过于繁琐，推荐使用transform2
     * 在后期的框架中可能会被移除
     *
     * @param list
     * @param keyAdapter
     * @return
     */
    @Deprecated
    public JSONArray transform(List list, KeyAdapter keyAdapter) {
        if (ObjectKit.isNull(keyAdapter)) throw new RuntimeException("键值适配器不能为null");
        return transform(list, keyAdapter, null);
    }

    /**
     * 此方法过于繁琐，推荐使用transform2
     *
     * @param list
     * @param keyAdapter
     * @param valueAdapter
     * @return
     */
    @Deprecated
    public JSONArray transform(List list, KeyAdapter keyAdapter, ValueAdapter valueAdapter) {
        JSONArray data = new JSONArray();
        for (Object obj : list) {
            JSONObject json = new JSONObject();
            Map<String, String> keyMapper = keyAdapter.keyMapper(new HashMap<>());
            for (String key : keyMapper.keySet()) {
                String column = keyMapper.get(key);
                Object value = ReflectKit.getFieldValue(obj, column);
                if (ObjectKit.isNotNull(valueAdapter)) {
                    value = valueAdapter.convert(obj, key, value);
                }

                json.element(key, value);
            }

            data.add(json);
        }
        return data;
    }

    /**
     * 此方法过于繁琐，推荐使用transform2
     */
    @Deprecated
    public interface KeyAdapter {
        Map<String, String> keyMapper(Map<String, String> keyMapper);
    }

    /**
     * 此方法过于繁琐，推荐使用transform2
     */
    @Deprecated
    public interface ValueAdapter {
        Object convert(Object obj, String column, Object value);
    }

    public <T> JSONArray transform2(List<T> list, TreeNodeAdapter<T> treeNodeAdapter) {
        JSONArray data = new JSONArray();
        for (T t : list) {
            TreeNode node = new TreeNode();
            treeNodeAdapter.adapter(t, node);

            data.add(toJSONObject(node));
        }
        return data;
    }

    /**
     * 转换为JSON
     *
     * @param treeNode
     * @return
     */
    public JSONObject toJSONObject(TreeNode treeNode) {
        JSONObject node = new JSONObject();
        node.element("id", treeNode.getId());
        node.element("text", treeNode.getText());
        if (treeNode.getParent() != null)
            node.element("parent", treeNode.getParent());
        if (treeNode.getIcon() != null)
            node.element("icon", treeNode.getIcon());
        if (treeNode.getChildren() != null)
            node.element("children", treeNode.getChildren());
        else if (!treeNode.isLeaf())
            node.element("children", true);

        JSONObject state = new JSONObject();
        if (treeNode.isOpened())
            state.element("opened", treeNode.isOpened());
        if (treeNode.isSelected())
            state.element("selected", treeNode.isSelected());
        if (treeNode.isDisabled())
            state.element("disabled", treeNode.isDisabled());

        if (treeNode.isOpened() || treeNode.isSelected() || treeNode.isDisabled())
            node.element("state", state);
        //设置额外参数
        treeNode.getAttrMap().forEach((key, value) -> {
            node.element(key, treeNode.getAttrMap().get(key));
        });

        return node;
    }
}
