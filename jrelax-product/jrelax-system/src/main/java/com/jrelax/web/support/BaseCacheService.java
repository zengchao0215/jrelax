package com.jrelax.web.support;

import com.jrelax.orm.dao.impl.BaseHibernateDao;
import com.jrelax.orm.query.PageBean;
import com.jrelax.orm.support.Records;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 缓存
 * Created by zengchao on 2017-05-19.
 */
@Service
public class BaseCacheService<M> {
    @Resource
    private BaseHibernateDao baseDao;
    private Class<M> entityClass;

    void setEntityClass(Class<M> entityClass) {
        this.entityClass = entityClass;
    }

    public Serializable saveAndCache(M m) {
        return baseDao.saveAndCache(m);
    }

    public Serializable saveAndCache(M m, long expireTime) {
        return baseDao.saveAndCache(m, expireTime);
    }

    public Serializable[] saveAndCache(Collection<M> mList) {
        return baseDao.saveAndCache(mList);
    }

    public Serializable[] saveAndCache(Collection<M> mList, long expireTime) {
        return baseDao.saveAndCache(mList, expireTime);
    }

    public void deleteAndCache(Serializable id) {
        baseDao.deleteAndCache(entityClass, id);
    }

    public void deletesAndCache(Serializable... ids) {
        baseDao.deletesAndCache(entityClass, ids);
    }

    public void deletesAndCache(Collection<Serializable> ids) {
        baseDao.deletesAndCache(entityClass, ids);
    }

    public void deleteOnlyCache(Serializable id) {
        baseDao.deleteOnlyCache(entityClass, id);
    }

    public void deletesOnlyCache(Serializable... ids) {
        baseDao.deletesOnlyCache(entityClass, ids);
    }

    public void deletesOnlyCache(Collection<Serializable> ids) {
        baseDao.deletesOnlyCache(entityClass, ids);
    }

    public void updateAndCache(M m) {
        baseDao.updateAndCache(m);
    }

    public void updateAndCache(M m, long expireTime) {
        baseDao.updateAndCache(m, expireTime);
    }

    public void updateAndCache(Collection<M> mList) {
        baseDao.updateAndCache(mList);
    }

    public void updateAndCache(Collection<M> mList, long expireTime) {
        baseDao.updateAndCache(mList, expireTime);
    }

    public void mergeAndCache(M m) {
        baseDao.mergeAndCache(m);
    }

    public void mergeAndCache(M m, long expireTime) {
        baseDao.mergeAndCache(m, expireTime);
    }

    public void mergeAndCache(Collection<M> mList) {
        baseDao.mergeAndCache(mList);
    }

    public void mergeAndCache(Collection<M> mList, long expireTime) {
        baseDao.mergeAndCache(mList, expireTime);
    }

    public M getByCache(Serializable id) {
        return baseDao.getByCache(entityClass, id);
    }

    public M getByCache(Serializable id, long expireTime) {
        return baseDao.getByCache(entityClass, id, expireTime);
    }

    public List<M> listByCache(String cacheName, String hql, Object... params) {
        return baseDao.listByCache(entityClass, cacheName, hql, params);
    }

    public List<M> listByCache(String cacheName, long expireTime, String hql, Object... params) {
        return baseDao.listByCache(entityClass, cacheName, expireTime, hql, params);
    }

    public List<M> listByCache(String cacheName, PageBean pageBean, String hql, Object... params) {
        return baseDao.listByCache(entityClass, cacheName, pageBean, hql, params);
    }

    public List<M> listByCache(String cacheName, long expireTime, PageBean pageBean, String hql, Object... params) {
        return baseDao.listByCache(entityClass, cacheName, expireTime, pageBean, hql, params);
    }

    public List<M> listToEntityByCache(String cacheName, String hql, Object... params) {
        return baseDao.listToEntityByCache(entityClass, cacheName, hql, params);
    }

    public List<M> listToEntityByCache(String cacheName, long expireTime, String hql, Object... params) {
        return baseDao.listToEntityByCache(entityClass, cacheName, expireTime, hql, params);
    }

    public List<M> listToEntityByCache(String cacheName, PageBean pageBean, String hql, Object... params) {
        return baseDao.listToEntityByCache(entityClass, cacheName, pageBean, hql, params);
    }

    public List<M> listToEntityByCache(String cacheName, long expireTime, PageBean pageBean, String hql, Object... params) {
        return baseDao.listToEntityByCache(entityClass, cacheName, expireTime, pageBean, hql, params);
    }

