package com.jrelax.orm.dao;

import com.jrelax.orm.query.Condition;
import com.jrelax.orm.query.PageBean;
import com.jrelax.orm.support.Record;
import com.jrelax.orm.support.Records;
import org.hibernate.criterion.Criterion;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * 查询Dao接口
 * Created by zengchao on 2016/8/9.
 * Updated by zengchao on 2017/01/16
 */
public interface IQueryDao {
    /*************Hibernate查询**************/
    <M> M load(Class<M> clazz, Serializable id);

    <M> M get(Class<M> clazz, Serializable id);

    <M> M get(Class<M> clazz, Condition condition);

    <M> M get(Class<M> clazz, String hql, Object... params);

    <M> M get(Class<M> clazz, Criterion... citrons);

    Object getObject(String hql, Object... params);

    Integer getInt(String hql, Object... params);

    Integer getIntOrDefault(String hql, Integer defaultValue, Object... params);

    Long getLong(String hql, Object... params);

    Long getLongOrDefault(String hql, Long defaultValue, Object... params);

    Double getDouble(String hql, Object... params);

    Double getDoubleOrDefault(String hql, Double defaultValue, Object... params);

    String getString(String hql, Object... params);

    String getStringOrDefault(String hql, String defaultValue, Object... params);

    Boolean getBoolean(String hql, Object... params);

    Boolean getBooleanOrDefault(String hql, Boolean defaultValue, Object... params);

    String getEntityName(Object object);

    Map<String, Object> getMap(String hql, Object... params);

    // Added by 2017-01-16
    List<Object> getList(String hql, Object... params);

    // Added by 2017-01-16
    Record getRecord(String hql, Object... params);

    <M> List<M> listAll(Class<M> clazz);

    <M> List<M> list(Class<M> clazz, PageBean pageBean);

    <M> List<M> list(Class<M> clazz, Condition condition);

    <M> List<M> list(Class<M> clazz, String hql, Object... params);

    <M> List<M> list(Class<M> clazz, PageBean pageBean, String hql, Object... params);

    <M> List<M> listToEntity(Class<M> clazz, String hql, Object... params);

    <M> List<M> listToEntity(Class<M> clazz, PageBean pageBean, String hql, Object... params);

    List<Map<String, Object>> listToMap(String hql, Object... params);

    List<Map<String, Object>> listToMap(PageBean pageBean, String hql, Object... params);

    List<Object> listToObject(String hql, Object... params);

    List<Object> listToObject(PageBean pageBean, String hql, Object... params);

    List<Object[]> listToArray(String hql, Object... params);

    List<Object[]> listToArray(PageBean pageBean, String hql, Object... params);

    // Added by 2017-01-16
    Records listToRecord(String hql, Object... params);

    // Added by 2017-01-16
    Records listToRecord(PageBean pageBean, String hql, Object... params);

    <M> Long countAll(Class<M> clazz);

    <M> Long count(Class<M> clazz, Condition condition);

    <M> Long count(String hql, Object... params);

    <M> Long count(Class<M> clazz, Criterion... citrons);


    /*************原生SQL**************/
    <M> M nativeGet(Class<M> clazz, String sql, Object... params);

    Object nativeGetObject(String sql, Object... params);

    Integer nativeGetInt(String sql, Object... params);

    Integer nativeGetIntOrDefault(String sql, Integer defaultValue, Object... params);

    Long nativeGetLong(String sql, Object... params);

    Long nativeGetLongOrDefault(String sql, Long defaultValue, Object... params);

    Double nativeGetDouble(String sql, Object... params);

    Double nativeGetDoubleOrDefault(String sql, Double defaultValue, Object... params);

    String nativeGetString(String sql, Object... params);

    String nativeGetStringOrDefault(String sql, String defaultValue, Object... params);

    Boolean nativeGetBoolean(String sql, Object... params);

    Boolean nativeGetBooleanOrDefault(String sql, Boolean defaultValue, Object... params);

    // Added by 2017-01-16
    Map<String, Object> nativeGetMap(String sql, Object... params);

    // Added by 2017-01-16
    List<Object> nativeGetList(String sql, Object... params);

    // Added by 2017-01-16
    Record nativeGetRecord(String sql, Object... params);

    List<Object> nativeList(String sql, Object... params);

    List<Object> nativeList(PageBean pageBean, String sql, Object... params);

    List<Map<String, Object>> nativeListToMap(String sql, Object... params);

    List<Map<String, Object>> nativeListToMap(PageBean pageBean, String sql, Object... params);

    List<Object[]> nativeListToArray(String sql, Object... params);

    List<Object[]> nativeListToArray(PageBean pageBean, String sql, Object... params);

    <M> List<M> nativeListToEntity(Class<M> clazz, String sql, Object... params);

    <M> List<M> nativeListToEntity(Class<M> clazz, PageBean pageBean, String sql, Object... params);

    // Added by 2017-01-16
    Records nativeListToRecord(String sql, Object... params);

    // Added by 2017-01-16
    Records nativeListToRecord(PageBean pageBean, String sql, Object... params);

    BigInteger nativeCount(String sql, Object... params);
}
