package com.jrelax.orm.util;

import com.jrelax.kit.ObjectKit;
import com.jrelax.kit.StringKit;

/**
 * HQL SQL 解析器
 * Created by zengc on 2016/8/14.
 */
public class HqlSqlParser {
    private String sql = "";

    public HqlSqlParser(String sql){
        this.sql = sql;
    }

    /**
     * 获取查询字段
     * @return
     */
    public String[] getSelectColumns(){
        String tempSql = sql.toLowerCase();
        int from = tempSql.indexOf("select") + 6;
        int to = tempSql.indexOf("from");
        String sqlAlias = sql.substring(from, to);

        String[] columns = StringKit.split(sqlAlias, ",");
        String[] columnAlias = new String[columns.length];
        int i = 0;
        for (String alias : columns) {
            String[] temp = StringKit.split(alias.trim(), " ");
            String t = temp[temp.length - 1].trim();
//            if (t.contains(".")) {
//                t = t.substring(t.indexOf(".") + 1);
//            }
            columnAlias[i] = t;
            i++;
        }
        return columnAlias;
    }
}
