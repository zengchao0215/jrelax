package com.jrelax.orm.query;

import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.config.JRelaxUserConfigHelper;
import com.jrelax.kit.ObjectKit;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * 分页Bean
 *
 * @author ZENGCHAO
 */
public class PageBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4462058285749649947L;
    private int totalCount = 0;// 总记录数
    private int pageCount = 0;// 总页数
    private int rows = JRelaxUserConfigHelper.getInt("system.page.rows", JRelaxSystemConfigHelper.getInt("system.page.rows", 10));// 分页大小，先从用户配置中读取，再从系统配置中读取
    private int page = 1;// 当前页数
    private boolean isFirstPage = false;// 是否为第一页
    private boolean isEndPage = false;// 是否为最后一页
    private String sort;
    private String order;
    private Condition condition = Condition.NEW();
    // 只在使用HQL语句查询时才会生效
    private Object[] params = null;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount == null ? 0 : totalCount.intValue();
    }

    public void setTotalCount(BigInteger totalCount) {
        this.totalCount = totalCount == null ? 0 : totalCount.intValue();
    }

    public int getPageCount() {
        if (totalCount % rows == 0)
            pageCount = totalCount / rows;
        else
            pageCount = (totalCount / rows) + 1;
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * 分页大小
     *
     * @return
     */
    public int getRows() {
        return rows;
    }

    /**
     * 分页大小
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    /**
     * 获取起始位置
     *
     * @return
     */
    public int getStart() {
        return (this.page - 1) * this.rows;
    }

    public boolean isFirstPage() {
        if (page == 1 || page == 0)
            isFirstPage = true;
        return isFirstPage;
    }

    public void setFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isEndPage() {
        if (page >= pageCount)
            isEndPage = true;
        return isEndPage;
    }

    public void setEndPage(boolean isEndPage) {
        this.isEndPage = isEndPage;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Order getOrderBy() {
        if (sort == null || sort.trim().length() == 0 || order == null || order.trim().length() == 0)
            return null;
        else
            return this.order.trim().toLowerCase().equals("desc") ? Order.desc(sort) : Order.asc(sort);
    }

    /**
     * 添加查询条件
     *
     * @param criterion
     */
    public void addCriterion(Criterion criterion) {
        this.condition.addCriterions(criterion);
    }

    /**
     * 清楚查询条件
     */
    public void clearCriterion() {
        this.condition.clearCriterions();
    }

    /**
     * 获取查询条件
     *
     * @return
     */
    public List<Criterion> getCriterions() {
        return this.condition.getCriterions();
    }

    /**
     * 获取查询条件数组
     *
     * @return
     */
    public Criterion[] getCriterionsArray() {
        Criterion[] array = new Criterion[this.condition.getCriterions().size()];
        for (int i = 0; i < this.getCriterions().size(); i++) {
            array[i] = this.condition.getCriterions().get(i);
        }
        return array;
    }

    /**
     * 增加排序字段
     *
     * @param order
     */
    public void addOrder(Order order) {
        this.condition.getOrders().add(order);
    }

    /**
     * 清楚所有排序字段
     */
    public void clearOrder() {
        this.condition.getOrders().clear();
    }

    /**
     * 获取所有排序字段
     *
     * @return
     */
    public List<Order> getOrders() {
        return this.condition.getOrders();
    }

    public void addExample(Object example) {
        this.condition.example(example);
    }

    /**
     * 获取排序条件数组
     *
     * @return
     */
    public Order[] getOrdersArray() {
        Order[] array = new Order[this.condition.getOrders().size()];
        for (int i = 0; i < this.condition.getOrders().size(); i++) {
            array[i] = this.condition.getOrders().get(i);
        }
        return array;
    }

    /**
     * 获取HQL语句参数列表 只在使用HQL语句查询时才会生效
     *
     * @return
     */
//	public Object[] getParams() {
//		return params;
//	}

    /**
     * 设置HQL语句参数列表 只在使用HQL语句查询时才会生效
     *
     * @return
     */
//	public void setParams(Object... params) {
//		this.params = params;
//	}

    /**
     * 增加HQL参数
     *
     * @param param
     */
    public void addParam(Object param) {
        int len = 0;
        if (ObjectKit.isNotNull(this.params))
            len = this.params.length;
        Object[] p = new Object[len + 1];
        for (int i = 0; i < len; i++)
            p[i] = params[i];
        p[len] = param;
        this.params = p;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}
