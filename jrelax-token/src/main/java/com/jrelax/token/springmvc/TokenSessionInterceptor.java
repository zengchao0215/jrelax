package com.jrelax.token.springmvc;

import com.jrelax.core.web.support.WR;
import com.jrelax.core.web.support.http.ContentType;
import com.jrelax.token.TokenSession;
import com.jrelax.token.TokenSessionUtil;
import com.jrelax.token.springmvc.annotation.TokenAuth;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * TokenSession拦截器
 * 调用HandlerMethod之前验证当前请求中有没有TokenSession，如果没有返回认证失败
 * 判断TokenSession作用域中是否存在指定参数，如果存在并且不为null，认证成功，否则返回认证失败
 * Created by zengchao on 2017-03-23.
 */
public class TokenSessionInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod method = (HandlerMethod) handler;
            TokenAuth tokenAuth = method.getMethodAnnotation(TokenAuth.class);
            if(tokenAuth != null){
                //获取TokenSession
                TokenSession session = TokenSessionUtil.getSession(request);
                if(session == null){
                    renderAuthError(response);
                    return false;
                }
                Object value = session.getAttribute(tokenAuth.value());
                if(value == null){
                    renderAuthError(response);
                    return false;
                }
            }
        }
        return true;
    }

    private void renderAuthError(HttpServletResponse response) throws IOException {
        response.setContentType(ContentType.JSON);
        PrintWriter out = response.getWriter();
        out.write(WR.error("Token无效").element("type", "TOKEN-INVALID").toString());
        out.close();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
