package com.jrelax.web.support.filter;

import com.jrelax.core.web.resolver.ViewPrefixResolver;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * URL拦截器
 * 过滤用户访问的连接
 * 如果连接为非公开，并且当前用户没有访问该连接的权限，则转向权限错误页面
 * 如果连接为非公开，当前用户没有登录，则转向登录页面
 *
 * @author ZENGCHAO
 */
public class URLInterceptor implements HandlerInterceptor {
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
        return true;
    }

    /**
     * 在业务处理器处理完成之后执行
     * 在生成视图操作之前执行
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object object, ModelAndView mav) throws Exception {
        if (object instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) object;
            //处理视图前缀名问题
            ViewPrefixResolver.getInstance().resolver(handlerMethod, mav);
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
