package com.jrelax.orm.query;

import com.jrelax.kit.ObjectKit;
import org.hibernate.criterion.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 查询条件类
 *
 * @author zenghao
 */
public class Condition {
    private List<Criterion> criterions = new ArrayList<>();//查询条件
    private List<Order> orders = new ArrayList<>();//排序
    private List<Projection> projections = new ArrayList<>();//分组

    /**
     * 等于
     *
     * @param property
     * @param value
     * @return
     */
    public Condition eq(String property, Object value) {
        this.criterions.add(Restrictions.eq(property, value));
        return this;
    }

    /**
     * 不等于
     *
     * @param property
     * @param value
     * @return
     */
    public Condition notEq(String property, Object value) {
        this.not(Restrictions.eq(property, value));
        return this;
    }

    /**
     * ID等于
     *
     * @param value
     * @return
     */
    public Condition idEq(Object value) {
        this.criterions.add(Restrictions.idEq(value));
        return this;
    }

    /**
     * ID不等于
     *
     * @param value
     * @return
     */
    public Condition notIdEq(Object value) {
        this.not(Restrictions.idEq(value));
        return this;
    }

    /**
     * 不等于 <>
     *
     * @param property
     * @param value
     * @return
     */
    public Condition ne(String property, Object value) {
        this.criterions.add(Restrictions.ne(property, value));
        return this;
    }

    /**
     * 不等于
     *
     * @param not
     * @return
     */
    public Condition not(Criterion not) {
        this.criterions.add(Restrictions.not(not));
        return this;
    }

    /**
     * 大于 >
     *
     * @param property
     * @param value
     * @return
     */
    public Condition gt(String property, Object value) {
        this.criterions.add(Restrictions.gt(property, value));
        return this;
    }

    /**
     * 大于等于 >=
     *
     * @param property
     * @param value
     * @return
     */
    public Condition ge(String property, Object value) {
        this.criterions.add(Restrictions.ge(property, value));
        return this;
    }

    /**
     * 小于 <
     *
     * @param property
     * @param value
     * @return
     */
    public Condition lt(String property, Object value) {
        this.criterions.add(Restrictions.lt(property, value));
        return this;
    }

    /**
     * 小于等于 <=
     *
     * @param property
     * @param value
     * @return
     */
    public Condition le(String property, Object value) {
        this.criterions.add(Restrictions.le(property, value));
        return this;
    }

    /**
     * 为空
     *
     * @param property
     * @return
     */
    public Condition isNull(String property) {
        this.criterions.add(Restrictions.isNull(property));
        return this;
    }

    /**
     * 不为空
     *
     * @param property
     * @return
     */
    public Condition isNotNull(String property) {
        this.criterions.add(Restrictions.isNotNull(property));
        return this;
    }

    /**
     * 为空
     *
     * @param property
     * @return
     */
    public Condition isEmpty(String property) {
        this.criterions.add(Restrictions.isEmpty(property));
        return this;
    }

    /**
     * 不为空
     *
     * @param property
     * @return
     */
    public Condition isNotEmpty(String property) {
        this.criterions.add(Restrictions.isNotEmpty(property));
        return this;
    }

    /**
     * 相似
     *
     * @param property
     * @param value
     * @param mode     匹配方式 MatchMode.ANYWHERE 全匹配 MatchMode.START开始 MatchMode.END 结束 MatchMode.EXACT精确匹配
     * @return
     */
    public Condition like(String property, String value, MatchMode mode) {
        this.criterions.add(Restrictions.like(property, value, mode));
        return this;
    }

    /**
     * 不相似
     *
     * @param property
     * @param value
     * @param mode
     * @return
     */
    public Condition notLike(String property, String value, MatchMode mode) {
        this.not(Restrictions.like(property, value, mode));
        return this;
    }

    /**
     * 逻辑和
     *
     * @param criterions
     * @return
     */
    public Condition and(Criterion... criterions) {
        this.criterions.add(Restrictions.and(criterions));
        return this;
    }

    /**
     * 逻辑与
     *
     * @param criterions
     * @return
     */
    public Condition conjunction(Criterion... criterions) {
        this.criterions.add(Restrictions.conjunction(criterions));
        return this;
    }

    /**
     * 逻辑或
     *
     * @param criterions
     * @return
     */
    public Condition disjunction(Criterion... criterions) {
        this.criterions.add(Restrictions.disjunction(criterions));
        return this;
    }

    /**
     * 逻辑或
     *
     * @param criterions
     * @return
     */
    public Condition or(Criterion... criterions) {
        this.criterions.add(Restrictions.or(criterions));
        return this;
    }

    /**
     * 等于列表中的某个值
     *
     * @param property
     * @param values
     * @return
     */
    public Condition in(String property, Object... values) {
        this.criterions.add(Restrictions.in(property, values));
        return this;
    }

    /**
     * 等于列表中的某个值
     *
     * @param property
     * @param values
     * @return
     */
    public Condition in(String property, Serializable[] values) {
        this.criterions.add(Restrictions.in(property, values));
        return this;
    }

