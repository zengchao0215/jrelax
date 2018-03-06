package com.jrelax.kit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件操作工具类
 * 实现文件的创建、删除、复制、压缩、解压以及目录的创建、删除、复制、压缩解压等功能
 * 继承自FileUtils
 *
 * @author zengc
 * @version 2013-06-21
 */
public class FileKit extends FileUtils {
    private static Logger logger = LoggerFactory.getLogger(FileKit.class);
    private final static String[] TYPE_IMAGE = new String[]{".png", ".jpg", ".jpeg", ".gif", ".bmp"};
    private final static String[] TYPE_VIDEO = new String[]{".flv", ".swf", ".mkv", ".avi", ".rm", ".rmvb", ".mpeg", ".mpg", ".ogv", ".mov", ".wmv", ".mp4", ".webm"};
    private final static String[] TYPE_AUDIO = new String[]{".mp3", ".wav", ".mid", ".ogg"};
    private final static String[] TYPE_DOC = new String[]{".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".pdf"};

    /**
     * 获取格式化后的路径
     * 将 `\\` 或 `/` 等替换为 `/`
     *
     * @param path 路径
     * @return
     */
    public static String getFormattedPath(String path) {
        String p = StringKit.replace(path, "\\", "/");
        p = StringKit.join(StringKit.split(p, "/"), "/");
        if (!StringKit.startsWithAny(p, "/") && StringKit.startsWithAny(path, "\\", "/")) {
            p += "/";
        }
        if (!StringKit.endsWithAny(p, "/") && StringKit.endsWithAny(path, "\\", "/")) {
            p = p + "/";
        }
        return p;
    }


    /**
     * 获取文件后缀名
     *
     * @param file 文件
     * @return
     */
    public static String getSuffix(File file) {
        return getSuffix(file.getName());
    }

    /**
     * 获取文件后缀名
     *
     * @param path 文件绝对路径
     * @return
     */
    public static String getSuffix(String path) {
        int idx = path.lastIndexOf(".");
        if (idx < 0)
            return "";
        return path.substring(path.lastIndexOf(".")).toLowerCase();
    }

    /**
     * 获取文件名
     *
     * @param path
     * @return
     */
    public static String getFilename(String path) {
        path = path.replaceAll("\\\\", "/");
        int idx = path.lastIndexOf("/");
        if (idx < 0) return path;
        return path.substring(idx + 1);
    }

    /**
     * 获取文件类型
     *
     * @param path
     * @return
     */
    public static String getType(String path) {
        String type = "file";
        String suffix = getSuffix(path);
        if (eqSuffix(suffix, TYPE_IMAGE))
            type = "image";
        else if (eqSuffix(suffix, TYPE_VIDEO))
            type = "video";
        else if (eqSuffix(suffix, TYPE_AUDIO))
            type = "audio";
        else if (eqSuffix(suffix, TYPE_DOC))
            type = "doc";
        return type;
    }

    private static boolean eqSuffix(String suffix, String[] types) {
        for (String type : types) {
            if (type.equals(suffix))
                return true;
        }
        return false;
    }

    /**
     * 判断文件是否存在
     *
     * @param path
     * @return
     */
    public static boolean exists(String path) {
        return new File(path).exists();
    }

    /**
     * 从互联网下载
     *
     * @param url      文件地址
     * @param destPath 文件保存路径
     */
    public static void downloadFromInternet(String url, String destPath) throws IOException {
        logger.debug("从网络下载文件：" + url);
        long byteSum = 0;
        int byteRead = 0;

        URL netUrl = new URL(url);

        URLConnection conn = netUrl.openConnection();
        InputStream in = conn.getInputStream();
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(destPath);
            byte[] buffer = new byte[1024];
            while ((byteRead = in.read(buffer)) != -1) {
                byteSum += byteRead;
                fos.write(buffer, 0, byteRead);
            }
        } finally {
            if (fos != null) fos.close();
        }

    }

