package com.jrelax.core.web.support;

import com.jrelax.config.JRelaxCoreConfigHelper;
import com.jrelax.config.JRelaxIOHelper;
import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.core.web.annotation.*;
import com.jrelax.core.web.model.FrontUser;
import com.jrelax.core.web.model.User;
import com.jrelax.core.web.support.http.ContentType;
import com.jrelax.kit.*;
import net.sf.json.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * Web请求相关
 *
 * @author zengchao
 */
public class WebApplicationCommon {

    public final static class ERROR {
        /**
         * 错误
         */
        public final static String ERROR = "error";
        /**
         * 非法访问
         */
        public final static String UNAUTHORIZED_ACCESS = "403";
        /**
         * 无权访问
         */
        public final static String NO_PERMISSION = "401";
        /**
         * 资源停用
         */
        public final static String RES_DISABLED = "405";

        /**
         * IP地址禁止访问
         */
        public final static String IP_NOT_ALLOW = "403-6";

        /**
         * 未找到
         */
        public final static String NOT_FOUND = "404";
    }

    /**
     * 地址前缀
     */
    public final static class UrlPrefix {
        /**
         * 登录页面地址前缀
         */
        public final static String LOGIN = "/login";
        /**
         * 登出地址前缀
         */
        public final static String LOGIN_OUT = "/logout";
        /**
         * 重新登录地址前缀
         */
        public final static String LOGIN_AGAIN = "/login-again";
        /**
         * 锁定页面地址前缀
         */
        public final static String LOCK = "/index/lock";
        /**
         * 欢迎页面地址前缀
         */
        public final static String INDEX = "/index";
        /**
         * 错误页面地址前缀
         */
        public final static String ERROR = "/error";
        /**
         * 个人中心
         */
        public final static String PROFILE = "/profile";
        /**
         * 欢迎页
         * 在TAB页面模式下生效
         */
        public final static String WELCOME = "/welcome";

        /**
         * 后端登录地址前缀
         */
        public final static String ADMIN = "/";
        /**
         * 前端页面前缀
         */
        public final static String FRONT = "/front";
        /**
         * 前端页面登录地址前缀
         */
        public final static String FRONT_LOGIN = FRONT + "/login";

        /**
         * 前端页面登出地址前缀
         */
        public final static String FRONT_LOGIN_OUT = FRONT + "/logout";

        /**
         * 开放页面地址前缀
         * 不是权限控制，所有人可以访问
         */
        public final static String OPEN = "/open";
        /**
         * 公共页面地址前缀
         * 不收权限控制，登录用户可以访问
         */
        public final static String COMMON = "/common";
    }

    /**
     * 系统定义的地址
     */
    public final static class Url {
        /**
         * 登录页面地址前缀
         */
        public static String LOGIN = UrlPrefix.LOGIN;
        /**
         * 登出地址前缀
         */
        public static String LOGIN_OUT = UrlPrefix.LOGIN_OUT;
        /**
         * 重新登录地址前缀
         */
        public static String LOGIN_AGAIN = UrlPrefix.LOGIN_AGAIN;
        /**
         * 锁定页面地址前缀
         */
        public static String LOCK = UrlPrefix.LOCK;
        /**
         * 欢迎页面地址前缀
         */
        public static String INDEX = UrlPrefix.INDEX;

        /**
         * 后台管理地址前缀
         */
        public static String ADMIN = UrlPrefix.ADMIN;

        /**
         * 欢迎页
         * 在TAB页面模式下生效
         */
        public static String WELCOME = UrlPrefix.WELCOME;

        /**
         * 开放页面地址前缀
         * 不是权限控制，所有人可以访问
         */
        public static String OPEN = UrlPrefix.OPEN + "/";
        /**
         * 公共页面地址前缀
         * 不收权限控制，登录用户可以访问
         */
        public static String COMMON = UrlPrefix.COMMON + "/";
        /**
         * 前端公共页面地址前缀
         */
        public static String FRONT_COMMON = UrlPrefix.FRONT + "/" + UrlPrefix.COMMON + "/";

