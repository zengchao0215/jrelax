package com.jrelax.token;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * TokenSession入口程序
 * Created by zengchao on 2017-03-22.
 */
public class TokenSessionFilter implements Filter {
    private TokenSessionManager sessionManager = null;
    private String parameterName = TokenSessionConfig.PARAMETER_NAME;
    private String attributeName = TokenSessionConfig.ATTRIBUTE_NAME;

    public void init(FilterConfig filterConfig) throws ServletException {
        sessionManager = new StandardTokenSessionManager();
        String timeout = get(filterConfig.getInitParameter("timeout"), TokenSessionConfig.TIMEOUT + "");
        try {
            int time = Integer.parseInt(timeout);
            sessionManager.setTimeout(time);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        parameterName = get(filterConfig.getInitParameter("parameterName"), parameterName);
        attributeName = get(filterConfig.getInitParameter("attributeName"), attributeName);
        sessionManager.startInternal();
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = request.getHeader(parameterName);//优先在header中寻找
        if(token == null)//parameter
            token = request.getParameter(parameterName);
        if(token == null)//request
            token = (String) request.getAttribute(parameterName);
        if(token == null)//session
            token = (String) request.getSession().getAttribute(parameterName);
        Session session = null;
        if (token != null)
            session = sessionManager.findSession(token);
        if (session == null) {
            session = sessionManager.createSession(token);
            sessionManager.add(session);
        }
        session.access();//访问

        //存储
        response.setHeader(parameterName, session.getId());
        request.setAttribute(attributeName, session.getSession());
        //依托于session存储
        request.getSession().setAttribute(parameterName, session.getId());
        request.getSession().setAttribute(attributeName, session.getSession());

        session.endAccess();//结束访问

        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
        sessionManager.stopInternal();
    }

    private String get(String value, String defaultValue) {
        if (value == null) return defaultValue;
        return value;
    }
}
