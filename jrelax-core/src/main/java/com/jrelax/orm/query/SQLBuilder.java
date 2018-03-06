package com.jrelax.orm.query;

/**
 * SQL构建器
 */
public class SQLBuilder extends Builder {

    public SQLBuilder(String base) {
        super(base);
    }

    public String getSQL() {
        return super.getQuery().toString();
    }

    /**
     * 左连接
     *
     * @param table 表名
     * @param alias 别名
     * @param on    on语句
     * @return
     */
    public SQLBuilder leftJoin(String table, String alias, String on) {
        return join(" left join ", table, alias, on);
    }

    /**
     * 右连接
     *
     * @param table 表名
     * @param alias 别名
     * @param on    on语句
     * @return
     */
    public SQLBuilder rightJoin(String table, String alias, String on) {
        return join(" right join ", table, alias, on);
    }

    /**
     * 内连接
     *
     * @param table 表名
     * @param alias 别名
     * @param on    on语句
     * @return
     */
    public SQLBuilder innerJoin(String table, String alias, String on) {
        return join(" inner join ", table, alias, on);
    }

    /**
     * 链接
     *
     * @param type
     * @param table
     * @param alias
     * @param on
     * @return
     */
    private SQLBuilder join(String type, String table, String alias, String on) {
        super.append(type).append(table).append(" ").append(alias).append(" ").append(on);
        return this;
    }
}
