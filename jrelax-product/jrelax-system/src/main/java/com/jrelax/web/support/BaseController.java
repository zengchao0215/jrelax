package com.jrelax.web.support;

import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.core.web.converter.WebParam;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.kit.ObjectKit;
import com.jrelax.kit.RegexKit;
import com.jrelax.kit.StringKit;
import com.jrelax.orm.query.PageBean;
import net.sf.json.JSONObject;
import org.hibernate.criterion.Restrictions;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

/**
 * 控制类的父类
 * 一般用于放置一些controller类里面用到的逻辑方法
 *
 * @param <M>
 * @author ZENGCHAO
 */
public class BaseController<M> extends BaseObject {
    protected final String SUCCESS = "_result/_success";
    protected final String ERROR = "_result/_error";
    protected final String INFO = "_result/_info";

    /**
     * 判断是否通过密码验证
     * 5分钟之后此次验证就将失效
     *
     * @return
     */
    public boolean isAllowAccess() {
        Object obj = WebApplicationCommon.getSession().getAttribute(ApplicationCommon.SESSION_ALLOW_ACCESS);
        if (ObjectKit.isNotNull(obj)) {//判断是否是第一次验证，第一次验证时session中应该是没有AllowAccess的值
            Boolean allowAccess = (Boolean) obj;
            //获取上次验证的时间
            Date dTimeSpan = (Date) WebApplicationCommon.getSession().getAttribute(ApplicationCommon.SESSION_ALLOW_ACCESS_TIMESPAN);
            //判断是否超过5分钟
            Date dCurrent = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(dTimeSpan);
            cal.add(Calendar.MINUTE, 5);
            if (cal.getTime().before(dCurrent)) {//超过时间
                //使上次验证失效
                WebApplicationCommon.getSession().setAttribute(ApplicationCommon.SESSION_ALLOW_ACCESS, false);
            }
            return allowAccess;
        } else {
            return false;
        }
    }

    /**
     * 转换表单验证消息。
     *
     * @param result
     * @return
     */
    public String convertBindingResultToString(BindingResult result) {
        StringBuffer message = new StringBuffer("\n");
        List<FieldError> errorList = result.getFieldErrors();
        for (FieldError e : errorList) {
            message.append(e.getField() + e.getDefaultMessage() + "\n");
        }
        return message.toString();
    }

    /**
     * 文件下载
     *
     * @param contentType 文件类型
     * @param file        文件
     */
    public void renderFile(String contentType, File file) {
        renderFile(contentType, file, file.getName());
    }

