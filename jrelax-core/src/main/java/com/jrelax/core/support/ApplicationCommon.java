package com.jrelax.core.support;

import com.jrelax.cache.ICacheProvider;
import com.jrelax.cache.IDataDict;
import com.jrelax.cache.provider.DefaultCacheProvider;
import com.jrelax.cache.provider.DefaultDataDictProvider;
import com.jrelax.core.factory.SpringContextLoaderListener;
import com.jrelax.core.factory.SpringDispatcherServlet;
import com.jrelax.core.web.IFileStore;
import com.jrelax.core.web.IWebRule;
import com.jrelax.core.web.upload.LocalFileStore;
import com.jrelax.event.ApplicationEventManager;
import com.jrelax.kit.ObjectKit;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.*;

/**
 * 系统全局变量配置类
 *
 * @author ZENGCHAO
 */
public final class ApplicationCommon implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -4723677111800151534L;
    /**
     * 项目BasePath
     */
    public static String BASEPATH = "";
    /**
     * 项目资源路径
     */
    public static String RESPATH = "";
    /**
     * 项目类根目录
     */
    public static String CLASSPATH = "";
    /**
     * web项目根目录
     */
    public static String WEBAPPS_PATH = "";
    /**
     * 当前App名称
     */
    public static String APP = "";
    /**
     * 后台管理标题
     */
    public static String SYSTEM_ADMIN_TITLE = "";
    /**
     * 登陆界面标题
     */
    public static String SYSTEM_LOGIN_TITLE = "";
    /**
     * 系统默认皮肤
     */
    public static String SYSTEM_DEFAULT_THEME = "palette";
    /**
     * Debug模式
     */
    public static boolean DEBUG = false;

    /**
     * 后台用户session
     */
    public static String SESSION_ADMIN = "ADMIN_SESSION";

    /**
     * 前台用户session
     */
    public static String SESSION_USER = "USER_SESSION";
    /**
     * 锁屏KEY
     */
    public static String SESSION_LOCK = "SESSION_LOCK";
    /**
     * 锁定时间
     */
    public static String SESSION_LOCK_TIME = "SESSION_LOCK_TIME";
    /**
     * IP黑名单
     */
    public static String SESSION_IP_BLACK_LIST = "SESSION_IP_BLACK_LIST";
    /**
     * 登录验证次数
     * 存储在Session中的名称
     */
    public static String SESSION_LOGIN_DENIED_NUMBER = "SESSION_LOGIN_DENIED_NUMBER";

    /**
     * 是否验证通过
     * 用于执行关键性操作且需要验证用户密码
     * 存储在Session中的名称
     */
    public static String SESSION_ALLOW_ACCESS = "SESSION_ALLOW_ACCESS";
    /**
     * 记录验证通过的时间
     * 用于下次验证时进行时间比较
     * 存储在Session中的名称
     */
    public static String SESSION_ALLOW_ACCESS_TIMESPAN = "SESSION_ALLOW_ACCESS_TIMESPAN";
    /**
     * 验证码
     * 存储在Session中的名称
     */
    public static String SESSION_CAPTCHA = "SESSION_CAPTCHA_CODE";

    /**
     * 国际化Session中标记
     */
    public static String SESSION_LOCALE = "SESSION_LANG_LOCALE";
    /**
     * 登录Session缓存KEY
     */
    public static String SESSION_CACHE_LOGIN_SESSIONS = "SESSION_CACHE_LOGIN_SESSIONS";
    /**
     * 登录Session缓存KEY
     */
    public static String SESSION_CACHE_SESSIONS = "SESSION_CACHE_SESSIONS";
    /**
     * 系统超级管理员用户名
     * 不可删除该用户
     */
    public static String SYSTEM_ADMIN = "superadmin";

    /**
     * 系统是否已经执行过安装向导
     */
    public static boolean SYSTEM_INSTALLED = true;
    /**
     * 系统安装后生成的文件名称
     */
    public static String SYSTEM_INSTALLED_FILE = ".locked";

    /**
     * Spring上下文加载
     */
    public static SpringContextLoaderListener SYSTEM_CONTEXT_LOADER_LISTENER = null;
    /**
     * SpringMVC核心控制器
     */
    public static SpringDispatcherServlet SYSTEM_DISPATCHER_SERVLET = null;

    /**
     * 加密Key
     * 没有此Key，无法从密文获取明文密码
     */
    public final static String DEC_KEY = "zc@zframework";

    /**
     * 国际化标记
     */
    public static String LOCALE_REQUEST = "locale";
    /**
     * 按钮权限request中标记
     */
    public static String AUTH_BUTTON = "_BUTTON_AUTH_";
    /**
     * 字段权限request中标记
     */
    public static String AUTH_COLUMNS = "_COLUMNS_AUTH_";

    /**
     * 资源权限request中的标记
     */
    public static String AUTH_RESOURCE = "_RESOURCE_AUTH_";
    /**
     * 登录用户缓存KEY
     */
    public static String CACHE_LOGIN_USER = "CACHE_LOGIN_USER";
    /**
     * 系统配置项缓存KEY
     */
    public static String CACHE_SYSTEM_CONFIG = "CACHE_SYSTEM_CONFIG";
    /**
     * 系统IP黑名单缓存KEY
     */
    public static String CACHE_IP_BLACK_LIST = "CACHE_BLACK_LIST";
    /**
     * 系统URL路由缓存KEY
     */
    public static String CACHE_URL_ROUTE = "CACHE_URL_ROUTE";
    /**
     * 已经匹配到的URL路由规则，用于提高路由规则效率
     */
    public static String CACHE_URL_ROUTE_MATCHED = "CACHE_URL_ROUTE_MATCHED";
    /**
     * 系统规则器
     */
    public static String CACHE_SYSTEM_RULES = "CACHE_SYSTEM_RULES";

    /**
     * 用户配置缓存KEY
     */
    public static String CACHE_USER_CONFIG = "CACHE_USER_CONFIG";

    /**
     * 数据字典
     */
    private static IDataDict dataDictProvider = null;
    /**
     * 存放缓存
     */
    private static ICacheProvider cacheProvider = null;

    /**
     * 文件存储
     */
    private static IFileStore fileStore = null;

    /**
     * 获取数据字典
     *
     * @return
     */
    public static IDataDict getDataDict() {
        if (dataDictProvider == null)
            dataDictProvider = ApplicationContextHelper.getInstance().createBean(DefaultDataDictProvider.class);
        return dataDictProvider;
    }

    /**
     * 设置数据字典
     *
     * @param dataDict
     */
    public static void setDataDict(IDataDict dataDict) {
        dataDictProvider = dataDict;
    }

    /**
     * 获取系统缓存
     *
     * @return
     */
    public static ICacheProvider getCacheProvider() {
        if (cacheProvider == null)
            cacheProvider = ApplicationContextHelper.getInstance().createBean(DefaultCacheProvider.class);
        return cacheProvider;
    }

    /**
     * 设置系统缓存提供类
     *
     * @param provider
     */
    public static void setCacheProvider(ICacheProvider provider) {
        cacheProvider = provider;
    }


    /**
     * 获取登录用户缓存
     *
     * @return
     */
    public static Set<String> getCacheOfLoginUser() {
        Set<String> userSet = ApplicationCommon.getCacheProvider().get(ApplicationCommon.CACHE_LOGIN_USER);
        if (ObjectKit.isNull(userSet)) {
            userSet = new HashSet<>();
            ApplicationCommon.getCacheProvider().put(ApplicationCommon.CACHE_LOGIN_USER, userSet);
        }
        return userSet;
    }

    /**
     * 获取登录Session缓存
     *
     * @return
     */
    public static Map<String, HttpSession> getCacheOfLoginSession() {
        Map<String, HttpSession> sessionMap = ApplicationCommon.getCacheProvider().get(ApplicationCommon.SESSION_CACHE_LOGIN_SESSIONS);
        if (ObjectKit.isNull(sessionMap)) {
            sessionMap = new HashMap<>();
            ApplicationCommon.getCacheProvider().put(ApplicationCommon.SESSION_CACHE_LOGIN_SESSIONS, sessionMap);
        }
        return sessionMap;
    }

    /**
     * 获取登录Session缓存
     *
     * @return
     */
    public static Map<String, HttpSession> getCacheOfSession() {
        Map<String, HttpSession> sessionMap = ApplicationCommon.getCacheProvider().get(ApplicationCommon.SESSION_CACHE_SESSIONS);
        if (ObjectKit.isNull(sessionMap)) {
            sessionMap = new HashMap<>();
            ApplicationCommon.getCacheProvider().put(ApplicationCommon.SESSION_CACHE_SESSIONS, sessionMap);
        }
        return sessionMap;
    }

    /**
     * 获取系统过滤规则
     *
     * @return
     */
    public static List<IWebRule> getFilterRules() {
        List<IWebRule> ruleAdapterList = ApplicationCommon.getCacheProvider().get(ApplicationCommon.CACHE_SYSTEM_RULES);
        if (ruleAdapterList == null) {
            ruleAdapterList = new ArrayList<>();
            ApplicationCommon.getCacheProvider().put(ApplicationCommon.CACHE_SYSTEM_RULES, ruleAdapterList);
        }
        return ruleAdapterList;
    }

    /**
     * 获取文件存储
     *
     * @return
     */
    public static IFileStore getFileStore() {
        if (fileStore == null) {
            fileStore = ApplicationContextHelper.getInstance().createBean(LocalFileStore.class);
        }
        return fileStore;
    }

    /**
     * 设置新的文件存储
     *
     * @param newFileStore
     */
    public static void setFileStore(IFileStore newFileStore) {
        fileStore = newFileStore;
        getFileStore();
    }

    /**
     * 获取系统事件管理器
     *
     * @return
     */
    public static ApplicationEventManager getEventManager() {
        return ApplicationContextHelper.getInstance().getBean(ApplicationEventManager.class);
    }
}