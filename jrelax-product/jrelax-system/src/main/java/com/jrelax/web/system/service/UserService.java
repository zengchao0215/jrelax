package com.jrelax.web.system.service;

import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.event.ApplicationEventDefined;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.core.web.support.http.HandlerRequest;
import com.jrelax.kit.DateKit;
import com.jrelax.kit.ObjectKit;
import com.jrelax.kit.StringKit;
import com.jrelax.orm.query.Condition;
import com.jrelax.web.support.BaseService;
import com.jrelax.core.web.session.SessionAttributeManager;
import com.jrelax.web.system.entity.*;
import net.sf.json.JSONObject;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
@EnableAsync
public class UserService extends BaseService<User> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ResourceButtonService btnService;
    @Autowired
    private RoleResourceButtonService rrbService;
    @Autowired
    private ResourceService resService;
    @Autowired
    private LogService logService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private RoleResourceColumnService rrcService;

    /**
     * 获取用户的所有菜单
     *
     * @param id
     * @return
     */
    public List<Resource> getUserResList(String id) {
        User user = this.getById(id);
        //初始化角色
        Hibernate.initialize(user.getRoles());
        //初始化单位
        Hibernate.initialize(user.getUnits());
        //初始化用户组
        Hibernate.initialize(user.getGroups());

        parseUserResource(user);
        return user.getResources();
    }

    /**
     * 通过用户名密码登录操作
     *
     * @param username 用户名
     * @param password 密码
     * @param captcha  验证码
     * @param session
     */
    public JSONObject executeLogin(String username, String password, String captcha, HttpSession session) {
        Condition condition = Condition.NEW();
        condition.or(Restrictions.eq("userName", username), Restrictions.eq("email", username))
                .eq("password", password);
        User user = this.get(condition);

        return loginHandler(user, captcha, session);
    }

    /**
     * 通过用户ID进行登录
     *
     * @param id      用户id
     * @param captcha 验证码
     * @param session
     * @return
     */
    public JSONObject executeLogin(String id, String captcha, HttpSession session) {
        User user = this.getById(id);

        return loginHandler(user, captcha, session);
    }

    /**
     * 登录处理程序
     *
     * @param user    用户
     * @param captcha 验证码
     * @param session
     * @return
     */
    private JSONObject loginHandler(User user, String captcha, HttpSession session) {
        if (ObjectKit.isNull(user))
            return WebResult.error("用户不存在！");
        if (!user.isEnabled()) {
            return WebResult.error("此用户被禁用！");
        }
        //触发事件
        getEventManager().trigger(ApplicationEventDefined.ON_BEFORE_LOGIN, this, user);

        if (JRelaxSystemConfigHelper.getBoolean("system.login.verify", true)) {
            //判断IP前三位是否相同
            if (StringKit.isNotEmpty(user.getLastLoginIp())) {
                String prefix = user.getLastLoginIp();
                if (prefix.lastIndexOf(".") > 0) {
                    prefix = prefix.substring(0, prefix.lastIndexOf("."));
                }
                //获取上次登录IP
                if (!getRequestAddr().startsWith(prefix)) {//判断是否与上次登录的IP地址一致
                    if (StringKit.isEmpty(captcha))
                        return WebResult.error("vc");
                    else if (!VerificationCode(captcha))
                        return WebResult.error("vc_error");
                }
            }
        }
        //初始化角色
        Hibernate.initialize(user.getRoles());
        //初始化用户组
        Hibernate.initialize(user.getGroups());
        //初始化单位
        Hibernate.initialize(user.getUnits());
        //初始化单位的负责人
        if(user.getUnits() != null){
            for(Unit unit : user.getUnits()){
                Hibernate.initialize(unit.getLeader());
            }
        }
        //初始化用户快捷菜单
        Hibernate.initialize(user.getQuickMenus());
        //初始化配置项
        Hibernate.initialize(user.getConfigs());

        //获取上级机构 + 判断机构状态，机构状态为禁用时，下属所有用户无法登陆
        if (user.getUnits().size() > 0) {
            if (!user.getDefaultUnit().isEnabled()) return WebResult.error("您的账号所在机构已禁用，请与系统管理员联系。");
            if (!user.getDefaultUnit().getParentId().equals("-1")) {
                //获取所有上级机构
                String code = user.getDefaultUnit().getCode();
                for (int i = 3; i < code.length(); i += 3) {
                    Unit parentUnit = unitService.getByProperty("code", code.substring(0, code.length() - i));
                    if (!parentUnit.isEnabled()) return WebResult.error("您的账号所在机构的上级机构已禁用，请与系统管理员联系。");
                }
            }
        }

        //解析用户资源+按钮权限
        parseUserResource(user);
        //判断用户是否有资源权限
        if (user.getResources().size() == 0) {
            return WebResult.error("您的账号没有任何资源权限，系统让您无法登录。");
        }
        //存入Session中
        WebApplicationCommon.setSessionAdminUser(session, user);
        ApplicationCommon.getCacheOfLoginSession().put(user.getUserName(), session);
        ApplicationCommon.getCacheOfSession().put(session.getId(), session);

        //唯一登录逻辑， 当系统配置为唯一登录时生效
        if (JRelaxSystemConfigHelper.getBoolean("system.login.unique", false)) {
            if (ObjectKit.isNotNull(ApplicationCommon.getCacheOfLoginSession().get(user.getUserName()))) {
                //之前的用户，强制下线
                HttpSession session2 = ApplicationCommon.getCacheOfLoginSession().get(user.getUserName());
                executeLoginOut(session2);
            }
        }

        //后置处理程序
        afterLogin(user);

        //触发事件
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("session", session);
        paramMap.put("user", user);
        paramMap.put("time", DateKit.now());
        getEventManager().trigger(ApplicationEventDefined.ON_AFTER_LOGIN, this, paramMap);

        return WebResult.success();
    }

    private void afterLogin(User user) {
        //记录登录日志
        logService.info("login", StringKit.format("[{0}]登录系统", user.getUserName()));

        //更新为在线状态
        user.setOnline(true);
        user.setLastLoginType(1);
        user.setLastLoginIp(getRequestAddr());
        user.setLastLoginTime(getCurrentTime());

        mergeWithAsync(user);
        //用户名存入内存中
        ApplicationCommon.getCacheOfLoginUser().add(user.getUserName());

        //缓存用户配置
        Map<String, String> configMap = new HashMap<>();
        for (UserConfig config : user.getConfigs()) {
            configMap.put(config.getK(), config.getV());
        }
        SessionAttributeManager.put(ApplicationCommon.CACHE_USER_CONFIG, configMap);
    }

    /**
     * 解析用户资源+按钮权限
     *
     * @param user
     */
    private void parseUserResource(User user) {
        boolean isSystemUnit = false;
        for (Unit unit : user.getUnits())
            if (unit.isSystem())
                isSystemUnit = true;
        if (isSystemUnit && ApplicationCommon.SYSTEM_ADMIN.equals(user.getUserName())) {//系统机构并且为系统机构的人员拥有全部菜单权限
            user.setResources(resService.list("from Resource where enabled=true and display=true order by location asc"));
            for (Resource r : user.getResources()) {
                Hibernate.initialize(r.getButtons());
                Hibernate.initialize(r.getColumns());

                resService.evict(r);//设置为游离状态
            }
            user.setSystemAdmin(true);//标识为系统管理员
        } else {//其他用户按照角色配置的来获取权限
            //菜单权限
            Map<String, Resource> resMap = new HashMap<>();
            List<String> roleIds = new ArrayList<String>();
            Set<Role> roleSet = new HashSet<>();//角色列表，不含重复
            roleSet.addAll(user.getRoles());
            //用户组列表
            for (Group group : user.getGroups()) {
                Hibernate.initialize(group.getRoles());
                roleSet.addAll(group.getRoles());
            }
            //角色列表
            for (Role role : roleSet) {
                Hibernate.initialize(role.getResources());
                for (Resource r : role.getResources()) {
                    if (r.isEnabled() && r.isDisplay() && !user.getResources().contains(r)) {//筛选出未启用的和重复的
                        Hibernate.initialize(r.getColumns());
                        Hibernate.initialize(r.getButtons());
                        user.getResources().add(r);
                        resMap.put(r.getId(), r);
                    }

                    resService.evict(r);//设置为游离状态
                }
                roleIds.add(role.getId());
            }
            if (roleIds.size() == 0)
                return;
            //按照location字段排序
            user.getResources().sort((o1, o2) -> o1.getLocation() >= o2.getLocation() ? 0 : -1);

            //按钮权限
            List<RoleResourceButton> rrbList = rrbService.list(Condition.NEW().in("roleId", roleIds));
            Map<String, List<RoleResourceButton>> rrbMap = new HashMap<>();
            for (RoleResourceButton rrb : rrbList) {
                List<RoleResourceButton> list = rrbMap.get(rrb.getResId());
                if (ObjectKit.isNull(list)) list = new ArrayList<>();
                list.add(rrb);
                rrbMap.put(rrb.getResId(), list);
            }

            //字段权限
            List<RoleResourceColumn> rrcList = rrcService.list(Condition.NEW().in("roleId", roleIds));
            Map<String, List<RoleResourceColumn>> rrcMap = new HashMap<>();
            for (RoleResourceColumn rrc : rrcList) {
                List<RoleResourceColumn> list = rrcMap.get(rrc.getResId());
                if (ObjectKit.isNull(list)) list = new ArrayList<>();
                list.add(rrc);
                rrcMap.put(rrc.getResId(), list);
            }
            for (String resId : resMap.keySet()) {
                //按钮权限
                List<RoleResourceButton> buttonList = rrbMap.get(resId);
                if (buttonList != null && buttonList.size() > 0) {
                    List<ResourceButton> rbList2 = new ArrayList<>();
                    resMap.get(resId).setPrivilegeButtons(rbList2);
                    List<ResourceButton> rbList = resMap.get(resId).getButtons();
                    for (RoleResourceButton rrb : buttonList) {
                        for (ResourceButton rb : rbList) {
                            if (rb.getId().equals(rrb.getBtnId())) {
                                rbList2.add(rb);
                            }
                        }
                    }
                }

                //字段权限
                List<RoleResourceColumn> columnList = rrcMap.get(resId);
                if (columnList != null && columnList.size() > 0) {
                    List<ResourceColumn> rcList2 = new ArrayList<>();
                    resMap.get(resId).setPrivilegeColumns(rcList2);
                    List<ResourceColumn> rcList = resMap.get(resId).getColumns();
                    for (RoleResourceColumn rrc : columnList) {
                        for (ResourceColumn rc : rcList) {
                            if (rc.getId().equals(rrc.getColumnId())) {
                                rcList2.add(rc);
                            }
                        }
                    }
                }

            }
        }
    }

    @Async
    protected void mergeWithAsync(User user) {
        super.merge(user);
    }

    /**
     * 删除用户相关所有数据
     *
     * @param id
     */
    public void deleteAsRelated(String id) {
        super.executeBatch("delete from UserQuickMenu where user.id = ?", id);
        super.executeBatch("delete from UserConfig where user.id = ?", id);
        super.executeBatch("delete from UserEmail where userId = ?", id);
        super.executeBatch("delete from UserNotify where user.id = ?", id);

        super.executeSqlBatch("delete from sys_user_role where user_id = ?", id);
        super.executeSqlBatch("delete from sys_user_unit where user_id = ?", id);
        super.executeSqlBatch("delete from sys_group_user where user_id = ?", id);

        super.delete(id);
        getEventManager().trigger("onUserRemove", this, id);
    }

    /**
     * 执行退出
     *
     * @param session
     */
    public void executeLoginOut(HttpSession session) {
        User user = (User) WebApplicationCommon.getSessionAdminUser(session);
        if (ObjectKit.isNotNull(user)) {
            super.executeBatch("update User set online=false where id='" + user.getId() + "'");
            session.removeAttribute(ApplicationCommon.SESSION_ADMIN);
            ApplicationCommon.getCacheOfLoginSession().remove(user.getUserName());
            ApplicationCommon.getCacheOfLoginUser().remove(user.getUserName());
            ApplicationCommon.getCacheOfSession().remove(session.getId());

            logService.info("login-out", StringKit.format("[{0}]退出系统", user.getUserName()), user.getUserName(), HandlerRequest.fromWebRequest(getRequest()));
            getEventManager().trigger("onLoginOut", this, user);
            session.invalidate();
        }
    }
}
