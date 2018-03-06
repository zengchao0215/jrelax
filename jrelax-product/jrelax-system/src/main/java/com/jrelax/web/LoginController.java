package com.jrelax.web;

import com.jrelax.aop.annotation.Log;
import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.core.web.annotation.Open;
import com.jrelax.core.web.annotation.OpenScope;
import com.jrelax.core.web.annotation.ViewPrefix;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.kit.HttpKit;
import com.jrelax.kit.Md5Kit;
import com.jrelax.kit.ObjectKit;
import com.jrelax.kit.StringKit;
import com.jrelax.orm.query.Condition;
import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.entity.User;
import com.jrelax.web.system.service.UserService;
import net.sf.json.JSONObject;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
@ViewPrefix("/index" + WebApplicationCommon.UrlPrefix.LOGIN)
public class LoginController extends BaseController<User> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserService userService;

    /**
     * 登录界面
     *
     * @param model
     * @param redirect_url 登录后的跳转地址
     * @param theme        登陆页皮肤，默认为default
     * @return
     */
    @RequestMapping(value = WebApplicationCommon.UrlPrefix.LOGIN, method = {RequestMethod.GET, RequestMethod.POST})
    @Log(name = "login", content = "登录", target = Log.Target.LOGGER)
    @Open(scope = OpenScope.ALL)
    public String login(Model model, @RequestParam(required = false) String redirect_url, String theme) {
        if (ObjectKit.isNotNull(redirect_url) && !redirect_url.contains(WebApplicationCommon.UrlPrefix.LOGIN)) {
            model.addAttribute(WebApplicationCommon.REDIRECT_URL, redirect_url);
            //记录日志
            logger.debug(String.format("ip: %s, return_url: %s", getRequestAddr(), redirect_url));
        } else
            model.addAttribute(WebApplicationCommon.REDIRECT_URL, ApplicationCommon.BASEPATH + WebApplicationCommon.UrlPrefix.INDEX);

        if (StringKit.isNotEmpty(theme)) {
            return theme;
        }
        return getSystemConfig("system.login.theme", "default");
    }

    /**
     * 登录操作
     *
     * @param session    会话
     * @param username   用户名
     * @param password   密码
     * @param verifycode 验证码
     * @return
     */
    @RequestMapping(value = WebApplicationCommon.UrlPrefix.LOGIN + "/do", method = {RequestMethod.POST})
    @ResponseBody
    @Open(scope = OpenScope.ALL)
    public JSONObject doLogin(HttpSession session, String username, String password, @RequestParam(required = false) String verifycode) {
        if (ObjectKit.isNotNull(verifycode))
            verifycode = verifycode.toLowerCase();
        if (StringKit.isBlank(username)) {
            return WebResult.error("用户名或邮箱不能为空!");//用户名不能为空
        }
        if (StringKit.isBlank(password)) {
            return WebResult.error("密码不能为空!");
        }
        username = username.trim();
        password = password.trim();
        password = Md5Kit.encode(password);//密码转为密文

        //根据用户名或密码查询
        if (userService.count(Condition.NEW().or(Restrictions.eq("userName", username), Restrictions.eq("email", username)).eq("password", password)) > 0) {
            //存储客户端信息
            session.setAttribute("user-agent", HttpKit.getUserAgent(getRequest()));
            session.setAttribute("ip-addr", getRequest().getRemoteAddr());
            session.setAttribute("login-time", getCurrentTime());
            // 用户名密码正确, 初始化用户权限
            return userService.executeLogin(username, password, verifycode, session);
        } else {
            //记录日志
            logger.debug(String.format("[UserName: %s, Password: %s] 登录失败", username, password));
            return WebResult.error("用户名或密码错误！");
        }
    }

    /**
     * 退出
     *
     * @param session 会话
     * @return
     */
    @RequestMapping(value = WebApplicationCommon.UrlPrefix.LOGIN_OUT, method = {RequestMethod.GET, RequestMethod.POST})
    @Open(scope = OpenScope.SESSION)
    @Log(name = "系统登录", content = "退出系统")
    public String doLogout(HttpSession session) {
        userService.executeLoginOut(session);
        return "redirect:" + WebApplicationCommon.UrlPrefix.LOGIN;
    }

    /**
     * 重新登录
     *
     * @param session 会话
     * @return
     */
    @RequestMapping(value = WebApplicationCommon.UrlPrefix.LOGIN_AGAIN)
    @Open(scope = OpenScope.SESSION)
    @Log(name = "系统登录", content = "重新登录")
    public String reLogin(HttpSession session) {
        User user = getCurrentUser();
        //先退出
        userService.executeLoginOut(session);
        //再重新登录
        userService.executeLogin(user.getId(), "", getSession());
        return "redirect:/index";
    }
}
