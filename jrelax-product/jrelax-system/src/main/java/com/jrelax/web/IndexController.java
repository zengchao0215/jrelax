package com.jrelax.web;

import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.core.web.annotation.Open;
import com.jrelax.core.web.annotation.OpenScope;
import com.jrelax.core.web.annotation.ViewPrefix;
import com.jrelax.core.web.session.SessionAttributeManager;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.core.web.support.http.HandlerRequest;
import com.jrelax.core.web.transform.DataGridTransforms;
import com.jrelax.kit.MailKit;
import com.jrelax.kit.Md5Kit;
import com.jrelax.kit.ObjectKit;
import com.jrelax.kit.StringKit;
import com.jrelax.orm.query.Condition;
import com.jrelax.orm.query.PageBean;
import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.entity.*;
import com.jrelax.web.system.service.*;
import com.sun.management.OperatingSystemMXBean;
import net.sf.json.JSONObject;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 系统界面
 *
 * @author zengchao
 */
@Controller
@RequestMapping("/")
@ViewPrefix("/index/")
@Open(scope = OpenScope.SESSION)
public class IndexController extends BaseController<Object> {

    private long lastSystemTime = 0;
    private long lastProcessCpuTime = 0;

    @Autowired
    private UserService userService;
    @Autowired
    private UserEmailService userEmailService;
    @Autowired
    private VersionService versionService;
    @Autowired
    private LogService logService;
    @Autowired
    private UserQuickMenuService userQuickMenuService;

    @RequestMapping(value = WebApplicationCommon.UrlPrefix.INDEX, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        return JRelaxSystemConfigHelper.get("system.index.page", "index-tabs");
    }

    @RequestMapping(value = WebApplicationCommon.UrlPrefix.WELCOME, method = {RequestMethod.GET, RequestMethod.POST})
    public String welcome() {
        return "welcome";
    }

    @RequestMapping(value = WebApplicationCommon.UrlPrefix.INDEX + "/version", method = {RequestMethod.GET, RequestMethod.POST})
    public String version(Model model) {
        Version version = versionService.getVersion();
        if (ObjectKit.isNotNull(version)) {
            model.addAttribute("version", version.getVersion());
            model.addAttribute("build", version.getBuild());
        }
        return "version";
    }

    /**
     * 皮肤
     *
     * @param model
     * @return
     */
    @RequestMapping(value = WebApplicationCommon.UrlPrefix.INDEX + "/theme", method = {RequestMethod.GET})
    public String theme(Model model) {
        //获取主题包
        model.addAttribute("themeMap", getDataDict().getMap("sys_themes"));
        return "theme";
    }

