package com.jrelax.plugins.blacklist;

import com.jrelax.core.plugin.IPlugin;
import com.jrelax.core.plugin.annotation.Plugin;
import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.core.web.IWebRule;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.web.system.service.BlackListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * IP黑名单插件
 * 配置黑名单规则后，只有启用黑名单插件，规则才会生效
 * Created by zengc on 2017-01-04.
 */
@Plugin(value = "IP黑名单插件", group = "系统", loadOnStartup = false, order = 2)
public class BlackListPlugin implements IPlugin, IWebRule {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BlackListService blackListService;

    @Override
    public boolean init() {
        //IP黑名单
        logger.info("加载IP黑名单...");
        blackListService.sync();
        ApplicationCommon.getFilterRules().add(this);
        logger.info("IP黑名单规则已启用");
        return true;
    }

    @Override
    public void destroy() {
        ApplicationCommon.getFilterRules().remove(this);
        logger.info("IP黑名单规则卸载");
    }

    @Override
    public boolean handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //IP黑名单规则
        if (session.getAttribute(ApplicationCommon.SESSION_IP_BLACK_LIST) == null) {//判断当前会话是否已经验证过
            if (!blackListService.isAllow(request.getRemoteAddr())) {
                session.setAttribute(ApplicationCommon.SESSION_IP_BLACK_LIST, false);
                request.getRequestDispatcher("/error/" + WebApplicationCommon.ERROR.IP_NOT_ALLOW).forward(request, response);
                return false;
            } else {
                session.setAttribute(ApplicationCommon.SESSION_IP_BLACK_LIST, true);
            }
        } else {
            boolean isAllow = (boolean) session.getAttribute(ApplicationCommon.SESSION_IP_BLACK_LIST);
            if (!isAllow) {
                request.getRequestDispatcher("/error/" + WebApplicationCommon.ERROR.IP_NOT_ALLOW).forward(request, response);
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
