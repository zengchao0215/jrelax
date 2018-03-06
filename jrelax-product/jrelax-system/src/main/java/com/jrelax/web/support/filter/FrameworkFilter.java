package com.jrelax.web.support.filter;

import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.core.web.IWebRule;
import com.jrelax.kit.StringKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;

public class FrameworkFilter implements Filter, Comparator<IWebRule> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Map<String, String> proxyMap = new HashMap<>();

    public void destroy() {

    }

    public void doFilter(ServletRequest arg0, ServletResponse arg1,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) arg0;
        HttpServletResponse response = (HttpServletResponse) arg1;
        if (ApplicationCommon.SYSTEM_INSTALLED) {
            if (ApplicationCommon.DEBUG) {
                // 打印连接地址信息
                StringBuffer url = request.getRequestURL();
                if (!StringKit.isEmpty(request.getQueryString())) {
                    url.append("?").append(request.getQueryString());
                }
                String format = "%s %s";
                logger.info(String.format(format, request.getMethod(), URLDecoder.decode(url.toString(), "UTF-8")));
                StringBuilder parameter = new StringBuilder();
                Enumeration<String> enumeration = request.getParameterNames();
                while (enumeration.hasMoreElements()) {
                    String name = enumeration.nextElement();
                    String value = request.getParameter(name);
                    parameter.append(",").append(name).append("=").append(value);
                }
                if (parameter.length() > 0)
                    logger.info("Parameter : {" + parameter.substring(1) + "}");
            }

            //规则适配器
            List<IWebRule> ruleAdapterList = ApplicationCommon.getCacheProvider().get(ApplicationCommon.CACHE_SYSTEM_RULES);
            if (ruleAdapterList != null) {
                //排序
                ruleAdapterList.sort(this);
                for (IWebRule ruleAdapter : ruleAdapterList) {
                    if (!ruleAdapter.handle(request, response))
                        return;
                }
            }

            //过滤 /favicon.ico
            String proxy = proxyMap.get(request.getRequestURI());
            if (proxy != null) {
                request.getRequestDispatcher(proxy).forward(arg0, arg1);
                return;
            }
            chain.doFilter(arg0, arg1);
        } else {
            //跳转到安装向导界面
            if (!request.getRequestURI().contains("/install/")) {
                response.sendRedirect(request.getContextPath() + "/install/");
            } else {
                chain.doFilter(arg0, arg1);
            }
        }
    }

    @Override
    public int compare(IWebRule o1, IWebRule o2) {
        Integer order1 = o1.order();
        Integer order2 = o2.order();
        return order1.compareTo(order2);
    }

    public void init(FilterConfig arg0) throws ServletException {
        proxyMap.put("/favicon.ico", "/framework/img/logo.png");
    }

}
