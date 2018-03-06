package com.jrelax.orm.support;

import net.sf.json.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Record
 * 针对数据库查询
 * Created by zengchao on 2017-01-14.
 */
public class Record implements Serializable {
    private Map<String, Object> dataMap = null;

    public Record() {
        dataMap = new HashMap<>();
    }

    public Record(Map<String, Object> dataMap) {
        if (dataMap == null)
            this.dataMap = new HashMap<>();
        else
            this.dataMap = dataMap;
    }

    public Record set(String column, Object value) {
        dataMap.put(column, value);
        return this;
    }

    public Object get(String column) {
        return dataMap.get(column);
    }

    public String getString(String column) {
        return (String) dataMap.get(column);
    }

    public Integer getInt(String column) {
        return (Integer) dataMap.get(column);
    }

    public BigInteger getBigInt(String column) {
        return (BigInteger) dataMap.get(column);
    }

    public Long getLong(String column) {
        return (Long) dataMap.get(column);
    }

    public BigDecimal getBigDecimal(String column) {
        return (BigDecimal) dataMap.get(column);
    }

    public Date getDate(String column) {
        return (Date) dataMap.get(column);
    }

    public Time getTime(String column) {
        return (Time) dataMap.get(column);
    }

    public Timestamp getTimestamp(String column) {
        return (Timestamp) dataMap.get(column);
    }

    public Double getDouble(String column) {
        return (Double) dataMap.get(column);
    }

    public Float getFloat(String column) {
        return (Float) dataMap.get(column);
    }

    public Boolean getBoolean(String column) {
        return (Boolean) dataMap.get(column);
    }

    public byte[] getBytes(String column) {
        return (byte[]) dataMap.get(column);
    }

    public Number getNumber(String column) {
        return (Number) dataMap.get(column);
    }

    public JSONObject toJSONObject() {
        return JSONObject.fromObject(dataMap);
    }

    @Override
    public String toString() {
        return toJSONObject().toString();
    }

    public String[] getColumns() {
        return dataMap.keySet().toArray(new String[]{});
    }

    public Object[] getValues() {
        return dataMap.values().toArray(new Object[]{});
    }
}