    /**
     * 文件下载
     * @param contentType 文件类型
     * @param file 文件
     * @param name 文件名
     */
    public void renderFile(String contentType, File file, String name) {
        HttpServletRequest request = getRequest();
        HttpServletResponse response = getResponse();
        response.setContentType(contentType);
        OutputStream fos = null;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            if (!file.exists())
                return;
            //以流的形式下载
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            name = java.net.URLEncoder.encode(name, "UTF-8");
            //清空response
            response.reset();
            response.setHeader("content-disposition", "attachment;filename=" + name);
            response.addHeader("Content-Length", "" + file.length());
            fos = new BufferedOutputStream(response.getOutputStream());
            fos.write(buffer);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ObjectKit.isNotNull(fos)) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ObjectKit.isNotNull(fis)) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ObjectKit.isNotNull(bis)) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 返回错误
     * @param errorCode
     */
    public void renderError(int errorCode){
        getResponse().setStatus(errorCode);
    }

    /**
     * 获取请求参数
     *
     * @param key
     * @return
     */
    public WebParam getParameter(String key) {
        HttpServletRequest request = getRequest();

        Object obj = request.getParameter(key);

        return new WebParam(obj);
    }

    /**
     * 获取参数Map
     *
     * @return
     */
    public Map<String, String[]> getParameterMap() {
        return getRequest().getParameterMap();
    }

    /**
     * 获取Attribute
     *
     * @param key
     * @return
     */
    public WebParam getAttr(String key) {
        HttpServletRequest request = getRequest();

        Object obj = request.getAttribute(key);

        return new WebParam(obj);
    }

    /**
     * 设置Request Attribute
     *
     * @param key
     * @param value
     */
    public void setAttr(String key, Object value) {
        getRequest().setAttribute(key, value);
    }

    /**
     * 移除Request Attribute
     *
     * @param key
     */
    public void removeAttr(String key) {
        getRequest().removeAttribute(key);
    }

    /**
     * 获取Header
     *
     * @param key
     * @return
     */
    public WebParam getHeader(String key) {
        HttpServletRequest request = getRequest();

        Object obj = request.getHeader(key);

        return new WebParam(obj);
    }

    /**
     * 设置Header
     *
     * @param key
     * @param value
     */
    public void setHeader(String key, String value) {
        getResponse().setHeader(key, value);
    }


    /**
     * 获取Cookie
     *
     * @param key
     * @return
     */
    public WebParam getCookie(String key) {
        HttpServletRequest request = getRequest();

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (key.equals(cookie.getName()))
                return new WebParam(cookie.getValue());
        }
        return new WebParam("");
    }

    /**
     * 设置Cookie
     *
     * @param cookie
     */
    public void setCookie(Cookie cookie) {
        getResponse().addCookie(cookie);
    }

    /**
     * 设置Cookie
     *
     * @param name            名称
     * @param value           值
     * @param maxAgeInSeconds 有效期
     * @param path            see Cookie.setPath(String)
     */
    public void setCookie(String name, String value, int maxAgeInSeconds, String path) {
        setCookie(name, value, maxAgeInSeconds, path, null);
    }

    /**
     * 设置Cookie
     *
     * @param name            名称
     * @param value           值
     * @param maxAgeInSeconds 有效期
     * @param path            see Cookie.setPath(String)
     * @param domain          the domain name within which this cookie is visible; form is according to RFC 2109
     */
    public void setCookie(String name, String value, int maxAgeInSeconds, String path, String domain) {
        Cookie cookie = new Cookie(name, value);
        if (domain != null)
            cookie.setDomain(domain);
        cookie.setMaxAge(maxAgeInSeconds);
        cookie.setPath(path);
        getResponse().addCookie(cookie);
    }


    /**
     * 获取Sesison Attribute
     *
     * @param key
     * @return
     */
    public WebParam getSessionAttr(String key) {
        HttpSession session = getSession();

        Object obj = session.getAttribute(key);

        return new WebParam(obj);
    }

    /**
     * 向Session中存值
     *
     * @param key
     * @param value
     */
    public void setSessionAttr(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    /**
     * 从Session中移除值
     *
     * @param key
     */
    public void removeSessionAttr(String key) {
        getSession().removeAttribute(key);
    }

    /**
     * 返回成功
     *
     * @return see JSONObject
     */
    public JSONObject success() {
        return WebResult.success();
    }

    /**
     * 返回失败
     *
     * @param msg 错误消息
     * @return see JSONObject
     */
    public JSONObject error(String msg) {
        return WebResult.error(msg);
    }


    /**
     * 打印请求参数
     */
    public void printRequestParams() {
        HttpServletRequest request = getRequest();
        Enumeration<String> names = request.getParameterNames();
        System.out.println("===========JRelax Request Report " + getCurrentTime() + "=============");
        while (names.hasMoreElements()) {
            String key = names.nextElement();
            String value = request.getParameter(key);

            System.out.println(key + ":" + value);
        }
    }

    /**
     * 打印请求参数
     */
    public void printRequestHeaders() {
        HttpServletRequest request = getRequest();
        Enumeration<String> names = request.getHeaderNames();
        System.out.println("===========JRelax Header Report " + getCurrentTime() + "=============");
        while (names.hasMoreElements()) {
            String key = names.nextElement();
            String value = request.getHeader(key);

            System.out.println(key + ":" + value);
        }
    }
}
