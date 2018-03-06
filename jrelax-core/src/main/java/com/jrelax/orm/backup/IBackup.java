package com.jrelax.orm.backup;

import java.io.File;
import java.util.List;
import java.util.Properties;

/**
 * 数据库备份接口
 * Created by zengchao on 2017-03-17.
 */
public interface IBackup {
    /**
     * 设置参数
     *
     * @param properties
     */
    void setProperties(Properties properties);

    /**
     * 设置可执行文件路径
     *
     * @param path
     */
    void setExecPath(String path);

    /**
     * 保存路径
     * @param path
     */
    void setSavePath(String path);

    /**
     * 备份整库
     */
    boolean backup();

    /**
     * 备份单表
     *
     * @param table
     */
    boolean backup(String table);

    /**
     * 获取库备份文件列表
     */
    List<String> list();

    /**
     * 获取表备份文件列表
     *
     * @param table
     */
    List<String> list(String table);

    /**
     * 获取库备份文件
     *
     * @param name
     */
    File getFile(String name);

    /**
     * 获取表备份文件
     *
     * @param table
     * @param name
     */
    File getFile(String table, String name);

    /**
     * 是否可以备份
     * @return
     */
    boolean canBackup();
}
