package com.jrelax.core.web.transform.treegrid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrelax.core.web.converter.JSONObjectMapper;
import com.jrelax.orm.query.PageBean;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JqGrid TreeGrid
 * Created by zengchao on 2017/4/15.
 */
public class JqGridTransform {
    public static JqGridTransform INSTANCE = new JqGridTransform();
    private JqGridTransform(){

    }

    /**
     * 响应数据
     * @param list
     * @param pageBean
     * @return
     */
    public Map<String,Object> transform(List list, PageBean pageBean, int level){
        Map<String,Object> map = new HashMap<>();
        //处理JSON，追加level字段
        ObjectMapper mapper = new JSONObjectMapper();
        List<JSONObject> jsonList = new ArrayList<>();
        for(Object object : list){
            try {
                String json = mapper.writeValueAsString(object);
                JSONObject jsonObject = JSONObject.fromObject(json);
                jsonObject.element("level", level);

                jsonList.add(jsonObject);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        map.put("rows", jsonList);
        map.put("total", pageBean.getPageCount());//总页数
        map.put("page", pageBean.getPage());//当前页
        map.put("records", pageBean.getTotalCount());//总条数

        return map;
    }
}
