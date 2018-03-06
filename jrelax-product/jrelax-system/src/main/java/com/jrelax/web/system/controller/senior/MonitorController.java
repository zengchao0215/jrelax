package com.jrelax.web.system.controller.senior;

import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.kit.ObjectKit;
import com.jrelax.web.LoginController;
import com.jrelax.web.support.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 数据库连接池监控
 *
 * @author zengchao
 */
@Controller
@RequestMapping("/system/senior/monitor")
public class MonitorController extends BaseController {
    private final String TPL = "/system/senior/monitor/";
    @Resource
    private LoginController signController;

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        return TPL + "index";
    }

    /**
     * 在线用户监控
     *
     * @param model
     * @return
     */
    @RequestMapping("/online")
    public String online(Model model) {
        model.addAttribute("sessionMap", ApplicationCommon.getCacheOfSession());
        model.addAttribute("key", ApplicationCommon.SESSION_ADMIN);
        model.addAttribute("sid", getSession().getId());
        return TPL + "online";
    }

    /**
     * 强制下线
     *
     * @param model
     * @param key   用户识别key
     * @return
     */
    @RequestMapping("/online/offline")
    public String onlineOffline(Model model, String key) {
        HttpSession session = ApplicationCommon.getCacheOfSession().get(key);
        if (ObjectKit.isNotNull(session)) {
            signController.doLogout(session);
        }
        online(model);
        return TPL + "online";
    }
}
