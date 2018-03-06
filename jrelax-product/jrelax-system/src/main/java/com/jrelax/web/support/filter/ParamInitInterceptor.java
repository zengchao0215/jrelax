package com.jrelax.web.support.filter;

import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.kit.DateKit;
import com.jrelax.kit.HttpKit;
import com.jrelax.kit.ObjectKit;
import com.jrelax.web.support.PrivilegeManager;
import com.jrelax.core.web.session.SessionAttributeManager;
import com.jrelax.web.system.entity.Resource;
import com.jrelax.web.system.entity.User;
import com.jrelax.web.system.service.LogService;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 添加每次请求中一些公用的参数
 *
 * @author ZENGCHAO
 */
public class ParamInitInterceptor implements HandlerInterceptor {
    @javax.annotation.Resource
    private LogService logService;

    /**
     * 在业务处理器处理之前调用
     * 如果返回false
     * 则从当前的处理器往回执行afterCompletion(),再退出拦截连
     * 如果返回true
     * 执行下一个拦截器，知道所有拦截器你执行完毕
     * 在执行业务处理器Controller
     * 然后进入拦截器链
     * 从最后一个拦截器往回执行所有的postHandle()
     * 接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object object) throws Exception {
        //初始化公用参数
        WebApplicationCommon.initCommonParams(request);
        //锤石华公用类
        WebApplicationCommon.initTools(request);
        String requestUrl = request.getServletPath();

        if (WebApplicationCommon.isOpenRequest(requestUrl, (HandlerMethod) object))
            return true;

        if (WebApplicationCommon.isAdminRequest(requestUrl, (HandlerMethod) object)) {
            //如果是请求数据，则不执行url解析
            if (HttpKit.isRequestData(request)) return true;
            User currentUser = (User) WebApplicationCommon.getSessionAdminUser(request.getSession());
            //初始化登录用户相关信息
            PrivilegeManager.initLoginUserParams(currentUser, request);
            PrivilegeManager.initLoginUserResource(currentUser, request);

            //-------------------------获取URL对应的资源------------------------------
            Resource currentResource = PrivilegeManager.matchResource(currentUser.getResources(), requestUrl);//当前机构，用于按钮权限控制
            if (ObjectKit.isNotNull(currentResource)) {
                //获取资源位置 类似：菜单1》菜单2》菜单3
                List<String> sidebarPath = new ArrayList<String>();//存储路径
                if (SessionAttributeManager.has("_sidebar_" + currentResource.getId())) {
                    sidebarPath = SessionAttributeManager.get("_sidebar_" + currentResource.getId());
                } else {
                    getResPath(currentResource, currentUser.getResources(), sidebarPath);
                    Collections.reverse(sidebarPath);

                    SessionAttributeManager.put("_sidebar_" + currentResource.getId(), sidebarPath);
                }
                request.setAttribute("_sidebar", sidebarPath);
                //获取按钮权限和字段权限
                if (currentUser.isSystemAdmin()) {//系统管理员不做按钮权限控制
                    request.setAttribute(ApplicationCommon.AUTH_BUTTON, "ALL");
                    request.setAttribute(ApplicationCommon.AUTH_COLUMNS, "ALL");
                    request.setAttribute(ApplicationCommon.AUTH_RESOURCE, "ALL");
                } else {
                    //按钮权限
                    request.setAttribute(ApplicationCommon.AUTH_BUTTON, PrivilegeManager.getResourceButtonPrivilege(currentResource));
                    //字段权限
                    request.setAttribute(ApplicationCommon.AUTH_COLUMNS, PrivilegeManager.getResourceColumnPrivilege(currentResource));
                    //资源权限
                    request.setAttribute(ApplicationCommon.AUTH_RESOURCE, PrivilegeManager.getResourcePrivilege(currentUser));
                }
            }
        }

        return true;
    }


    /**
     * 验证时间，判断是否是在评估时间内
     *
     * @param response
     * @return
     */
    private boolean validateDate(HttpServletResponse response) {
        try {

            Date date = DateKit.getNetDate();// 获取网络时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);// 输出北京时间
            Date endDate = sdf.parse("2017-12-18 00:00:00");

            if (DateKit.before(endDate, date)) {
                response.sendError(503, "评估版本已到期！");
                return false;
            }
        } catch (IOException | ParseException e) {
            try {
                response.sendError(503, "服务器异常！");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return false;
        }

        return true;
    }

    /**
     * 获取资源的位置
     * 一级菜单》二级菜单》三级菜单》...
     *
     * @param sidebarPath
     */
    private void getResPath(Resource res, List<Resource> allRes, List<String> sidebarPath) {
        sidebarPath.add(res.getName());
        if (!res.getParentId().equals("-1")) {
            Resource parentRes = null;
            for (Resource r : allRes) {
                if (r.getId().equals(res.getParentId()))
                    parentRes = r;
            }
            if (ObjectKit.isNotNull(parentRes))
                getResPath(parentRes, allRes, sidebarPath);
        }
    }

    /**
     * 在业务处理器处理完成之后执行
     * 在生成视图操作之前执行
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object object, ModelAndView mav) throws Exception {
        if (ObjectKit.isNotNull(mav)) {
            String basePath = request.getContextPath().replace("/", "");
            if (basePath.trim().length() > 0) {
                basePath = "/" + basePath;
            }
            if (ObjectKit.isNotNull(mav.getViewName())) {
                if (mav.getViewName().equals(WebApplicationCommon.ERROR.UNAUTHORIZED_ACCESS)) {
                    if (HttpKit.isAjaxWithRequest(request)) {
                        WebApplicationCommon.renderJson(response, WebResult.notAllow());
                    } else {
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.sendRedirect(basePath + WebApplicationCommon.Url.ERROR + "/" + WebApplicationCommon.ERROR.UNAUTHORIZED_ACCESS);
                    }
                    mav.clear();
                } else if (mav.getViewName().equals(WebApplicationCommon.ERROR.NO_PERMISSION)) {//无权访问
                    if (HttpKit.isAjaxWithRequest(request)) {
                        WebApplicationCommon.renderJson(response, WebResult.noPermission());
                    } else {
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.sendRedirect(basePath + WebApplicationCommon.Url.ERROR + "/" + WebApplicationCommon.ERROR.NO_PERMISSION);
                    }
                    mav.clear();
                } else if (mav.getViewName().equals(WebApplicationCommon.ERROR.NOT_FOUND)) {
                    if (HttpKit.isAjaxWithRequest(request)) {
                        WebApplicationCommon.renderJson(response, WebResult.error("404 Page Not Found"));
                    } else {
                        response.setStatus(HttpStatus.NOT_FOUND.value());
                        response.sendRedirect(basePath + WebApplicationCommon.Url.ERROR + "/" + WebApplicationCommon.ERROR.NOT_FOUND);
                    }
                    mav.clear();
                } else if (mav.getViewName().equals(WebApplicationCommon.ERROR.ERROR)) {
                    if (HttpKit.isAjaxWithRequest(request)) {
                        WebApplicationCommon.renderJson(response, WebResult.error("系统繁忙!"));
                    } else {
                        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                        response.sendRedirect(basePath + WebApplicationCommon.Url.ERROR + "/" + WebApplicationCommon.ERROR.ERROR);
                    }
                    mav.clear();
                } else if (mav.getViewName().equals(WebApplicationCommon.CustomError.getViewName())) {
                    if (HttpKit.isAjaxWithRequest(request)) {
                        WebApplicationCommon.renderJson(response, WebResult.error(WebApplicationCommon.CustomError.getScript()));
                    } else {
                        response.sendRedirect(basePath + WebApplicationCommon.Url.ERROR + "/" + WebApplicationCommon.ERROR.ERROR);
                    }
                    mav.clear();
                }
            }
        }
    }

    /**
     * 在DispatcherServlet完全处理完请求后调用
     * 如果发生异常，则会从当前的拦截器往回执行所有的afterCompletion
     */
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object object, Exception exception)
            throws Exception {
    }
}
