package com.jrelax.core.web.transform.datagrid;

import com.jrelax.core.web.transform.IDataGridTransform;
import com.jrelax.orm.query.PageBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EasyUI数据表格结果集转换
 * Created by zengc on 2016/8/17.
 */
public class EasyUIDataGridTransform implements IDataGridTransform{
    public static EasyUIDataGridTransform INSTANCE = new EasyUIDataGridTransform();

    private EasyUIDataGridTransform(){

    }

    /**
     * 响应数据
     * @param list
     * @param pageBean
     * @return
     */
    public Map<String,Object> transform(List list, PageBean pageBean){
        Map<String,Object> map = new HashMap<>();

        map.put("rows", list);
        map.put("totals", pageBean.getTotalCount());

        return map;
    }

    /**
     * 响应数据
     * @param list
     * @return
     */
    public Map<String,Object> transform(List list){
        Map<String,Object> map = new HashMap<>();

        map.put("rows", list);
        map.put("totals", list.size());

        return map;
    }
}
