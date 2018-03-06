package com.jrelax.orm.dao.impl;

import com.jrelax.orm.dao.IBaseDao;
import com.jrelax.orm.dao.ICacheDao;
import com.jrelax.orm.dao.IPersistentDao;
import com.jrelax.orm.dao.IQueryDao;
import org.hibernate.Criteria;
import org.hibernate.StatelessSession;
import org.hibernate.query.Query;
import org.hibernate.query.NativeQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.jrelax.core.support.ApplicationContextHelper;
import com.jrelax.orm.datasource.DataSourceSwitcher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;

/**
 * 数据库基础操作类的父类
 * 实现IBaseDao接口、IQueryDao、IPersistentDao
 *
 * @author ZENGCHAO
 */
public abstract class BaseHibernateDao implements IBaseDao, IQueryDao, IPersistentDao, ICacheDao {
    public abstract Session getSession();

    /**
     * 打开Session
     * @return
     */
    public abstract Session openSession();

    /**
     * 无状态Session，不参与一二级缓存，性能更加接近于JDBC
     * @return session
     */
    public abstract StatelessSession openStatelessSession();

    public abstract Query getQuery(String hql);

    public abstract NativeQuery getSQLQuery(String sql);

    public abstract Criteria getCriteria(Class<?> clazz);

    public abstract SessionFactory getSessionFactory();

    public abstract EntityManagerFactory getEntityManagerFactory();

    public abstract EntityManager getEntityManager();

    public abstract CriteriaBuilder getCriteriaBuilder();

    /**
     * 切换为指定数据源
     *
     * @param dataSourceName
     */
    public void setDataSource(String dataSourceName) {
        DataSourceSwitcher dataSource = ApplicationContextHelper.getInstance().getBean(DataSourceSwitcher.class);
        dataSource.setDataSource(dataSource.getDataSource(dataSourceName));
    }

    /**
     * 恢复为默认数据源
     * 无需手动调用
     */
    public void restoreDefaultDataSource() {
        DataSourceSwitcher dataSource = ApplicationContextHelper.getInstance().getBean(DataSourceSwitcher.class);
        dataSource.restoreDefaultDataSource();
    }
}
