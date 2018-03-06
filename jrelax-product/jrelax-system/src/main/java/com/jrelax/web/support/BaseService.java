package com.jrelax.web.support;

import com.jrelax.kit.ObjectKit;
import com.jrelax.orm.dao.ILazyInitializer;
import com.jrelax.orm.dao.impl.BaseHibernateDao;
import com.jrelax.orm.query.Condition;
import com.jrelax.orm.query.PageBean;
import com.jrelax.orm.support.DBType;
import com.jrelax.orm.support.Record;
import com.jrelax.orm.support.Records;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class BaseService<M> extends BaseObject {
    @Resource
    protected BaseHibernateDao baseDao;
    private Class<M> entityClass;
    private ThreadLocal<ILazyInitializer<M>> lazyTreadLocal = new ThreadLocal<>();
    @Resource
    private BaseCacheService<M> cacheService;
    @Resource
    private BaseJPAService<M> jpaService;

    public BaseCacheService<M> getCacheService() {
        cacheService.setEntityClass(this.entityClass);
        return cacheService;
    }

    public BaseJPAService<M> getJPAService() {
        jpaService.setEntityClass(this.entityClass);
        return jpaService;
    }

    /**
     * 设置延迟加载初始化器
     *
     * @param lazyInit 延迟加载
     */
    public void setLazyInitializer(ILazyInitializer<M> lazyInit) {
        lazyTreadLocal.set(lazyInit);
    }

    /**
     * 切换数据源
     *
     * @param dataSourceName 数据源在配置文件中配置的名称
     */
    public void setDataSource(String dataSourceName) {
        baseDao.setDataSource(dataSourceName);
    }

    /**
     * 恢复为默认数据源
     * 在同一个方法中，如果存在以下情况，则需要调用该方法恢复为默认数据源
     * 1. 切换完数据库之后需要操作默认数据库时
     */
    public void restoreDefaultDataSource() {
        baseDao.restoreDefaultDataSource();
    }

    /**
     * 获取BaseDao
     *
     * @return
     */
    public BaseHibernateDao getBaseDao() {
        return this.baseDao;
    }

    public DBType getDBType() {
        SessionFactoryImplementor sfi = ((SessionImpl) getBaseDao().getSession()).getFactory();
        String dbType = sfi.getDialect().getClass().getName().toLowerCase();
        if (dbType.contains("oracle"))
            return DBType.Oracle;
        else if (dbType.contains("mysql"))
            return DBType.Mysql;

        return DBType.Oracle;
    }

    /**
     * 获取<M>的Class对象
     */
    @SuppressWarnings("unchecked")
    public BaseService() {
        Class<?> c = this.getClass();
        Type t = c.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            this.entityClass = (Class<M>) p[0];
        } else {
            this.entityClass = null;
        }
    }

    public void setEntityClass(Class<M> entityClass) {
        this.entityClass = entityClass;
    }

    /****************************Query*********************************/

    /**
     * 根据Id获取<M>
     *
     * @param id
     * @return
     */
    public M getById(Serializable id) {
        return _lazyInitHandler(baseDao.get(entityClass, id));
    }

    /**
     * 根据Id获取<M>
     *
     * @param id
     * @return
     */
    public M loadById(Serializable id) {
        return _lazyInitHandler(baseDao.load(entityClass, id));
    }

    /**
     * 根据多个ID获取多个<M>
     *
     * @param ids
     * @return
     */
    public List<M> getByIds(Object[] ids) {
        return _lazyInitHandler(baseDao.list(entityClass, Condition.NEW().in("id", ids)));
    }

    /**
     * 根据Name获取<M>
     *
     * @param name
     * @return
     */
    public M getByName(String name) {
        return _lazyInitHandler(baseDao.get(entityClass, Restrictions.eq("name", name)));
    }

    /**
     * 根据属性名获取<M>
     *
     * @param propName
     * @param propValue
     * @return
     */
    public M getByProperty(String propName, Object propValue) {
        return _lazyInitHandler(baseDao.get(entityClass, Condition.NEW().eq(propName, propValue)));
    }

    /**
     * 根据多个的查询条件查询单个对象
     *
     * @param criterions
     * @return
     */
    public M get(Criterion... criterions) {
        return _lazyInitHandler(baseDao.get(entityClass, criterions));
    }

    public M get(String hql, Object... params) {
        return _lazyInitHandler(baseDao.get(entityClass, hql, params));
    }

    /**
     * 根据条件获取对象
     * 如果有多条符合数据，则只返回首条数据
     *
     * @param condition 查询条件
     * @return
     */
    public M get(Condition condition) {
        return _lazyInitHandler(baseDao.get(entityClass, condition));
    }

    public M load(Serializable id) {
        return _lazyInitHandler(baseDao.load(entityClass, id));
    }

    /**
     * 使用HQL语句查询单个值
     *
     * @param hql
     * @return
     */
    public Object getObject(String hql, Object... params) {
        return baseDao.getObject(hql, params);
    }

    public Integer getInt(String hql, Object... params) {
        return baseDao.getInt(hql, params);
    }

    public Integer getIntOrDefault(String hql, Integer defaultValue, Object... params) {
        return baseDao.getIntOrDefault(hql, defaultValue, params);
    }

    public Long getLong(String hql, Object... params) {
        return baseDao.getLong(hql, params);
    }

    public Long getLongOrDefault(String hql, Long defaultValue, Object... params) {
        return baseDao.getLongOrDefault(hql, defaultValue, params);
    }

    public Double getDouble(String hql, Object... params) {
        return baseDao.getDouble(hql, params);
    }

    public Double getDoubleOrDefault(String hql, Double defaultValue, Object... params) {
        return baseDao.getDoubleOrDefault(hql, defaultValue, params);
    }

    public BigDecimal getBigDecimal(String hql, Object... params) {
        Object value = baseDao.getObject(hql, params);
        if (value == null) return null;
        if (value instanceof BigDecimal)
            return (BigDecimal) value;
        return null;
    }

    public BigDecimal getBigDecimalOrDefault(String hql, BigDecimal defaultValue, Object... params) {
        BigDecimal value = getBigDecimal(hql, params);
        return value == null ? defaultValue : value;
    }

    public String getString(String hql, Object... params) {
        return baseDao.getString(hql, params);
    }

    public String getStringOrDefault(String hql, String defaultValue, Object... params) {
        return baseDao.getString(hql, defaultValue, params);
    }

    public Boolean getBoolean(String hql, Object... params) {
        return baseDao.getBoolean(hql, params);
    }

    public Boolean getBooleanOrDefault(String hql, Boolean defaultValue, Object... params) {
        return baseDao.getBooleanOrDefault(hql, defaultValue, params);
    }

    public Map<String, Object> getMap(String hql, Object... params) {
        return baseDao.getMap(hql, params);
    }

    public List<Object> getList(String hql, Object... params) {
        return baseDao.getList(hql, params);
    }

    public Record getRecord(String hql, Object... params) {
        return baseDao.getRecord(hql, params);
    }

    /**
     * 获取实体类的名称
     *
     * @param object
     * @return
     */
    public String getEntityName(Object object) {
        return baseDao.getEntityName(object);
    }


    /**
     * 获取全部数据
     *
     * @return
     */
    public List<M> list() {
        return _lazyInitHandler(baseDao.listAll(entityClass));
    }

    /**
     * 根据HQL查询
     *
     * @param hql
     * @return
     */
    public List<M> list(String hql) {
        return _lazyInitHandler(baseDao.list(entityClass, hql));
    }

    /**
     * 自定实体查询
     *
     * @param hql
     * @return
     */
    public List<M> listToEntity(String hql) {
        return _lazyInitHandler(this.baseDao.listToEntity(this.entityClass, hql));
    }

    /**
     * 获取集合 分页
     *
     * @param condition 查询条件
     * @param pageBean  分页bean
     * @return
     */
    public List<M> list(Condition condition, PageBean pageBean) {
        setTotalCount(pageBean, condition);
        pageBean.setCondition(condition);
        return _lazyInitHandler(baseDao.list(entityClass, pageBean));
    }

    /**
     * 分组查询
     *
     * @param projs
     * @return
     */
    public List<M> group(Projection... projs) {
        return _lazyInitHandler(this.baseDao.list(entityClass, Condition.NEW().addProjection(projs)));
    }

    /**
     * 使用SQL查询单个String值
     *
     * @param sql
     * @return
     */
    public String nativeGetString(String sql, Object... params) {
        return baseDao.nativeGetString(sql, params);
    }

    public String nativeGetStringOrDefault(String sql, String defaultValue, Object... params) {
        return baseDao.nativeGetStringOrDefault(sql, defaultValue, params);
    }

    public boolean nativeGetBoolean(String sql, Object... params) {
        return baseDao.nativeGetBoolean(sql, params);
    }

    public boolean nativeGetBooleanOrDefault(String sql, Boolean defaultValue, Object... params) {
        return baseDao.nativeGetBooleanOrDefault(sql, defaultValue, params);
    }

    public Map<String, Object> nativeGetMap(String sql, Object... params) {
        return baseDao.nativeGetMap(sql, params);
    }

    public List<Object> nativeGetList(String sql, Object... params) {
        return baseDao.nativeGetList(sql, params);
    }

    public Record nativeGetRecord(String sql, Object... params) {
        return baseDao.nativeGetRecord(sql, params);
    }

    public List<M> listAll() {
        return baseDao.listAll(entityClass);
    }

    /**
     * 分页查询
     *
     * @param pageBean 分页bean
     * @return
     */
    public List<M> list(PageBean pageBean) {
        setTotalCount(pageBean);
        return _lazyInitHandler(baseDao.list(entityClass, pageBean));
    }

    /**
     * 自定实体查询
     *
     * @param hql
     * @return
     */
    public List<M> listToEntity(String hql, Object... params) {
        return _lazyInitHandler(this.baseDao.listToEntity(this.entityClass, hql, params));
    }

    /**
     * 自定实体查询
     *
     * @param hql
     * @return
     */
    public List<M> listToEntity(PageBean pageBean, String hql, Object... params) {
        setTotalCount(pageBean, hql, params);
        return _lazyInitHandler(this.baseDao.listToEntity(this.entityClass, pageBean, hql, params));
    }

    /**
     * 获取集合
     *
     * @param condition 需要查询字段
     * @return
     */
    public List<M> list(Condition condition) {
        return _lazyInitHandler(baseDao.list(entityClass, condition));
    }


    public List<M> list(String hql, Object... params) {
        return _lazyInitHandler(baseDao.list(entityClass, hql, params));
    }

    public List<M> list(PageBean pageBean, String hql, Object... params) {
        setTotalCount(pageBean, hql, params);
        return _lazyInitHandler(baseDao.list(entityClass, pageBean, hql, params));
    }

    public List<Map<String, Object>> listToMap(String hql, Object... params) {
        return baseDao.listToMap(hql, params);
    }

    public List<Map<String, Object>> listToMap(PageBean pageBean, String hql, Object... params) {
        setTotalCount(pageBean, hql, params);
        return baseDao.listToMap(pageBean, hql, params);
    }

    public List<Object> listToObject(String hql, Object... params) {
        return baseDao.listToObject(hql, params);
    }

    public List<Object> listToObject(PageBean pageBean, String hql, Object... params) {
        setTotalCount(pageBean, hql, params);
        return baseDao.listToObject(pageBean, hql, params);
    }

    public List<Object[]> listToArray(String hql, Object... params) {
        return baseDao.listToArray(hql, params);
    }

    public List<Object[]> listToArray(PageBean pageBean, String hql, Object... params) {
        setTotalCount(pageBean, hql, params);
        return baseDao.listToArray(pageBean, hql, params);
    }

    public Records listToRecord(String hql, Object... params) {
        return baseDao.listToRecord(hql, params);
    }

    public Records listToRecord(PageBean pageBean, String hql, Object... params) {
        setTotalCount(pageBean, hql, params);
        return baseDao.listToRecord(pageBean, hql, params);
    }

    public Integer countAll() {
        return baseDao.countAll(entityClass).intValue();
    }

    public Integer count(Condition condition) {
        return baseDao.count(entityClass, condition).intValue();
    }

    public Integer count(String hql, Object... params) {
        return baseDao.count(hql, params).intValue();
    }

    public Integer count(Criterion... citrons) {
        return baseDao.count(entityClass, citrons).intValue();
    }

    public boolean has(Condition condition) {
        return count(condition) > 0;
    }

    public boolean has(String hql, Object... params) {
        return count(hql, params) > 0;
    }

    public boolean has(Criterion... citrons) {
        return count(citrons) > 0;
    }

    public M nativeGet(String sql, Object... params) {
        return baseDao.nativeGet(entityClass, sql, params);
    }

    public Object nativeGetObject(String sql, Object... params) {
        return baseDao.nativeGetObject(sql, params);
    }

    /**
     * 使用SQL查询单个int值
     *
     * @param sql
     * @return
     */
    public Integer nativeGetInt(String sql, Object... params) {
        return baseDao.nativeGetInt(sql, params);
    }

    public Integer nativeGetIntOrDefault(String sql, Integer defaultValue, Object... params) {
        return baseDao.nativeGetIntOrDefault(sql, defaultValue, params);
    }

    /**
     * 使用sql查询单个Long值
     *
     * @param sql
     * @return
     */
    public Long nativeGetLong(String sql, Object... params) {
        return baseDao.nativeGetLong(sql, params);
    }

    public Long nativeGetLongOrDefault(String sql, Long defaultValue, Object... params) {
        return baseDao.nativeGetLongOrDefault(sql, defaultValue, params);
    }

    public Double nativeGetDouble(String sql, Object... params) {
        return baseDao.nativeGetDouble(sql, params);
    }

    public Double nativeGetDoubleOrDefault(String sql, Double defaultValue, Object... params) {
        return baseDao.nativeGetDoubleOrDefault(sql, defaultValue, params);
    }

    /**
     * 使用SQL查询多列值
     *
     * @param sql
     * @return
     */
    public List<Map<String, Object>> nativeListToMap(String sql, Object... params) {
        return baseDao.nativeListToMap(sql, params);
    }

    /**
     * 使用SQL查询多列值
     *
     * @param sql
     * @return
     */
    public List<Map<String, Object>> nativeListToMap(PageBean pageBean, String sql, Object... params) {
        setTotalCountToNative(pageBean, sql, params);
        return baseDao.nativeListToMap(pageBean, sql, params);
    }

    public List<Object[]> nativeListToArray(String sql, Object... params) {
        return baseDao.nativeListToArray(sql, params);
    }

    public List<Object[]> nativeListToArray(PageBean pageBean, String sql, Object... params) {
        setTotalCountToNative(pageBean, sql, params);
        return baseDao.nativeListToArray(pageBean, sql, params);
    }

    public List<M> nativeListToEntity(String sql, Object... params) {
        return baseDao.nativeListToEntity(entityClass, sql, params);
    }

    public List<M> nativeListToEntity(PageBean pageBean, String sql, Object... params) {
        setTotalCountToNative(pageBean, sql, params);
        return baseDao.nativeListToEntity(entityClass, pageBean, sql, params);
    }

    public int nativeCount(String sql, Object... params) {
        return baseDao.nativeCount(sql, params).intValue();
    }

    /**
     * 使用SQL语句查询
     *
     * @param sql
     * @return
     */
    public List<?> nativeList(String sql, Object... params) {
        return baseDao.nativeList(sql, params);
    }

    public List<Object> nativeList(PageBean pageBean, String sql, Object... params) {
        return baseDao.nativeList(pageBean, sql, params);
    }

    public Records nativeListToRecord(String sql, Object... params) {
        return baseDao.nativeListToRecord(sql, params);
    }

    public Records nativeListToRecord(PageBean pageBean, String sql, Object... params) {
        return baseDao.nativeListToRecord(pageBean, sql, params);
    }

    public Blob getBlob(Object obj) {

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            ByteArrayInputStream bis = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            Blob blob = Hibernate.getLobCreator(baseDao.getSession()).createBlob(bis, (long) bis.available());
            bis.close();

            return blob;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取总条数
     *
     * @param pageBean
     */
    protected void setTotalCount(PageBean pageBean) {
        if (ObjectKit.isNotNull(pageBean.getCriterions()) && ObjectKit.isNotEmpty(pageBean.getCriterions())) {
            Condition condition = Condition.NEW();
            condition.setCriterions(pageBean.getCriterions());
            pageBean.setTotalCount(baseDao.count(entityClass, condition).intValue());
        } else
            pageBean.setTotalCount(baseDao.count(entityClass).intValue());
    }

    /**
     * 获取总条数
     *
     * @param pageBean
     * @param hql
     * @param params
     */
    protected void setTotalCount(PageBean pageBean, String hql, Object... params) {
        hql = "select count(*) " + hql.substring(hql.indexOf("from"));
        pageBean.setTotalCount(baseDao.count(hql, params));
    }

    /**
     * 获取总条数
     *
     * @param pageBean
     * @param sql
     * @param params
     */
    protected void setTotalCountToNative(PageBean pageBean, String sql, Object... params) {
        sql = "select count(*) from (" + sql + ") as a";
        pageBean.setTotalCount(baseDao.nativeCount(sql, params));
    }

    /**
     * 获取总数
     *
     * @param pageBean
     * @param condition
     */
    private void setTotalCount(PageBean pageBean, Condition condition) {
        if (ObjectKit.isNotNull(pageBean.getCriterions()) && ObjectKit.isNotEmpty(pageBean.getCriterions()))
            condition.getCriterions().addAll(pageBean.getCriterions());
        if (ObjectKit.isNotEmpty(condition.getCriterions()))
            pageBean.setTotalCount(baseDao.count(entityClass, condition));
        else
            pageBean.setTotalCount(baseDao.count(entityClass));
    }

    /**********************************Persisten*************************************/

    /**
     * 保存<M>
     *
     * @param m
     */
    public void save(M m) {
        baseDao.save(m);
    }

    /**
     * 批量保存
     *
     * @param mList
     */
    public void save(Collection<M> mList) {
        baseDao.save(mList);
    }

    /**
     * 保存或更新
     *
     * @param m
     */
    public void saveOrUpdate(M m) {
        baseDao.saveOrUpdate(m);
    }

    /**
     * 更新<M>
     *
     * @param m
     */
    public void update(M m) {
        baseDao.update(m);
    }

    /**
     * 更新<M>
     *
     * @param mList
     */
    public void update(Collection<M> mList) {
        baseDao.update(mList);
    }

    /**
     * 更新<M>
     *
     * @param m
     */
    public M merge(M m) {
        return baseDao.merge(m);
    }

    /**
     * 更新<M>
     *
     * @param mList
     */
    public Collection<M> merge(Collection<M> mList) {
        return baseDao.merge(mList);
    }

    /**
     * 根据ID删除<M>
     *
     * @param id
     */
    public void delete(Serializable id) {
        baseDao.delete(entityClass, id);
    }

    /**
     * 刷新
     *
     * @param m
     */
    public void refresh(M m) {
        baseDao.refresh(m);
    }

    /**
     * 从session缓存中清除
     *
     * @param m
     */
    public void evict(M m) {
        baseDao.evict(m);
    }

    /**
     * 根据多个id删除
     *
     * @param ids
     */
    public void delete(Serializable[] ids) {
        StringBuffer strIds = new StringBuffer();
        for (Serializable id : ids)
            strIds.append("," + id);
        String hql = "delete " + entityClass.getSimpleName() + " where id in(" + strIds.substring(1) + ")";
        baseDao.executeBatch(hql);
    }

    /**
     * 删除<M>
     *
     * @param m
     */
    public void delete(M m) {
        baseDao.delete(m);
    }

    /**
     * 根据主键删除数据
     *
     * @param ids 主键值数组
     */
    public void deletes(Serializable... ids) {
        baseDao.deletes(entityClass, ids);
    }

    /**
     * 根据主键删除数据
     *
     * @param idList 主键值集合
     */
    public void deletes(Collection<Serializable> idList) {
        baseDao.deletes(entityClass, idList);
    }

    /**
     * 复制
     *
     * @param source 原对象
     * @param target 目标对象
     * @return 返回复制后的target对象
     */
    public M copy(M source, M target) {
        //复制对象，排除主键id
        BeanUtils.copyProperties(source, target, "id");
        //保存
        this.save(target);
        return target;
    }

    /**
     * 执行HQL语句
     *
     * @param hql
     * @param params 参数列表
     * @return
     */
    public int executeBatch(String hql, Object... params) {
        return baseDao.executeBatch(hql, params);
    }

    /**
     * 批量执行HQL语句
     *
     * @param hql
     * @param params
     * @return
     */
    public int[] executeBatch(String hql, Object[][] params) {
        return baseDao.executeBatch(hql, params);
    }

    /**
     * 执行SQL语句
     *
     * @param sql
     * @param params 参数列表
     * @return
     */
    public int executeSqlBatch(String sql, Object... params) {
        return baseDao.executeSqlBatch(sql, params);
    }

    /**
     * 批量执行SQL语句
     *
     * @param sql
     * @param params
     * @return
     */
    public int[] executeSqlBatch(String sql, Object[][] params) {
        return baseDao.executeSqlBatch(sql, params);
    }

    /**************解决延迟加载属性立即加载问题*************/
    private M _lazyInitHandler(M m) {
        ILazyInitializer<M> initializer = lazyTreadLocal.get();
        if (ObjectKit.isNotNull(initializer) && ObjectKit.isNotNull(m)) {
            initializer.init(m);
        }
        return m;
    }

    private List<M> _lazyInitHandler(List<M> mList) {
        ILazyInitializer<M> initializer = lazyTreadLocal.get();
        if (ObjectKit.isNotNull(initializer)) {
            if (ObjectKit.isNotNull(mList) && mList.size() > 0) {
                for (M m : mList) {
                    initializer.init(m);
                }
            }
        }
        return mList;
    }
}