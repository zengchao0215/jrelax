package com.jrelax.orm.support;

import net.sf.json.JSONArray;

import java.io.Serializable;
import java.util.*;

/**
 * Record集合
 * Created by zengchao on 2017-01-14.
 */
public class Records extends ArrayList<Record> implements Collection<Record>, Serializable {
    public Records() {

    }

    public Records(List<Map<String, Object>> mapList) {
        mapList.forEach(map -> this.add(new Record(map)));
    }

    public JSONArray toJSONArray() {
        JSONArray jsonArray = new JSONArray();

        for (Record record : this) {
            jsonArray.add(record.toJSONObject());
        }

        return jsonArray;
    }
}
