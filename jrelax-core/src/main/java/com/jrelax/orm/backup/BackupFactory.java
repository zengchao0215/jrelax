package com.jrelax.orm.backup;

/**
 * 数据库备份工厂类
 * Created by zengchao on 2017-03-17.
 */
public class BackupFactory {

    /**
     * 创建数据库备份
     * @param type 数据库类型
     * @return
     */
    public static IBackup create(String type){
        IBackup backup = null;
        if(type.toLowerCase().equals("mysql")){
            backup = new MySQLBackup();
        }
        return backup;
    }
}
