package com.jrelax.web.system.controller;

import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.event.ApplicationEventDefined;
import com.jrelax.core.web.annotation.RequestList;
import com.jrelax.core.web.annotation.ViewPrefix;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.core.web.transform.DataGridTransforms;
import com.jrelax.kit.Md5Kit;
import com.jrelax.kit.ObjectKit;
import com.jrelax.kit.StringKit;
import com.jrelax.orm.query.Condition;
import com.jrelax.orm.query.HQLBuilder;
import com.jrelax.orm.query.PageBean;
import com.jrelax.orm.query.SQLBuilder;
import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.entity.*;
import com.jrelax.web.system.service.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/user")
@ViewPrefix("/system/user/")
public class UserController extends BaseController<User> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private LogService logService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private UnitService unitService;

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String index() {
        return "index";
    }

    /**
     * 获取机构下级用户
     * 不显示超级管理员账户，超级管理员账户名定义在ApplicationCommon.SYSTEM_ADMIN中
     *
     * @param pageBean
     * @param unitId          机构ID
     * @param userName        用户名
     * @param realName        真是姓名
     * @param mobile          联系方式
     * @param includeChildren 是否包含下一级
     * @return
     */
    @RequestMapping(value = "/unituser/data", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> unitUserData(PageBean pageBean, String unitId, String userName, String realName, String mobile, boolean includeChildren) {
        List<User> userList = new ArrayList<>();
        SQLBuilder sql = new SQLBuilder("SELECT id,user_name as userName, real_name as realName, mobile, email, enabled, online FROM sys_user");
        if (!"-1".equals(unitId)) {
            Unit unit = unitService.getById(unitId);
            if (unit != null) {
                if (includeChildren)
                    sql.append(" where id IN (SELECT user_id FROM sys_user_unit WHERE unit_id in (select id from sys_unit where code like '" + unit.getCode() + "%'))");
                else
                    sql.append(" where id IN (SELECT user_id FROM sys_user_unit WHERE unit_id = '" + unitId + "')");

            } else {
                sql.eq("1", "2");
            }
        }
        if (StringKit.isNotEmpty(userName))//用户名
            sql.like("user_name", "%" + userName + "%");
        if (StringKit.isNotEmpty(realName))//真实姓名
            sql.like("real_name", "%" + realName + "%");
        if (StringKit.isNotEmpty(mobile))//联系方式
            sql.like("mobile", "%" + mobile + "%");
        sql.asc("user_name");
        List<Map<String, Object>> listMap = userService.nativeListToMap(pageBean, sql.getSQL(), sql.getParams());
        userList = ObjectKit.mapToList(User.class, listMap);
        logService.info("sys-user", StringKit.format("查询用户列表[{0}, {1}]", pageBean.getPage(), pageBean.getRows()));
        return DataGridTransforms.JQGRID.transform(userList, pageBean);
    }

    /**
     * 增加用户
     *
     * @param model
     * @param uid   机构ID
     * @return
     */
    @RequestMapping(value = "/add", method = {RequestMethod.GET})
    public String add(Model model, String uid) {
        Unit unit = unitService.getById(uid);
        model.addAttribute("unit", unit);
        //获取主题包
        model.addAttribute("themeMap", getDataDict().getMap("sys_themes"));
        return "add";
    }

    /**
     * 获取机构下的角色
     *
     * @param unitId
     * @return
     */
    @RequestMapping(value = "/unitrole/{unitId}", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject unitrole(@PathVariable String unitId) {
        JSONObject json = new JSONObject();
        String[] unitIds = unitId.split(",");
        List<Role> roleList;
        if (JRelaxSystemConfigHelper.getBoolean("system.unit.role", true)) {
            HQLBuilder builder = new HQLBuilder("select id, name from Role");
            builder.in("unit.id", unitIds);
            roleList = roleService.listToEntity(builder.getHQL(), builder.getParams());
        } else {
//            Unit unit = unitService.getById(unitId);
//            roleList = roleService.listToEntity("select id, name from Role where unit.code like ?", unit.getCode().substring(0, 3) + "%");
            roleList = roleService.listToEntity("select id, name from Role");
        }
        JSONArray roles = new JSONArray();
        for (Role role : roleList) {
            JSONObject r = new JSONObject();
            r.element("id", role.getId());
            r.element("name", role.getName());

            roles.add(r);
        }
        json.element("roles", roles);
        return json;
    }

    /**
     * 执行新增
     *
     * @param user
     * @param unitIds
     * @param roleIds
     * @return
     */
    @RequestMapping(value = "/add/do", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject doAdd(User user, String unitIds, String[] roleIds) {
        try {
            if (StringKit.isBlank(user.getUserName())) {
                return WebResult.error("用户名不能为空！");
            }
            if (StringKit.isBlank(user.getPassword())) {
                return WebResult.error("密码不能为空！");
            }
            //判断用户名是否重复
            int count = userService.count(Condition.NEW().eq("userName", user.getUserName()));
            if (count > 0)
                return WebResult.error("用户名 '" + user.getUserName() + "' 已存在！");
            if (!StringKit.isBlank(user.getEmail())) {
                count = userService.count(Condition.NEW().eq("email", user.getEmail()));
                if (count > 0)
                    return WebResult.error("邮箱 '" + user.getEmail() + "' 已被使用！");
            }
            //绑定角色
            for (String roleId : roleIds) {
                Role role = new Role();
                role.setId(roleId);

                user.getRoles().add(role);
            }
            //绑定单位
            String[] unitIdArray = unitIds.split(",");
            for (String unitId : unitIdArray) {
                //绑定单位
                Unit unit = new Unit();
                unit.setId(unitId);
                user.getUnits().add(unit);
            }
            //设置创建时间
            user.setCreateTime(getCurrentTime());
            //密码加密
            user.setPassword(Md5Kit.encode(user.getPassword()));

            userService.save(user);
            logService.info("sys-user", StringKit.format("创建用户[{0}, {1}, {2}]", user.getId(), user.getUserName(), user.getRealName()));
            //触发事件
            getEventManager().trigger(ApplicationEventDefined.ON_USER_CREATED, this, user);
            return WebResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }

    /**
     * 批量增加
     *
     * @param model
     * @param uid
     * @return
     */
    @RequestMapping(value = "/batch/add", method = {RequestMethod.GET})
    public String batchAdd(Model model, String uid) {
        Unit unit = unitService.getById(uid);
        model.addAttribute("unit", unit);
        return "batch";
    }

    /**
     * 批量增加
     *
     * @return
     */
    @RequestMapping(value = "/batch/add/do", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject doBatchAdd(String[] unitId, String[] roleIds, String[] userName, String[] realName, String[] password, String[] email) {
        int total = 0, success = 0;
        String lastUnitId = null, lastRoleIds = null, lastPassword = null;
        if (userName != null) {
            for (int i = 0; i < userName.length; i++) {
                String name = userName[i];
                if (StringKit.isEmpty(name)) continue;
                String unit = null, role = null, real = null, pwd = null, mail = null;
                //机构处理同上
                if (unitId.length > i) {
                    String id = unitId[i];
                    if (StringKit.isEmpty(id)) {
                        unit = lastUnitId;
                    } else {
                        unit = id;
                    }
                } else {
                    unit = lastUnitId;
                }
                //角色处理同上
                if (roleIds.length > i) {
                    String id = roleIds[i];
                    if (StringKit.isEmpty(id)) {
                        role = lastRoleIds;
                    } else {
                        role = id;
                    }
                } else {
                    role = lastRoleIds;
                }
                //密码处理同上
                if (password.length > i) {
                    String id = password[i];
                    if (StringKit.isEmpty(id)) {
                        pwd = lastPassword;
                    } else {
                        pwd = id;
                    }
                } else {
                    pwd = lastPassword;
                }
                if (unit == null) continue;
                if (role == null) continue;
                if (pwd == null) continue;
                real = realName[i];
                mail = email[i];
                //执行新增
                User user = new User();
                user.setUserName(name);
                user.setPassword(pwd);
                user.setRealName(real);
                user.setEmail(mail);
                user.setMobile("");
                user.setEnabled(true);
                user.setPageStyle("palette");

                JSONObject result = doAdd(user, unit, role.split(","));
                if (result.getBoolean("success")) {
                    success++;
                }

                lastUnitId = unit;
                lastRoleIds = role;
                lastPassword = pwd;

                total++;
            }
        }
        if (success == 0)
            return WebResult.error("添加失败");

        return WebResult.success().element("total", total).element("successCount", success);
    }

    /**
     * 删除单个用户
     *
     * @param id 用户ID
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject delete(@PathVariable String id) {
        try {
            User user = userService.getById(id);
            if (user != null) {
                userService.deleteAsRelated(id);
                logService.info("sys-user", StringKit.format("删除用户[{0}, {1}, {2}]", user.getId(), user.getUserName(), user.getRealName()));
            }
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }

    /**
     * 删除多个用户
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject delete(String[] ids) {
        try {
            for (String id : ids)
                userService.deleteAsRelated(id);
            logService.info("sys-user", StringKit.format("删除用户（批量）[{0}]", Arrays.toString(ids)));
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }

    /**
     * 启用
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/enable/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject enable(@PathVariable String id) {
        try {
            userService.executeBatch("update User set enabled = true where id = ?", id);
            logService.info("sys-user", StringKit.format("启用用户[{0}]", id));
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }

    /**
     * 禁用
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/disable/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject disable(@PathVariable String id) {
        try {
            userService.executeBatch("update User set enabled=false where id = ?", id);
            logService.info("sys-user", StringKit.format("禁用用户[{0}]", id));
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }

    /**
     * 密码重置
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/resetPwd/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject resetPwd(@PathVariable String id) {
        try {
            String defaultPwd = JRelaxSystemConfigHelper.get("system.default.pwd", "123456");
            String pwd = Md5Kit.encode(defaultPwd);
            userService.executeBatch("update User set password = ? where id = ?", pwd, id);
            logService.info("sys-user", StringKit.format("重置密码[{0}]", id));
            return WebResult.success().element("pwd", defaultPwd);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }

    /**
     * 编辑用户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method = {RequestMethod.GET})
    public String edit(Model model, @PathVariable String id) {
        userService.setLazyInitializer(user -> {
            Hibernate.initialize(user.getRoles());
            Hibernate.initialize(user.getUnits());
        });
        User user = userService.getById(id);
        if (ObjectKit.isNull(user)) {
            return WebApplicationCommon.ERROR.ERROR;
        }
        //获取用户所在单位
        List<String> unitIds = new ArrayList<>();
        if (user.getUnits().size() > 0) {
            List<String> names = new ArrayList<>();

            for (Unit unit : user.getUnits()) {
                names.add(unit.getName());
                unitIds.add(unit.getId());
            }
            model.addAttribute("unitIds", StringKit.toString(unitIds));
            model.addAttribute("unitNames", StringKit.toString(names));
        } else {
            model.addAttribute("unitNames", "未分配单位");
        }
        //获取角色列表
        List<String> roleIds = new ArrayList<String>();
        for (Role role : user.getRoles()) {
            roleIds.add(role.getId());
        }
        model.addAttribute("roleIds", roleIds);
        //获取角色列表
        HQLBuilder hql = new HQLBuilder("select id, name from Role");
        if (JRelaxSystemConfigHelper.getBoolean("system.unit.role", true)) {
            if (unitIds.size() > 0)
                hql.in("unit.id", unitIds);
            else
                hql.eq("unit.id", "");
        }
        List<Role> roleList = roleService.listToEntity(hql.getHQL(), hql.getParams());
        model.addAttribute("roleList", roleList);
        model.addAttribute("user", user);
        //获取主题包
        model.addAttribute("themeMap", getDataDict().getMap("sys_themes"));
        return "edit";
    }

    /**
     * 执行编辑
     *
     * @param user
     * @param unitIds
     * @param roleIds
     * @return
     */
    @RequestMapping(value = "/edit/do", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject doEdit(User user, String unitIds, String[] roleIds) {
        try {
            if (StringKit.isBlank(user.getUserName())) {
                return WebResult.error("用户名不能为空！");
            }
            if (StringKit.isBlank(user.getPassword())) {
                return WebResult.error("密码不能为空！");
            }
            userService.setLazyInitializer(user1 -> {
                Hibernate.initialize(user1.getRoles());
                Hibernate.initialize(user1.getUnits());
            });
            //判断用户名是否重复
            int count = userService.count(Condition.NEW().eq("userName", user.getUserName()).not(Restrictions.eq("id", user.getId())));
            if (count > 0)
                return WebResult.error("用户名 '" + user.getUserName() + "' 已存在！");
            if (!StringKit.isBlank(user.getEmail())) {
                count = userService.count(Condition.NEW().eq("email", user.getEmail()).not(Restrictions.eq("id", user.getId())));
                if (count > 0)
                    return WebResult.error("邮箱 '" + user.getEmail() + "' 已被使用！");
            }

            User eqUser = userService.getById(user.getId());

            eqUser.setUserName(user.getUserName());//修改用户名
            eqUser.setRealName(user.getRealName());
            if (StringKit.isNotEmpty(user.getPassword())) {
                eqUser.setPassword(Md5Kit.encode(user.getPassword()));
            }
            eqUser.setEmail(user.getEmail());
            eqUser.setMobile(user.getMobile());
            eqUser.setPageStyle(user.getPageStyle());
            eqUser.setQq(user.getQq());
            eqUser.setEnabled(user.isEnabled());

            //绑定角色
            eqUser.getRoles().clear();
            for (String roleId : roleIds) {
                Role role = new Role();
                role.setId(roleId);

                eqUser.getRoles().add(role);
            }

            //绑定机构
            eqUser.getUnits().clear();
            String[] unitIdArray = unitIds.split(",");
            for (String unitId : unitIdArray) {
                //绑定单位
                Unit unit = new Unit();
                unit.setId(unitId);
                eqUser.getUnits().add(unit);
            }

            userService.merge(eqUser);

            logService.info("sys-user", StringKit.format("编辑用户[{0}, {1}, {2}]", user.getId(), user.getUserName(), user.getRealName()));
            return WebResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }

    /**
     * 查看用户基本信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail/info/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String detail_info(Model model, @PathVariable String id) {
        userService.setLazyInitializer(user -> {
            Hibernate.initialize(user.getRoles());
            Hibernate.initialize(user.getUnits());
            Hibernate.initialize(user.getGroups());
        });
        User user = userService.getById(id);
        if (ObjectKit.isNull(user))
            return WebApplicationCommon.ERROR.UNAUTHORIZED_ACCESS;
        model.addAttribute("user", user);
        if (user.getUnits().size() > 0) {
            List<String> names = new ArrayList<>();

            for (Unit unit : user.getUnits()) {
                names.add(unit.getName());
            }
            model.addAttribute("unitNames", StringKit.toString(names));
        }
        List<String> roles = new ArrayList<String>();
        for (Role role : user.getRoles()) {
            roles.add(role.getName());
        }
        List<String> groups = new ArrayList<>();
        for (Group group : user.getGroups()) {
            groups.add(group.getName());
        }
        model.addAttribute("roles", StringKit.toString(roles));
        model.addAttribute("groups", StringKit.toString(groups));
        logService.info("sys-user", StringKit.format("查看用户详情[{0}, {1}, {2}]", user.getId(), user.getUserName(), user.getRealName()));
        return "detail_info";
    }

    /**
     * 查看用户权限
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail/right/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String detail_right(Model model, @PathVariable String id) {
        userService.setLazyInitializer(user -> Hibernate.initialize(user.getUnits()));
        User user = userService.getById(id);
        if (ObjectKit.isNull(user))
            return WebApplicationCommon.ERROR.UNAUTHORIZED_ACCESS;
        model.addAttribute("user", user);
        if (user.getUnits().size() > 0) {
            List<String> names = new ArrayList<>();

            for (Unit unit : user.getUnits()) {
                names.add(unit.getName());
            }
            model.addAttribute("unitNames", StringKit.toString(names));
        }
        //获取用户菜单资源
        List<Resource> resList = userService.getUserResList(id);
        List<Resource> firstResList = new ArrayList<Resource>();
        JSONArray jsonResList = new JSONArray();
        for (Resource res : resList) {
            res.setRoles(null);//防止延迟加载属性
            if (res.getParentId().equals("-1")) {
                firstResList.add(res);
            }
            JSONObject jsonRes = new JSONObject();
            jsonRes.element("id", res.getId());
            jsonRes.element("name", res.getName());
            jsonRes.element("parentId", res.getParentId());
            jsonRes.element("icon", res.getIcon());
            jsonRes.element("url", res.getUrl());
            JSONArray buttons = new JSONArray();
            for (ResourceButton button : res.getPrivilegeButtons()) {
                JSONObject btn = new JSONObject();
                btn.element("id", button.getId());
                btn.element("name", button.getName());
                buttons.add(btn);
            }
            jsonRes.element("buttons", buttons);
            jsonResList.add(jsonRes);
        }
        model.addAttribute("allRes", jsonResList.toString());
        model.addAttribute("resList", firstResList);
        logService.info("sys-user", StringKit.format("查看用户权限[{0}, {1}, {2}]", user.getId(), user.getUserName(), user.getRealName()));
        return "detail_right";
    }


    @RequestMapping(value = "/edit/realname", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject edit_username(String pk, String name, String value) {
        try {
            userService.executeBatch("update User set realName=? where id=?", value, pk);
            logService.info("sys-user", StringKit.format("编辑用户-真实姓名[{0}, {1}]", pk, value));
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e.getMessage());
        }
    }

    @RequestMapping(value = "/edit/mobile", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject edit_mobile(String pk, String name, String value) {
        try {
            userService.executeBatch("update User set mobile=? where id=?", value, pk);
            logService.info("sys-user", StringKit.format("编辑用户-联系方式[{0}, {1}]", pk, value));
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e.getMessage());
        }
    }

    @RequestMapping(value = "/edit/email", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject edit_email(String pk, String name, String value) {
        try {
            userService.executeBatch("update User set email=? where id=?", value, pk);
            logService.info("sys-user", StringKit.format("编辑用户-电子邮箱[{0}, {1}]", pk, value));

            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e.getMessage());
        }
    }

    /**
     * 管理用户组
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/group/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String group(Model model, @PathVariable String id) {
        userService.setLazyInitializer(user -> Hibernate.initialize(user.getGroups()));
        User user = userService.getById(id);
        if (ObjectKit.isNull(user))
            return WebApplicationCommon.ERROR.UNAUTHORIZED_ACCESS;
        model.addAttribute("user", user);
        return "group";
    }

    /**
     * 拥有的用户组
     *
     * @param pageBean
     * @param userId
     * @return
     */
    @RequestMapping(value = "/group/data", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> groupData(PageBean pageBean, String userId) {
        userService.setLazyInitializer(user -> Hibernate.initialize(user.getUnits()));
        User user = userService.getById(userId);
        pageBean.addCriterion(Restrictions.eq("unit", user.getDefaultUnit()));
        List<Group> groupList = groupService.list(pageBean);

        logService.info("sys-user", StringKit.format("查看用户所属组[{0}, {1}, {2}]", user.getId(), user.getUserName(), user.getRealName()));

        return DataGridTransforms.JQGRID.transform(groupList, pageBean);
    }

    /**
     * 执行管理用户组
     *
     * @return
     */
    @RequestMapping(value = "/group/do", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject doGroup(String id, String[] groupIds) {
        try {
            userService.setLazyInitializer(user -> Hibernate.initialize(user.getGroups()));
            User user = userService.getById(id);
            user.getGroups().clear();
            if (groupIds.length > 0) {
                List<Group> groupList = groupService.list(Condition.NEW().in("id", groupIds));
                user.setGroups(groupList);
            }
            userService.update(user);
            logService.info("sys-user", StringKit.format("编辑用户-用户组[{0}, {1}, {2}]", user.getId(), user.getUserName(), user.getRealName()));
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e.getMessage());
        }
    }

    /**
     * 移动用户到机构
     *
     * @param idList
     * @param unitId
     * @return
     */
    @RequestMapping("/move")
    @ResponseBody
    public JSONObject move(@RequestList("ids") List<String> idList, String unitId) {
        Unit unit = unitService.getById(unitId);
        if (unit != null) {
            userService.setLazyInitializer(user -> {
                Hibernate.initialize(user.getUnits());
            });
            List<User> userList = userService.list(Condition.NEW().in("id", idList));

            for (User user : userList) {
                user.getUnits().clear();
                user.getUnits().add(unit);

                userService.merge(user);
            }

            logService.info("sys-user", StringKit.format("编辑用户-修改机构[{0}]", unitId));
        }
        return WebResult.success();
    }
}