    public List<Map<String, Object>> listToMapByCache(String cacheName, String hql, Object... params) {
        return baseDao.listToMapByCache(cacheName, hql, params);
    }

    public List<Map<String, Object>> listToMapByCache(String cacheName, long expireTime, String hql, Object... params) {
        return baseDao.listToMapByCache(cacheName, expireTime, hql, params);
    }

    public List<Map<String, Object>> listToMapByCache(String cacheName, PageBean pageBean, String hql, Object... params) {
        return baseDao.listToMapByCache(cacheName, pageBean, hql, params);
    }

    public List<Map<String, Object>> listToMapByCache(String cacheName, long expireTime, PageBean pageBean, String hql, Object... params) {
        return baseDao.listToMapByCache(cacheName, expireTime, pageBean, hql, params);
    }

    public Records listToRecordByCache(String cacheName, String hql, Object... params) {
        return baseDao.listToRecordByCache(cacheName, hql, params);
    }

    public Records listToRecordByCache(String cacheName, long expireTime, String hql, Object... params) {
        return baseDao.listToRecordByCache(cacheName, expireTime, hql, params);
    }

    public Records listToRecordByCache(String cacheName, PageBean pageBean, String hql, Object... params) {
        return baseDao.listToRecordByCache(cacheName, pageBean, hql, params);
    }

    public Records listToRecordByCache(String cacheName, long expireTime, PageBean pageBean, String hql, Object... params) {
        return baseDao.listToRecordByCache(cacheName, expireTime, pageBean, hql, params);
    }

    public List<Object> nativeListByCache(String cacheName, String sql, Object... params) {
        return baseDao.nativeListByCache(cacheName, sql, params);
    }

    public List<Object> nativeListByCache(String cacheName, long expireTime, String sql, Object... params) {
        return baseDao.nativeListByCache(cacheName, expireTime, sql, params);
    }

    public List<Object> nativeListByCache(String cacheName, PageBean pageBean, String sql, Object... params) {
        return baseDao.nativeListByCache(cacheName, pageBean, sql, params);
    }

    public List<Object> nativeListByCache(String cacheName, long expireTime, PageBean pageBean, String sql, Object... params) {
        return baseDao.nativeListByCache(cacheName, expireTime, pageBean, sql, params);
    }

    public List<M> nativeListToEntityByCache(String cacheName, String sql, Object... params) {
        return baseDao.nativeListToEntityByCache(cacheName, entityClass, sql, params);
    }

    public List<M> nativeListToEntityByCache(String cacheName, long expireTime, String sql, Object... params) {
        return baseDao.nativeListToEntityByCache(cacheName, expireTime, entityClass, sql, params);
    }

    public List<Map<String, Object>> nativeListToMapByCache(String cacheName, String sql, Object... params) {
        return baseDao.nativeListToMapByCache(cacheName, sql, params);
    }

    public List<Map<String, Object>> nativeListToMapByCache(String cacheName, long expireTime, String sql, Object... params) {
        return baseDao.nativeListToMapByCache(cacheName, expireTime, sql, params);
    }

    public Records nativeListToRecordByCache(String cacheName, String sql, Object... params) {
        return baseDao.nativeListToRecordByCache(cacheName, sql, params);
    }

    public Records nativeListToRecordByCache(String cacheName, long expireTime, String sql, Object... params) {
        return baseDao.nativeListToRecordByCache(cacheName, expireTime, sql, params);
    }

    public Records nativeListToRecordByCache(String cacheName, PageBean pageBean, String sql, Object... params) {
        return baseDao.nativeListToRecordByCache(cacheName, pageBean, sql, params);
    }

    public Records nativeListToRecordByCache(String cacheName, long expireTime, PageBean pageBean, String sql, Object... params) {
        return baseDao.nativeListToRecordByCache(cacheName, expireTime, pageBean, sql, params);
    }

    public Long countByCache(String cacheName, String hql, Object... params) {
        return baseDao.countByCache(cacheName, hql, params);
    }

    public Long countByCache(String cacheName, long expireTime, String hql, Object... params) {
        return baseDao.countByCache(cacheName, expireTime, hql, params);
    }

    public BigInteger nativeCountByCache(String cacheName, String sql, Object... params) {
        return baseDao.nativeCountByCache(cacheName, sql, params);
    }

    public BigInteger nativeCountByCache(String cacheName, long expireTime, String sql, Object... params) {
        return baseDao.nativeCountByCache(cacheName, expireTime, sql, params);
    }

    public void removeCache(String cacheName) {
        baseDao.removeCache(cacheName);
    }
}
