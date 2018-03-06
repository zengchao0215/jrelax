package com.jrelax.core.web.transform.datagrid;

import com.jrelax.orm.query.PageBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JQGrid转换
 * Created by zengchao on 2016-09-02.
 */
public class JqGridTransform {
    public static JqGridTransform INSTANCE = new JqGridTransform();

    private JqGridTransform() {

    }

    /**
     * 响应数据
     *
     * @param list 数据集
     * @param pageBean 分页
     * @return
     */
    public Map<String, Object> transform(List list, PageBean pageBean) {
        Map<String, Object> map = new HashMap<>();

        map.put("rows", list);
        map.put("total", pageBean.getPageCount());//总页数
        map.put("page", pageBean.getPage());//当前页
        map.put("records", pageBean.getTotalCount());//总条数

        return map;
    }

    /**
     * 响应数据
     * @param list 数据集
     * @return
     */
    public Map<String, Object> transform(List list) {
        Map<String, Object> map = new HashMap<>();

        map.put("rows", list);
        map.put("total", 1);//总页数
        map.put("page", 1);//当前页
        map.put("records", list.size());//总条数

        return map;
    }
}
