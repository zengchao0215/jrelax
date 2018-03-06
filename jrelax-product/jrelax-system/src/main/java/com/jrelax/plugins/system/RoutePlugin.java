package com.jrelax.plugins.system;

import com.jrelax.core.plugin.IPlugin;
import com.jrelax.core.plugin.annotation.Plugin;
import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.core.web.IWebRule;
import com.jrelax.web.system.entity.Route;
import com.jrelax.web.system.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 系统URL路由插件
 * 配置规则后，只有启用路由插件，规则才会生效
 * Created by zengc on 2017-01-04.
 */
@Plugin(value = "系统URL路由插件", group = "系统", loadOnStartup = false, order = 2)
public class RoutePlugin implements IPlugin, IWebRule {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RouteService routeService;

    @Override
    public boolean init() {
        //系统URL路由规则
        logger.info("系统URL路由规则...");
        routeService.sync();
        ApplicationCommon.getFilterRules().add(this);
        logger.info("系统URL路由规则已启用");
        return true;
    }

    @Override
    public void destroy() {
        ApplicationCommon.getFilterRules().remove(this);
    }

    @Override
    public boolean handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //路由规则
        String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
        Route route = routeService.getRoute(requestUrl);
        if (route != null) {
            String targetUrl = route.getTargetUrl();
            if (route.isRedirect()) {//是否是重定向
                if (route.isInternal()) {//如果目标链接时系统内部链接，则加上contextPath
                    if (targetUrl.startsWith("/"))
                        targetUrl = ApplicationCommon.BASEPATH + targetUrl;
                    else
                        targetUrl = ApplicationCommon.BASEPATH + "/" + targetUrl;
                }
                response.sendRedirect(targetUrl);
                return false;
            } else {
                request.getRequestDispatcher(targetUrl).forward(request, response);
                return false;
            }
        }
        return true;
    }

    @Override
    public int order() {
        return 1;
    }
}
