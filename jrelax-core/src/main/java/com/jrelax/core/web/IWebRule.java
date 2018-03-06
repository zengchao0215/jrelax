package com.jrelax.core.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 规则适配器
 * 黑名单规则、路由规则、
 * Created by zengc on 2017-01-04.
 */
public interface IWebRule {

    /**
     * 处理程序
     * @param request
     * @param response
     * @return 返回true表示通过，返回false表示被拦截
     * @throws ServletException
     * @throws IOException
     */
    boolean handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    /**
     * 处理顺序，值越小，排序越靠前
     * @return
     */
    int order();
}
