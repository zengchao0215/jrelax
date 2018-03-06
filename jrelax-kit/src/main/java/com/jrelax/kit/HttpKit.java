package com.jrelax.kit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * 网络请求工具类
 * Created by Administrator on 2016/6/30.
 */
public class HttpKit {
    private static Logger logger = LoggerFactory.getLogger(HttpKit.class);

    /**
     * 发送GET请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String sendGet(String url, Map<String, String> params) {
        String response = "";
        try {
            StringBuffer requestParams = paramsMapToString(params);

            response = send(url, "GET", requestParams.toString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return response;
    }

    /**
     * 发送POST请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String sendPost(String url, Map<String, String> params) {
        String response = "";
        try {
            StringBuffer requestParams = paramsMapToString(params);

            response = send(url, "POST", requestParams.toString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return response;
    }

    /**
     * 组装参数
     *
     * @param params
     * @return
     */
    private static StringBuffer paramsMapToString(Map<String, String> params) {
        StringBuffer requestParams = new StringBuffer();
        if (ObjectKit.isNotNull(params)) {
            Iterator<String> keyIter = params.keySet().iterator();
            while (keyIter.hasNext()) {
                String key = keyIter.next();
                String value = params.get(key);

                requestParams.append(key + "=" + value + "&");
            }
        }
        return requestParams;
    }

    /**
     * 发送网络请求
     *
     * @param url           地址
     * @param method        请求方法
     * @param requestParams 请求参数，多个用`&`号隔开
     * @return
     * @throws Exception
     */
    public static String send(String url, String method, String requestParams) throws Exception {
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod(method);
        conn.setUseCaches(false);
        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Connection", "Close");
        conn.setRequestProperty("Content-length", String.valueOf(requestParams.length()));
        conn.setDoInput(true);
        conn.connect();
        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
        out.write(requestParams);
        out.flush();
        out.close();
        InputStream in = conn.getInputStream();
        InputStreamReader r = new InputStreamReader(in, "UTF-8");
        LineNumberReader din = new LineNumberReader(r);
        String line = null;
        StringBuffer sb = new StringBuffer();
        while ((line = din.readLine()) != null) {
            sb.append(line + "\n");
        }
        return sb.toString();
    }


    /**
     * 判断是否是ajax请求
     * 根据请求头信息中的x-requested-with属性
     *
     * @param request
     * @return
     */
    public static boolean isAjaxWithRequest(HttpServletRequest request) {
        if (ObjectKit.isNotNull(request)) {
            if (ObjectKit.isNotNull(request.getHeader("x-requested-with"))) {
                return request.getHeader("x-requested-with").equals("XMLHttpRequest");
            } else {
                if ("true".equals(request.getParameter("_SYSTEM_REQUEST_FROM_000")) || "true".equals(request.getHeader("_SYSTEM_REQUEST_FROM_000"))) {
                    return true;
                }
                String accept = request.getHeader("accept");
                if (accept != null) {
                    return "*/*".equals(accept) || accept.contains("application/json");
                }
                return false;
            }
        }
        return false;
    }

    /**
     * 是否是请求数据
     *
     * @param request
     * @return
     */
    public static boolean isRequestData(HttpServletRequest request) {
        String accept = request.getHeader("accept");
        return accept != null && !(accept.contains("text/html") || accept.contains("*/*"));
    }

    /**
     * 设置 Cookie（生成时间为1天）
     *
     * @param name  名称
     * @param value 值
     */
    public static void setCookie(HttpServletResponse response, String name, String value) {
        setCookie(response, name, value, 60 * 60 * 24);
    }

    /**
     * 设置 Cookie
     *
     * @param response
     * @param name     名称
     * @param value    值
     * @param path     路径
     */
    public static void setCookie(HttpServletResponse response, String name, String value, String path) {
        setCookie(response, name, value, path, 60 * 60 * 24);
    }

    /**
     * 设置 Cookie
     *
     * @param response
     * @param name     名称
     * @param value    值
     * @param maxAge   生存时间（单位秒）
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        setCookie(response, name, value, "/", maxAge);
    }

    /**
     * 设置 Cookie
     *
     * @param response
     * @param name     名称
     * @param value    值
     * @param path     路径
     * @param maxAge   生存时间（单位秒）
     */
    public static void setCookie(HttpServletResponse response, String name, String value, String path, int maxAge) {
        setCookie(response, name, value, null, path, maxAge, false);
    }

