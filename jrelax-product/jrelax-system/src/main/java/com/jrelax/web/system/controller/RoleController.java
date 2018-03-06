package com.jrelax.web.system.controller;

import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.core.web.support.http.HandlerRequest;
import com.jrelax.core.web.transform.DataGridTransforms;
import com.jrelax.event.ApplicationEventDefined;
import com.jrelax.kit.ObjectKit;
import com.jrelax.kit.StringKit;
import com.jrelax.orm.dao.ILazyInitializer;
import com.jrelax.orm.query.Condition;
import com.jrelax.orm.query.PageBean;
import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.entity.*;
import com.jrelax.web.system.service.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Hibernate;
import org.hibernate.criterion.MatchMode;
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

/**
 * 角色管理
 *
 * @author zengchao
 */
@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController<Role> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String TPL = "/system/role/";
    @Autowired
    private RoleService roleService;
    @Autowired
    private ResourceService resService;
    @Autowired
    private LogService logService;
    @Autowired
    private RoleResourceButtonService rrbService;
    @Autowired
    private RoleResourceColumnService rrcService;
    @Autowired
    private UnitService unitService;

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        roleService.setLazyInitializer(role -> Hibernate.initialize(role.getUnit()));
        List<Role> roleList = roleService.list(Condition.NEW().in("unit", getCurrentUser().getUnits()));
        model.addAttribute("roleList", roleList);

        logService.info(logService.getModule("sys-role"), "查询角色列表");
        return TPL + "index";
    }

    /**
     * 机构下角色信息
     *
     * @param pageBean
     * @param unitId
     * @param name
     * @return
     */
    @RequestMapping(value = "/unitrole/data", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> unitRoleData(PageBean pageBean, String unitId, String name, boolean includeChildren) {
        if (!"-1".equals(unitId)) {
            if (includeChildren) {
                Unit unit = unitService.getById(unitId);
                pageBean.addCriterion(Restrictions.like("unit.code", unit.getCode(), MatchMode.START));
            } else {
                pageBean.addCriterion(Restrictions.eq("unit.id", unitId));
            }
        }

        if (StringKit.isNotEmpty(name))
            pageBean.addCriterion(Restrictions.like("name", name, MatchMode.ANYWHERE));
        List<Role> roleList = roleService.list(pageBean);

        logService.info("sys-role", StringKit.format("查询角色列表[{0}, {1}]", pageBean.getPage(), pageBean.getRows()));
        return DataGridTransforms.JQGRID.transform(roleList, pageBean);
    }

    /**
     * 新增角色
     *
     * @param model
     * @param uid   机构ID
     * @return
     */
    @RequestMapping(value = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String add(Model model, String uid) {
        if (StringKit.isNotEmpty(uid) && !"-1".equals(uid)) {
            Unit unit = unitService.getById(uid);
            model.addAttribute("unit", unit);
        }
        model.addAttribute("permMap", getDataDict().getMap("sys_role_perm"));
        return TPL + "add";
    }

    /**
     * 执行新增角色
     *
     * @param role
     * @return
     */
    @RequestMapping(value = "/add/do", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject doAdd(Role role) {
        try {
            if (StringKit.isBlank(role.getName()))
                return WebResult.error("角色名不能为空!");
            if (roleService.count(Restrictions.eq("name", role.getName()), Restrictions.eq("unit.id", role.getUnit().getId())) > 0)
                return WebResult.error("角色名已存在！");

            role.setCreateTime(getCurrentTime());
            roleService.save(role);

            logService.info("sys-role", StringKit.format("创建角色[{0}, {1}]", role.getId(), role.getName()));

            getEventManager().trigger(ApplicationEventDefined.ON_ROLE_CREATED, this, role);
            return WebResult.success().element("id", role.getId());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }

    }


    /**
     * 修改基本信息
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method = {RequestMethod.GET})
    public String edit(Model model, @PathVariable String id) {
        roleService.setLazyInitializer(role -> Hibernate.initialize(role.getUnit()));
        Role role = roleService.getById(id);
        if (ObjectKit.isNull(role))
            return WebApplicationCommon.ERROR.UNAUTHORIZED_ACCESS;
        model.addAttribute("role", role);
        model.addAttribute("permMap", getDataDict().getMap("sys_role_perm"));
        return TPL + "edit";
    }

    @RequestMapping(value = "/edit/do", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject doEdit(Role role) {
        try {
            if (roleService.count(Restrictions.eq("name", role.getName()), Restrictions.eq("unit.id", role.getUnit().getId()), Restrictions.not(Restrictions.eq("id", role.getId()))) > 0)
                return WebResult.error("角色名已存在！");
            Role eqRole = roleService.getById(role.getId());
            if (ObjectKit.isNull(eqRole))
                return WebResult.error("角色不存在！");
            eqRole.setName(role.getName());
            eqRole.setDescript(role.getDescript());
            eqRole.setEnabled(role.isEnabled());
            eqRole.setPerm(role.getPerm());//更新数据权限

            roleService.merge(eqRole);

            logService.info("sys-role", StringKit.format("编辑角色[{0}, {1}]", role.getId(), role.getName()));
            return WebResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }

    /**
     * 修改权限
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/right/{id}", method = {RequestMethod.GET})
    public String editRight(Model model, @PathVariable String id) {
        roleService.setLazyInitializer(role -> {
            Hibernate.initialize(role.getResources());
            Hibernate.initialize(role.getUnit());
        });
        Role role = roleService.getById(id);
        if (ObjectKit.isNull(role))
            return WebApplicationCommon.ERROR.UNAUTHORIZED_ACCESS;

        //获取资源权限
        JSONArray resources = new JSONArray();
        for (Resource res : role.getResources()) {
            resources.add(res.getId());
        }
        //获取按钮权限
        List<RoleResourceButton> rrbList = rrbService.list(Condition.NEW().eq("roleId", role.getId()));
        JSONObject buttonJson = new JSONObject();
        for (RoleResourceButton rrb : rrbList) {
            JSONArray array = new JSONArray();
            if (buttonJson.containsKey(rrb.getResId())) {
                array = buttonJson.getJSONArray(rrb.getResId());
            }
            array.add(rrb.getBtnId());
            buttonJson.element(rrb.getResId(), array);
        }

        List<RoleResourceColumn> rrcList = rrcService.list(Condition.NEW().eq("roleId", role.getId()));
        JSONObject columnJson = new JSONObject();
        for (RoleResourceColumn rrc : rrcList) {
            JSONArray array = new JSONArray();
            if (columnJson.containsKey(rrc.getResId())) {
                array = columnJson.getJSONArray(rrc.getResId());
            }
            array.add(rrc.getColumnId());
            columnJson.element(rrc.getResId(), array);
        }

        model.addAttribute("resources", resources);
        model.addAttribute("button", buttonJson);
        model.addAttribute("column", columnJson);
        model.addAttribute("role", role);
        return TPL + "right";
    }

    /**
     * 获取资源+按钮
     *
     * @param model
     * @param resId 资源ID
     * @return
     */
    @RequestMapping(value = "/edit/right/button", method = {RequestMethod.GET, RequestMethod.POST})
    public String editRightResBtn(Model model, String resId) {
        resService.setLazyInitializer(resource -> Hibernate.initialize(resource.getButtons()));
        //获取选中菜单
        Resource resource = resService.getById(resId);
        model.addAttribute("buttons", resource.getButtons());
        model.addAttribute("name", resource.getName());
        return TPL + "button";
    }

    /**
     * 获取资源+字段
     *
     * @param model
     * @param resId 资源ID
     * @return
     */
    @RequestMapping(value = "/edit/right/column", method = {RequestMethod.GET, RequestMethod.POST})
    public String editRightResColumns(Model model, String resId) {
        resService.setLazyInitializer(resource -> Hibernate.initialize(resource.getColumns()));
        //获取选中菜单
        Resource resource = resService.getById(resId);

        model.addAttribute("columns", resource.getColumns());
        model.addAttribute("name", resource.getName());
        return TPL + "column";
    }

    @RequestMapping(value = "/edit/right/do", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject doEditRight(String roleId, String data) {
        try {
            if (roleService.count(Restrictions.idEq(roleId)) == 0)
                return WebResult.error("非法操作");
            //修改角色权限
            roleService.executeEditRoleRes(roleId, data);

            logService.info("sys-role", StringKit.format("编辑角色-授权[{0}]", roleId));
            return WebResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject delete(@PathVariable String id) {
        try {
            roleService.deleteAsRelated(id);
            logService.info("sys-role", StringKit.format("删除角色[{0}]", id));
            return WebResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject delete(String[] ids) {
        try {
            if (ObjectKit.isNull(ids)) return WebResult.error("非法操作");
            for (String id : ids)
                roleService.deleteAsRelated(id);

            logService.info("sys-role", StringKit.format("删除角色(批量)[{0}]", Arrays.toString(ids)));
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
            roleService.executeBatch("update Role set enabled=true where id=?", id);

            logService.info("sys-role", StringKit.format("启用角色[{0}]", id));
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
            roleService.executeBatch("update Role set enabled=false where id=?", id);

            logService.info("sys-role", StringKit.format("禁用角色[{0}]", id));
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }

    /**
     * 查看下属用户
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail/user/{id}", method = {RequestMethod.GET})
    public String detail_info(Model model, @PathVariable String id) {
        roleService.setLazyInitializer(new ILazyInitializer<Role>() {
            @Override
            public void init(Role role) {
                Hibernate.initialize(role.getUsers());
            }
        });
        Role role = roleService.getById(id);
        if (ObjectKit.isNull(role))
            return WebApplicationCommon.ERROR.UNAUTHORIZED_ACCESS;
        //获取下属用户
        model.addAttribute("role", role);

        logService.info("sys-role", StringKit.format("查看下属用户[{0}]", id));
        return TPL + "detail_user";
    }

    /**
     * 查看权限
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail/right/{id}", method = {RequestMethod.GET})
    public String detail_right(Model model, @PathVariable String id) {
        Role role = roleService.getById_noLazyRes(id);
        if (ObjectKit.isNull(role))
            return WebApplicationCommon.ERROR.UNAUTHORIZED_ACCESS;
        //获取用户菜单资源
        List<Resource> resList = role.getResources();
        List<Resource> firstResList = new ArrayList<Resource>();
        JSONArray jsonResList = new JSONArray();
        for (Resource res : resList) {
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
        model.addAttribute("role", role);

        logService.info("sys-role", StringKit.format("查看角色权限[{0}]", id));
        return TPL + "detail_right";
    }

    /**
     * 机构菜单树
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/unit/res/tree/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONArray roleRestree(@PathVariable String id) {
        JSONArray data = new JSONArray();
        unitService.setLazyInitializer(new ILazyInitializer<Unit>() {
            @Override
            public void init(Unit unit) {
                Hibernate.initialize(unit.getResources());
            }
        });
        Unit unit = unitService.getById(id);
        if (ObjectKit.isNotNull(unit)) {
            //如果是系统机构，则获取全部菜单
            List<Resource> list = new ArrayList<>();
            //根据当前用户来决定菜单权限
            //如果机构独立菜单功能关闭了，则获取全部菜单
            if (unit.isSystem() || !JRelaxSystemConfigHelper.getBoolean("system.unit.res", false)) {
                list = resService.list(Condition.NEW().eq("enabled", true).asc("location"));
            } else {
                list = unit.getResources();
            }
            for (Resource res : list) {
                if (!res.getParentId().equals("-1"))
                    continue;
                JSONObject node = new JSONObject();

                node.element("id", res.getId());
                node.element("text", res.getName());
                node.element("icon", res.getIcon());
                node.element("data", res.getDescript());
                node.element("children", res.hasChildren());

                if (WebApplicationCommon.Url.WELCOME.equals(res.getUrl())) {
                    JSONObject state = new JSONObject();
                    state.element("disabled", true);
                    state.element("selected", true);

                    node.element("state", state);
                }

                data.add(node);
            }
        }
        return data;
    }

    /**
     * 获取子节点
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/unit/res/tree/{id}/{pid}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONArray roleRestreeChild(@PathVariable String id, @PathVariable String pid) {
        JSONArray data = new JSONArray();
        unitService.setLazyInitializer(new ILazyInitializer<Unit>() {
            @Override
            public void init(Unit unit) {
                Hibernate.initialize(unit.getResources());
            }
        });
        Unit unit = unitService.getById(id);
        if (ObjectKit.isNotNull(unit)) {
            List<Resource> list = new ArrayList<>();
            //根据当前用户来决定菜单权限
            //如果机构独立菜单功能关闭了，则获取全部菜单
            if (unit.isSystem() || !JRelaxSystemConfigHelper.getBoolean("system.unit.res", false)) {
                list = resService.list(Condition.NEW().eq("enabled", true).eq("parentId", pid).asc("location"));
            } else {
                list = unit.getResources();
            }
            for (Resource res : list) {
                if (!unit.isSystem())
                    if (!res.getParentId().equals(pid))
                        continue;
                JSONObject node = new JSONObject();

                node.element("id", res.getId());
                node.element("text", res.getName());
                node.element("icon", res.getIcon());
                node.element("data", res.getDescript());
                node.element("children", res.hasChildren());

                data.add(node);
            }
        }
        return data;
    }

    /**
     * 复制角色
     *
     * @param id
     * @return
     */
    @RequestMapping("/copy")
    public String copy(Model model, String id) {
        roleService.setLazyInitializer(role -> {
            Hibernate.initialize(role.getUnit());
        });
        Role role = roleService.getById(id);

        model.addAttribute("role", role);
        return TPL + "copy";
    }

    /**
     * 执行复制
     *
     * @param id
     * @param unitId
     * @param name
     * @return
     */
    @RequestMapping("/copy/do")
    @ResponseBody
    public JSONObject doCopy(String id, String unitId, String name) {
        boolean result;
        try {
            result = roleService.copyRole(id, unitId, name);

            logService.info("sys-role", StringKit.format("复制角色[{0}]", id));
        } catch (Exception e) {
            e.printStackTrace();
            return WebResult.error(e);
        }
        return result ? WebResult.success() : WebResult.error("复制失败");
    }
}
