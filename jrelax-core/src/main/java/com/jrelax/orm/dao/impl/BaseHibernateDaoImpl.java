package com.jrelax.orm.dao.impl;

import com.jrelax.cache.ICacheProvider;
import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.kit.ObjectKit;
import com.jrelax.kit.ReflectKit;
import com.jrelax.orm.query.Condition;
import com.jrelax.orm.query.PageBean;
import com.jrelax.orm.support.Record;
import com.jrelax.orm.support.Records;
import com.jrelax.orm.transform.AliasToEntityKeyMapResultTransformer;
import com.jrelax.orm.transform.AliasToEntityLinkedMapResultTransformer;
import com.jrelax.orm.transform.ToArrayResultTransformer;
import com.jrelax.orm.util.HqlSqlParser;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.lang.InstantiationException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class BaseHibernateDaoImpl extends BaseHibernateDao {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final int BATCH_SIZE = 50;
    //------------------------------IBaseDaoImpl-----------------------------------/
    @Resource
    private SessionFactory sessionFactory;

    /**
     * 获取Session对象
     *
     * @return Hibernate Session会话
     */
    public Session getSession() {
        // 事务必须是开启的，否则获取不到
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Session openSession() {
        return sessionFactory.openSession();
    }

    @Override
    public StatelessSession openStatelessSession() {
        return sessionFactory.openStatelessSession();
    }

    /**
     * 获取查询对象Query
     *
     * @param hql HQL语句
     * @return HQL查询对象
     */
    public Query getQuery(String hql) {
        Query query = getSession().createQuery(hql);
        //非DEBUG模式下 启用二级缓存
        if (!ApplicationCommon.DEBUG)
            query.setCacheable(true);
        return query;
    }

    /**
     * 获取查询对象SQLQuery
     *
     * @param sql sql语句
     * @return SQL查询对象
     */
    public NativeQuery getSQLQuery(String sql) {
        return getSession().createNativeQuery(sql);
    }

    /**
     * 获取存储过程执行器
     *
     * @param procedureName 存储过程名称
     * @return 存储过程调用对象
     */
    public ProcedureCall getProcedureCall(String procedureName) {
        return getSession().createStoredProcedureCall(procedureName);
    }

    /**
     * 获取查询对象Criteria
     *
     * @return QBC查询对象
     */
    public Criteria getCriteria(Class<?> clazz) {
        Criteria criteria = getSession().createCriteria(clazz);
        //非DEBUG模式下 启用二级缓存
        if (!ApplicationCommon.DEBUG)
            criteria.setCacheable(true);
        return criteria;
    }

    /**
     * 获取SessionFactory对象
     *
     * @return Sesssion会话工厂
     */
    public SessionFactory getSessionFactory() {
        checkSessionFactory();
        return sessionFactory;
    }

    /**
     * 检查sessionFactory对象是否为空
     */
    private void checkSessionFactory() {
        if (sessionFactory == null) {
            throw new NullPointerException("未注入SessionFactory，请检查配置文件!");
        }
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return getSession().getEntityManagerFactory();
    }

    @Override
    public EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return getEntityManager().getCriteriaBuilder();
    }

    //------------------------------IPersistenDaoImpl-----------------------------------

    /**
     * 保存
     *
     * @param <M> 持久化对象
     * @return 生成的ID
     */
    public <M> Serializable save(M m) {
        return getSession().save(m);
    }

    /**
     * 批量保存
     *
     * @param <M> 持久化对象
     * @return 生成的ID
     */
    public <M> Serializable[] save(Collection<M> mList) {
        Serializable[] array = new Serializable[mList.size()];
        Session session = getSessionFactory().getCurrentSession();
        int idx = 0;
        for (M m : mList) {
            array[idx] = session.save(m);
            if (idx > 0 && idx % BATCH_SIZE == 0) {
                session.flush();
            }
            idx++;
        }
        return array;
    }

    /**
     * 保存
     *
     * @param <M> 持久化对象
     */
    public <M> void persist(M m) {
        getSession().persist(m);
    }

    /**
     * 批量保存
     *
     * @param <M> 持久化对象
     */
    public <M> void persist(Collection<M> mList) {
        Session session = getSessionFactory().getCurrentSession();
        int idx = 0;
        for (M m : mList) {
            session.persist(m);
            if (idx > 0 && idx % BATCH_SIZE == 0) {
                session.flush();
            }
            idx++;
        }
    }

    /**
     * 保存或者更新
     *
     * @param <M> 持久化对象
     */
    public <M> void saveOrUpdate(M m) {
        getSession().saveOrUpdate(m);
    }

    /**
     * 保存或者更新
     *
     * @param <M> 持久化对象
     */
    public <M> void saveOrUpdate(Collection<M> mList) {
        Session session = getSessionFactory().getCurrentSession();
        int idx = 0;
        for (M m : mList) {
            session.saveOrUpdate(m);
            if (idx > 0 && idx % BATCH_SIZE == 0) {
                session.flush();
            }
            idx++;
        }
    }

    /**
     * 删除
     *
     * @param m 单个对象
     */
    public <M> void delete(M m) {
        getSession().delete(m);
    }

    /**
     * 根据ID删除
     *
     * @param id ID
     */
    public <M> void delete(Class<M> clazz, Serializable id) {
        M m = getSession().get(clazz, id);
        if (m != null)
            getSession().delete(m);
    }

    /**
     * 批量删除
     */
    public <M> void deletes(Class<M> clazz, Serializable... ids) {
        Query query = this.getQuery("delete from " + clazz + " where id in ?");
        query.setParameter(0, ids);
        query.executeUpdate();
    }

    /**
     * 批量删除
     */
    public <M> void deletes(Class<M> clazz, Collection<Serializable> ids) {
        Query query = this.getQuery("delete from " + clazz + " where id in ?");
        query.setParameter(0, ids);
        query.executeUpdate();
    }

    /**
     * 更新
     *
     * @param m 单个对象
     */
    public <M> void update(M m) {
        getSession().update(m);
    }

    /**
     * 批量更新
     *
     * @param mList 对象集合
     */
    public <M> void update(Collection<M> mList) {
        Session session = getSessionFactory().getCurrentSession();
        int idx = 0;
        for (M m : mList) {
            session.update(m);
            if (idx > 0 && idx % BATCH_SIZE == 0) {
                session.flush();
            }
            idx++;
        }
    }

    /**
     * 更新
     *
     * @param m 单个对象
     */
    public <M> M merge(M m) {
        return (M) getSession().merge(m);
    }

    /**
     * 批量更新
     *
     * @param mList 对象集合
     */
    public <M> Collection<M> merge(Collection<M> mList) {
        Collection<M> list = new ArrayList<>();
        Session session = getSessionFactory().getCurrentSession();
        int idx = 0;
        for (M m : mList) {
            list.add((M) session.merge(m));
            if (idx > 0 && idx % BATCH_SIZE == 0) {
                session.flush();
            }
            idx++;
        }
        return list;
    }

    /**
     * 刷新,将对象与数据库同步
     */
    public <M> void refresh(M m) {
        getSession().refresh(m);
    }

    /**
     * 批量刷新
     *
     * @param mList 对象集合
     */
    public <M> void refresh(Collection<M> mList) {
        Session session = getSessionFactory().getCurrentSession();
        int idx = 0;
        for (M m : mList) {
            session.refresh(m);
            if (idx > 0 && idx % BATCH_SIZE == 0) {
                session.flush();
            }
            idx++;
        }
    }

    @Override
    public <M> void refresh(M m, LockMode lockMode) {
        getSession().refresh(m, lockMode);
    }

    @Override
    public <M> void evict(M m) {
        getSession().evict(m);
    }

    @Override
    public <M> void replicate(M m, ReplicationMode replicationMode) {
        getSession().replicate(m, replicationMode);
    }

    @Override
    public <M> void lock(M m, LockMode lockMode) {
        getSession().lock(m, lockMode);
    }

    /**
     * 执行批处理语句.如 之间insert, update, delete 等.
     *
     * @param hql    语句
     * @param params 参数列表
     * @return 返回影响行数
     */
    public int executeBatch(final String hql, final Object... params) {
        Query query = getQuery(hql);
        setQueryParameters(query, params);
        Object result = query.executeUpdate();
        return (Integer) result;
    }

    @Override
    public int[] executeBatch(String hql, Object[][] params) {
        int[] results = new int[params.length];
        Session session = getSessionFactory().getCurrentSession();
        int idx = 0;
        Query query = session.createQuery(hql);
        for (Object[] param : params) {
            setQueryParameters(query, param);
            results[idx] = query.executeUpdate();
            if (idx > 0 && idx % BATCH_SIZE == 0) {
                session.flush();
            }
            idx++;
        }
        return results;
    }

    /**
     * 执行批处理语句.如 之间insert, update, delete 等.
     *
     * @param sql    SQL语句
     * @param params 参数列表
     * @return 返回影响行数
     */
    public int executeSqlBatch(final String sql, final Object... params) {
        Query query = getSQLQuery(sql);
        setQueryParameters(query, params);
        Object result = query.executeUpdate();
        return (Integer) result;
    }

    @Override
    public int[] executeSqlBatch(String sql, Object[][] params) {
        int[] results = new int[params.length];
        Session session = getSessionFactory().getCurrentSession();
        int idx = 0;
        for (Object[] param : params) {
            NativeQuery query = session.createNativeQuery(sql);
            setQueryParameters(query, param);
            results[idx] = query.executeUpdate();
            if (idx > 0 && idx % BATCH_SIZE == 0) {
                session.flush();
            }
            idx++;
        }
        return results;
    }

    //------------------------------IQueryDaoImpl--------------------------------

    @Override
    public <M> M load(Class<M> clazz, Serializable id) {
        return getSession().load(clazz, id);
    }

    @Override
    public <M> M get(Class<M> clazz, Serializable id) {
        return getSession().get(clazz, id);
    }

    @Override
    public <M> M get(Class<M> clazz, Condition condition) {
        Criteria criteria = getCriteria(clazz);
        setCriteriaCondition(criteria, condition);
        return (M) criteria.setMaxResults(1).uniqueResult();
    }

    @Override
    public <M> M get(Class<M> clazz, String hql, Object... params) {
        Query query = getQuery(hql);
        setQueryParameters(query, params);
        M obj = (M) query.setMaxResults(1).uniqueResult();
        if (ObjectKit.isNull(obj))
            return null;
        obj = _parseEntity(clazz, hql, obj);
        return obj;
    }

    @Override
    public <M> M get(Class<M> clazz, Criterion... citrons) {
        Criteria criteria = getCriteria(clazz);
        if (citrons != null)
            for (Criterion criterion : citrons)
                criteria.add(criterion);
        return (M) criteria.setMaxResults(1).uniqueResult();
    }

    @Override
    public Object getObject(String hql, Object... params) {
        Query query = getQuery(hql);
        setQueryParameters(query, params);
        query.setMaxResults(1);
        return query.uniqueResult();
    }

    @Override
    public Integer getInt(String hql, Object... params) {
        Object object = getObject(hql, params);
        if (object == null) return null;
        return Integer.parseInt(object + "");
    }

    @Override
    public Integer getIntOrDefault(String hql, Integer defaultValue, Object... params) {
        Integer value = getInt(hql, params);
        return value == null ? defaultValue : value;
    }

    @Override
    public Long getLong(String hql, Object... params) {
        Object object = getObject(hql, params);
        if (object == null) return null;
        return Long.parseLong(object + "");
    }

    @Override
    public Long getLongOrDefault(String hql, Long defaultValue, Object... params) {
        Long value = getLong(hql, params);
        return value == null ? defaultValue : value;
    }

    @Override
    public Double getDouble(String hql, Object... params) {
        Object object = getObject(hql, params);
        if (object == null) return null;
        if(object instanceof BigDecimal)
            return ((BigDecimal) object).doubleValue();
        return Double.parseDouble(object + "");
    }

    @Override
    public Double getDoubleOrDefault(String hql, Double defaultValue, Object... params) {
        Double value = getDouble(hql, params);
        return value == null ? defaultValue : value;
    }

    @Override
    public String getString(String hql, Object... params) {
        Object object = getObject(hql, params);
        if (object == null) return null;
        return object.toString();
    }

    @Override
    public String getStringOrDefault(String hql, String defaultValue, Object... params) {
        String value = getString(hql, params);
        return value == null ? defaultValue : value;
    }

    @Override
    public Boolean getBoolean(String hql, Object... params) {
        Object object = getObject(hql, params);
        if (object == null) return null;
        return Boolean.parseBoolean(object + "");
    }

    @Override
    public Boolean getBooleanOrDefault(String hql, Boolean defaultValue, Object... params) {
        Boolean value = getBoolean(hql, params);
        return value == null ? defaultValue : value;
    }

    @Override
    public String getEntityName(Object object) {
        return getSession().getEntityName(object);
    }

    @Override
    public Map<String, Object> getMap(String hql, Object... params) {
        Query query = getQuery(hql);
        setQueryParameters(query, params);
        query.setResultTransformer(new AliasToEntityKeyMapResultTransformer(hql));

        List list = query.list();
        Map<String, Object> map = new HashMap();
        if (list.size() > 0)
            map = (Map<String, Object>) list.get(0);
        return map;
    }

    @Override
    public List<Object> getList(String hql, Object... params) {
        Query query = getQuery(hql);
        setQueryParameters(query, params);
        query.setMaxResults(1);
        query.setResultTransformer(Transformers.TO_LIST);
        List list = query.list();
        List<Object> objectList = new ArrayList<>();
        if (list.size() > 0)
            objectList = (List<Object>) list.get(0);
        return objectList;
    }

    @Override
    public Record getRecord(String hql, Object... params) {
        Map<String, Object> map = getMap(hql, params);
        return new Record(map);
    }

    @Override
    public <M> List<M> listAll(Class<M> clazz) {
        return getCriteria(clazz).list();
    }

    @Override
    public <M> List<M> list(Class<M> clazz, PageBean pageBean) {
        Criteria criteria = getCriteria(clazz);
        setCriteriaPageBean(criteria, pageBean);
        return criteria.list();
    }

    @Override
    public <M> List<M> list(Class<M> clazz, Condition condition) {
        Criteria criteria = getCriteria(clazz);
        setCriteriaCondition(criteria, condition);
        return criteria.list();
    }

    @Override
    public <M> List<M> list(Class<M> clazz, String hql, Object... params) {
        if (startWithSelect(hql)) {
            return listToEntity(clazz, hql, params);
        } else {
            return _list(clazz, hql, params);
        }
    }

    private <M> List<M> _list(Class<M> clazz, String hql, Object... params) {
        if (startWithSelect(hql)) {
            return listToEntity(clazz, hql, params);
        } else {
            Query query = getQuery(hql);
            if (ObjectKit.isNotNull(params)) {
                setQueryParameters(query, params);
            }
            return query.list();
        }
    }

    @Override
    public <M> List<M> list(Class<M> clazz, PageBean pageBean, String hql, Object... params) {
        if (startWithSelect(hql)) {
            return listToEntity(clazz, pageBean, hql, params);
        } else {
            return _list(clazz, pageBean, hql, params);
        }
    }

    private <M> List<M> _list(Class<M> clazz, PageBean pageBean, String hql, Object... params) {
        Query query = getQuery(hql);
        setQueryPageBean(query, pageBean);
        if (ObjectKit.isNotNull(params))
            setQueryParameters(query, params);
        return query.list();
    }

    @Override
    public <M> List<M> listToEntity(Class<M> clazz, String hql, Object... params) {
        Query query = getQuery(hql);
        if (ObjectKit.isNotNull(params)) {
            setQueryParameters(query, params);
        }
        List<M> list = query.list();
        return parseEntity(clazz, hql, list);
    }

    @Override
    public <M> List<M> listToEntity(Class<M> clazz, PageBean pageBean, String hql, Object... params) {
        List<M> list = _list(clazz, pageBean, hql, params);
        return parseEntity(clazz, hql, list);
    }

    @Override
    public List<Map<String, Object>> listToMap(String hql, Object... params) {
        Query query = getQuery(hql);
        setQueryParameters(query, params);
        query.setResultTransformer(new AliasToEntityKeyMapResultTransformer(hql));
        return query.list();
    }

    @Override
    public List<Map<String, Object>> listToMap(PageBean pageBean, String hql, Object... params) {
        Query query = getQuery(hql);
        setQueryParameters(query, params);
        query.setResultTransformer(new AliasToEntityKeyMapResultTransformer(hql));
        setQueryPageBean(query, pageBean);
        return query.list();
    }

    @Override
    public List<Object> listToObject(String hql, Object... params) {
        Query query = getQuery(hql);
        setQueryParameters(query, params);
        return query.list();
    }

    @Override
    public List<Object> listToObject(PageBean pageBean, String hql, Object... params) {
        Query query = getQuery(hql);
        setQueryParameters(query, params);
        setQueryPageBean(query, pageBean);
        return query.list();
    }

    @Override
    public List<Object[]> listToArray(String hql, Object... params) {
        Query query = getQuery(hql);
        setQueryParameters(query, params);
        return query.list();
    }

    @Override
    public List<Object[]> listToArray(PageBean pageBean, String hql, Object... params) {
        Query query = getQuery(hql);
        setQueryParameters(query, params);
        setQueryPageBean(query, pageBean);
        return query.list();
    }

    @Override
    public Records listToRecord(String hql, Object... params) {
        return new Records(listToMap(hql, params));
    }

    @Override
    public Records listToRecord(PageBean pageBean, String hql, Object... params) {
        return new Records(listToMap(pageBean, hql, params));
    }

    @Override
    public <M> Long countAll(Class<M> clazz) {
        Criteria criteria = getCriteria(clazz);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    @Override
    public <M> Long count(Class<M> clazz, Condition condition) {
        Criteria criteria = getCriteria(clazz);
        setCriteriaCondition(criteria, condition);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    @Override
    public Long count(String hql, Object... params) {
        Query query = getQuery(hql);
        setQueryParameters(query, params);
        return (Long) query.uniqueResult();
    }

    @Override
    public <M> Long count(Class<M> clazz, Criterion... citrons) {
        Criteria criteria = getCriteria(clazz);
        if (ObjectKit.isNotNull(citrons))
            for (Criterion criterion : citrons)
                criteria.add(criterion);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    @Override
    public <M> M nativeGet(Class<M> clazz, String sql, Object... params) {
        NativeQuery query = getSQLQuery(sql);
        query.addEntity(clazz);
        setQueryParameters(query, params);
        return (M) query.setMaxResults(1).uniqueResult();
    }

    @Override
    public Object nativeGetObject(String sql, Object... params) {
        NativeQuery query = getSQLQuery(sql);
        setQueryParameters(query, params);
        query.setMaxResults(1);
        return query.uniqueResult();
    }

    @Override
    public Integer nativeGetInt(String sql, Object... params) {
        Object object = nativeGetObject(sql, params);
        if (object == null) return null;
        return Integer.parseInt(object + "");
    }

    @Override
    public Integer nativeGetIntOrDefault(String sql, Integer defaultValue, Object... params) {
        Integer value = nativeGetInt(sql, params);
        return value == null ? defaultValue : value;
    }

    @Override
    public Long nativeGetLong(String sql, Object... params) {
        Object object = nativeGetObject(sql, params);
        if (object == null) return null;
        return Long.parseLong(object + "");
    }

    @Override
    public Long nativeGetLongOrDefault(String sql, Long defaultValue, Object... params) {
        Long value = nativeGetLong(sql, params);
        return value == null ? defaultValue : value;
    }

    @Override
    public Double nativeGetDouble(String sql, Object... params) {
        Object object = nativeGetObject(sql, params);
        if (object == null) return null;
        return Double.parseDouble(object + "");
    }

    @Override
    public Double nativeGetDoubleOrDefault(String sql, Double defaultValue, Object... params) {
        Double value = nativeGetDouble(sql, params);
        return value == null ? defaultValue : value;
    }

    @Override
    public String nativeGetString(String sql, Object... params) {
        Object object = nativeGetObject(sql, params);
        if (object == null) return null;
        return object.toString();
    }

    @Override
    public String nativeGetStringOrDefault(String sql, String defaultValue, Object... params) {
        String value = nativeGetString(sql, params);
        return value == null ? defaultValue : value;
    }

    @Override
    public Boolean nativeGetBoolean(String sql, Object... params) {
        Object object = nativeGetObject(sql, params);
        if (object == null) return null;
        return Boolean.parseBoolean(object + "");
    }

    @Override
    public Boolean nativeGetBooleanOrDefault(String sql, Boolean defaultValue, Object... params) {
        Boolean value = nativeGetBoolean(sql, params);
        return value == null ? defaultValue : value;
    }

    @Override
    public Map<String, Object> nativeGetMap(String sql, Object... params) {
        NativeQuery query = getSQLQuery(sql);
        setQueryParameters(query, params);
        query.setResultTransformer(AliasToEntityLinkedMapResultTransformer.INSTANCE);
        query.setMaxResults(1);

        List<?> list = query.list();
        if (list.size() > 0)
            return (Map<String, Object>) list.get(0);
        return null;
    }

    @Override
    public List<Object> nativeGetList(String sql, Object... params) {
        NativeQuery query = getSQLQuery(sql);
        setQueryParameters(query, params);
        query.setMaxResults(1);
        query.setResultTransformer(Transformers.TO_LIST);

        List list = query.list();
        List<Object> objectList = new ArrayList<>();
        if (list.size() > 0)
            objectList = (List<Object>) list.get(0);
        return objectList;
    }

    @Override
    public Record nativeGetRecord(String sql, Object... params) {
        return new Record(nativeGetMap(sql, params));
    }

    @Override
    public List<Object> nativeList(String sql, Object... params) {
        NativeQuery query = getSQLQuery(sql);
        setQueryParameters(query, params);
        query.setResultTransformer(Transformers.TO_LIST);
        return query.list();
    }

    @Override
    public List<Object> nativeList(PageBean pageBean, String sql, Object... params) {
        NativeQuery query = getSQLQuery(sql);
        setQueryParameters(query, params);
        setQueryPageBean(query, pageBean);
        query.setResultTransformer(Transformers.TO_LIST);
        return query.list();
    }

    @Override
    public List<Map<String, Object>> nativeListToMap(String sql, Object... params) {
        NativeQuery query = getSQLQuery(sql);
        setQueryParameters(query, params);
        query.setResultTransformer(AliasToEntityLinkedMapResultTransformer.INSTANCE);
        return query.list();
    }

    @Override
    public List<Map<String, Object>> nativeListToMap(PageBean pageBean, String sql, Object... params) {
        NativeQuery query = getSQLQuery(sql);
        setQueryParameters(query, params);
        setQueryPageBean(query, pageBean);
        query.setResultTransformer(AliasToEntityLinkedMapResultTransformer.INSTANCE);
        return query.list();
    }

    @Override
    public List<Object[]> nativeListToArray(String sql, Object... params) {
        NativeQuery query = getSQLQuery(sql);
        setQueryParameters(query, params);
        query.setResultTransformer(new ToArrayResultTransformer());
        List<Object> objectList = query.list();
        return objectList.stream().map(obj -> (Object[]) obj).collect(Collectors.toList());
    }

    @Override
    public List<Object[]> nativeListToArray(PageBean pageBean, String sql, Object... params) {
        NativeQuery query = getSQLQuery(sql);
        setQueryParameters(query, params);
        setQueryPageBean(query, pageBean);
        query.setResultTransformer(new ToArrayResultTransformer());
        List<Object> objectList = query.list();
        return objectList.stream().map(obj -> (Object[]) obj).collect(Collectors.toList());
    }

    @Override
    public <M> List<M> nativeListToEntity(Class<M> clazz, String sql, Object... params) {
        NativeQuery query = getSQLQuery(sql);
        setQueryParameters(query, params);
        query.setResultTransformer(new AliasToEntityKeyMapResultTransformer(sql));
        List<M> entities = new ArrayList<>();
        List<M> list = query.list();
        for (M obj : list) {
            entities.add(_parseEntity(clazz, sql, obj));
        }
        return entities;
    }

    @Override
    public <M> List<M> nativeListToEntity(Class<M> clazz, PageBean pageBean, String sql, Object... params) {
        NativeQuery query = getSQLQuery(sql);
        setQueryParameters(query, params);
        setQueryPageBean(query, pageBean);
        query.setResultTransformer(new AliasToEntityKeyMapResultTransformer(sql));
        List<M> entityList = new ArrayList<>();
        List<M> list = query.list();
        for (M obj : list) {
            entityList.add(_parseEntity(clazz, sql, obj));
        }
        return entityList;
    }

    @Override
    public BigInteger nativeCount(String sql, Object... params) {
        NativeQuery query = getSQLQuery(sql);
        setQueryParameters(query, params);
        return (BigInteger) query.uniqueResult();
    }

    @Override
    public Records nativeListToRecord(String sql, Object... params) {
        return new Records(nativeListToMap(sql, params));
    }

    @Override
    public Records nativeListToRecord(PageBean pageBean, String sql, Object... params) {
        return new Records(nativeListToMap(pageBean, sql, params));
    }

    //----------------------------- 工具方法 -------------------------------

    /**
     * 判断是否是查询语句
     *
     * @param hql
     * @return
     */
    private boolean startWithSelect(String hql) {
        return hql != null && hql.toLowerCase().trim().startsWith("select");
    }

    /**
     * 解析参数
     *
     * @param clazz 实体类
     * @param hql   HQL语句
     * @param list  实体集合
     * @return 转换后的实体集合
     */
    private <M> List<M> parseEntity(Class<M> clazz, String hql, List<M> list) {
        List<M> entityList = new ArrayList<>();
        for (M obj : list) {
            entityList.add(_parseEntity(clazz, hql, obj));
        }
        return entityList;
    }

    /**
     * 通过反射设置值
     *
     * @param clazz 实体类
     * @param hql   HQL语句
     * @param obj   实体对象实例
     * @return 转换后的实体
     */
    private <M> M _parseEntity(Class<M> clazz, String hql, M obj) {
        if (!obj.getClass().equals(clazz)) {
            String[] columns = new HqlSqlParser(hql).getSelectColumns();//hql.substring(hql.indexOf("select") + 6, hql.indexOf("from")).split(",");
            if (obj instanceof Object[]) {
                try {
                    M m = clazz.newInstance();
                    Object[] values = (Object[]) obj;
                    for (int i = 0; i < columns.length; i++) {
                        ReflectKit.setFieldValue(m, columns[i], values[i]);
                    }
                    obj = m;
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("HQL语句错误 - " + e.getMessage());
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException("HQL语句实体定义错误 - " + e.getMessage());
                }
            } else if (obj instanceof Map) {
                try {
                    Map<String, Object> valueMap = (Map<String, Object>) obj;
                    M m = clazz.newInstance();
                    for (String column : columns) {
                        ReflectKit.setFieldValue(m, column, valueMap.get(column));
                    }
                    obj = m;
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("HQL语句错误 - " + e.getMessage());
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException("HQL语句实体定义错误 - " + e.getMessage());
                }
            } else {
                try {
                    M m = clazz.newInstance();
                    ReflectKit.setFieldValue(m, columns[0], obj);
                    obj = m;
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("HQL语句错误 - " + e.getMessage());
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException("HQL语句实体定义错误 - " + e.getMessage());
                }
            }
        }
        return obj;
    }

    /**
     * 解析PageBean
     *
     * @param criteria 查询
     * @param pageBean 分页Bean
     */
    private void setCriteriaPageBean(Criteria criteria, PageBean pageBean) {
        Map<String, Criteria> asMap = new HashMap<>();
        setCriteriaPageBean(criteria, pageBean, asMap);
    }

    private void setCriteriaPageBean(Criteria criteria, PageBean pageBean, Map<String, Criteria> asMap) {
        if (pageBean.getPage() <= 1)
            criteria.setFirstResult(0);
        else
            criteria.setFirstResult((pageBean.getPage() - 1) * pageBean.getRows());
        criteria.setMaxResults(pageBean.getRows());
        if (pageBean.getOrderBy() != null) {
            criteria.addOrder(pageBean.getOrderBy());
            _analyzeCriterionAlias(criteria, asMap, pageBean.getOrderBy().getPropertyName());
        }
        if (pageBean.getOrders().size() > 0) {
            pageBean.getOrders().forEach(order -> {
                criteria.addOrder(order);
                _analyzeCriterionAlias(criteria, asMap, order.getPropertyName());
            });
        }
        if (ObjectKit.isNotNull(pageBean.getCondition())) {
            setCriteriaCondition(criteria, pageBean.getCondition(), asMap);
        }
    }

    /**
     * 解析Condition
     *
     * @param criteria  查询
     * @param condition 条件
     */
    private void setCriteriaCondition(Criteria criteria, Condition condition) {
        Map<String, Criteria> asMap = new HashMap<>();
        setCriteriaCondition(criteria, condition, asMap);
    }

    private void setCriteriaCondition(Criteria criteria, Condition condition, Map<String, Criteria> asMap) {
        condition.getOrders().forEach(criteria::addOrder);

        for (Criterion criterion : condition.getCriterions()) {
            _analyzeCriterion(criteria, criterion, asMap, true);
        }
        if (condition.getProjections().size() > 0) {
            ProjectionList projList = Projections.projectionList();
            for (Projection proj : condition.getProjections()) {
                projList.add(proj);
            }
            criteria.setProjection(projList);
        }
    }

    /**
     * 绑定参数
     *
     * @param query     查询
     * @param paramlist 参数列表
     */
    private void setQueryParameters(Query query, Object[] paramlist) {
        int pos = 0;
        if(query instanceof NativeQuery)
            pos = 1;
        if (paramlist != null) {
            for (int i = 0; i < paramlist.length; i++) {
                query.setParameter(pos + i, paramlist[i]);
            }
        }
    }

    /**
     * 设置分页
     *
     * @param query    查询
     * @param pageBean 分页bean
     */
    private void setQueryPageBean(Query query, PageBean pageBean) {
        if (pageBean.getPage() <= 1)
            query.setFirstResult(0);
        else
            query.setFirstResult((pageBean.getPage() - 1) * pageBean.getRows());
        query.setMaxResults(pageBean.getRows());

        if (ObjectKit.isNotEmpty(pageBean.getCriterions())) {
            logger.debug("HQL查询模式下，pageBean中的Criterion不生效");
        }
        if (ObjectKit.isNotEmpty(pageBean.getOrders())) {
            logger.debug("HQL查询模式下，pageBean中的Order不生效");
        }
    }

    /**
     * 处理关联表非主键QBC查询
     *
     * @param criteria  查询对象
     * @param criterion 查询条件
     */
    private void _analyzeCriterion(Criteria criteria, Criterion criterion, Map<String, Criteria> asMap, boolean append) {
        if (criterion instanceof SimpleExpression ||
                criterion instanceof NullExpression ||
                criterion instanceof NotNullExpression ||
                criterion instanceof LikeExpression ||
                criterion instanceof BetweenExpression ||
                criterion instanceof InExpression ||
                criteria instanceof EmptyExpression ||
                criteria instanceof NotEmptyExpression ||
                criteria instanceof SizeExpression) {
            String propName = ReflectKit.getFieldValue(criterion, "propertyName") + "";
            _analyzeCriterionAlias(criteria, asMap, propName);
        } else if (criterion instanceof PropertyExpression) {
            PropertyExpression exp = (PropertyExpression) criterion;
            String propName = ReflectKit.getFieldValue(exp, "propertyName") + "";
            String otherPropName = ReflectKit.getFieldValue(exp, "otherPropertyName") + "";

            _analyzeCriterionAlias(criteria, asMap, propName);
            _analyzeCriterionAlias(criteria, asMap, otherPropName);
        } else if (criterion instanceof LogicalExpression) {
            LogicalExpression exp = (LogicalExpression) criterion;

            Criterion lhs = (Criterion) ReflectKit.getFieldValue(exp, "lhs");
            Criterion rhs = (Criterion) ReflectKit.getFieldValue(exp, "rhs");

            _analyzeCriterion(criteria, lhs, asMap, false);
            _analyzeCriterion(criteria, rhs, asMap, false);
        } else if (criterion instanceof Disjunction) { // or
            Disjunction exp = (Disjunction) criterion;

            for (Criterion criterion1 : exp.conditions()) {
                _analyzeCriterion(criteria, criterion1, asMap, false);
            }
        } else if (criterion instanceof Conjunction) { // and
            Conjunction exp = (Conjunction) criterion;

            for (Criterion criterion1 : exp.conditions()) {
                _analyzeCriterion(criteria, criterion1, asMap, false);
            }
        } else if (criterion instanceof NotExpression) { // not
            NotExpression exp = (NotExpression) criterion;

            _analyzeCriterion(criteria, (Criterion) ReflectKit.getFieldValue(exp, "criterion"), asMap, false);
        }
        if (append)
            criteria.add(criterion);
    }

    /**
     * 根据属性名创建查询中的别名
     *
     * @param criteria QBC查询对象
     * @param asMap    别名map
     * @param propName 参数名
     */
    private void _analyzeCriterionAlias(Criteria criteria, Map<String, Criteria> asMap, String propName) {
        int idx = propName.indexOf(".");
        if (idx > 0) {
            String as = propName.substring(0, idx);
            if (as.equals("this")) {
                return;
            }
            Criteria c = asMap.get(as);
            if (ObjectKit.isNull(c))
                c = criteria.createAlias(as, as);
            asMap.put(as, c);
        }
    }


    /********Cache相关********/

    private ICacheProvider getCacheProvider() {
        return ApplicationCommon.getCacheProvider();
    }

    private String getCacheName(Object object, Serializable id) {
        return String.format("%s$%s", object.getClass().getName(), id);
    }

    private void setEntityCache(Object object) {
        Object id = ReflectKit.getFieldValue(object, "id");
        if (id != null) {
            getCacheProvider().put(getCacheName(object, id.toString()), object);
        } else {
            throw new RuntimeException("更新缓存失败，实体必需包含‘id’字段");
        }
    }

    private void setEntityCache(Object object, long expireTime) {
        Object id = ReflectKit.getFieldValue(object, "id");
        if (id != null) {
            getCacheProvider().put(getCacheName(object, id.toString()), object, expireTime);
        } else {
            throw new RuntimeException("更新缓存失败，实体必需包含‘id’字段");
        }
    }

    @Override
    public <M> Serializable saveAndCache(M m) {
        Serializable id = save(m);
        getCacheProvider().put(getCacheName(m, id), m);
        return id;
    }

    @Override
    public <M> Serializable saveAndCache(M m, long expireTime) {
        Serializable id = save(m);
        getCacheProvider().put(getCacheName(m, id), m, expireTime);
        return id;
    }

    @Override
    public <M> Serializable[] saveAndCache(Collection<M> mList) {
        Serializable[] ids = save(mList);
        int i = 0;
        for (M m : mList)
            getCacheProvider().put(getCacheName(m, ids[i++]), m);
        return ids;
    }

    @Override
    public <M> Serializable[] saveAndCache(Collection<M> mList, long expireTime) {
        Serializable[] ids = save(mList);
        int i = 0;
        for (M m : mList)
            getCacheProvider().put(getCacheName(m, ids[i++]), m, expireTime);
        return ids;
    }

    @Override
    public <M> void deleteAndCache(Class<M> clazz, Serializable id) {
        delete(clazz, id);
        deleteOnlyCache(clazz, id);
    }

    @Override
    public <M> void deletesAndCache(Class<M> clazz, Serializable... ids) {
        deletes(clazz, ids);
        deletesOnlyCache(clazz, ids);
    }

    @Override
    public <M> void deletesAndCache(Class<M> clazz, Collection<Serializable> ids) {
        deletes(clazz, ids);
        deletesOnlyCache(clazz, ids);
    }

    @Override
    public <M> void deleteOnlyCache(Class<M> clazz, Serializable id) {
        getCacheProvider().remove(getCacheName(clazz, id));
    }

    @Override
    public <M> void deletesOnlyCache(Class<M> clazz, Serializable... ids) {
        for (Serializable id : ids)
            getCacheProvider().remove(getCacheName(clazz, id));
    }

    @Override
    public <M> void deletesOnlyCache(Class<M> clazz, Collection<Serializable> ids) {
        for (Serializable id : ids)
            getCacheProvider().remove(getCacheName(clazz, id));
    }

    @Override
    public <M> void updateAndCache(M m) {
        update(m);
        setEntityCache(m);
    }

    @Override
    public <M> void updateAndCache(M m, long expireTime) {
        update(m);
        setEntityCache(m, expireTime);
    }

    @Override
    public <M> void updateAndCache(Collection<M> mList) {
        update(mList);
        for (M m : mList) {
            setEntityCache(m);
        }
    }

    @Override
    public <M> void updateAndCache(Collection<M> mList, long expireTime) {
        update(mList);
        for (M m : mList) {
            setEntityCache(m, expireTime);
        }
    }

    @Override
    public <M> void mergeAndCache(M m) {
        merge(m);
        setEntityCache(m);
    }

    @Override
    public <M> void mergeAndCache(M m, long expireTime) {
        merge(m);
        setEntityCache(m, expireTime);
    }

    @Override
    public <M> void mergeAndCache(Collection<M> mList) {
        merge(mList);
        for (M m : mList)
            setEntityCache(m);
    }

    @Override
    public <M> void mergeAndCache(Collection<M> mList, long expireTime) {
        merge(mList);
        for (M m : mList)
            setEntityCache(m, expireTime);
    }

    @Override
    public <M> M getByCache(Class<M> clazz, Serializable id) {
        String cacheName = getCacheName(clazz, id);
        M m = getCacheProvider().get(cacheName);
        if (m == null) {
            m = get(clazz, id);
            if (m != null)
                getCacheProvider().put(cacheName, m);
        }
        return m;
    }

    @Override
    public <M> M getByCache(Class<M> clazz, Serializable id, long expireTime) {
        String cacheName = getCacheName(clazz, id);
        M m = getCacheProvider().get(cacheName);
        if (m == null) {
            m = get(clazz, id);
            if (m != null)
                getCacheProvider().put(cacheName, m, expireTime);
        }
        return m;
    }

    @Override
    public <M> List<M> listByCache(Class<M> clazz, String cacheName, String hql, Object... params) {
        List<M> list = getCacheProvider().get(cacheName);
        if (list == null) {
            list = _list(clazz, hql, params);
            getCacheProvider().put(cacheName, list);
        }
        return list;
    }

    @Override
    public <M> List<M> listByCache(Class<M> clazz, String cacheName, long expireTime, String hql, Object... params) {
        List<M> list = getCacheProvider().get(cacheName);
        if (list == null) {
            list = _list(clazz, hql, params);
            getCacheProvider().put(cacheName, list, expireTime);
        }
        return list;
    }

    @Override
    public <M> List<M> listByCache(Class<M> clazz, String cacheName, PageBean pageBean, String hql, Object... params) {
        cacheName = cacheName + "$" + pageBean.getPage();
        List<M> list = getCacheProvider().get(cacheName);
        if (list == null) {
            list = _list(clazz, pageBean, hql, params);
            getCacheProvider().put(cacheName, list);
        }
        return list;
    }

    @Override
    public <M> List<M> listByCache(Class<M> clazz, String cacheName, long expireTime, PageBean pageBean, String hql, Object... params) {
        cacheName = cacheName + "$" + pageBean.getPage();
        List<M> list = getCacheProvider().get(cacheName);
        if (list == null) {
            list = _list(clazz, pageBean, hql, params);
            getCacheProvider().put(cacheName, list, expireTime);
        }
        return list;
    }

    @Override
    public <M> List<M> listToEntityByCache(Class<M> clazz, String cacheName, String hql, Object... params) {
        List<M> list = getCacheProvider().get(cacheName);
        if (list == null) {
            list = listToEntity(clazz, hql, params);
            getCacheProvider().put(cacheName, list);
        }
        return list;
    }

    @Override
    public <M> List<M> listToEntityByCache(Class<M> clazz, String cacheName, long expireTime, String hql, Object... params) {
        List<M> list = getCacheProvider().get(cacheName);
        if (list == null) {
            list = listToEntity(clazz, hql, params);
            getCacheProvider().put(cacheName, list, expireTime);
        }
        return list;
    }

    @Override
    public <M> List<M> listToEntityByCache(Class<M> clazz, String cacheName, PageBean pageBean, String hql, Object... params) {
        cacheName = cacheName + "$" + pageBean.getPage();
        List<M> list = getCacheProvider().get(cacheName);
        if (list == null) {
            list = listToEntity(clazz, pageBean, hql, params);
            getCacheProvider().put(cacheName, list);
        }
        return list;
    }

    @Override
    public <M> List<M> listToEntityByCache(Class<M> clazz, String cacheName, long expireTime, PageBean pageBean, String hql, Object... params) {
        cacheName = cacheName + "$" + pageBean.getPage();
        List<M> list = getCacheProvider().get(cacheName);
        if (list == null) {
            list = listToEntity(clazz, pageBean, hql, params);
            getCacheProvider().put(cacheName, list, expireTime);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> listToMapByCache(String cacheName, String hql, Object... params) {
        List<Map<String, Object>> list = getCacheProvider().get(cacheName);
        if (list == null) {
            list = listToMap(hql, params);
            getCacheProvider().put(cacheName, list);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> listToMapByCache(String cacheName, long expireTime, String hql, Object... params) {
        List<Map<String, Object>> list = getCacheProvider().get(cacheName);
        if (list == null) {
            list = listToMap(hql, params);
            getCacheProvider().put(cacheName, list, expireTime);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> listToMapByCache(String cacheName, PageBean pageBean, String hql, Object... params) {
        cacheName = cacheName + "$" + pageBean.getPage();
        List<Map<String, Object>> list = getCacheProvider().get(cacheName);
        if (list == null) {
            list = listToMap(pageBean, hql, params);
            getCacheProvider().put(cacheName, list);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> listToMapByCache(String cacheName, long expireTime, PageBean pageBean, String hql, Object... params) {
        cacheName = cacheName + "$" + pageBean.getPage();
        List<Map<String, Object>> list = getCacheProvider().get(cacheName);
        if (list == null) {
            list = listToMap(pageBean, hql, params);
            getCacheProvider().put(cacheName, list, expireTime);
        }
        return list;
    }

    @Override
    public Records listToRecordByCache(String cacheName, String hql, Object... params) {
        Records records = getCacheProvider().get(cacheName);
        if (records == null) {
            records = listToRecord(hql, params);
            getCacheProvider().put(cacheName, records);
        }
        return records;
    }

    @Override
    public Records listToRecordByCache(String cacheName, long expireTime, String hql, Object... params) {
        Records records = getCacheProvider().get(cacheName);
        if (records == null) {
            records = listToRecord(hql, params);
            getCacheProvider().put(cacheName, records, expireTime);
        }
        return records;
    }

    @Override
    public Records listToRecordByCache(String cacheName, PageBean pageBean, String hql, Object... params) {
        cacheName = cacheName + "$" + pageBean.getPage();
        Records records = getCacheProvider().get(cacheName);
        if (records == null) {
            records = listToRecord(pageBean, hql, params);
            getCacheProvider().put(cacheName, records);
        }
        return records;
    }

    @Override
    public Records listToRecordByCache(String cacheName, long expireTime, PageBean pageBean, String hql, Object... params) {
        cacheName = cacheName + "$" + pageBean.getPage();
        Records records = getCacheProvider().get(cacheName);
        if (records == null) {
            records = listToRecord(pageBean, hql, params);
            getCacheProvider().put(cacheName, records, expireTime);
        }
        return records;
    }

    @Override
    public List<Object> nativeListByCache(String cacheName, String sql, Object... params) {
        List<Object> list = getCacheProvider().get(cacheName);
        if (list == null) {
            list = nativeList(sql, params);
            getCacheProvider().put(cacheName, list);
        }
        return list;
    }

    @Override
    public List<Object> nativeListByCache(String cacheName, long expireTime, String sql, Object... params) {
        List<Object> list = getCacheProvider().get(cacheName);
        if (list == null) {
            list = nativeList(sql, params);
            getCacheProvider().put(cacheName, list, expireTime);
        }
        return list;
    }

    @Override
    public List<Object> nativeListByCache(String cacheName, PageBean pageBean, String sql, Object... params) {
        cacheName = cacheName + "$" + pageBean.getPage();
        List<Object> list = getCacheProvider().get(cacheName);
        if (list == null) {
            list = nativeList(pageBean, sql, params);
            getCacheProvider().put(cacheName, list);
        }
        return list;
    }

    @Override
    public List<Object> nativeListByCache(String cacheName, long expireTime, PageBean pageBean, String sql, Object... params) {
        cacheName = cacheName + "$" + pageBean.getPage();
        List<Object> list = getCacheProvider().get(cacheName);
        if (list == null) {
            list = nativeList(pageBean, sql, params);
            getCacheProvider().put(cacheName, list, expireTime);
        }
        return list;
    }

    @Override
    public <M> List<M> nativeListToEntityByCache(String cacheName, Class<M> clazz, String sql, Object... params) {
        List<M> list = getCacheProvider().get(cacheName);
        if (list == null) {
            list = nativeListToEntity(clazz, sql, params);
            getCacheProvider().put(cacheName, list);
        }
        return list;
    }

    @Override
    public <M> List<M> nativeListToEntityByCache(String cacheName, long expireTime, Class<M> clazz, String sql, Object... params) {
        List<M> list = getCacheProvider().get(cacheName);
        if (list == null) {
            list = nativeListToEntity(clazz, sql, params);
            getCacheProvider().put(cacheName, list, expireTime);
        }
        return list;
    }

    @Override
    public <M> List<M> nativeListToEntityByCache(String cacheName, Class<M> clazz, PageBean pageBean, String sql, Object... params) {
        cacheName = cacheName + "$" + pageBean.getPage();
        List<M> list = getCacheProvider().get(cacheName);
        if (list == null) {
            list = nativeListToEntity(clazz, sql, params);
            getCacheProvider().put(cacheName, list);
        }
        return list;
    }

    @Override
    public <M> List<M> nativeListToEntityByCache(String cacheName, long expireTime, Class<M> clazz, PageBean pageBean, String sql, Object... params) {
        cacheName = cacheName + "$" + pageBean.getPage();
        List<M> list = getCacheProvider().get(cacheName);
        if (list == null) {
            list = nativeListToEntity(clazz, sql, params);
            getCacheProvider().put(cacheName, list, expireTime);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> nativeListToMapByCache(String cacheName, String sql, Object... params) {
        List<Map<String, Object>> list = getCacheProvider().get(cacheName);
        if (list == null) {
            list = nativeListToMap(sql, params);
            getCacheProvider().put(cacheName, list);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> nativeListToMapByCache(String cacheName, long expireTime, String sql, Object... params) {
        List<Map<String, Object>> list = getCacheProvider().get(cacheName);
        if (list == null) {
            list = nativeListToMap(sql, params);
            getCacheProvider().put(cacheName, list, expireTime);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> nativeListToMapByCache(String cacheName, PageBean pageBean, String sql, Object... params) {
        cacheName = cacheName + "$" + pageBean.getPage();
        List<Map<String, Object>> list = getCacheProvider().get(cacheName);
        if (list == null) {
            list = nativeListToMap(sql, params);
            getCacheProvider().put(cacheName, list);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> nativeListToMapByCache(String cacheName, long expireTime, PageBean pageBean, String sql, Object... params) {
        cacheName = cacheName + "$" + pageBean.getPage();
        List<Map<String, Object>> list = getCacheProvider().get(cacheName);
        if (list == null) {
            list = nativeListToMap(sql, params);
            getCacheProvider().put(cacheName, list, expireTime);
        }
        return list;
    }

    @Override
    public Records nativeListToRecordByCache(String cacheName, String sql, Object... params) {
        Records records = getCacheProvider().get(cacheName);
        if (records == null) {
            records = nativeListToRecord(sql, params);
            getCacheProvider().put(cacheName, records);
        }
        return records;
    }

    @Override
    public Records nativeListToRecordByCache(String cacheName, long expireTime, String sql, Object... params) {
        Records records = getCacheProvider().get(cacheName);
        if (records == null) {
            records = nativeListToRecord(sql, params);
            getCacheProvider().put(cacheName, records, expireTime);
        }
        return records;
    }

    @Override
    public Records nativeListToRecordByCache(String cacheName, PageBean pageBean, String sql, Object... params) {
        cacheName = cacheName + "$" + pageBean.getPage();
        Records records = getCacheProvider().get(cacheName);
        if (records == null) {
            records = nativeListToRecord(sql, params);
            getCacheProvider().put(cacheName, records);
        }
        return records;
    }

    @Override
    public Records nativeListToRecordByCache(String cacheName, long expireTime, PageBean pageBean, String sql, Object... params) {
        cacheName = cacheName + "$" + pageBean.getPage();
        Records records = getCacheProvider().get(cacheName);
        if (records == null) {
            records = nativeListToRecord(sql, params);
            getCacheProvider().put(cacheName, records, expireTime);
        }
        return records;
    }

    @Override
    public Long countByCache(String cacheName, String hql, Object... params) {
        Long count = getCacheProvider().get(cacheName);
        if (count == null) {
            count = count(hql, params);
            getCacheProvider().put(cacheName, count);
        }
        return count;
    }

    @Override
    public Long countByCache(String cacheName, long expireTime, String hql, Object... params) {
        Long count = getCacheProvider().get(cacheName);
        if (count == null) {
            count = count(hql, params);
            getCacheProvider().put(cacheName, count, expireTime);
        }
        return count;
    }

    @Override
    public BigInteger nativeCountByCache(String cacheName, String sql, Object... params) {
        BigInteger count = getCacheProvider().get(cacheName);
        if (count == null) {
            count = nativeCount(sql, params);
            getCacheProvider().put(cacheName, count);
        }
        return count;
    }

    @Override
    public BigInteger nativeCountByCache(String cacheName, long expireTime, String sql, Object... params) {
        BigInteger count = getCacheProvider().get(cacheName);
        if (count == null) {
            count = nativeCount(sql, params);
            getCacheProvider().put(cacheName, count, expireTime);
        }
        return count;
    }

    @Override
    public void removeCache(String cacheName) {
        getCacheProvider().remove(cacheName);
    }

    @Override
    public void removeCacheByPrefix(String cacheNamePrefix) {
        getCacheProvider().removeByPrefix(cacheNamePrefix);
    }
}
