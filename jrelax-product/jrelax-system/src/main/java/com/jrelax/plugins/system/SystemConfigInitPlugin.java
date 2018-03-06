package com.jrelax.plugins.system;

import com.jrelax.config.JRelaxIOHelper;
import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.core.plugin.IPlugin;
import com.jrelax.core.plugin.annotation.Plugin;
import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.orm.query.Condition;
import com.jrelax.web.system.entity.Version;
import com.jrelax.web.system.service.BlackListService;
import com.jrelax.web.system.service.ConfigService;
import com.jrelax.web.system.service.RouteService;
import com.jrelax.web.system.service.VersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;

@Plugin(value = "系统参数初始化插件", group = "系统", loadOnStartup = true, order = 0)
public class SystemConfigInitPlugin implements IPlugin {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ConfigService configService;
    @Autowired
    private VersionService versionService;

    @Override
    public boolean init() {
        logger.info("加载系统配置文件...");
        //设置项目目录
        try {
            String path = URLDecoder.decode(this.getClass().getResource("/").toURI().getPath(), "UTF-8");

            ApplicationCommon.CLASSPATH = path;
            //是否为标准JAVAEE项目启动方式
            File parent = new File(path).getParentFile();
            if (parent.getName().equals("WEB-INF")) {
                ApplicationCommon.WEBAPPS_PATH = JRelaxIOHelper.getInstance().formatPath(parent.getParentFile().getPath());
            }
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 加载系统配置信息
        logger.info("加载系统配置参数...");
        configService.flush();
        //设置项目相关信息
        logger.info("加载项目配置...");
        configService.afterPropertiesSet();
        // 设置系统参数
        ApplicationCommon.SYSTEM_LOGIN_TITLE = JRelaxSystemConfigHelper.get("system.login.title");
        ApplicationCommon.SYSTEM_ADMIN_TITLE = JRelaxSystemConfigHelper.get("system.admin.title");
        ApplicationCommon.SYSTEM_DEFAULT_THEME = JRelaxSystemConfigHelper.get("system.default.theme", ApplicationCommon.SYSTEM_DEFAULT_THEME);//系统默认皮肤
        ApplicationCommon.SESSION_ADMIN = JRelaxSystemConfigHelper.get("system.session.admin", ApplicationCommon.SESSION_ADMIN);//后台用户SESSION KEY
        ApplicationCommon.SESSION_USER = JRelaxSystemConfigHelper.get("system.session.user", ApplicationCommon.SESSION_USER);//前台用户SESSION KEY
        ApplicationCommon.SESSION_LOCK = JRelaxSystemConfigHelper.get("system.session.lock", ApplicationCommon.SESSION_LOCK);//锁定SESSION KEY
        ApplicationCommon.SESSION_LOCK_TIME = JRelaxSystemConfigHelper.get("system.session.lock.time", ApplicationCommon.SESSION_LOCK_TIME);//锁定时间SESSION KEY
        ApplicationCommon.SESSION_IP_BLACK_LIST = JRelaxSystemConfigHelper.get("system.session.ip.blacklist", ApplicationCommon.SESSION_IP_BLACK_LIST);//IP地址黑名单
        ApplicationCommon.SYSTEM_ADMIN = JRelaxSystemConfigHelper.get("system.admin.account", ApplicationCommon.SYSTEM_ADMIN);//系统超级管理员账号
        ApplicationCommon.SYSTEM_INSTALLED_FILE = JRelaxSystemConfigHelper.get("system.installed.file", ApplicationCommon.SYSTEM_INSTALLED_FILE);//系统安装成功标示文件名

        //设置缓存参数
        ApplicationCommon.CACHE_IP_BLACK_LIST = JRelaxSystemConfigHelper.get("system.cache.ip.blacklist", ApplicationCommon.CACHE_IP_BLACK_LIST);//IP黑名单缓存
        ApplicationCommon.CACHE_LOGIN_USER = JRelaxSystemConfigHelper.get("system.cache.login.user", ApplicationCommon.CACHE_LOGIN_USER);//登录用户缓存
        ApplicationCommon.CACHE_SYSTEM_CONFIG = JRelaxSystemConfigHelper.get("system.cache.system.config", ApplicationCommon.CACHE_SYSTEM_CONFIG);//系统配置缓存
        ApplicationCommon.CACHE_SYSTEM_RULES = JRelaxSystemConfigHelper.get("system.cache.system.rules", ApplicationCommon.CACHE_SYSTEM_RULES);//系统规则缓存
        ApplicationCommon.CACHE_URL_ROUTE = JRelaxSystemConfigHelper.get("system.cache.url.route", ApplicationCommon.CACHE_URL_ROUTE);//URL路由缓存
        ApplicationCommon.CACHE_URL_ROUTE_MATCHED = JRelaxSystemConfigHelper.get("system.cache.url.route.matched", ApplicationCommon.CACHE_URL_ROUTE_MATCHED);//URL路由匹配记录缓存

        //设置URL参数
        WebApplicationCommon.Url.LOGIN = JRelaxSystemConfigHelper.get("system.login.url", WebApplicationCommon.Url.LOGIN);//登录
        WebApplicationCommon.Url.LOGIN_OUT = JRelaxSystemConfigHelper.get("system.login.out.url", WebApplicationCommon.Url.LOGIN_OUT);//登出
        WebApplicationCommon.Url.LOGIN_AGAIN = JRelaxSystemConfigHelper.get("system.login.again.url", WebApplicationCommon.Url.LOGIN_AGAIN);//重新登录
        WebApplicationCommon.Url.LOCK = JRelaxSystemConfigHelper.get("system.lock.url", WebApplicationCommon.Url.LOCK);//锁定
        WebApplicationCommon.Url.INDEX = JRelaxSystemConfigHelper.get("system.index.url", WebApplicationCommon.Url.INDEX);//首页
        WebApplicationCommon.Url.OPEN = JRelaxSystemConfigHelper.get("system.open.url", WebApplicationCommon.Url.OPEN);//全用户开放地址前缀
        WebApplicationCommon.Url.COMMON = JRelaxSystemConfigHelper.get("system.common.url", WebApplicationCommon.Url.COMMON);//登录用户开发地址前缀
        WebApplicationCommon.Url.ERROR = JRelaxSystemConfigHelper.get("system.error.url", WebApplicationCommon.Url.ERROR);//错误
        WebApplicationCommon.Url.WELCOME = JRelaxSystemConfigHelper.get("system.welcome.url", WebApplicationCommon.Url.WELCOME);//欢迎页
        WebApplicationCommon.Url.ADMIN = JRelaxSystemConfigHelper.get("system.admin.url", WebApplicationCommon.Url.ADMIN);//后台拦截地址前缀
        WebApplicationCommon.Url.FRONT = JRelaxSystemConfigHelper.get("system.front.url", WebApplicationCommon.Url.FRONT);//前台拦截地址前缀
        WebApplicationCommon.Url.FRONT_LOGIN = JRelaxSystemConfigHelper.get("system.front.login.url", WebApplicationCommon.Url.FRONT_LOGIN);//前台登录地址
        WebApplicationCommon.Url.FRONT_LOGIN_OUT = JRelaxSystemConfigHelper.get("system.front.login.out.url", WebApplicationCommon.Url.FRONT_LOGIN_OUT);//前台退出地址

        logger.info("加载项目版本...");
        Version version = versionService.getVersion();
        logger.info("系统配置初始化完成，当前系统版本：" + version.getVersion());
        return true;
    }

    @Override
    public void destroy() {
        logger.info("系统配置参数已注销");
    }
}
