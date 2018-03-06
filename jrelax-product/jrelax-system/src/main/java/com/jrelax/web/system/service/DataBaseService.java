package com.jrelax.web.system.service;

import com.jrelax.kit.StringKit;
import com.jrelax.web.support.BaseService;
import org.hibernate.SQLQuery;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

@Service
public class DataBaseService extends BaseService<Object> {

    /**
     * 执行SQL语句
     *
     * @param sql
     * @return
     */
    public List<?> executeQuery(String sql) {
        SQLQuery query = getBaseDao().getSQLQuery(sql);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setMaxResults(20);
        return query.list();
    }

    /**
     * 获取数据库 Catalog
     *
     * @return
     */
    public List<String> getAllCatalog() {
        final List<String> catalogs = new ArrayList<String>();
        this.getBaseDao().getSession().doWork(new Work() {
            @Override
            public void execute(Connection conn) throws SQLException {
                DatabaseMetaData meta = conn.getMetaData();
                ResultSet rs = meta.getCatalogs();

                while (rs.next()) {
                    catalogs.add(rs.getString(1));
                }
            }
        });
        return catalogs;
    }

    /**
     * 获取当前数据库的Catalog
     *
     * @return
     */
    public String getCatalog() {
        final StringBuffer catalog = new StringBuffer();
        this.getBaseDao().getSession().doWork(new Work() {
            @Override
            public void execute(Connection conn) throws SQLException {
                catalog.append(conn.getCatalog());
            }
        });
        return catalog.toString();
    }

    /**
     * 获取当前连接数据库的Schema
     *
     * @return
     */
    public String getSchema() {
        final StringBuffer schema = new StringBuffer();
        this.getBaseDao().getSession().doWork(new Work() {
            @Override
            public void execute(Connection conn) throws SQLException {
                schema.append(conn.getSchema());
            }
        });
        return schema.toString();
    }

    /**
     * 获取当前数据库所有表名
     *
     * @return
     */
    public List<String> getTables() {
        return getTables(null, null);
    }

    /**
     * 获取数据库表
     *
     * @param catalog
     * @return
     */
    public List<String> getTables(final String catalog, final String schema) {
        final List<String> tableNames = new ArrayList<String>();
        this.getBaseDao().getSession().doWork(new Work() {
            @Override
            public void execute(Connection conn) throws SQLException {
                DatabaseMetaData meta = conn.getMetaData();
                ResultSet rs = meta.getTables(catalog == null ? conn.getCatalog() : catalog, schema == null ? conn.getSchema() : schema, "%", new String[]{"TABLE"});

                while (rs.next()) {
                    tableNames.add(rs.getString(3));
                }
            }
        });
        return tableNames;
    }

    /**
     * 获取外键
     *
     * @param catalog
     * @param schema
     * @param table
     * @return Map<表名,列名>
     */
    public Map<String, String> getForgetKeys(final String catalog, final String schema, final String table) {
        final Map<String, String> map = new HashMap<>();
        this.baseDao.getSession().doWork(new Work() {

            @Override
            public void execute(Connection conn) throws SQLException {
                DatabaseMetaData databaseMetaData = conn.getMetaData();
                //获取外键信息
                ResultSet rs_for = databaseMetaData.getImportedKeys(catalog == null ? conn.getCatalog() : catalog, schema == null ? conn.getSchema() : schema, table);
                while (rs_for.next()) {
                    map.put(rs_for.getString(8), rs_for.getString(3));
                }
            }
        });
        return map;
    }

    /**
     * 获取当前数据库中的表的外键
     *
     * @param table
     * @return
     */
    public Map<String, String> getForgetKeys(final String table) {
        return getForgetKeys(null, null, table);
    }

    /**
     * 获取主键
     *
     * @param catalog
     * @param schema
     * @param table
     * @return
     */
    public Map<String, String> getPrimaryKeys(final String catalog, final String schema, final String table) {
        final Map<String, String> map = new HashMap<>();
        this.baseDao.getSession().doWork(new Work() {

            @Override
            public void execute(Connection conn) throws SQLException {
                DatabaseMetaData databaseMetaData = conn.getMetaData();
                //获取外键信息
                ResultSet rs_for = databaseMetaData.getPrimaryKeys(catalog == null ? conn.getCatalog() : catalog, schema == null ? conn.getSchema() : schema, table);
                while (rs_for.next()) {
                    map.put(rs_for.getString(8), rs_for.getString(3));
                }
            }
        });
        return map;
    }

    /**
     * 获取主键
     *
     * @param table
     * @return
     */
    public Map<String, String> getPrimaryKeys(final String table) {
        return getPrimaryKeys(null, null, table);
    }

    /**
     * 获取表字段
     *
     * @param catalog
     * @param schema
     * @param table
     * @return
     */
    public List<String> getColumns(final String catalog, final String schema, final String table) {
        final List<String> columns = new ArrayList<>();
        this.baseDao.getSession().doWork(new Work() {
            @Override
            public void execute(Connection conn) throws SQLException {
                //获取列备注信息
                DatabaseMetaData databaseMetaData = conn.getMetaData();
                ResultSet rs = databaseMetaData.getColumns(catalog == null ? conn.getCatalog() : catalog, schema == null ? conn.getSchema() : schema, table, "%");
                while (rs.next()) {
                    String name = rs.getString("COLUMN_NAME");
                    columns.add(name);
                }
            }
        });
        return columns;
    }

    /**
     * 获取表字段
     *
     * @param table
     * @return
     */
    public List<String> getColumns(final String table) {
        return getColumns(null, null, table);
    }