    /**
     * 保存Cookie
     *
     * @param response
     * @param name     名称
     * @param value    值
     * @param domain   域名
     * @param path     路径
     * @param maxAge   有效期
     * @param httpOnly 是否只读
     */
    public static void setCookie(HttpServletResponse response, String name, String value, String domain, String path, int maxAge, boolean httpOnly) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setDomain(domain);
        cookie.setHttpOnly(httpOnly);
        try {
            cookie.setValue(URLEncoder.encode(value, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.addCookie(cookie);
    }

    /**
     * 获得指定Cookie的值
     *
     * @param name 名称
     * @return 值
     */
    public static String getCookie(HttpServletRequest request, String name) {
        return getCookie(request, null, name, false);
    }

    /**
     * 获得指定Cookie的值，并删除。
     *
     * @param name 名称
     * @return 值
     */
    public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        return getCookie(request, response, name, true);
    }

    /**
     * 获得指定Cookie的值
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param name     名字
     * @param isRemove 是否移除
     * @return 值
     */
    public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name, boolean isRemove) {
        String value = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    try {
                        value = URLDecoder.decode(cookie.getValue(), "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    if (isRemove) {
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
            }
        }
        return value;
    }

    /**
     * 获取请求IP地址
     *
     * @param request
     * @return
     */
    public static String getRequestAddr(HttpServletRequest request) {
        if (request == null) return "";
        String ip = request.getHeader("x-forwarded-for");
        if(StringKit.isEmpty(ip))
            ip = request.getHeader("x-real-ip");//兼容nginx反向代理
        if(StringKit.isEmpty(ip))
            ip = request.getRemoteAddr();
        if ("0:0:0:0:0:0:0:1".equals(ip) || "127.0.0.1".equals(ip))
            ip = "本地主机";
        return ip;
    }

    /**
     * 获取浏览器型号
     *
     * @param request
     * @return
     */
    public static String getBrowser(HttpServletRequest request) {
        String browser = "未知";
        if (request != null) {
            try {
                String ua = getUserAgent(request);
                if (ua.contains("MSIE")) {//IE浏览器
                    int idx = ua.indexOf("MSIE");
                    browser = ua.substring(idx, ua.indexOf(";", idx));
                } else if (ua.contains("Edge/")) {//Microsoft Edge浏览器
                    int idx = ua.indexOf("Edge/");
                    browser = ua.substring(idx);
                } else if (ua.contains("gecko") && ua.contains("rv:11.0")) {
                    browser = "MSIE 11.0";
                } else if (ua.contains("OPR/")) {//Opera浏览器
                    int idx = ua.indexOf("OPR/");
                    browser = ua.substring(idx);
                    browser = browser.replace("OPR", "Opera");
                } else if (ua.contains("Chrome/")) {//Google Chrome浏览器
                    int idx = ua.indexOf("Chrome");
                    browser = ua.substring(idx, ua.indexOf(" ", idx));
                } else if (ua.contains("Firefox/")) {//Firefox浏览器
                    int idx = ua.indexOf("Firefox/");
                    browser = ua.substring(idx);
                } else if (ua.contains("Safari/")) {//Safari浏览器
                    int idx = ua.indexOf("Safari/");
                    browser = ua.substring(idx);
                }
            } catch (Exception e) {
                browser = "未知";
            }

        }
        return browser;
    }

    /**
     * 获取平台型号
     *
     * @param request
     * @return
     */
    public static String getPlatform(HttpServletRequest request) {
        String platform = "未知";
        if (request != null) {
            String ua = getUserAgent(request);
            if (ua.contains("Windows Phone")) {
                platform = "Windows Phone";
            } else if (ua.contains("Windows")) {
                platform = "Windows";
                if (ua.contains("Windows NT 10.0"))
                    platform = "Windows 10";
                else if (ua.contains("Windows NT 6.3"))
                    platform = "Windows 8.1";
                else if (ua.contains("Windows NT 6.2"))
                    platform = "Windows 8";
                else if (ua.contains("Windows NT 6.1"))
                    platform = "Windows 7";
                else if (ua.contains("Windows NT 6.0"))
                    platform = "Windows Vista";
                else if (ua.contains("Windows NT 5.1"))
                    platform = "Windows XP";
            } else if (ua.contains("iPad")) {
                platform = "iPad";
            } else if (ua.contains("iPhone")) {
                platform = "iPhone";
            } else if (ua.contains("Android")) {
                platform = "Android";
            } else if (ua.contains("Linux")) {
                platform = "Linux";
            }
        }
        return platform;
    }

    /**
     * 获取UserAgent
     *
     * @param request
     * @return
     */
    public static String getUserAgent(HttpServletRequest request) {
        String userAgent = "";
        if (request != null) {
            userAgent = request.getHeader("user-agent");
        }
        return userAgent;
    }
}
