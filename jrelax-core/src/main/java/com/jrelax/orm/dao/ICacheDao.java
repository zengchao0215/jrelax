package com.jrelax.orm.dao;

import com.jrelax.orm.query.PageBean;
import com.jrelax.orm.support.Records;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 持久层缓存
 * 获取缓存策略：
 * 如果缓存不存在 则从数据库获取
 * 数据库中获取之后，自动保存到缓存中
 * Created by zengchao on 2017-05-19.
 * Updated by zengchao on 2017-06-16
 */
public interface ICacheDao {
    <M> Serializable saveAndCache(M m);

    <M> Serializable saveAndCache(M m, long expireTime);

    <M> Serializable[] saveAndCache(Collection<M> mList);

    <M> Serializable[] saveAndCache(Collection<M> mList, long expireTime);

    /**
     * 删除数据并删除缓存
     * @param clazz
     * @param id
     * @param <M>
     */
    <M> void deleteAndCache(Class<M> clazz, Serializable id);

    <M> void deletesAndCache(Class<M> clazz, Serializable... ids);

    <M> void deletesAndCache(Class<M> clazz, Collection<Serializable> ids);

    /**
     * 仅删除缓存
     * @param clazz
     * @param id
     * @param <M>
     */
    <M> void deleteOnlyCache(Class<M> clazz, Serializable id);

    <M> void deletesOnlyCache(Class<M> clazz, Serializable... ids);

    <M> void deletesOnlyCache(Class<M> clazz, Collection<Serializable> ids);

    <M> void updateAndCache(M m);

    <M> void updateAndCache(M m, long expireTime);

    <M> void updateAndCache(Collection<M> mList);

    <M> void updateAndCache(Collection<M> mList, long expireTime);

    <M> void mergeAndCache(M m);

    <M> void mergeAndCache(M m, long expireTime);

    <M> void mergeAndCache(Collection<M> mList);

    <M> void mergeAndCache(Collection<M> mList, long expireTime);

    /**
     * 从缓存中获取，
     * 如果缓存不存在 则从数据库获取
     * 数据库中获取之后，自动保存到缓存中
     *
     * @param clazz
     * @param id
     * @param <M>
     * @return
     */
    <M> M getByCache(Class<M> clazz, Serializable id);

    <M> M getByCache(Class<M> clazz, Serializable id, long expireTime);

    <M> List<M> listByCache(Class<M> clazz, String cacheName, String hql, Object... params);

    <M> List<M> listByCache(Class<M> clazz, String cacheName, long expireTime, String hql, Object... params);

    <M> List<M> listByCache(Class<M> clazz, String cacheName, PageBean pageBean, String hql, Object... params);

    <M> List<M> listByCache(Class<M> clazz, String cacheName, long expireTime, PageBean pageBean, String hql, Object... params);

    <M> List<M> listToEntityByCache(Class<M> clazz, String cacheName, String hql, Object... params);

    <M> List<M> listToEntityByCache(Class<M> clazz, String cacheName, long expireTime, String hql, Object... params);

    <M> List<M> listToEntityByCache(Class<M> clazz, String cacheName, PageBean pageBean, String hql, Object... params);

    <M> List<M> listToEntityByCache(Class<M> clazz, String cacheName, long expireTime, PageBean pageBean, String hql, Object... params);

    List<Map<String, Object>> listToMapByCache(String cacheName, String hql, Object... params);

    List<Map<String, Object>> listToMapByCache(String cacheName, long expireTime, String hql, Object... params);

    List<Map<String, Object>> listToMapByCache(String cacheName, PageBean pageBean, String hql, Object... params);

    List<Map<String, Object>> listToMapByCache(String cacheName, long expireTime, PageBean pageBean, String hql, Object... params);

    Records listToRecordByCache(String cacheName, String hql, Object... params);

    Records listToRecordByCache(String cacheName, long expireTime, String hql, Object... params);

    Records listToRecordByCache(String cacheName, PageBean pageBean, String hql, Object... params);

    Records listToRecordByCache(String cacheName, long expireTime, PageBean pageBean, String hql, Object... params);

    List<Object> nativeListByCache(String cacheName, String sql, Object... params);

    List<Object> nativeListByCache(String cacheName, long expireTime, String sql, Object... params);

    List<Object> nativeListByCache(String cacheName, PageBean pageBean, String sql, Object... params);

    List<Object> nativeListByCache(String cacheName, long expireTime, PageBean pageBean, String sql, Object... params);

    <M> List<M> nativeListToEntityByCache(String cacheName, Class<M> clazz, String sql, Object... params);

    <M> List<M> nativeListToEntityByCache(String cacheName, long expireTime, Class<M> clazz, String sql, Object... params);

    <M> List<M> nativeListToEntityByCache(String cacheName, Class<M> clazz, PageBean pageBean, String sql, Object... params);

    <M> List<M> nativeListToEntityByCache(String cacheName, long expireTime, Class<M> clazz, PageBean pageBean, String sql, Object... params);

    List<Map<String, Object>> nativeListToMapByCache(String cacheName, String sql, Object... params);

    List<Map<String, Object>> nativeListToMapByCache(String cacheName, long expireTime, String sql, Object... params);

    List<Map<String, Object>> nativeListToMapByCache(String cacheName, PageBean pageBean, String sql, Object... params);

    List<Map<String, Object>> nativeListToMapByCache(String cacheName, long expireTime, PageBean pageBean, String sql, Object... params);

    Records nativeListToRecordByCache(String cacheName, String sql, Object... params);

    Records nativeListToRecordByCache(String cacheName, long expireTime, String sql, Object... params);

    Records nativeListToRecordByCache(String cacheName, PageBean pageBean, String sql, Object... params);

    Records nativeListToRecordByCache(String cacheName, long expireTime, PageBean pageBean, String sql, Object... params);

    <M> Long countByCache(String cacheName, String hql, Object... params);

    <M> Long countByCache(String cacheName, long expireTime, String hql, Object... params);

    BigInteger nativeCountByCache(String cacheName, String sql, Object... params);

    BigInteger nativeCountByCache(String cacheName, long expireTime, String sql, Object... params);

    void removeCache(String cacheName);

    //added by 2017-06-16
    void removeCacheByPrefix(String cacheNamePrefix);
}
