package com.jrelax.orm.backup;

import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.kit.DateKit;
import com.jrelax.kit.FileKit;
import com.jrelax.kit.RegexKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.im.InputContext;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * MySQL数据库备份
 * Created by zengchao on 2017-03-17.
 */
public class MySQLBackup implements IBackup {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Properties props = new Properties();
    private String execPath = "";
    private String execName = "mysqldump";
    private String savePath = "";
    private String suffix = ".sql";

    private String host = "127.0.0.1";
    private int port = 3306;//默认端口
    private String dbName = null;//数据库名
    private String username = "";
    private String password = "";


    @Override
    public void setProperties(Properties properties) {
        this.props = properties;
        afterPropertiesSet();
    }

    private void afterPropertiesSet() {
        String url = props.getProperty("url");
        this.username = props.getProperty("username");
        this.password = props.getProperty("password");

        //通过URL来解析
        String patten = "^jdbc:mysql://(.+):(\\d+)/(.+)";
        if (RegexKit.isMatch(url, patten)) {
            List<String> list = RegexKit.getMatchArray(url, patten);
            this.host = list.get(1);
            this.port = Integer.parseInt(list.get(2));
            String db = list.get(3);
            if (db.contains("?")) {
                db = db.substring(0, db.indexOf("?"));
            }
            this.dbName = db;
        } else {
            logger.error("请确保参数中有：url, username, password，并且符合对应数据的规范");
            throw new RuntimeException("参数错误");
        }
    }

    @Override
    public void setExecPath(String path) {
        if (path != null && !path.endsWith("/"))
            path = path + "/";
        this.execPath = path;
    }

    @Override
    public void setSavePath(String savePath) {
        if (!savePath.endsWith("/"))
            savePath = savePath + "/";
        this.savePath = savePath;
    }

    @Override
    public boolean backup() {
        logger.info("备份库：" + this.dbName);
        if (!this.canBackup()) {
            logger.debug("备份程序不可用");
            return false;
        }
        StringBuilder cmd = buildCmd();
        try {
            Process process = Runtime.getRuntime().exec(cmd.toString());

            String file = this.dbName + "." + DateKit.now("yyyy_MM_dd_HH_mm");
            doSave(process.getInputStream(), this.savePath, file);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean backup(String table) {
        logger.info("备份表：" + table);
        if (!this.canBackup()) {
            logger.debug("备份程序不可用");
            return false;
        }
        StringBuilder cmd = buildCmd();
        cmd.append(" ").append(table);
        try {
            Process process = Runtime.getRuntime().exec(cmd.toString());

            String file = table + "." + DateKit.now("yyyy_MM_dd_HH_mm");
            String dir = this.savePath + table + "/";
            //检测保存路径
            this.detectSavePath(dir);
            this.doSave(process.getInputStream(), dir, file);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private StringBuilder buildCmd() {
        StringBuilder cmd = new StringBuilder();
        cmd.append(this.execPath).append(execName)
                .append(" -u").append(this.username)
                .append(" -p").append(this.password)
                .append(" -h").append(this.host)
                .append(" -P").append(this.port)
                .append(" ").append(this.dbName);
        return cmd;
    }

    /**
     * 执行保存文件
     *
     * @param in
     * @param file
     * @throws IOException
     */
    private void doSave(InputStream in, String savePath, String file) {
        //写入文件
        logger.debug("写入SQL文件");
        FileOutputStream fos = null;
        OutputStreamWriter writer = null;
        try {
            //判断保存路径是否存在
            File dir = new File(savePath);
            if (!dir.exists())
                dir.mkdirs();
            //拼接文件路径
            String path = savePath + file + this.suffix;
            fos = new FileOutputStream(path);
            writer = new OutputStreamWriter(fos, "UTF-8");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            int len = 0;
            char[] chars = new char[1024];
            while ((len = reader.read(chars)) != -1) {
                writer.write(chars, 0, len);
            }
            logger.info("已备份：" + path);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (writer != null)
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (fos != null)
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    private void detectSavePath(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Override
    public List<String> list() {
        File dir = new File(this.savePath);
        return getFileList(dir);
    }

    @Override
    public List<String> list(String table) {
        File dir = new File(this.savePath + table + "/");
        return getFileList(dir);
    }

    private List<String> getFileList(File dir) {
        List<String> fileList = new ArrayList<>();
        if (dir.isDirectory()) {
            String[] files = dir.list((dir1, name) -> name.endsWith(this.suffix));
            if (files != null)
                fileList.addAll(Arrays.asList(files));
        }
        return fileList;
    }

    @Override
    public File getFile(String name) {
        return new File(this.savePath + name);
    }

    @Override
    public File getFile(String table, String name) {
        return new File(this.savePath + table + "/" + name);
    }

    @Override
    public boolean canBackup() {
        String path = this.execPath + this.execName;
        String os = System.getProperty("os.name");
        if (os.contains("Windows"))
            path += ".exe";
        //检测可执行程序是否存在
        return new File(path).exists();
    }
}
