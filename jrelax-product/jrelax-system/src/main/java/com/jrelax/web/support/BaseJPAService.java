package com.jrelax.web.support;

import com.jrelax.orm.dao.impl.BaseHibernateDao;
import com.jrelax.orm.query.PageBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import java.io.Serializable;
import java.util.List;

@Service
public class BaseJPAService<M> {
    @Resource
    private BaseHibernateDao baseDao;
    private Class<M> entityClass;

    public void setEntityClass(Class<M> entityClass) {
        this.entityClass = entityClass;
    }

    public EntityManager getEntityManager() {
        return baseDao.getEntityManager();
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return baseDao.getCriteriaBuilder();
    }

    public CriteriaQuery<M> createCriteriaQuery() {
        return getCriteriaBuilder().createQuery(this.entityClass);
    }

    public CriteriaDelete<M> createCriteriaDelete() {
        return getCriteriaBuilder().createCriteriaDelete(this.entityClass);
    }

    public CriteriaUpdate<M> createCriteriaUpdate() {
        return getCriteriaBuilder().createCriteriaUpdate(this.entityClass);
    }

    public CriteriaQuery createTupleQuery() {
        return getCriteriaBuilder().createTupleQuery();
    }

    public TypedQuery<M> createTypeQuery(String jpql) {
        return getEntityManager().createQuery(jpql, this.entityClass);
    }

    public Query createQuery(String jpql) {
        return getEntityManager().createQuery(jpql);
    }

    //持久化操作
    public void persist(M m) {
        getEntityManager().persist(m);
    }

    public void refresh(M m) {
        getEntityManager().refresh(m);
    }

    public void remove(M m) {
        getEntityManager().remove(m);
    }


    public M find(Object primaryKey) {
        return getEntityManager().find(this.entityClass, primaryKey);
    }

    //查询操作
    public List<M> list(PageBean pageBean) {
        TypedQuery<M> query = getEntityManager().createQuery(createCriteriaQuery());

        setPageBean(query, pageBean);
        return query.getResultList();
    }

    /**
     * 设置分页bean
     *
     * @param query
     * @param pageBean
     */
    private void setPageBean(Query query, PageBean pageBean) {
        if (pageBean.getPage() <= 1)
            query.setFirstResult(0);
        else
            query.setFirstResult((pageBean.getPage() - 1) * pageBean.getRows());
        query.setMaxResults(pageBean.getRows());
    }

    // TODO: 2017/12/22 需要完善
}