    @RequestMapping(value = WebApplicationCommon.UrlPrefix.INDEX + "/change/theme", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject changeTheme(String theme) {
        try {
            User user = getCurrentUser();
            if (ObjectKit.isNull(user))
                return WebResult.error("登录超时");
            user.setPageStyle(theme);
            userService.executeBatch("update User set pageStyle='" + theme + "' where id='" + user.getId() + "'");

            logService.info("系统首页", String.format("切换皮肤：[theme: %s]", theme), getCurrentUser().getUserName(), HandlerRequest.fromWebRequest(getRequest()));
            return WebResult.success();
        } catch (Exception e) {
            return WebResult.error(e);
        }
    }

    /**
     * 更换布局
     *
     * @param layout
     * @return
     */
    @RequestMapping(value = WebApplicationCommon.UrlPrefix.INDEX + "/change/layout", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject changeLayout(String layout) {
        try {
            User user = getCurrentUser();
            if (ObjectKit.isNull(user))
                return WebResult.error("登录超时");
            layout = layout.replace("ext", "").trim();
            user.setLayout(layout);
            userService.executeBatch("update User set layout='" + layout + "' where id='" + user.getId() + "'");

            logService.info("系统首页", String.format("切换布局：[layout: %s]", layout), getCurrentUser().getUserName(), HandlerRequest.fromWebRequest(getRequest()));
            return WebResult.success();
        } catch (Exception e) {
            return WebResult.error(e);
        }
    }

    /**
     * 个人中心
     *
     * @param model
     * @return
     */
    @RequestMapping(value = WebApplicationCommon.UrlPrefix.PROFILE, method = {RequestMethod.GET, RequestMethod.POST})
    public String profile(Model model) {
        User user = getCurrentUser();
        model.addAttribute("realName", user.getRealName());
        StringBuilder roleNames = new StringBuilder();
        for (Role role : user.getRoles()) {
            roleNames.append(",").append(role.getName());
        }
        if (roleNames.length() > 0)
            roleNames = new StringBuilder(roleNames.substring(1));
        model.addAttribute("roleNames", roleNames.toString());
        model.addAttribute("unitName", user.getUnits().get(0).getName());
        model.addAttribute("user", user);
        PageBean pageBean = new PageBean();
        pageBean.setRows(8);
        pageBean.addCriterion(Restrictions.or(Restrictions.eq("module", logService.getModule("login-out")), Restrictions.eq("module", logService.getModule("login"))));
        pageBean.addCriterion(Restrictions.eq("user", getCurrentUser().getUserName()));
        pageBean.addOrder(Order.desc("time"));
        model.addAttribute("logs", logService.list(pageBean));

        //邮件设置
        UserEmail userEmail = userEmailService.get("select mailUser from UserEmail where userId='" + getCurrentUser().getId() + "'");
        if (ObjectKit.isNotNull(userEmail)) {
            model.addAttribute("mailUser", userEmail.getMailUser());
        }
        model.addAttribute("range", new Random().nextInt(99999));
        return WebApplicationCommon.UrlPrefix.PROFILE + "/index";
    }

    /**
     * 修改密码
     *
     * @param oldPwd 原密码
     * @param newPwd 新密码
     * @return
     */
    @RequestMapping(value = WebApplicationCommon.UrlPrefix.PROFILE + "/pwd", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject pwd(String oldPwd, String newPwd) {
        //判断原密码是否正确
        oldPwd = Md5Kit.encode(oldPwd);
        User user = userService.get("select password from User where id = '" + getCurrentUser().getId() + "'");
        if (user.getPassword().equals(oldPwd)) {
            userService.executeBatch("update User set password='" + Md5Kit.encode(newPwd) + "' where id='" + getCurrentUser().getId() + "'");

            logService.info("系统首页", "修改密码", getCurrentUser().getUserName(), HandlerRequest.fromWebRequest(getRequest()));
        } else {
            return WebResult.error("原密码输入错误！");
        }
        return WebResult.success();
    }

    /**
     * 用户头像
     *
     * @return
     */
    @RequestMapping(value = WebApplicationCommon.UrlPrefix.PROFILE + "/head", method = {RequestMethod.GET, RequestMethod.POST})
    public String head() {
        return WebApplicationCommon.UrlPrefix.PROFILE + "/head";
    }

    /**
     * 上传用户头像
     *
     * @param path 图像路径
     * @return
     */
    @RequestMapping(value = WebApplicationCommon.UrlPrefix.PROFILE + "/head/upload", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject doUploadHead(String path) {
        try {
            getCurrentUser().setHeadImage(path);
            userService.merge(getCurrentUser());//更新到数据库中

            logService.info("个人中心", "上传新头像", getCurrentUser().getUserName(), HandlerRequest.fromWebRequest(getRequest()));
            return WebResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return WebResult.error(e);
        }
    }

    /**
     * 设置外部邮箱
     *
     * @param model
     * @return
     */
    @RequestMapping(value = WebApplicationCommon.UrlPrefix.PROFILE + "/email", method = {RequestMethod.GET, RequestMethod.POST})
    public String email(Model model) {
        UserEmail userEmail = userEmailService.get(Restrictions.eq("userId", getCurrentUser().getId()));
        if (ObjectKit.isNotNull(userEmail)) {
            model.addAttribute("email", userEmail);
        }
        return WebApplicationCommon.UrlPrefix.PROFILE + "/email";
    }

    /**
     * 保存外部邮箱设置
     *
     * @param email
     * @return
     */
    @RequestMapping(value = WebApplicationCommon.UrlPrefix.PROFILE + "/email/do", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject doEmail(UserEmail email) {
        UserEmail userEmail = userEmailService.get(Restrictions.eq("userId", getCurrentUser().getId()));
        if (ObjectKit.isNull(userEmail)) {
            userEmail = new UserEmail();
            userEmail.setUserId(getCurrentUser().getId());
        }
        userEmail.setMailType(email.getMailType());
        userEmail.setMailHost(email.getMailHost());
        userEmail.setMailPort(email.getMailPort());
        userEmail.setSslEnable(email.getSslEnable());
        userEmail.setMailUser(email.getMailUser());
        userEmail.setMailPass(email.getMailPass());

        logService.info("个人中心", "更新邮箱", getCurrentUser().getUserName(), HandlerRequest.fromWebRequest(getRequest()));
        userEmailService.saveOrUpdate(userEmail);
        return WebResult.success();
    }

    /**
     * 保存外部邮箱设置
     *
     * @param email
     * @return
     */
    @RequestMapping(value = WebApplicationCommon.UrlPrefix.PROFILE + "/email/test", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject doTestEmail(UserEmail email) {
        try {
            int result = MailKit.getMessageCount(email.getMailType(), email.getMailHost(), email.getMailPort(), email.getMailUser(), email.getMailPass(), email.getSslEnable());
            if (result < 0)
                return WebResult.error("配置不可用！");
        } catch (MessagingException e) {
            e.printStackTrace();
            return WebResult.error("配置不可用，" + e.getMessage());
        }
        return WebResult.success();
    }

    /**
     * 设置快捷菜单
     *
     * @param model
     * @return
     */
    @RequestMapping(value = WebApplicationCommon.UrlPrefix.PROFILE + "/quickmenu", method = {RequestMethod.GET, RequestMethod.POST})
    public String quickMenu(Model model) {
        return WebApplicationCommon.UrlPrefix.PROFILE + "/quickmenu/index";
    }

    /**
     * 当前拥有的快捷菜单
     *
     * @param pageBean
     * @return
     */
    @RequestMapping(value = WebApplicationCommon.UrlPrefix.PROFILE + "/quickmenu/data", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> quickMenuData(PageBean pageBean) {
        List<UserQuickMenu> quickMenuList = userQuickMenuService.listToEntity(pageBean, "select id, name, url from UserQuickMenu where user.id=?", getCurrentUser().getId());
        return DataGridTransforms.JQGRID.transform(quickMenuList, pageBean);
    }

    /**
     * 跳转到新增快捷菜单界面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = WebApplicationCommon.UrlPrefix.PROFILE + "/quickmenu/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addQuickMenu(Model model) {
        return WebApplicationCommon.UrlPrefix.PROFILE + "/quickmenu/add";
    }

    /**
     * 添加快捷菜单
     *
     * @return
     */
    @RequestMapping(value = WebApplicationCommon.UrlPrefix.PROFILE + "/quickmenu/add/do", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public JSONObject doAddQuickMenu(String name, String url) {
        int maxCount = 8;
        if (userQuickMenuService.count(Condition.NEW().eq("user", getCurrentUser())) >= maxCount)
            return WebResult.error("最多添加" + maxCount + "个快捷菜单");
        if (userQuickMenuService.count(Condition.NEW().eq("user", getCurrentUser()).eq("url", url)) > 0)
            return WebResult.error("已存在相同的快捷菜单");

        if (StringKit.isEmpty(name) || StringKit.isEmpty(url)) return WebResult.notAllow();
        UserQuickMenu quickMenu = new UserQuickMenu();
        quickMenu.setName(name);
        quickMenu.setUrl(url);
        quickMenu.setUser(getCurrentUser());

        userQuickMenuService.save(quickMenu);

        getCurrentUser().setQuickMenus(userQuickMenuService.list(Condition.NEW().eq("user", getCurrentUser())));
        return WebResult.success();
    }

    /**
     * 删除快捷菜单
     *
     * @return
     */
    @RequestMapping(value = WebApplicationCommon.UrlPrefix.PROFILE + "/quickmenu/remove", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public JSONObject removeQuickMenu(String[] menuIds) {
        if (menuIds.length > 0)
            for (String id : menuIds)
                userQuickMenuService.executeBatch("delete from UserQuickMenu where id = ?", id);
        getCurrentUser().setQuickMenus(userQuickMenuService.list(Condition.NEW().eq("user", getCurrentUser())));
        return WebResult.success();
    }

    /**
     * 锁定界面
     *
     * @return
     */
    @RequestMapping(WebApplicationCommon.UrlPrefix.LOCK)
    public String lock(Model model) {
        if (ObjectKit.isNotNull(getSession().getAttribute(ApplicationCommon.SESSION_LOCK))) {
            model.addAttribute("head", getCurrentUser().getHeadImage());
            model.addAttribute("realname", getCurrentUser().getRealName());
            model.addAttribute("locktime", getSessionAttr(ApplicationCommon.SESSION_LOCK_TIME));
            return "lock";
        } else {
            return "redirect:/index";
        }
    }

    /**
     * 锁定
     *
     * @return
     */
    @RequestMapping(WebApplicationCommon.UrlPrefix.LOCK + "/on")
    public String doLock() {
        setSessionAttr(ApplicationCommon.SESSION_LOCK, true);
        setSessionAttr(ApplicationCommon.SESSION_LOCK_TIME, getCurrentTime("yyyy-MM-dd HH:mm:ss"));
        logService.info("个人中心", "锁定系统", getCurrentUser().getUserName(), HandlerRequest.fromWebRequest(getRequest()));
        return "redirect:/index/lock";
    }

    /**
     * 解锁
     *
     * @return
     */
    @RequestMapping(WebApplicationCommon.UrlPrefix.LOCK + "/off")
    @ResponseBody
    public JSONObject doUnLock(String pwd) {
        if (StringKit.isEmpty(pwd)) {
            return WebResult.error("密码验证不通过");
        }
        User user = getCurrentUser();
        if (Md5Kit.encode(pwd).equals(user.getPassword())) {
            removeSessionAttr(ApplicationCommon.SESSION_LOCK);
            removeSessionAttr(ApplicationCommon.SESSION_LOCK_TIME);
            logService.info("个人中心", "解锁系统", getCurrentUser().getUserName(), HandlerRequest.fromWebRequest(getRequest()));
            return WebResult.success();
        }
        return WebResult.error("密码验证不通过");
    }


    /**
     * 获取系统状态
     *
     * @return
     */
    @RequestMapping(value = WebApplicationCommon.UrlPrefix.INDEX + "/status", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public JSONObject status() {
        try {
            OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
            //获取cpu使用率
            long systemTime = System.nanoTime();
            long processCpuTime = 0;
            processCpuTime = osmxb.getProcessCpuTime();
            int cpu = ((Double) ((processCpuTime - lastProcessCpuTime) / (systemTime - lastSystemTime) * 100.0)).intValue();

            lastProcessCpuTime = processCpuTime;
            lastSystemTime = systemTime;
            //获取内存占用率
            long totalMemory = osmxb.getTotalSwapSpaceSize();
            long freeMemory = osmxb.getFreeSwapSpaceSize();
            int mem = ((Double) ((1 - freeMemory * 1.0 / totalMemory) * 100)).intValue();
            //获取在线用户数
            int users = ApplicationCommon.getCacheOfLoginUser().size();


            return WebResult.success().element("cpu", cpu).element("mem", mem).element("users", users);
        } catch (Exception e) {
            return WebResult.error(e);
        }
    }

    /**
     * 二级菜单页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = WebApplicationCommon.UrlPrefix.INDEX + "/menu/second/{id}")
    public String secondMenu(Model model, @PathVariable String id) {
        //获取一级菜单名称
        String name = "功能列表";
        Map<String, Resource> resMap = SessionAttributeManager.get(WebApplicationCommon.RequestAttribute.SYSTEM_RESOURCE);
        if (resMap.containsKey(id)) {
            name = resMap.get(id).getName();
        }
        //获取二级菜单资源
        Map<String, List<Resource>> secondRes = SessionAttributeManager.get(WebApplicationCommon.RequestAttribute.SYSTEM_RESOURCE_LEVEL_2);
        model.addAttribute("list", secondRes.get(id));
        model.addAttribute("name", name);

        return "menu/second";
    }

    /**
     * 三级菜单页
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = WebApplicationCommon.UrlPrefix.INDEX + "/menu/third/{id}")
    public String thirdMenu(Model model, @PathVariable String id) {
        String name = "功能列表";
        Map<String, Resource> resMap = SessionAttributeManager.get(WebApplicationCommon.RequestAttribute.SYSTEM_RESOURCE);
        Resource res = resMap.get(id);
        if (res != null) {
            name = res.getName();
        }
        //获取三级菜单资源
        Map<String, List<Resource>> thirdRes = SessionAttributeManager.get(WebApplicationCommon.RequestAttribute.SYSTEM_RESOURCE_LEVEL_3);
        List<Resource> list = thirdRes.get(id);
        model.addAttribute("list", list);
        model.addAttribute("name", name);

        if (list != null && list.size() > 0) {
            res = list.get(0);
        }
        model.addAttribute("res", res);
        return "menu/third";
    }

    /**
     * 三级菜单页
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = WebApplicationCommon.UrlPrefix.INDEX + "/menu/icon/{id}")
    public String iconMenu(Model model, @PathVariable String id) {
        String name = "功能列表";
        Map<String, Resource> resMap = SessionAttributeManager.get(WebApplicationCommon.RequestAttribute.SYSTEM_RESOURCE);
        Resource res = resMap.get(id);
        if (res != null) {
            name = res.getName();
            model.addAttribute("pid", res.getParentId());
        }
        //获取三级菜单资源
        Map<String, List<Resource>> secondRes = SessionAttributeManager.get(WebApplicationCommon.RequestAttribute.SYSTEM_RESOURCE_LEVEL_2);
        List<Resource> list = secondRes.get(id);
        model.addAttribute("list", list);
        model.addAttribute("name", name);

        if (list != null && list.size() > 0) {
            res = list.get(0);
        }
        model.addAttribute("res", res);
        return "menu/icon";
    }
}