    /**
     * 获取表字段名
     *
     * @param catalog
     * @param schema
     * @param table
     * @return
     */
    public Map<String, String> getColumnRemarks(final String catalog, final String schema, final String table) {
        final Map<String, String> columns = new LinkedHashMap<String, String>();
        this.baseDao.getSession().doWork(new Work() {
            @Override
            public void execute(Connection conn) throws SQLException {
                //获取列备注信息
                DatabaseMetaData databaseMetaData = conn.getMetaData();
                ResultSet rs = databaseMetaData.getColumns(catalog == null ? conn.getCatalog() : catalog, schema == null ? conn.getSchema() : schema, table, "%");
                while (rs.next()) {
                    String name = rs.getString("COLUMN_NAME");
                    String remarks = rs.getString("REMARKS");
                    columns.put(name, remarks);
                }
            }
        });

        return columns;
    }

    /**
     * 获取当前数据库中的表的列名
     *
     * @param table
     * @return K：字段名 V：备注
     */
    public Map<String, String> getColumnRemarks(final String table) {
        return getColumnRemarks(null, null, table);
    }

    /**
     * 获取表字段
     *
     * @param catalog
     * @param schema
     * @param table
     */
    public Map<String, Map<String, Object>> getColumnFull(final String catalog, final String schema, final String table) {
        final Map<String, Map<String, Object>> mapInfo = new LinkedHashMap<>();
        this.baseDao.getSession().doWork(new Work() {
            @Override
            public void execute(Connection conn) throws SQLException {
                String sql = "select * from " + table;
                String newCatalog = catalog == null ? conn.getCatalog() : catalog;
                String newSchema = schema == null ? conn.getSchema() : schema;

                DatabaseMetaData dbMetaData = conn.getMetaData();
                //获取主键，用于判断字段是否是主键
                List<String> primaryKeys = new ArrayList<>();
                ResultSet rs = dbMetaData.getPrimaryKeys(newCatalog, newSchema, table);
                while (rs.next()) {
                    primaryKeys.add(rs.getString(4));
                }

                //获取表结构信息
                String oldCatalog = conn.getCatalog();
                conn.setCatalog(newCatalog);
                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                ResultSetMetaData metaData = rs.getMetaData();
                int count = metaData.getColumnCount();
                for (int i = 1; i <= count; i++) {
                    Map<String, Object> map = new LinkedHashMap<>();
                    String columnName = metaData.getColumnName(i);//名称
                    String columnType = getColumnType(metaData.getColumnClassName(i));//对应Java类型
                    boolean nullable = metaData.isNullable(i) == ResultSetMetaData.columnNullable;//是否可以为null
                    boolean primary = primaryKeys.contains(columnName);
                    //首字母大写字段名
                    String name = StringKit.firstUpperCase(getFieldName(columnName));
                    //首字母小写字段名
                    String fieldName = StringKit.firstLowerCase(name);
                    map.put("name", name);
                    map.put("fieldName", fieldName);//用于实体字段
                    map.put("columnName", columnName);//用于@Column
                    map.put("type", columnType.replaceAll("java.lang.", ""));
                    map.put("nullable", nullable);
                    map.put("primary", primary);
                    map.put("length", metaData.getColumnDisplaySize(i));//长度
                    map.put("readOnly", metaData.isReadOnly(i));//是否只读
                    map.put("writable", metaData.isWritable(i));//是否可写
                    map.put("autoIncrement", metaData.isAutoIncrement(i));//是否自动增长

                    mapInfo.put(columnName, map);
                }
                //还原
                conn.setCatalog(oldCatalog);
            }
        });
        return mapInfo;
    }

    /**
     * 获取当前数据库中的表的列
     *
     * @param table
     * @return
     */
    public Map<String, Map<String, Object>> getColumnFull(final String table) {
        return getColumnFull(null, null, table);
    }

    /**
     * 获取数据库相关信息
     *
     * @return
     */
    public Map<String, String> getDBInfo() {
        Map<String, String> metaMap = new HashMap<>();
        this.getBaseDao().getSession().doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                DatabaseMetaData meta = connection.getMetaData();

                metaMap.put("url", meta.getURL());//连接
                metaMap.put("user", meta.getUserName());//用户名
                metaMap.put("driver", meta.getDriverName());//驱动
                metaMap.put("type", meta.getDatabaseProductName());//数据库类型
                metaMap.put("version", meta.getDatabaseProductVersion());//数据库版本
            }
        });
        return metaMap;
    }

    /**
     * 获取字段名
     * 将下划线风格的命名方式，转换为驼峰式命名方式
     *
     * @param columnName 表字段名
     * @return
     */
    private String getFieldName(String columnName) {
        String[] names = columnName.split("_");
        if (names.length > 1) {
            StringBuffer sb = new StringBuffer();
            for (String n : names) {
                sb.append(StringKit.firstUpperCase(n));
            }
            columnName = sb.toString();
        }
        return columnName;
    }

    /**
     * 获取字段类型
     *
     * @param columnType
     * @return
     */
    private String getColumnType(String columnType) {
        if (columnType.equals("java.lang.Double")) {
            columnType = "java.math.BigDecimal";
        } else if (columnType.equals("java.lang.Long")) {
            columnType = "java.math.BigDecimal";
        } else if (columnType.equals("java.sql.Timestamp")) {
            columnType = "java.util.Date";
        } else if (columnType.equals("java.sql.Date")) {
            columnType = "java.util.Date";
        } else if (columnType.equals("java.sql.Time")) {
            columnType = "java.util.Date";
        } else if (columnType.equals("java.lang.Short")) {
            columnType = "java.lang.Integer";
        } else if (columnType.equals("[B")) {//BLOB类型
            columnType = "java.lang.String";
        } else if (columnType.equals("java.lang.Boolean")) {
            columnType = "boolean";
        }
        return columnType;
    }
}
