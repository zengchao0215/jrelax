package com.jrelax.web.system.controller;

import com.jrelax.core.web.annotation.RequestList;
import com.jrelax.core.web.annotation.ViewPrefix;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.core.web.transform.DataGridTransforms;
import com.jrelax.core.web.transform.TreeTransforms;
import com.jrelax.event.ApplicationEventDefined;
import com.jrelax.kit.ObjectKit;
import com.jrelax.kit.StringKit;
import com.jrelax.orm.query.Condition;
import com.jrelax.orm.query.PageBean;
import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.entity.Resource;
import com.jrelax.web.system.entity.ResourceButton;
import com.jrelax.web.system.entity.ResourceColumn;
import com.jrelax.web.system.entity.Role;
import com.jrelax.web.system.service.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 资源菜单管理
 *
 * @author zengc
 */
@Controller
@RequestMapping("/system/res")
@ViewPrefix("/system/res/")
public class ResourceController extends BaseController<Resource> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @javax.annotation.Resource
    private ResourceService resService;
    @javax.annotation.Resource
    private LogService logService;
    @javax.annotation.Resource
    private ResourceColumnService resourceColumnService;
    @javax.annotation.Resource
    private ResourceButtonService resourceButtonService;
    @javax.annotation.Resource
    private RoleService roleService;

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model, String status) {
        String hql = "select id, name, icon, url, parentId, enabled, hasChildren, beta, display from Resource where parentId='-1' and enabled = true order by location asc";
        if (ObjectKit.isNotNull(status) && !StringKit.isBlank(status)) {
            hql = "select id, name, icon, url, parentId, enabled, hasChildren from Resource where parentId='-1' order by location asc";
            model.addAttribute("status", status);
        }
        List<Resource> list = resService.listToEntity(hql);
        model.addAttribute("list", list);
        model.addAttribute("count", resService.count());

        logService.info("sys-res", "查询菜单列表");
        return "index";
    }

    /**
     * 首页TreeTable子菜单
     *
     * @param model
     * @param pid
     * @return
     */
    @RequestMapping(value = "/child/{pid}", method = {RequestMethod.GET, RequestMethod.POST})
    public String children(Model model, @PathVariable String pid) {
        List<Resource> list = resService.listToEntity("select id, name, icon, url, parentId, enabled, hasChildren, beta, display,newWindow from Resource where parentId=? order by location asc", pid);
        model.addAttribute("list", list);
        return "children";
    }

    /**
     * 新增菜单
     *
     * @param model
     * @param pid
     * @return
     */
    @RequestMapping(value = "add", method = {RequestMethod.GET, RequestMethod.POST})
    public String add(Model model, String pid) {
        if (ObjectKit.isNotNull(pid)) {
            Resource parentRes = resService.get("select id,name,icon from Resource where id=?", pid);
            if (ObjectKit.isNotNull(parentRes)) {
                model.addAttribute("parentRes", parentRes);
            }
        }
        model.addAttribute("noRes", resService.count() <= 0);
        return "add";
    }

    /**
     * 执行新增菜单
     *
     * @param res
     * @return
     */
    @RequestMapping(value = "/add/do", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject doAdd(Resource res) {
        try {
            if (StringKit.isBlank(res.getParentId()))
                res.setParentId("-1");
            res.setCreateTime(getCurrentTime());

            //计算菜单位置
            resService.saveAndUpdateParent(res);

            logService.info("sys-res", StringKit.format("创建菜单[{0}, {1}, {2}]", res.getId(), res.getName(), res.getParentId()));

            getEventManager().trigger(ApplicationEventDefined.ON_RESOURCE_CREATED, this, res);
            return WebResult.success().element("parentId", res.getParentId());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }

    /**
     * 批量添加
     *
     * @param model
     * @param pid
     * @return
     */
    @RequestMapping(value = "/batch/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String batchAdd(Model model, String pid) {
        if (ObjectKit.isNotNull(pid)) {
            Resource parentRes = resService.get("select id,name,icon from Resource where id=?", pid);
            if (ObjectKit.isNotNull(parentRes)) {
                model.addAttribute("parentRes", parentRes);
            }
        }
        model.addAttribute("noRes", resService.count() <= 0);
        return "batch";
    }

    /**
     * 批量添加
     *
     * @param parentIds
     * @param names
     * @param urls
     * @param icons
     * @return
     */
    @RequestMapping(value = "/batch/add/do", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject doBatchAdd(String[] parentIds, String[] names, String[] urls, String[] icons) {
        try {
            int total = 0, success = 0;
            String lastParentId = null;
            if (names != null) {
                for (int i = 0; i < names.length; i++) {
                    String name = names[i];
                    if (StringKit.isEmpty(name)) continue;
                    String parentId = null, url = "", icon = "";
                    //处理上级菜单同上
                    if (parentIds.length > i) {
                        String id = parentIds[i];
                        if (StringKit.isEmpty(id))
                            parentId = lastParentId;
                        else
                            parentId = id;
                    }
                    if (urls.length > i) {
                        url = urls[i];
                    }
                    if (icons.length > i) {
                        icon = icons[i];
                    }
                    if (parentId == null) continue;
                    Resource resource = new Resource();
                    resource.setName(name);
                    resource.setUrl(url);
                    resource.setIcon(icon);
                    resource.setParentId(parentId);
                    resource.setDescript("");
                    resource.setHasChildren(false);
                    resource.setEnabled(true);
                    resource.setDisplay(true);
                    resource.setBeta(false);
                    resource.setNewWindow(false);

                    JSONObject result = doAdd(resource);
                    if (result.getBoolean("success")) {
                        success++;
                    }
                    lastParentId = parentId;

                    total++;
                }
            }

            return WebResult.success().element("parentId", "-1").element("total", total).element("successCount", success);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }

    /**
     * 菜单树
     *
     * @return
     */
    @RequestMapping(value = "/tree", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONArray tree(boolean parent) {
        List<Resource> list = resService.listToEntity("select id, name, icon, parentId, hasChildren, descript, url from Resource where parentId='-1' order by location");
        JSONArray data = new JSONArray();
        if (parent) {
            JSONObject node = new JSONObject();
            node.element("id", "-1");
            node.element("text", "顶级菜单");
            node.element("icon", "fa fa-thumb-tack");
            node.element("url", "/");

            data.add(node);
        }
        data.addAll(TreeTransforms.JSTree.transform2(list, (res, treeNode) -> {
            treeNode.setId(res.getId());
            treeNode.setText(res.getName());
            treeNode.setIcon(res.getIcon());
            treeNode.setLeaf(!res.hasChildren());
            treeNode.attr("data", res.getDescript()).attr("url", res.getUrl());
        }));
        return data;
    }

    /**
     * 获取才担保子节点
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/tree/{pid}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONArray tree(@PathVariable String pid) {
        List<Resource> list = resService.listToEntity("select id, name, icon, parentId, hasChildren, descript, url from Resource where parentId=? order by location", pid);
        return TreeTransforms.JSTree.transform2(list, (res, treeNode) -> {
            treeNode.setId(res.getId());
            treeNode.setText(res.getName());
            treeNode.setIcon(res.getIcon());
            treeNode.setLeaf(!res.hasChildren());
            treeNode.attr("data", res.getDescript()).attr("url", res.getUrl());
        });
    }

    /**
     * 资源详情
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String detail(Model model, @PathVariable String id) {
        Resource res = resService.getById(id);
        if (ObjectKit.isNull(res))
            return WebApplicationCommon.ERROR.UNAUTHORIZED_ACCESS;
        if (!res.getParentId().equals("-1")) {
            Resource parentRes = resService.get("select name from Resource where id=?", res.getParentId());
            if (ObjectKit.isNotNull(parentRes)) {
                model.addAttribute("parentMenu", parentRes.getName());
            }
        }
        model.addAttribute("res", res);

        logService.info("sys-res", StringKit.format("查看菜单详情[{0}, {1}, {2}]", res.getId(), res.getName(), res.getParentId()));
        return "detail";
    }

    /**
     * 删除
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject delete(Model model, @PathVariable String id) {
        try {
            resService.deleteAndRelated(id);

            logService.info("sys-res", StringKit.format("删除菜单[{0}]", id));
            return WebResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }

    /**
     * 资源详情
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/sort", method = {RequestMethod.GET, RequestMethod.POST})
    public String sort(Model model) {
        List<Resource> list = resService.listToEntity("select id,name,icon,parentId,hasChildren from Resource order by location asc");
        StringBuffer html = new StringBuffer();
        renderSort(list, list, "-1", html);
        model.addAttribute("html", html.toString());
        return "sort";
    }

    /**
     * 递归生成资源树
     *
     * @param allList
     * @param list
     * @param parentId
     * @param html
     */
    private void renderSort(List<Resource> allList, List<Resource> list, String parentId, StringBuffer html) {
        html.append("<ol class=\"dd-list\">");

        for (Resource res : list) {
            if (parentId.equals(res.getParentId())) {
                html.append(String.format("<li class=\"dd-item\" data-id=\"%s\"><div class=\"dd-handle\"><i class=\"%s mr10\"></i> %s<div class=\"pull-right text-muted\"><i class=\"fa fa-navicon\"></i></div></div>", res.getId(), res.getIcon(), res.getName()));
                if (res.hasChildren()) {
                    renderSort(allList, getChildRes(allList, res.getId()), res.getId(), html);
                }
                html.append("</li>");
            }
        }

        html.append("</ol>");
    }

    @RequestMapping(value = "/sort/do", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject doSort(Model model, String data) {
        try {
            resService.executeSort(data);

            logService.info("sys-res", "菜单排序");
            return WebResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }

    /**
     * 获取子菜单
     *
     * @param list
     * @param pid
     * @return
     */
    public List<Resource> getChildRes(List<Resource> list, String pid) {
        List<Resource> childRes = new ArrayList<Resource>();
        for (Resource res : list) {
            if (res.getParentId().equals(pid)) {
                childRes.add(res);
            }
        }
        return childRes;
    }

    @RequestMapping(value = "/edit/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String edit(Model model, @PathVariable String id) {
        Resource res = resService.getById(id);
        if (ObjectKit.isNull(res))
            return WebApplicationCommon.ERROR.UNAUTHORIZED_ACCESS;//非法访问
        model.addAttribute("res", res);
        //获取资源的上级菜单名称
        if (res.getParentId().equals("-1")) {
            model.addAttribute("parentName", "顶级菜单");
        } else {
            String name = resService.get("select name from Resource where id=?", res.getParentId()).getName();
            model.addAttribute("parentName", name);
        }
        return "edit";
    }

    /**
     * 修改URL
     *
     * @param pk
     * @param value
     * @return
     */
    @RequestMapping(value = "/edit/url", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject editUrl(String pk, String value) {
        try {
            Resource eqRes = resService.getById(pk);
            if (ObjectKit.isNull(eqRes))
                return WebResult.error("此菜单不存在！");
            String oldUrl = eqRes.getUrl();
            eqRes.setUrl(value);

            resService.merge(eqRes);

            logService.info("sys-res", StringKit.format("编辑菜单-链接地址[{0}, {1}, {2}]", eqRes.getId(), oldUrl, eqRes.getUrl()));
            return WebResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }

    /**
     * 修改名称
     *
     * @param pk
     * @param value
     * @return
     */
    @RequestMapping(value = "/edit/name", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject editName(String pk, String value) {
        try {
            Resource eqRes = resService.getById(pk);
            if (ObjectKit.isNull(eqRes))
                return WebResult.error("此菜单不存在！");
            String oldName = eqRes.getName();
            eqRes.setName(value);

            resService.merge(eqRes);

            logService.info("sys-res", StringKit.format("编辑菜单-名称[{0}, {1}, {2}]", eqRes.getId(), oldName, eqRes.getName()));
            return WebResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }

    @RequestMapping(value = "/edit/do", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject doEdit(Resource res) {
        try {
            Resource eqRes = resService.getById(res.getId());
            if (ObjectKit.isNull(eqRes))
                return WebResult.error("此菜单不存在！");
            eqRes.setName(res.getName());
            eqRes.setIcon(res.getIcon());
            eqRes.setUrl(res.getUrl());
            eqRes.setDescript(res.getDescript());
            eqRes.setEnabled(res.isEnabled());
            eqRes.setParentId(res.getParentId());
            eqRes.setDisplay(res.isDisplay());
            eqRes.setBeta(res.isBeta());
            eqRes.setNewWindow(res.isNewWindow());

            resService.merge(eqRes);
            //更新父级菜单为拥有下级子菜单
            if (!eqRes.getParentId().equals("-1"))
                resService.executeBatch("update Resource set hasChildren=true where id=?", eqRes.getParentId());
            if (resService.count(Condition.NEW().eq("parentId", eqRes.getId())) > 0) {
                resService.executeBatch("update Resource set hasChildren=true where id=?", eqRes.getId());
            }
            logService.info("sys-res", StringKit.format("编辑菜单[{0}, {1}, {2}]", res.getId(), res.getName(), res.getParentId()));
            return WebResult.success().element("parentId", res.getParentId());
        } catch (Exception e) {
            e.printStackTrace();
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
            resService.executeBatch("update Resource set enabled=true where id=?", id);

            logService.info("sys-res", StringKit.format("启用菜单[{0}]", id));
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
            resService.executeBatch("update Resource set enabled=false where id=?", id);
            logService.info("sys-res", StringKit.format("禁用菜单[{0}]", id));
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }

    /**
     * 选择图标
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/icon", method = {RequestMethod.GET, RequestMethod.POST})
    public String icon(Model model) {
        List[] icons = resService.getIconList();

        model.addAttribute("fa", icons[0]);
        model.addAttribute("ti", icons[1]);
        model.addAttribute("gly", icons[2]);
        model.addAttribute("sli", icons[3]);
        return "icon";
    }

    /**
     * 管理字段
     *
     * @param model
     * @return
     */
    @RequestMapping("/columns")
    public String columns(Model model, String id) {
        Resource res = resService.getById(id);
        if (ObjectKit.isNotNull(res)) {
            List<ResourceColumn> columnList = resourceColumnService.list(Condition.NEW().eq("resource", res));

            model.addAttribute("columnList", columnList);
        }
        model.addAttribute("resId", id);
        return "columns";
    }

    /**
     * 管理字段，先删除，再添加
     *
     * @param name
     * @param code
     * @return
     */
    @RequestMapping("/columns/do")
    @ResponseBody
    public JSONObject doColumns(String resId, String[] name, String[] code) {
        Resource res = resService.getById(resId);
        resourceColumnService.executeBatch("delete from RoleResourceColumn where resId=?", res.getId());
        resourceColumnService.executeBatch("delete from ResourceColumn where resource.id=?", res.getId());
        if (ObjectKit.isNotNull(name) && name.length == code.length) {
            List<ResourceColumn> columnList = new ArrayList<>();
            for (int i = 0; i < name.length; i++) {
                ResourceColumn column = new ResourceColumn();

                column.setResource(res);
                column.setName(name[i]);
                column.setCode(code[i]);
                column.setCreateTime(getCurrentTime());
                column.setCreateUser(getCurrentUser().getUserName());

                columnList.add(column);

            }
            res.setColumns(columnList);
            resService.merge(res);

            logService.info("sys-res", StringKit.format("编辑菜单-管理字段[{0}]", res.getId()));
        }
        return WebResult.success();
    }

    /**
     * 管理字段
     *
     * @param model
     * @return
     */
    @RequestMapping("/buttons")
    public String buttons(Model model, String id) {
        Resource res = resService.getById(id);
        if (ObjectKit.isNotNull(res)) {
            List<ResourceButton> buttonList = resourceButtonService.list(Condition.NEW().eq("resource", res));

            model.addAttribute("buttonList", buttonList);
        }
        model.addAttribute("resId", id);
        return "buttons";
    }

    /**
     * 管理字段，先删除，再添加
     *
     * @param name
     * @param code
     * @return
     */
    @RequestMapping("/buttons/do")
    @ResponseBody
    public JSONObject doButtons(String resId, String[] name, String[] code) {
        Resource res = resService.getById(resId);
        resourceButtonService.executeBatch("delete from ResourceButton where resource.id=?", res.getId());
        if (ObjectKit.isNotNull(name) && name.length == code.length) {
            List<ResourceButton> buttonList = new ArrayList<>();
            for (int i = 0; i < name.length; i++) {
                ResourceButton button = new ResourceButton();

                button.setResource(res);
                button.setName(name[i]);
                button.setCode(code[i]);
                button.setCreateTime(getCurrentTime());
                button.setCreateUser(getCurrentUser().getUserName());

                buttonList.add(button);

            }
            res.setButtons(buttonList);
            resService.merge(res);

            logService.info("sys-res", StringKit.format("编辑菜单-管理按钮[{0}]", res.getId()));
        }
        return WebResult.success();
    }

    /**
     * 显示组织结构图
     *
     * @return
     */
    @RequestMapping("/chart")
    public String chart(Model model) {
        model.addAttribute("list", resService.list(Condition.NEW().asc("code")));

        logService.info("sys-res", "查看菜单结构图");
        return "chart";
    }

    /**
     * 拥有此菜单的角色
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/role")
    public String role(Model model, String id) {
        model.addAttribute("id", id);
        return "role";
    }

    /**
     * 菜单下的角色列表
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/role/data")
    @ResponseBody
    public Map<String, Object> roleData(String id) {
        resService.setLazyInitializer(resource -> {
            Hibernate.initialize(resource.getRoles());
        });
        Resource resource = resService.getById(id);
        return DataGridTransforms.JQGRID.transform(resource.getRoles(), new PageBean());
    }

    /**
     * 添加角色到菜单
     *
     * @param id
     * @param roleIds
     * @return
     */
    @RequestMapping(value = "/role/add")
    @ResponseBody
    public JSONObject doAddRole(String id, @RequestList("roleIds") List<String> roleIds) {
        try {
            for (String roleId : roleIds) {
                if (resService.nativeCount("select count(*) from sys_role_res where res_id = ? and role_id = ?", id, roleId) == 0) {
                    resService.executeSqlBatch("insert into sys_role_res(res_id, role_id) values(?,?)", id, roleId);
                }
            }
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e.toString());
        }
    }

    /**
     * 从菜单中移除角色
     *
     * @param id
     * @param roleIds
     * @return
     */
    @RequestMapping(value = "/role/remove")
    @ResponseBody
    public JSONObject doRemoveRole(String id, @RequestList("roleIds") List<String> roleIds) {
        try {
            for (String roleId : roleIds) {
                resService.executeSqlBatch("delete from sys_role_res where res_id = ? and role_id = ?", id, roleId);
            }
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e.toString());
        }
    }
}
