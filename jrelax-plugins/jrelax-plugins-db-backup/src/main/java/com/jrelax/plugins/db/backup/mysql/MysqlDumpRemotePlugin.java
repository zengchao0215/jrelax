package com.jrelax.plugins.db.backup.mysql;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

import com.jrelax.core.plugin.IPluginProperty;
import org.slf4j.Logger;

import com.jrelax.core.plugin.IPlugin;
import com.jrelax.core.plugin.annotation.Plugin;
import org.slf4j.LoggerFactory;

/**
 * 数据库远程备份插件
 * 注：1.必须在Windows环境下
 * 2. 运行环境必须要安装mysql，并将mysql的bin目录配置到系统变量Path中
 *
 * @author zengchao
 */
@Plugin(value = "MySql数据库自动备份——远程备份(6小时)", group = "数据库", loadOnStartup = false)
public class MysqlDumpRemotePlugin implements IPlugin, IPluginProperty {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Timer timer = new Timer();
    private Properties props = new Properties();//配置参数
    private boolean isStarted = false;

    public boolean init() {
        props.setProperty("host", "localhost");
        props.setProperty("db", "test");
        props.setProperty("user", "root");
        props.setProperty("password", "pass");
        props.setProperty("path", "");
        return true;
    }

    public void destroy() {
        timer.cancel();//取消定时备份
        logger.info("Mysql自动备份插件已卸载");
    }


    @Override
    public void setProperties(Properties properties) {
        for (Object o : this.props.keySet()) {
            String name = o.toString();
            this.props.setProperty(name, properties.getProperty(name, this.props.getProperty(name)));
        }
    }

    @Override
    public Properties getProperties() {
        return this.props;
    }

    @Override
    public void afterPropertiesSet() {
        final String host = this.props.getProperty("host");
        final String db = this.props.getProperty("db");
        final String user = this.props.getProperty("user");
        final String password = this.props.getProperty("password");
        final String path = this.props.getProperty("path");

        if(isStarted){
            timer.cancel();
            timer = new Timer();
        }
        //每天自动备份
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    logger.info("/* 数据库远程备份开始... */");
                    //CMD 命令
                    String cmd = String.format("mysqldump -u%s -p%s -h %s %s", user, password, host, db);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
                    String filePath = path + sdf.format(new Date()) + ".sql";
                    Runtime run = Runtime.getRuntime();
                    Process p = run.exec(cmd);
                    InputStream is = p.getInputStream();// 控制台的输出信息作为输入流
                    InputStreamReader isr = new InputStreamReader(is, "UTF-8");//设置输入流编码格式
                    BufferedReader br = new BufferedReader(isr);
                    File file = new File(filePath);
                    if (!file.exists()) {
                        file.getParentFile().mkdirs();
                        file.createNewFile();
                    }
                    //将控制台输入信息写入到文件输出流中
                    FileOutputStream fos = new FileOutputStream(filePath);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
                    String temp = null;
                    while ((temp = br.readLine()) != null) {
                        bw.write(temp);
                        bw.newLine();
                    }
                    bw.flush();
                    bw.close();
                    br.close();
                    logger.info("/* 数据库远程已备份，  存放于 " + filePath + "。*/");
                } catch (IOException e) {
                    logger.info("/* 数据库远程备份失败，" + e.getLocalizedMessage() + "*/");
                }
            }
        }, 5000, 1000 * 60 * 60 * 6);//6个小时备份一次
        this.isStarted = true;
    }

    @Override
    public LinkedHashMap<String, String> getPropertyMapping() {
        LinkedHashMap<String, String> mapping = new LinkedHashMap<>();
        mapping.put("host", "主机地址");
        mapping.put("db", "数据库");
        mapping.put("user", "用户名");
        mapping.put("password", "密码");
        mapping.put("path", "保存路径");
        return mapping;
    }
}
