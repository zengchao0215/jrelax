package com.jrelax.orm.dao;

import com.jrelax.orm.query.Condition;
import com.jrelax.orm.query.PageBean;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 对数据库的基本操作
 * 查询条件封装类:Restrictions
 * 派讯条件封装类:Order
 *
 * @author ZENGCHAO
 */
public interface IBaseDao {
    /**
     * 获取SessionFactory
     *
     * @return
     */
    SessionFactory getSessionFactory();

    /**
     * 获取Session
     * 所有的操作，包括查询，增删改都要使用该对象
     * 在提供的方法满足不了业务需求是调用此对象进行操作
     *
     * @return
     */
    Session getSession();

    /**
     * 获取查询对象Query
     *
     * @return
     */
    Query getQuery(String hql);

    /**
     * 获取查询对象SQLQuery
     *
     * @param sql
     * @return
     */
    SQLQuery getSQLQuery(String sql);

    /**
     * 获取查询对象Criteria
     *
     * @return
     */
    Criteria getCriteria(Class<?> clazz);

}
