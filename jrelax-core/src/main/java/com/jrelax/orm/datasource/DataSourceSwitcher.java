package com.jrelax.orm.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;

import com.jrelax.core.support.ApplicationContextHelper;
import com.jrelax.kit.ObjectKit;
import org.slf4j.LoggerFactory;

/**
 * 多数据源转换类
 *
 * @author ZENGCHAO
 */
public class DataSourceSwitcher implements DataSource {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ThreadLocal<DataSource> dataSource = new ThreadLocal<>();//当前线程数据源
    private ThreadLocal<String> dataSourceName = new ThreadLocal<>();
    private DataSource defaultDataSource = null;//默认数据源
    private String defaultDataSourceName = "masterDataSource";

    /**
     * 获取当前实例
     *
     * @return
     */
    public static DataSourceSwitcher getInstance() {
        DataSourceSwitcher dataSourceSwitcher = ApplicationContextHelper.getInstance().getBean(DataSourceSwitcher.class);
        dataSourceSwitcher.restoreDefaultDataSource();
        return dataSourceSwitcher;
    }

    private DataSourceSwitcher() {

    }

    public DataSourceSwitcher(String dataSourceName) {
        this.dataSource.set(getDataSource(dataSourceName));
        this.dataSourceName.set(dataSourceName);
    }

    public String getDataSourceName() {
        String name = this.dataSourceName.get();
        if (ObjectKit.isNull(name)) return this.defaultDataSourceName;
        return name;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName.set(dataSourceName);
    }

    public void setDataSource(DataSource dataSource) {
        if (ObjectKit.isNotNull(dataSource)) {
            this.dataSource.set(dataSource);
            if (ObjectKit.isNull(defaultDataSource)) {
                this.defaultDataSource = dataSource;
            }
        }
    }

    /**
     * 设置数据源
     *
     * @param dataSourceName
     */
    public void setDataSource(String dataSourceName) {
        setDataSource(getDataSource(dataSourceName));
        setDataSourceName(dataSourceName);
    }

    public DataSource getDataSource() {
        DataSource ds = this.dataSource.get();
        if (ObjectKit.isNull(ds)) return this.defaultDataSource;
        return ds;
    }

    /**
     * 从Spring配置文件中动态读取数据源
     *
     * @param dataSourceName 数据源Bean的ID
     * @return
     */
    public DataSource getDataSource(String dataSourceName) {
        return ApplicationContextHelper.getInstance().getBean(dataSourceName);
    }

    /**
     * 获取所有数据源
     *
     * @return
     */
    public List<String> getAllDataSources() {
        List<String> dataSources = new ArrayList<String>();

        dataSources.add("masterDataSource");
        dataSources.add("slaveDataSource");

        return dataSources;
    }

    /**
     * 还原为默认数据源
     */
    public void restoreDefaultDataSource() {
        logger.info("设置数据源为初始数据源!");
        setDataSource(this.defaultDataSource);
        setDataSourceName(this.defaultDataSourceName);
    }

    /**
     * 切换至主服务器
     * 主要负责写入更新操作
     */
    public boolean switchToMaster() {
        DataSource ds = getDataSource("masterDataSource");
        if (ObjectKit.isNotNull(ds)) {
            setDataSource(ds);
            setDataSourceName("masterDataSource");
            return true;
        } else {
            restoreDefaultDataSource();
            logger.error("未找到masterDataSource数据源配置,使用默认数据源");
            return false;
        }
    }

    /**
     * 切换至从服务器
     * 主要负责读取操作
     */
    public boolean switchToSlave() {
        DataSource ds = getDataSource("slaveDataSource");
        if (ObjectKit.isNotNull(ds)) {
            setDataSource(ds);
            setDataSourceName("slaveDataSource");
            return true;
        } else {
            restoreDefaultDataSource();
            logger.error("未找到slaveDataSource数据源配置，使用默认数据源");
            return false;
        }
    }

    /**
     * 切换到任意数据源
     *
     * @param dataSourceName
     * @return
     */
    public boolean switchTo(String dataSourceName) {
        DataSource ds = getDataSource(dataSourceName);
        if (ObjectKit.isNotNull(ds)) {
            setDataSource(ds);
            setDataSourceName(dataSourceName);
            return true;
        } else {
            restoreDefaultDataSource();
            logger.error("未找到" + dataSourceName + "数据源配置，使用默认数据源");
            return false;
        }
    }

    public PrintWriter getLogWriter() throws SQLException {
        return getDataSource().getLogWriter();
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
        getDataSource().setLogWriter(out);
    }

    public void setLoginTimeout(int seconds) throws SQLException {
        getDataSource().setLoginTimeout(seconds);
    }

    public int getLoginTimeout() throws SQLException {
        return getDataSource().getLoginTimeout();
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return getDataSource().unwrap(iface);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return getDataSource().isWrapperFor(iface);
    }

    public Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    public Connection getConnection(String username, String password)
            throws SQLException {
        return getDataSource().getConnection(username, password);
    }

    public java.util.logging.Logger getParentLogger()
            throws SQLFeatureNotSupportedException {
        return java.util.logging.Logger.getLogger("DataSourceSwitcher");
    }


}