        /**
         * 错误页面地址前缀
         */
        public static String ERROR = UrlPrefix.ERROR;

        /**
         * 前端页面前缀
         */
        public static String FRONT = UrlPrefix.FRONT;
        /**
         * 前端页面登录地址
         */
        public static String FRONT_LOGIN = UrlPrefix.FRONT_LOGIN;
        /**
         * 前端页面登出地址
         */
        public static String FRONT_LOGIN_OUT = UrlPrefix.FRONT_LOGIN_OUT;

    }

    /**
     * 请求参数
     */
    public final static class RequestAttribute {
        public static final String BASEPATH = "basePath";
        public static final String RESOURCE_PATH = "resPath";
        public static final String UPLOAD_PATH = "uploadPath";
        public static final String SYSTEM_LOGIN_TITLE = "system_login_title";
        public static final String SYSTEM_ADMIN_TITLE = "system_admin_title";
        public static final String SYSTEM_LOGO = "system_logo";
        public static final String SYSTEM_THEME = "theme";
        public static final String SYSTEM_LAYOUT = "layout";
        public static final String SYSTEM_RESOURCE = "_allRes";
        public static final String SYSTEM_RESOURCE_LEVEL_1 = "_firstRes";
        public static final String SYSTEM_RESOURCE_LEVEL_2 = "_secondRes";
        public static final String SYSTEM_RESOURCE_LEVEL_3 = "_thirdRes";
        public static final String SYSTEM_RESOURCE_LEVEL_4 = "_fourRes";
        public static final String LOGIN_USER_REALNAME = "RealName";
        public static final String LOGIN_USER_HEAD_IMAGE = "headimg";
        public static final String LOGIN_USER_USERNAME = "username";
        public static final String LOGIN_USER_PASSWORD = "password";
        public static final String LOGIN_USER_QUICK_MENUS = "_quickMenus";

    }

    /**
     * 转向页面参数名
     */
    public final static String REDIRECT_URL = "redirect_url";

    /**
     * 跳转到外站
     */
    public final static String GO_URL = "/open/go";

    /**
     * 自定义错误类，使用前先调用setError;如果需要执行script脚本，请setScript
     * 直接return getViewName即可
     *
     * @author zengchao
     */
    public static class CustomError {
        private static String error = null;
        private static String script = null;

        /**
         * @return
         */
        public static String getViewName() {
            return "CustomError_CustomError";
        }

        public static void setError(String err) {
            error = err;
        }

        /**
         * 获取错误信息
         * 获取一次后清空
         *
         * @return
         */
        public static String getError() {
            String err = error;
            error = null;
            return err;
        }

        public static String getScript() {
            String s = script;
            script = null;
            return s;
        }

        /**
         * @param script
         */
        public static void setScript(String script) {
            CustomError.script = script;
        }

        /**
         * 判断是否有脚本
         *
         * @return
         */
        public static boolean nullScript() {
            return script == null;
        }

        public static boolean nullError() {
            return error == null;
        }
    }

