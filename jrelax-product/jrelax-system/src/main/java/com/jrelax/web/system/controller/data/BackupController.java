package com.jrelax.web.system.controller.data;

import com.jrelax.config.JRelaxPropertyPlaceholderConfigurer;
import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.core.support.ApplicationContextHelper;
import com.jrelax.core.web.annotation.ViewPrefix;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.kit.StringKit;
import com.jrelax.orm.backup.BackupFactory;
import com.jrelax.orm.backup.IBackup;
import com.jrelax.web.system.service.ConfigService;
import com.jrelax.web.system.service.DataBaseService;
import com.jrelax.web.system.service.data.BackupService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库备份
 * Created by zengchao on 2017-03-17.
 */
@Controller
@RequestMapping("/system/data/backup")
@ViewPrefix("/system/data/backup/")
public class BackupController {
    private final String backupPath = "/backup";
    @Resource
    private BackupService backupService;
    @Resource
    private DataBaseService dataBaseService;
    @Resource
    private ConfigService configService;
    /**
     * 数据库备份首页
     * 1. 获取当前系统连接数据库的类型、用户名密码等信息
     * 2. 检测当前数据库类型是否可以支持备份操作（系统是否支持，备份程序是否可用）
     * 3. 获取表
     * @param model
     * @return
     */
    @RequestMapping
    public String index(Model model){
        String catalog = dataBaseService.getCatalog();
        List<String> tableList = dataBaseService.getTables();

        model.addAttribute("catalog", catalog);
        model.addAttribute("tableList", tableList);
        IBackup backup = createBackup();
        if(backup == null){
            model.addAttribute("error", "当前系统使用数据库不支持备份");
        }else if(!backup.canBackup()){
            model.addAttribute("error", "当前备份程序不可用，请设置备份程序路径后点击");
        }
        return "index";
    }

    /**
     * 配置路径
     * @param path 执行文件路径
     * @return
     */
    @RequestMapping("config")
    @ResponseBody
    public JSONObject config(String path){
        JRelaxSystemConfigHelper.put("system.backup.exec", path);
        IBackup backup = createBackup();
        if(backup != null && backup.canBackup()){
            configService.save("system.backup.exec", path);
        }
        return WebResult.success();
    }

    /**
     * 备份
     * @param table  表名，为null时 备份库
     * @return
     */
    @RequestMapping("backup")
    @ResponseBody
    public JSONObject backup(String table){
        IBackup backup = createBackup();
        if(backup != null && backup.canBackup()){
            if(StringKit.isEmpty(table))
                backup.backup();
            else
                backup.backup(table);
        }else{
            return WebResult.error("备份失败");
        }
        return WebResult.success();
    }

    /**
     * 查看备份文件列表
     * @param table 表名，为null时 删除库备份文件
     * @return
     */
    @RequestMapping("list")
    public String list(Model model, String table){
        List<String> list = new ArrayList<>();
        IBackup backup = createBackup();
        if(backup != null && backup.canBackup()){
            if(StringKit.isEmpty(table))
                list = backup.list();
            else
                list = backup.list(table);
        }
        model.addAttribute("list", list);
        model.addAttribute("table", table);
        return "list";
    }

    /**
     * 删除备份文件
     * @param table 表名，为null时 删除库备份文件
     * @param name 文件名
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public JSONObject delete(String table, String name){
        IBackup backup = createBackup();
        File file = null;
        if(backup != null && backup.canBackup()){
            if(StringKit.isEmpty(table))
                file = backup.getFile(name);
            else
                file = backup.getFile(table, name);
        }
        if(file != null && file.exists()){
            if(!file.delete()){
                return WebResult.error("备份文件删除失败！");
            }
        }else{
            return WebResult.error("备份文件不存在");
        }
        return WebResult.success();
    }

    private IBackup createBackup(){
        JRelaxPropertyPlaceholderConfigurer configurer = ApplicationContextHelper.getInstance().getBean("preferences");
        IBackup backup = null;
        Map<String, String> dbInfoMap = dataBaseService.getDBInfo();
        String type = dbInfoMap.get("type");

        Properties props = configurer.getHibernateProperties();

        Properties config = new Properties();
        config.setProperty("url", props.getProperty("jdbc.master.url"));
        config.setProperty("username", props.getProperty("jdbc.master.username"));
        config.setProperty("password", props.getProperty("jdbc.master.password"));

        backup = BackupFactory.create(type);
        if(backup != null){
            backup.setProperties(config);
            backup.setExecPath(JRelaxSystemConfigHelper.get("system.backup.exec"));
            backup.setSavePath(ApplicationCommon.WEBAPPS_PATH + backupPath);
        }
        return backup;
    }
}
