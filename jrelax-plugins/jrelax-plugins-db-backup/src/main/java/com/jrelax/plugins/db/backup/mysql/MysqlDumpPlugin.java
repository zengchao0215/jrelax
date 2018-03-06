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
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;

import com.jrelax.core.plugin.IPlugin;
import com.jrelax.core.plugin.annotation.Plugin;
import org.slf4j.LoggerFactory;

@Plugin(value = "MySql数据库自动备份(6小时)", group = "数据库", loadOnStartup = false)
public class MysqlDumpPlugin implements IPlugin {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Timer timer = new Timer();

    public boolean init() {
        //每天自动备份
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    logger.info("/* 数据备份开始... */");
                    //CMD 命令
                    String cmd = "C:/Program Files (x86)/MySQL/MySQL Server 5.0/bin/mysqldump -uroot -ppass  nspms";
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
                    String filePath = "D:/NSPMS_DB/数据库" + sdf.format(new Date()) + ".sql";
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
                    logger.info("/* 数据库已备份，  存放于 " + filePath + "。*/");
                } catch (IOException e) {
                    logger.info("/* 数据库备份失败。*/");
                }
            }
        }, 5000, 1000 * 60 * 60 * 6);//6个小时备份一次
        return true;
    }

    public void destroy() {
        timer.cancel();//取消定时备份
        logger.info("Mysql自动备份插件已卸载");
    }
}
