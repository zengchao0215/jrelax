package com.jrelax.web.support.filter;

import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.kit.HttpKit;
import com.jrelax.web.support.PrivilegeManager;
import com.jrelax.web.support.PrivilegeResult;
import com.jrelax.web.system.entity.Resource;
import com.jrelax.web.system.entity.User;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PrivilegeInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        if(JRelaxSystemConfigHelper.getBoolean("system.perm.force", true)){//配置是否开启URL菜单权限
            String requestUrl = request.getServletPath();
            if (WebApplicationCommon.isAdminRequest(requestUrl, (HandlerMethod) object)) {
                //开放模块无需验证
                if (WebApplicationCommon.isOpenRequest(requestUrl, (HandlerMethod) object))
                    return true;
                //公共模块无需验证
                if (WebApplicationCommon.isCommonRequest(requestUrl, (HandlerMethod) object))
                    return true;
                //判断用户是否有访问这个连接的权限
                User currentUser = (User) WebApplicationCommon.getSessionAdminUser(request.getSession());
                PrivilegeResult result = PrivilegeManager.hasPrivilege(currentUser, requestUrl, (HandlerMethod) object);
                if (!result.hasPrivilege()) {//没有权限
                    //判断是否是异步请求
                    if (HttpKit.isAjaxWithRequest(request)) {
                        WebApplicationCommon.renderJson(response, WebResult.noPermission());
                    } else {
                        response.setStatus(403);
                        request.getRequestDispatcher(WebApplicationCommon.Url.ERROR + "/" + WebApplicationCommon.ERROR.NO_PERMISSION).forward(request, response);
                    }
                    return false;
                } else if (result.isDisabled()) {//资源已停用
                    Resource resource = result.getResource();
                    //判断是否是异步请求
                    if (HttpKit.isAjaxWithRequest(request)) {
                        WebApplicationCommon.renderJson(response, WebResult.notEnable(resource.getName()));
                        return false;
                    } else {
                        response.setStatus(403);
                        request.getRequestDispatcher(WebApplicationCommon.Url.ERROR + "/" + WebApplicationCommon.ERROR.RES_DISABLED).forward(request, response);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
