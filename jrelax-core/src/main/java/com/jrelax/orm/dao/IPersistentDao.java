package com.jrelax.orm.dao;

import org.hibernate.LockMode;
import org.hibernate.ReplicationMode;

import java.io.Serializable;
import java.util.Collection;

/**
 * 持久化接口
 * Created by zengc on 2016-05-18.
 */
public interface IPersistentDao {
    /**
     * 保存对象到数据库
     * 无需主键值
     * 保存成功后，对象拥有主键值
     *
     * @param <M>
     * @param m   要持久化的对象
     * @return
     */
    <M> Serializable save(M m);

    /**
     * 批量保存对象到数据库
     * 无需主键值
     * 保存成功后，对象拥有主键值
     *
     * @param <M>
     * @param mList 要持久化的对象
     * @return
     */
    <M> Serializable[] save(Collection<M> mList);

    /**
     * 保存对象到数据库
     * 此方法不会讲数据库生成的主键值绑定到对象中
     *
     * @param <M>
     * @param m   要持久化的对象
     */
    <M> void persist(M m);

    /**
     * 批量保存对象到数据库
     * 此方法不会讲数据库生成的主键值绑定到对象中
     *
     * @param <M>
     * @param mList 要持久化的对象
     */
    <M> void persist(Collection<M> mList);

    /**
     * 保存或者更新对象
     * 如果对象主键值存在，则执行修改，否则执行增加
     *
     * @param <M>
     * @param m   要保存或者修改的对象
     */
    <M> void saveOrUpdate(M m);

    /**
     * 保存或者更新对象
     * 如果对象主键值存在，则执行修改，否则执行增加
     *
     * @param <M>
     * @param mList 要保存或者修改的对象
     */
    <M> void saveOrUpdate(Collection<M> mList);

    /**
     * 删除对象
     * 无需设置对象的所有属性值，只需主键值即可
     * 其他值如果存在，将被加入删除条件中
     *
     * @param <M>
     * @param m   需要删除的对象
     */
    <M> void delete(M m);

    /**
     * 根据主键删除数据
     *
     * @param <M>
     * @param clazz 要删除的对象的Class
     * @param id    主键值
     */
    <M> void delete(Class<M> clazz, Serializable id);

    /**
     * 根据主键批量删除数据
     *
     * @param <M>
     * @param clazz 要删除的对象的Class
     * @param ids   主键值数组
     */
    <M> void deletes(Class<M> clazz, Serializable... ids);

    /**
     * 根据主键批量删除数据
     *
     * @param <M>
     * @param clazz 要删除的对象的Class
     * @param ids   主键值集合
     */
    <M> void deletes(Class<M> clazz, Collection<Serializable> ids);

    /**
     * 更新对象
     * 对象的主键值必须不为空，否则无法更新
     *
     * @param <M>
     * @param m   要更新的对象
     */
    <M> void update(M m);

    /**
     * 批量更新对象
     * 对象的主键值必须不为空，否则无法更新
     *
     * @param <M>
     * @param mList 要更新的对象集合
     */
    <M> void update(Collection<M> mList);

    /**
     * 只修改更改过的属性
     *
     * @param m
     */
    <M> M merge(M m);

    /**
     * 只修改更改过的属性 - 批量
     *
     * @param mList 对象集合
     */
    <M> Collection<M> merge(Collection<M> mList);

    /**
     * 刷新
     *
     * @param m
     */
    <M> void refresh(M m);

    /**
     * 根据指定的锁定模式（LockMode），从数据库中重新读取给定实例的状态
     *
     * @param m
     * @param lockMode
     * @param <M>
     */
    <M> void refresh(M m, LockMode lockMode);

    /**
     * 刷新
     *
     * @param mList
     */
    <M> void refresh(Collection<M> mList);

    /**
     * 将当前对象实例从session缓存中清除
     *
     * @param m
     * @param <M>
     */
    <M> void evict(M m);

    /**
     * 使用当前的标识值持久化给定的游离状态（Transient）的实体
     *
     * @param m
     * @param replicationMode
     * @param <M>
     */
    <M> void replicate(M m, ReplicationMode replicationMode);

    /**
     * 从给定的对象上获取指定的锁定级别
     *
     * @param m
     * @param lockMode
     * @param <M>
     */
    <M> void lock(M m, LockMode lockMode);

    /**
     * 增删改
     *
     * @param hql    hql语句
     * @param params 参数列表
     * @return
     */
    int executeBatch(final String hql, final Object... params);

    /**
     * 批量增删改
     *
     * @param hql
     * @param params
     * @return
     */
    int[] executeBatch(final String hql, final Object[][] params);

    /**
     * 增删改
     *
     * @param sql    SQL语句
     * @param params 参数列表
     * @return
     */
    int executeSqlBatch(final String sql, final Object... params);

    /**
     * 批量增删改
     *
     * @param sql
     * @param params
     * @return
     */
    int[] executeSqlBatch(final String sql, final Object[][] params);

}
