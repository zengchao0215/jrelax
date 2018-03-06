package com.jrelax.web.support;


import com.jrelax.config.JRelaxIOHelper;
import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.kit.StringKit;

import java.io.File;

/**
 * 上传文件工具类
 * Created by zengchao on 2017/4/17.
 */
public class UploadKit {
    /**
     * 解析地址，转换为实际文件的保存地址
     *
     * @param path
     * @return
     */
    public static String getAbsolutePath(String path) {
        if (path.startsWith("http://") || path.startsWith("https://")) {
            String prefix = WebApplicationCommon.getUploadPrefixPath();
            int idx = path.indexOf(prefix);
            if (idx > -1) {
                path = path.substring(idx + prefix.length() + 1);
            }
        }

        return getUploadFolder() + path;
    }

    @Deprecated
    public static String toFilePath(String path) {
        return getUploadFolder() + path;
    }

    /**
     * 处理上传文件路径
     *
     * @param path
     * @return
     */
    public static String formatPath(String path) {
        return StringKit.cleanPath(path);
    }

    /**
     * 获取上传文件根目录
     *
     * @return
     */
    public static String getUploadFolder() {
        String root = JRelaxSystemConfigHelper.get("upload.folder.root", "/resources/upload/");
        root = JRelaxIOHelper.getInstance().resolvePath(root);
        //创建文件夹
        File rootFile = new File(root);
        if (!rootFile.exists())
            rootFile.mkdirs();
        return root;
    }
}