    /**
     * 初始化公共请求参数
     *
     * @param request
     */
    public static void initCommonParams(HttpServletRequest request) {
        //项目基本路径
        if (StringKit.isEmpty(ApplicationCommon.BASEPATH)) {
            ApplicationCommon.BASEPATH = request.getContextPath();
        }
        request.setAttribute(RequestAttribute.BASEPATH, ApplicationCommon.BASEPATH);
        if (StringKit.isEmpty(ApplicationCommon.RESPATH)) {//默认使用项目路径
            ApplicationCommon.RESPATH = ApplicationCommon.BASEPATH;
        }
        request.setAttribute(RequestAttribute.RESOURCE_PATH, ApplicationCommon.RESPATH);//资源文件路径
        if (StringKit.isEmpty(ApplicationCommon.WEBAPPS_PATH)) {
            ApplicationCommon.WEBAPPS_PATH = JRelaxIOHelper.getInstance().formatPath(request.getServletContext().getRealPath("/"));
        }
        request.setAttribute(RequestAttribute.UPLOAD_PATH, getUploadPrefixPath());
        request.setAttribute(RequestAttribute.SYSTEM_LOGIN_TITLE, ApplicationCommon.SYSTEM_LOGIN_TITLE);
        request.setAttribute(RequestAttribute.SYSTEM_ADMIN_TITLE, ApplicationCommon.SYSTEM_ADMIN_TITLE);
        request.setAttribute(RequestAttribute.SYSTEM_THEME, ApplicationCommon.SYSTEM_DEFAULT_THEME);
        //系统图标
        String logo = JRelaxSystemConfigHelper.get("system.logo");
        if (!StringKit.isEmpty(logo)) {
            request.setAttribute(RequestAttribute.SYSTEM_LOGO, getUploadPrefixPath() + logo);
        } else {
            request.setAttribute(RequestAttribute.SYSTEM_LOGO, ApplicationCommon.BASEPATH + "/framework/img/logo.png");
        }
    }

    /**
     * 初始化请求工具类
     *
     * @param request
     */
    public static void initTools(HttpServletRequest request) {
        //BigDecimalKit 数字精度管理
        request.setAttribute("bigDecimalKit", BigDecimalKit.class);
        request.setAttribute("stringKit", StringKit.class);//字符串工具类
        request.setAttribute("dateKit", DateKit.class);//日期
        request.setAttribute("objectKit", ObjectKit.class);//对象工具类
        request.setAttribute("regexKit", RegexKit.class);//正则
    }

    /**
     * 是否是登录请求
     *
     * @param requestUrl 请求地址
     * @return
     */
    public static boolean isLoginRequest(String requestUrl, HandlerMethod handlerMethod) {
        if (handlerMethod != null) {
            //方法注解
            Login login = handlerMethod.getMethodAnnotation(Login.class);
            if (login == null)
                login = handlerMethod.getBeanType().getAnnotation(Login.class);
            if (login != null && login.type() == LoginType.ADMIN) return true;
        }
        return requestUrl.startsWith(Url.LOGIN);
    }

    /**
     * 是否是前端登录请求
     *
     * @param requestUrl
     * @return
     */
    public static boolean isFrontLoginRequest(String requestUrl, HandlerMethod handlerMethod) {
        if (handlerMethod != null) {
            //方法注解
            Login login = handlerMethod.getMethodAnnotation(Login.class);
            if (login == null)
                login = handlerMethod.getBeanType().getAnnotation(Login.class);
            if (login != null && login.type() == LoginType.FRONT) return true;
        }
        return requestUrl.startsWith(Url.FRONT_LOGIN);
    }

    /**
     * 是否是后台管理请求
     *
     * @param requestUrl 请求地址
     * @return
     */
    public static boolean isAdminRequest(String requestUrl, HandlerMethod handlerMethod) {
        //已管理地址前缀开头，并且不已前端地址前缀开头
        return requestUrl.startsWith(WebApplicationCommon.Url.ADMIN) && !isFrontRequest(requestUrl, handlerMethod);
    }

    /**
     * 是否时前台页面请求
     *
     * @param requestUrl 请求地址
     * @return
     */
    public static boolean isFrontRequest(String requestUrl, HandlerMethod handlerMethod) {
        if (handlerMethod != null) {
            //方法注解
            Front front = handlerMethod.getMethodAnnotation(Front.class);
            if (front == null)
                front = handlerMethod.getBeanType().getAnnotation(Front.class);
            if (front != null) return true;
        }
        return requestUrl.startsWith(Url.FRONT);
    }

