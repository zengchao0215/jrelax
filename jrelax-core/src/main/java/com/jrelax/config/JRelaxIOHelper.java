package com.jrelax.config;

import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.kit.FileKit;
import com.jrelax.kit.ObjectKit;
import com.jrelax.kit.StringKit;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * IO帮助类
 * Created by zengc on 2016-12-25.
 */
public class JRelaxIOHelper {
    private static JRelaxIOHelper instance;

    public static JRelaxIOHelper getInstance() {
        if (ObjectKit.isNull(instance))
            instance = new JRelaxIOHelper();
        return instance;
    }

    private JRelaxIOHelper() {

    }

    /**
     * 地址转换
     * 替换预设变量
     *
     * @param location
     * @return
     */
    public String resolvePath(String location) {
        //${project.baseDir} 项目根目录
        //${project.name} 项目名
        location = location.replace("${project.baseDir}", ApplicationCommon.WEBAPPS_PATH);
        location = location.replace("${project.name}", ApplicationCommon.APP);

        return location;
    }

    /**
     * 格式化路径
     * 1. 统一使用 `/`
     * 2. 结尾不保留 `/`
     *
     * @param location
     * @return
     */
    public String formatPath(String location) {
        return StringKit.cleanPath(location);
    }

    /**
     * 获取文件类型图标
     *
     * @param file
     * @return
     */
    public String getFileIcon(File file) {
        if (file.isDirectory())
            return "fa fa-folder-open";
        return getFileIcon(file.getName());
    }

    public String getFileIcon(String name) {
        if (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".gif") || name.endsWith(".png") || name.endsWith(".bmp"))
            return "fa fa-file-image-o";
        else if (name.endsWith(".doc") || name.endsWith(".docx"))
            return "fa fa-file-word-o";
        else if (name.endsWith(".xls") || name.endsWith(".xlsx"))
            return "fa fa-file-excel-o";
        else if (name.endsWith(".ppt") || name.endsWith(".pptx"))
            return "fa fa-file-powerpoint-o";
        else if (name.endsWith(".pdf"))
            return "fa fa-file-pdf-o";
        else if (name.endsWith(".rar") || name.endsWith(".zip"))
            return "fa fa-file-zip-o";
        else if (name.endsWith(".js") || name.endsWith(".java") || name.endsWith(".html") || name.endsWith(".jsp") || name.endsWith(".xml"))
            return "fa fa-file-code-o";
        else if (name.endsWith(".wav") || name.endsWith(".mp3"))
            return "fa fa-file-sound-o";
        else if (name.endsWith(".text") || name.endsWith(".properties"))
            return "fa fa-file-text-o";
        else if (name.endsWith(".sql"))
            return "fa fa-database";
        //从数据字典中获取图标映射
        Map<String, String> iconMap = ApplicationCommon.getDataDict().getMap("sys_file_icon");
        if(iconMap != null){
            String suffix = FileKit.getSuffix(name);
            if(iconMap.containsKey(suffix)){
                return iconMap.get(suffix);
            }
        }

        return "fa fa-file-o";
    }
}
