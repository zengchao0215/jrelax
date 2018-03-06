package com.jrelax.web.system.controller;

import com.jrelax.core.web.annotation.ViewPrefix;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.core.web.transform.DataGridTransforms;
import com.jrelax.event.ApplicationEventDefined;
import com.jrelax.kit.ObjectKit;
import com.jrelax.kit.StringKit;
import com.jrelax.orm.query.Condition;
import com.jrelax.orm.query.PageBean;
import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.entity.Group;
import com.jrelax.web.system.entity.Role;
import com.jrelax.web.system.entity.Unit;
import com.jrelax.web.system.entity.User;
import com.jrelax.web.system.service.*;
import net.sf.json.JSONObject;
import org.hibernate.Hibernate;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zengchao
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping(value = "/system/group")
@ViewPrefix("/system/group/")
public class GroupController extends BaseController<Group> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private GroupService groupService;
    @Resource
    private UnitService unitService;
    @Resource
    private RoleService roleService;
    @Resource
    private UserService userService;
    @Resource
    private LogService logService;

    /**
     * 首页
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String index() {
        return "index";
    }

    /**
     * 数据
     *
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/data", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> data(PageBean pageBean, String unitId) {
        if (!"-1".equals(unitId))
            pageBean.addCriterion(Restrictions.eq("unit.id", unitId));
        List<Group> list = groupService.list(pageBean);

        logService.info("sys-group", StringKit.format("查询用户组列表[{0}, {1}]", pageBean.getPage(), pageBean.getRows()));
        return DataGridTransforms.JQGRID.transform(list, pageBean);
    }

    /**
     * 转向新增页面
     *
     * @param model
     * @param pid
     */
    @RequestMapping(value = "/add", method = {RequestMethod.GET})
    public String add(Model model, String pid) {
        model.addAttribute("unitId", pid);
        return "add";
    }

    /**
     * 执行新增
     *
     * @param group
     * @return
     */
    @RequestMapping(value = "/add/do", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject doAdd(Group group) {
        try {
            Unit unit = unitService.getById(group.getUnit().getId());
            if (ObjectKit.isNull(unit)) {
                return WebResult.notAllow();
            }
            group.setUnit(unit);
            group.setCreateUser(getCurrentUser().getRealName());
            group.setCreateTime(getCurrentTime());
            groupService.save(group);

            logService.info("sys-group", StringKit.format("创建用户组[{0}, {1}]", group.getId(), group.getName()));

            getEventManager().trigger(ApplicationEventDefined.ON_GROUP_CREATED, this, group);
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e.toString());
        }
    }

    /**
     * 转向编辑页面
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String edit(Model model, @PathVariable String id) {
        Group group = groupService.getById(id);
        if (!ObjectKit.isNotNull(group))
            return WebApplicationCommon.ERROR.UNAUTHORIZED_ACCESS;
        model.addAttribute("group", group);
        return "edit";
    }

    /**
     * 执行编辑
     *
     * @param group
     * @return
     */
    @RequestMapping(value = "/edit/do", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject doEdit(Group group) {
        try {
            //从数据库中获取最新数据
            Group eqGroup = groupService.getById(group.getId());
            if (ObjectKit.isNull(eqGroup)) {
                return WebResult.notAllow();
            }

            eqGroup.setName(group.getName());
            eqGroup.setDescript(group.getDescript());

            groupService.merge(eqGroup);

            logService.info("sys-group", StringKit.format("编辑用户组[{0}, {1}]", group.getId(), group.getName()));
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e.toString());
        }
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject delete(@PathVariable String id) {
        try {
            //删除相关的所有数据
            groupService.delete(id);

            logService.info("sys-group", StringKit.format("删除用户组[{0}]", id));
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e.toString());
        }
    }

    /**
     * 查看详情
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String detail(Model model, @PathVariable String id) {
        groupService.setLazyInitializer(group -> {
            Hibernate.initialize(group.getUnit());
            Hibernate.initialize(group.getRoles());
        });
        Group group = groupService.getById(id);
        if (!ObjectKit.isNotNull(group))
            return WebApplicationCommon.ERROR.UNAUTHORIZED_ACCESS;
        model.addAttribute("group", group);

        logService.info("sys-group", StringKit.format("查看用户组详情[{0}, {1}]", group.getId(), group.getName()));
        return "detail";
    }

    /**
     * 授权 - 即分配角色
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/role/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String role(Model model, @PathVariable String id) {
        groupService.setLazyInitializer(group -> {
            Hibernate.initialize(group.getUnit());
            Hibernate.initialize(group.getRoles());
        });
        Group group = groupService.getById(id);
        if (!ObjectKit.isNotNull(group))
            return WebApplicationCommon.ERROR.UNAUTHORIZED_ACCESS;
        model.addAttribute("group", group);
        return "role";
    }

    /**
     * 机构下的所有角色列表
     *
     * @param pageBean
     * @param unitId   机构id
     * @return
     */
    @RequestMapping("/role/data")
    @ResponseBody
    public Map<String, Object> roleData(PageBean pageBean, String unitId) {
        Unit unit = unitService.getById(unitId);
        List<Role> roleList = new ArrayList<>();
        if (ObjectKit.isNotNull(unit)) {
            pageBean.addCriterion(Restrictions.like("unit.code", unit.getCode(), MatchMode.START));
            roleService.setLazyInitializer(role -> {
                Hibernate.initialize(role.getUnit());
            });
            roleList = roleService.list(pageBean);
        }
        return DataGridTransforms.JQGRID.transform(roleList, pageBean);
    }

    /**
     * 执行授权
     *
     * @param id
     * @param roleIds
     * @return
     */
    @RequestMapping("/role/do")
    @ResponseBody
    public JSONObject doRole(String id, String[] roleIds) {
        groupService.setLazyInitializer(group -> {
            Hibernate.initialize(group.getRoles());
        });
        Group group = groupService.getById(id);
        if (ObjectKit.isNull(group)) return WebResult.notAllow();
        group.getRoles().clear();
        if (roleIds.length > 0) {
            List<Role> roleList = roleService.list(Condition.NEW().in("id", roleIds));
            group.setRoles(roleList);
        }
        groupService.update(group);

        logService.info("sys-group", StringKit.format("编辑用户组-授权[{0}, {1}]", group.getId(), group.getName()));
        return WebResult.success();
    }

    /**
     * 管理用户
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/user/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String user(Model model, @PathVariable String id) {
        Group group = groupService.getById(id);
        if (!ObjectKit.isNotNull(group))
            return WebApplicationCommon.ERROR.UNAUTHORIZED_ACCESS;
        model.addAttribute("group", group);
        return "user";
    }

    /**
     * 用户组下所有用户
     *
     * @param pageBean
     * @param groupId  用户组ID
     * @return
     */
    @RequestMapping("/user/data")
    @ResponseBody
    public Map<String, Object> userData(PageBean pageBean, String groupId) {
        List<User> userList = new ArrayList<>();
        if (StringKit.isNotEmpty(groupId)) {
            Group group = groupService.getById(groupId);
            if (ObjectKit.isNotNull(group)) {
                userList = userService.nativeListToEntity(pageBean, "select id, user_name as userName , real_name as realName, mobile from sys_user where id in(select user_id from sys_group_user where group_id=?)", groupId);
            }
        }

        logService.info("sys-group", StringKit.format("查看用户组-用户列表[{0}, {1}]"));
        return DataGridTransforms.JQGRID.transform(userList, pageBean);
    }

    /**
     * 增加用户到用户组
     *
     * @param groupId 用户组ID
     * @param userIds 用户ID数组
     * @return
     */
    @RequestMapping("/user/add")
    @ResponseBody
    public JSONObject userAdd(String groupId, String[] userIds) {
        if (StringKit.isNotEmpty(groupId) && userIds.length > 0) {
            if (groupService.count(Restrictions.idEq(groupId)) > 0) {
                userRemove(groupId, userIds);
                for (String userId : userIds)
                    groupService.executeSqlBatch("insert into sys_group_user(user_id,group_id) values(?,?)", userId, groupId);

                logService.info("sys-group", StringKit.format("编辑用户组-用户列表[{0}, {1}]", groupId));
            }
        }
        return WebResult.success();
    }

    /**
     * 从用户组中移除用户
     *
     * @param groupId 用户组ID
     * @param userIds 用户ID数组
     * @return
     */
    @RequestMapping("/user/remove")
    @ResponseBody
    public JSONObject userRemove(String groupId, String[] userIds) {
        if (StringKit.isNotEmpty(groupId) && userIds.length > 0) {
            if (groupService.count(Restrictions.idEq(groupId)) > 0) {
                for (String userId : userIds)
                    groupService.executeSqlBatch("delete from sys_group_user where user_id=? and group_id=?", userId, groupId);

                logService.info("sys-group", StringKit.format("编辑用户组-移除用户[{0}, {1}]", groupId));
            }
        }
        return WebResult.success();
    }
}