    /**
     * 等于列表中的某个值
     *
     * @param property
     * @param values
     * @return
     */
    public Condition in(String property, Collection<?> values) {
        this.criterions.add(Restrictions.in(property, values));
        return this;
    }

    /**
     * 不等于列表中的某个值
     *
     * @param property
     * @param values
     * @return
     */
    public Condition notIn(String property, Object... values) {
        this.criterions.add(Restrictions.not(Restrictions.in(property, values)));
        return this;
    }

    /**
     * 不等于列表中的某个值
     *
     * @param property
     * @param values
     * @return
     */
    public Condition notIn(String property, Serializable[] values) {
        this.criterions.add(Restrictions.not(Restrictions.in(property, values)));
        return this;
    }

    /**
     * 不等于列表中的某个值
     *
     * @param property
     * @param values
     * @return
     */
    public Condition notIn(String property, Collection<?> values) {
        this.criterions.add(Restrictions.not(Restrictions.in(property, values)));
        return this;
    }

    /**
     * 在两值之间
     *
     * @param property
     * @param x
     * @param y
     * @return
     */
    public Condition between(String property, Object x, Object y) {
        this.criterions.add(Restrictions.between(property, x, y));
        return this;
    }

    /**
     * 不在在两值之间
     *
     * @param property
     * @param x
     * @param y
     * @return
     */
    public Condition notBetween(String property, Object x, Object y) {
        this.criterions.add(Restrictions.not(Restrictions.between(property, x, y)));
        return this;
    }

    /**
     * 比较两个属性值相等
     *
     * @param property      属性1
     * @param otherProperty 属性2
     * @return
     */
    public Condition eqProperty(String property, String otherProperty) {
        this.criterions.add(Restrictions.eqProperty(property, otherProperty));
        return this;
    }

    public Condition notEqProperty(String property, String otherProperty) {
        this.not(Restrictions.eqProperty(property, otherProperty));
        return this;
    }

    /**
     * 比较两个属性值 大于
     *
     * @param property      属性1
     * @param otherProperty 属性2
     * @return
     */
    public Condition gtProperty(String property, String otherProperty) {
        this.criterions.add(Restrictions.gtProperty(property, otherProperty));
        return this;
    }

    /**
     * 比较两个属性值 小于
     *
     * @param property      属性1
     * @param otherProperty 属性2
     * @return
     */
    public Condition ltProperty(String property, String otherProperty) {
        this.criterions.add(Restrictions.ltProperty(property, otherProperty));
        return this;
    }

    /**
     * 比较两个属性值 大于等于
     *
     * @param property      属性1
     * @param otherProperty 属性2
     * @return
     */
    public Condition geProperty(String property, String otherProperty) {
        this.criterions.add(Restrictions.geProperty(property, otherProperty));
        return this;
    }

    /**
     * 比较两个属性值 小于等于
     *
     * @param property      属性1
     * @param otherProperty 属性2
     * @return
     */
    public Condition leProperty(String property, String otherProperty) {
        this.criterions.add(Restrictions.leProperty(property, otherProperty));
        return this;
    }

    /**
     * 拼接sql语句查询
     *
     * @param sql
     * @return
     */
    public Condition sql(String sql) {
        this.criterions.add(Restrictions.sqlRestriction(sql));
        return this;
    }

    /**
     * 升序
     *
     * @param property
     * @return
     */
    public Condition asc(String property) {
        this.orders.add(Order.asc(property));
        return this;
    }

    /**
     * 降序
     *
     * @param property
     * @return
     */
    public Condition desc(String property) {
        this.orders.add(Order.desc(property));
        return this;
    }

    /**
     * 示例查询
     *
     * @param example
     * @return
     */
    public Condition example(Object example) {
        this.criterions.add(Example.create(example));
        return this;
    }

    public List<Criterion> getCriterions() {
        return criterions;
    }

    public Condition setCriterions(List<Criterion> criterions) {
        this.criterions = criterions;

        return this;
    }

    public Condition addCriterions(Criterion... criterions) {
        if (ObjectKit.isNotNull(criterions)) {
            for (Criterion criterion : criterions)
                this.criterions.add(criterion);
        }
        return this;
    }

    public Condition clearCriterions() {
        this.criterions.clear();
        return this;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Condition setOrders(List<Order> orders) {
        this.orders = orders;
        return this;
    }

    public Condition addOrder(Order... orders) {
        if (ObjectKit.isNotNull(orders)) {
            for (Order order : orders)
                this.orders.add(order);
        }
        return this;
    }

    public Condition clearOrder() {
        this.orders.clear();
        return this;
    }

    public List<Projection> getProjections() {
        return projections;
    }

    public Condition setProjections(List<Projection> projections) {
        this.projections = projections;
        return this;
    }

    /**
     * 增加分组条件
     *
     * @param projs
     * @return
     */
    public Condition addProjection(Projection... projs) {
        for (Projection proj : projs)
            this.projections.add(proj);
        return this;
    }

    public Condition clearProjections() {
        this.projections.clear();
        return this;
    }

    /*=============静态方法=============*/
    public static Condition NEW() {
        return new Condition();
    }


}
