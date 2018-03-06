package com.jrelax.orm.support;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库池
 * 存放外部数据库链接
 * Created by zengchao on 2017-01-18.
 */
public class DBPool {
    private static DBPool instance = new DBPool();
    //存放所有的外部数据库链接
    private Map<String, DataSource> dbMap = new HashMap<>();
    private DBPool() {
    }

    public static DBPool getInstance() {
        return instance;
    }

    /**
     * 测试配置文件是否可用
     * @param driver 数据库驱动
     * @param url 数据库链接
     * @param username 数据库用户
     * @param password 数据库密码
     * @return
     */
    public boolean test(String driver, String url , String username, String password){
        ComboPooledDataSource c3p0 = new ComboPooledDataSource();
        try {
            c3p0.setDriverClass(driver);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        c3p0.setJdbcUrl(url);
        c3p0.setUser(username);
        c3p0.setPassword(password);
        c3p0.setCheckoutTimeout(10000);//10秒超时
        try {
            Statement statement = c3p0.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("select 1");
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        c3p0.close();
        return true;
    }

    /**
     * 创建数据源，并存放到池中
     * @param key 数据源名称（不可重复）
     * @param driver 数据库驱动
     * @param url 数据库链接
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public DBPool create(String key, String driver, String url, String username, String password) throws PropertyVetoException {
        if(!dbMap.containsKey(key)){
            ComboPooledDataSource pool = new ComboPooledDataSource();
            pool.setDataSourceName(key);
            pool.setDriverClass(driver);
            pool.setJdbcUrl(url);
            pool.setUser(username);
            pool.setPassword(password);
            pool.setDebugUnreturnedConnectionStackTraces(true);
            pool.setUnreturnedConnectionTimeout(60);

            dbMap.put(key, pool);
        }
        return this;
    }

    public DBPool create(String key, DataSource dataSource){
        dbMap.put(key, dataSource);
        return this;
    }

    /**
     * 获取数据源
     * @param key
     * @return
     */
    public DataSource getDataSource(String key){
        return dbMap.get(key);
    }

    /**
     * 获取用于交换/汇集的数据库
     * @return
     */
    public DataSource getBIExchangeDataSource(){
        return dbMap.get("BIExchange");
    }

    /**
     * 获取数据库链接
     * @param key
     * @return
     * @throws SQLException
     */
    public Connection getConnection(String key) throws SQLException {
        DataSource pool = dbMap.get(key);
        if(pool == null) return null;
        else return pool.getConnection();
    }

    /**
     * 销毁指定数据源
     * @param key
     */
    public void destroy(String key){
        DataSource pool = dbMap.get(key);
        if(pool != null){
            if(pool instanceof ComboPooledDataSource)
                ((ComboPooledDataSource)pool).close();
            dbMap.remove(key);
        }
    }

    /**
     * 销毁所有数据源
     */
    public void destroyAll(){
        for (String s : dbMap.keySet()) {
            destroy(s);
        }
        dbMap.clear();
    }

    /**
     * 数据源数量
     * @return
     */
    public int size(){
        return dbMap.size();
    }
}
