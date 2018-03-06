package com.jrelax.orm.query;

import com.jrelax.kit.StringKit;

import java.util.*;

public abstract class Builder {
    private StringBuilder query = new StringBuilder();
    private List<Object> params = new ArrayList<>();

    public Builder(String base) {
        query.append(base);
    }

    /**
     * 查询条件
     *
     * @param field
     * @param condition
     * @param param
     * @return
     */
    public Builder where(String field, String condition, Object param) {
        String template = " %s %s ?";
        String where = String.format(template, field, condition);
        if (hasWhere()) {
            query.append(" and").append(where);
        } else {
            query.append(" where").append(where);
        }
        params.add(param);
        return this;
    }

    /**
     * 追加自定义查询语句
     *
     * @param q
     * @return
     */
    public Builder append(String q) {
        query.append(" ").append(q);
        return this;
    }

    /**
     * 相等
     *
     * @param field
     * @param param
     * @return
     */
    public Builder eq(String field, Object param) {
        return where(field, "=", param);
    }

    public Builder like(String field, Object param) {
        return where(field, "like", param);
    }

    public Builder notEq(String field, Object param) {
        return where(field, "<>", param);
    }

    public Builder gt(String field, Object param) {
        return where(field, ">", param);
    }

    public Builder lt(String field, Object param) {
        return where(field, "<", param);
    }

    public Builder ge(String field, Object param) {
        return where(field, ">=", param);
    }

    public Builder le(String field, Object param) {
        return where(field, "<=", param);
    }

    public Builder in(String field, Object param) {
        return where(field, "in", param);
    }

    public Builder in(String field, Collection params) {
        if (params.size() > 0) {
            if (params.size() == 1) {
                in(field, params.iterator().next());
            } else {
                String template = " %s in (%s)";
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < params.size(); i++) {
                    sb.append(",?");
                }
                String where = String.format(template, field, sb.substring(1));
                if (hasWhere()) {
                    query.append(" and").append(where);
                } else {
                    query.append(" where").append(where);
                }
                this.params.addAll(params);
            }

        }

        return this;
    }

    public Builder in(String field, Object[] params) {
        return in(field, Arrays.asList(params));
    }

    /**
     * 排序
     *
     * @param field
     * @param sort
     * @return
     */
    public Builder order(String field, String sort) {
        query.append(" order by ").append(field).append(" ").append(sort);
        return this;
    }

    /**
     * 正序
     *
     * @param field
     * @return
     */
    public Builder asc(String field) {
        return order(field, "asc");
    }

    /**
     * 倒序
     *
     * @param field
     * @return
     */
    public Builder desc(String field) {
        return order(field, "desc");
    }

    /**
     * 分组
     *
     * @param field
     * @return
     */
    public Builder group(String field) {
        query.append(" group by ").append(field);
        return this;
    }

    /**
     * having
     *
     * @param field
     * @return
     */
    public Builder having(String field) {
        query.append(" having ").append(field);
        return this;
    }

    /**
     * 判断是否包含where
     *
     * @return
     */
    private boolean hasWhere() {
        return query.toString().toLowerCase().indexOf("where") > 0;
    }

    /**
     * 获取查询语句
     *
     * @return
     */
    protected StringBuilder getQuery() {
        return query;
    }

    /**
     * 获取参数
     *
     * @return
     */
    public Object[] getParams() {
        return params.toArray();
    }

    /**
     * 重置
     *
     * @return
     */
    public Builder reset() {
        query = new StringBuilder();
        params.clear();

        return this;
    }
}