    /**
     * 判断是否是对所有用户开放的链接
     *
     * @param requestUrl 请求链接
     * @return
     */
    public static boolean isOpenRequest(String requestUrl, HandlerMethod handlerMethod) {
        if (handlerMethod != null) {
            //方法注解
            Open open = handlerMethod.getMethodAnnotation(Open.class);
            if (open == null)
                open = handlerMethod.getBeanType().getAnnotation(Open.class);
            if (open != null && open.scope() == OpenScope.ALL) return true;
        }
        return requestUrl.startsWith(Url.OPEN);
    }

    /**
     * 判断是否是对登录用户开放的链接
     *
     * @param requestUrl
     * @return
     */
    public static boolean isCommonRequest(String requestUrl, HandlerMethod handlerMethod) {
        if (handlerMethod != null) {
            Open open = handlerMethod.getMethodAnnotation(Open.class);
            if (open == null)
                open = handlerMethod.getBeanType().getAnnotation(Open.class);
            if (open != null && open.scope() == OpenScope.SESSION) return true;
        }
        return requestUrl.startsWith(Url.COMMON);
    }

    /**
     * 判断是否是对登录用户开放的链接
     *
     * @param requestUrl
     * @return
     */
    public static boolean isFrontCommonRequest(String requestUrl, HandlerMethod handlerMethod) {
        if (handlerMethod != null) {
            Open open = handlerMethod.getMethodAnnotation(Open.class);
            if (open == null)
                open = handlerMethod.getBeanType().getAnnotation(Open.class);
            if (open != null && open.scope() == OpenScope.FRONT_SESSION) return true;
        }
        return requestUrl.startsWith(Url.FRONT_COMMON);
    }

    /**
     * 向客户端输出JSON
     *
     * @param response
     * @param json
     */
    public static void renderJson(HttpServletResponse response, JSONObject json) {
        try {
            response.setContentType(ContentType.JSON);
            PrintWriter out = response.getWriter();
            out.println(json);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 向客户端输出Text
     *
     * @param response
     * @param text
     */
    public static void renderText(HttpServletResponse response, String text) {
        try {
            response.setContentType(ContentType.TEXT);
            PrintWriter out = response.getWriter();
            out.println(text);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取session
     *
     * @return
     */
    public static HttpSession getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attr == null ? null : attr.getRequest().getSession();
    }

    /**
     * 获取HttpServletRequest
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attr == null ? null : attr.getRequest();
    }

    /**
     * 获取HttpServletResponse
     *
     * @return
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attr == null ? null : attr.getResponse();
    }

    /**
     * 获取国际化支持
     *
     * @param request
     * @return
     */
    public static RequestContext getRequestContext(HttpServletRequest request) {
        return new RequestContext(request);
    }

    /**
     * 获取文件上传后下载地址前缀
     *
     * @return
     */
    public static String getUploadPrefixPath() {
        String path = "";
        //获取上传文件根路径
        if (JRelaxSystemConfigHelper.getBoolean("upload.remote.enabled", false)) {
            path = JRelaxSystemConfigHelper.get("upload.remote.view");//上传文件保存路径
        } else {
            path = ApplicationCommon.BASEPATH + JRelaxCoreConfigHelper.getProperty("resources.upload", "/dl");
        }
        return path;
    }

    /**
     * 设置后台登录用户
     *
     * @param session
     * @param user
     */
    public static void setSessionAdminUser(HttpSession session, User user) {
        session.setAttribute(ApplicationCommon.SESSION_ADMIN, user);
    }

    /**
     * 获取后台登录用户
     *
     * @param session
     * @return
     */
    public static User getSessionAdminUser(HttpSession session) {
        return (User) session.getAttribute(ApplicationCommon.SESSION_ADMIN);
    }

    /**
     * 设置前台登录用户
     *
     * @param session
     * @param user
     */
    public static void setSessionFrontUser(HttpSession session, FrontUser user) {
        session.setAttribute(ApplicationCommon.SESSION_USER, user);
    }

    /**
     * 获取前台登录用户
     *
     * @param session
     * @return
     */
    public static FrontUser getSessionFrontUser(HttpSession session) {
        return (FrontUser) session.getAttribute(ApplicationCommon.SESSION_USER);
    }
}
