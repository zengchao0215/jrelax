package com.jrelax.web.support.filter;

import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.kit.HttpKit;
import com.jrelax.kit.ObjectKit;
import com.jrelax.kit.StringKit;
import com.jrelax.core.web.model.User;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * 后台过滤器
 * 过滤未登录的用户
 *
 * @author ZENGCHAO
 */
public class AdminInterceptor implements HandlerInterceptor {
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
        String requestUrl = request.getServletPath();

        if (WebApplicationCommon.isAdminRequest(requestUrl, (HandlerMethod) object)) {
            //判断如果是登陆页面且已经登陆了，则直接跳转到首页
            if (WebApplicationCommon.isLoginRequest(requestUrl, (HandlerMethod) object) && !requestUrl.startsWith(WebApplicationCommon.UrlPrefix.LOGIN_AGAIN)) {
                if (ObjectKit.isNotNull(WebApplicationCommon.getSessionAdminUser(request.getSession()))) {
                    response.sendRedirect(request.getContextPath() + WebApplicationCommon.Url.INDEX);
                    return false;
                }
            }
            //开放模块无需验证
            if (WebApplicationCommon.isOpenRequest(requestUrl, (HandlerMethod) object))
                return true;
            if (ObjectKit.isNotNull(WebApplicationCommon.getSessionAdminUser(request.getSession()))) {
                //判断是否存在于ApplicationCommon中
                User cUser = WebApplicationCommon.getSessionAdminUser(request.getSession());
                if (ApplicationCommon.DEBUG) {
                    if (!ApplicationCommon.getCacheOfLoginUser().contains(cUser.getUserName())) {
                        //在服务器热重启的情况下 ApplicationCommon中的数据丢失
                        ApplicationCommon.getCacheOfLoginUser().add(cUser.getUserName());
                        ApplicationCommon.getCacheOfLoginSession().put(cUser.getUserName(), request.getSession());
                    }
                }
                //判断是否已经锁屏
                if (ObjectKit.isNotNull(request.getSession().getAttribute(ApplicationCommon.SESSION_LOCK))) {
                    if (!requestUrl.startsWith(WebApplicationCommon.Url.LOCK)) {//锁定界面
                        response.sendRedirect(request.getContextPath() + WebApplicationCommon.Url.LOCK);
                        return false;
                    }
                }
            } else {
                //判断是否是ajax请求，如果不是ajax请求则直接重定向
                if (HttpKit.isAjaxWithRequest(request)) {
                    WebApplicationCommon.renderJson(response, WebResult.sessionTimeout());
                } else {
                    String queryString = StringKit.isEmpty(request.getQueryString()) ? "" : "?" + request.getQueryString();
                    String redirect_url = request.getRequestURI() + queryString;
                    redirect_url = URLEncoder.encode(redirect_url, "UTF-8");
                    String url = request.getContextPath() + WebApplicationCommon.Url.LOGIN + "?" + WebApplicationCommon.REDIRECT_URL + "=" + redirect_url;
                    response.sendRedirect(url);
                }
                return false;
            }
        }
        return true;
    }

    /**
     * 在业务处理器处理完成之后执行
     * 在生成视图操作之前执行
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object object, ModelAndView mav) throws Exception {
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