    /**
     * 从FTP服务器下载
     *
     * @param ftpUrl   ftp文件地址
     * @param user     ftp用户名【可选】
     * @param pass     ftp密码【可选】
     * @param destPath 保存路径
     */
    public static void downloadFromFTP(String ftpUrl, String user, String pass, String destPath) {
        logger.debug("从FTP服务器下载文件：" + ftpUrl);
        throw new UnsupportedOperationException();
    }

    /**
     * 转换文件大小为友好显示
     *
     * @param size
     * @return
     */
    public static String getDisplaySize(long size) {
        String displaySize = size + "";
        if (size < 1024) {
            displaySize = size + "B";
        } else if (size < 1024 * 1024) {
            displaySize = getDisplaySizeKB(size);
        } else if (size < 1024 * 1024 * 1024) {
            displaySize = getDisplaySizeMB(size);
        } else if (size < 1024.0 * 1024 * 1024 * 1024) {
            displaySize = getDisplaySizeGB(size);
        } else if (size < 1024.0 * 1024 * 1024 * 1024 * 1024) {
            displaySize = getDisplaySizeTB(size);
        }
        return displaySize;
    }

    /**
     * 转换文件大小为友好显示
     *
     * @param size
     * @return
     */
    public static String getDisplaySizeKB(long size) {
        return new BigDecimal(size).divide(new BigDecimal(1024)).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue() + "KB";
    }

    /**
     * 转换文件大小为友好显示
     *
     * @param size
     * @return
     */
    public static String getDisplaySizeMB(long size) {
        return new BigDecimal(size).divide(new BigDecimal(1024 * 1024)).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue() + "MB";
    }

    /**
     * 转换文件大小为友好显示
     *
     * @param size
     * @return
     */
    public static String getDisplaySizeGB(long size) {
        return new BigDecimal(size).divide(new BigDecimal(1024 * 1024 * 1024.0)).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue() + "GB";
    }

    /**
     * 转换文件大小为友好显示
     *
     * @param size
     * @return
     */
    public static String getDisplaySizeTB(long size) {
        return new BigDecimal(size).divide(new BigDecimal(1024.0 * 1024 * 1024 * 1024)).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue() + "TB";
    }

    /**
     * 随机生成文件名
     *
     * @param suffix 文件后缀名
     * @param type   hex36 hex62 date
     * @return
     */
    public static String getRandomFilename(String suffix, String type) {
        String newFileName = "";
        switch (type) {
            case "hex36":
                newFileName = NumberKit.toHex36(System.currentTimeMillis() + "");
                newFileName += NumberKit.toHex36(Math.round(10 + (Math.random() * 9990)) + "");
                break;
            case "hex62":
                newFileName = NumberKit.toHex62(System.currentTimeMillis() + "");
                newFileName += NumberKit.toHex62(Math.round(10 + (Math.random() * 9990)) + "");
                break;
            case "date":
                newFileName = "FN" + new SimpleDateFormat("yyyyMMddHHmmsss").format(new Date());
                newFileName += Math.round(10 + (Math.random() * 9990));
                break;
        }
        newFileName += suffix;
        return newFileName;
    }

    /**
     * 美化路径
     *
     * @param path
     * @return
     */
    public static String beautifyPath(String path) {
        return new File(path).getAbsolutePath();
    }

    /**
     * 文件读取
     *
     * @param inputStream
     * @param encoding
     * @return
     */
    public static String readToString(InputStream inputStream, String encoding) {
        try {
            return IOUtils.toString(inputStream, encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 文件读取
     *
     * @param uri
     * @param encoding
     * @return
     */
    public static String readToString(URI uri, String encoding) {
        try {
            return IOUtils.toString(uri, encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 文件读取
     *
     * @param url
     * @param encoding
     * @return
     */
    public static String readToString(URL url, String encoding) {
        try {
            return IOUtils.toString(url, encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取资源文件，基于SpringIO
     *
     * @param location 支持classpath:等
     * @return
     */
    public static Resource getResource(String location) {
        return new DefaultResourceLoader().getResource(location);
    }

    /**
     * 获取类路径下的文件
     * @param location
     * @return
     */
    public static Resource getClassPathResource(String location) {
        return getResource("classpath:" + location);
    }
}
