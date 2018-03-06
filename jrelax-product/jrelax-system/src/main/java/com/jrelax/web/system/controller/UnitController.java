package com.jrelax.web.system.controller;

import com.jrelax.aop.annotation.Log;
import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.core.web.support.http.HandlerRequest;
import com.jrelax.event.ApplicationEventDefined;
import com.jrelax.kit.ObjectKit;
import com.jrelax.kit.StringKit;
import com.jrelax.orm.dao.ILazyInitializer;
import com.jrelax.orm.query.Condition;
import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.entity.Resource;
import com.jrelax.web.system.entity.Unit;
import com.jrelax.web.system.service.LogService;
import com.jrelax.web.system.service.ResourceService;
import com.jrelax.web.system.service.UnitService;
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
import java.util.List;

@Controller
@RequestMapping("/system/unit")
public class UnitController extends BaseController<Unit> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UnitService unitService;
    @Autowired
    private ResourceService resService;
    @Autowired
    private LogService logService;

    @RequestMapping(method = {RequestMethod.GET})
    @Log(name = "机构管理", content = "访问机构列表")
    public String index(Model model) {
        unitService.setLazyInitializer(unit -> {
            Hibernate.initialize(unit.getLeader());
        });
        if (getCurrentUser().isSystemAdmin()) {// 系统机构，查出所有的顶级机构
            List<Unit> unitList = unitService.list(Condition.NEW().eq("parentId", "-1").asc("code"));
            model.addAttribute("list", unitList);
            model.addAttribute("count", unitService.count());
        } else {// 查出当前用户用户下属的机构
            List<Unit> unitList = unitService.list(Condition.NEW().in("id", getCurrentUnitIds()).asc("code"));
            model.addAttribute("list", unitList);
            model.addAttribute("count", unitService.count());
        }
        model.addAttribute("isSystemUnit", isSystemUnit());
        //机构独立菜单开关
        model.addAttribute("unitRes", JRelaxSystemConfigHelper.getBoolean("system.unit.res", false));

        logService.info("sys-unit", "查询机构列表");
        return "system/unit/index";
    }

    @RequestMapping(value = "/tree", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONArray tree(boolean parent) {
        JSONArray data = unitService.tree();

        if (parent) {
            JSONArray array = new JSONArray();
            JSONObject node = new JSONObject();
            node.element("id", "-1");
            node.element("text", "所有机构");
            node.element("children", data);
            node.element("state", "{opened:true}");
            node.element("icon", "fa fa-sitemap");

            array.add(node);
            data = array;
        }
        return data;
    }

    /**
     * 获取子节点
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/tree/{pid}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONArray tree(@PathVariable String pid) {
        return unitService.tree(pid);
    }

    /**
     * 新增页面
     *
     * @param model
     * @param pid
     * @return
     */
    @RequestMapping(value = "/add", method = {RequestMethod.GET})
    public String add(Model model, String pid) {
        // 判断是否有机构，没有机构则页面上不可以进行上级机构的选择
        model.addAttribute("noUnit", unitService.count() <= 0);
        if (ObjectKit.isNotNull(pid)) {
            // 获取父节点信息
            Unit parentUnit = unitService.get("select id,name from Unit where id = ?", pid);
            if (ObjectKit.isNotNull(parentUnit)) {
                model.addAttribute("parentUnit", parentUnit);
            }
        }
        // 判断当前机构是否是系统机构，如果不是系统机构不允许出现系统机构按钮
        model.addAttribute("isSystemUnit", isSystemUnit());
        model.addAttribute("unitRes", JRelaxSystemConfigHelper.getBoolean("system.unit.res", false));
        return "system/unit/add";
    }

    /**
     * 执行新增操作
     *
     * @param unit
     * @return
     */
    @RequestMapping(value = "/add/do", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject doAdd(Unit unit) {
        try {
            if (StringKit.isBlank(unit.getName()))
                return WebResult.error("机构名称不能为空！");
            if (StringKit.isEmpty(unit.getParentId())) {
                if (isSystemUnit()) {
                    unit.setParentId("-1");
                } else {
                    return WebResult.error("必须选择一个上级机构！");
                }
            }
            unit.setCreateTime(getCurrentTime());
            //判断是否选择了负责人
            if (StringKit.isEmpty(unit.getLeader().getId()))
                unit.setLeader(null);
            // 判断此机构名称，在当前父机构下是否存在
            if (unitService.count(Condition.NEW().eq("parentId", unit.getParentId()).eq("name", unit.getName())) > 0)
                return WebResult.error("'" + unit.getName() + "' 已存在！");
            // 生成编码
            unit.setCode(unitService.generateCode(unit.getParentId()));
            unitService.save(unit);
            if (!unit.getParentId().equals("-1"))
                unitService.executeBatch("update Unit set hasChildren=true where id = ?", unit.getParentId());

            logService.info("sys-unit", StringKit.format("创建机构[{0}, {1}, {2}]", unit.getId(), unit.getName(), unit.getParentId()));

            getEventManager().trigger(ApplicationEventDefined.ON_UNIT_CREATED, this, unit);
            return WebResult.success().element("id", unit.getId());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }


    /**
     * 首页TreeTable子机构
     *
     * @param model
     * @param pid
     * @return
     */
    @RequestMapping(value = "/child/{pid}", method = {RequestMethod.GET, RequestMethod.POST})
    public String children(Model model, @PathVariable String pid) {
        unitService.setLazyInitializer(unit -> {
            Hibernate.initialize(unit.getLeader());
        });
        List<Unit> list = unitService.list(Condition.NEW().eq("parentId", pid));
        model.addAttribute("list", list);
        //机构独立菜单开关
        model.addAttribute("unitRes", JRelaxSystemConfigHelper.getBoolean("system.unit.res", false));
        return "system/unit/children";
    }

    /**
     * 机构详情
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String detail(Model model, @PathVariable String id) {
        unitService.setLazyInitializer(unit -> {
            Hibernate.initialize(unit.getLeader());
        });
        Unit unit = unitService.getById(id);
        if (!ObjectKit.isNotNull(unit))
            return WebApplicationCommon.ERROR.UNAUTHORIZED_ACCESS;
        if (StringKit.isBlank(unit.getAddress()))
            unit.setAddress("未登记地址");
        model.addAttribute("unit", unit);
        // 不等于-1，查询上级菜单
        if (!unit.getParentId().equals("-1")) {
            Unit parentUnit = unitService.get("select name from Unit where id = ?", unit.getParentId());
            if (ObjectKit.isNotNull(parentUnit)) {
                model.addAttribute("parentUnit", parentUnit.getName());
            }
        }
        logService.info("sys-unit", StringKit.format("查询机构详情[{0}, {1}, {2}]", unit.getId(), unit.getName(), unit.getParentId()));
        return "system/unit/detail";
    }

    /**
     * 机构菜单
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail/res/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String detailRes(Model model, @PathVariable String id) {
        unitService.setLazyInitializer(new ILazyInitializer<Unit>() {
            @Override
            public void init(Unit unit) {
                Hibernate.initialize(unit.getResources());
            }
        });
        Unit unit = unitService.getById(id);
        if (!ObjectKit.isNotNull(unit))
            return WebApplicationCommon.ERROR.UNAUTHORIZED_ACCESS;
        if (StringKit.isBlank(unit.getAddress()))
            unit.setAddress("未登记地址");
        model.addAttribute("unit", unit);
        // 不等于-1，查询上级菜单
        if (!unit.getParentId().equals("-1")) {
            Unit parentUnit = unitService.get("select name from Unit where id = ?", unit.getParentId());
            if (ObjectKit.isNotNull(parentUnit)) {
                model.addAttribute("parentUnit", parentUnit.getName());
            }
        }
        // 获取用户菜单资源
        List<Resource> resList = unit.getResources();
        List<Resource> firstResList = new ArrayList<Resource>();
        JSONArray jsonResList = new JSONArray();
        for (Resource res : resList) {
            if (res.getParentId().equals("-1")) {
                firstResList.add(res);
            }
            // 防止由于roles延迟加载导致出错
            res.setRoles(null);
            res.setColumns(null);
            res.setButtons(null);
            JSONObject jsonRes = JSONObject.fromObject(res);
            jsonResList.add(jsonRes);
        }
        model.addAttribute("allRes", jsonResList.toString());
        model.addAttribute("resList", firstResList);

        logService.info("sys-unit", StringKit.format("查看机构菜单[{0}, {1}, {2}]", unit.getId(), unit.getName(), unit.getParentId()));
        return "system/unit/detail_right";
    }

    /**
     * 编辑机构
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String edit(Model model, @PathVariable String id) {
        unitService.setLazyInitializer(unit -> {
            Hibernate.initialize(unit.getLeader());
        });
        Unit unit = unitService.getById(id);
        if (!ObjectKit.isNotNull(unit))
            return WebApplicationCommon.ERROR.UNAUTHORIZED_ACCESS;
        model.addAttribute("unit", unit);
        // 不等于-1，查询上级菜单
        if (!unit.getParentId().equals("-1")) {
            Unit parentUnit = unitService.get("select name from Unit where id = ?", unit.getParentId());
            if (ObjectKit.isNotNull(parentUnit)) {
                model.addAttribute("parentUnit", parentUnit.getName());
            }
        } else {
            model.addAttribute("parentUnit", "顶级机构");
        }
        // 判断当前机构是否是系统机构，如果不是系统机构不允许出现系统机构按钮
        model.addAttribute("isSystemUnit", isSystemUnit());
        return "system/unit/edit";
    }

    @RequestMapping(value = "/edit/do", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject doEdit(Unit unit) {
        try {
            if (StringKit.isBlank(unit.getId()))
                return WebResult.error("非法操作！");
            if (StringKit.isBlank(unit.getName()))
                return WebResult.error("机构名称不能为空！");
            if (StringKit.isBlank(unit.getParentId()))
                unit.setParentId("-1");
            unit.setCreateTime(getCurrentTime());
            //判断是否选择了负责人
            if (StringKit.isEmpty(unit.getLeader().getId()))
                unit.setLeader(null);
            // 判断此机构名称，在当前父机构下是否存在
            if (unitService.count(Condition.NEW().eq("parentId", unit.getParentId()).eq("name", unit.getName()).not(Restrictions.eq("id", unit.getId()))) > 0)
                return WebResult.error("'" + unit.getName() + "' 已存在！");

            Unit eqUnit = unitService.getById(unit.getId());
            String oldPid = eqUnit.getParentId();
            eqUnit.setName(unit.getName());
            eqUnit.setAddress(unit.getAddress());
            eqUnit.setEmail(unit.getEmail());
            eqUnit.setEnabled(unit.isEnabled());
            eqUnit.setContact(unit.getContact());
            eqUnit.setMobile(unit.getMobile());
            eqUnit.setWeb(unit.getWeb());
            eqUnit.setSystem(unit.isSystem());
            eqUnit.setLeader(unit.getLeader());//设置部门负责人
            // 判断父节点是否变化
            if (!eqUnit.getParentId().equals(unit.getParentId())) {// 如果改变了父节点，那么重新生成编号
                eqUnit.setParentId(unit.getParentId());
                eqUnit.setCode(unitService.generateCode(eqUnit.getParentId()));
            }
            unitService.merge(eqUnit);
            // 更新现在的父节点的子节点状态
            if (!eqUnit.getParentId().equals("-1"))
                unitService.executeBatch("update Unit set hasChildren=true where id=?", unit.getParentId());
            // 更新之前的父节点的子节点状态
            if (!oldPid.equals("-1") && !oldPid.equals(unit.getParentId()))
                unitService.executeBatch("update Unit set hasChildren=true where id=?", oldPid);
            // 判断是否有子节点
            int count = unitService.count(Condition.NEW().eq("parentId", unit.getParentId()));
            if (count == 0) {
                unitService.executeBatch("update Unit set hasChildren=false where id=?", unit.getParentId());
            }
            if (!oldPid.equals(unit.getParentId())) {
                count = unitService.count(Condition.NEW().eq("parentId", oldPid));
                if (count == 0) {
                    unitService.executeBatch("update Unit set hasChildren=false where id=?", oldPid);
                }
            }
            logService.info("sys-unit", StringKit.format("编辑机构[{0}, {1}, {2}]", unit.getId(), unit.getName(), unit.getParentId()));
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
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
            unitService.deleteAndRelated(id);
            logService.info("sys-unit", StringKit.format("删除机构[{0}]", id));
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
            unitService.executeBatch("update Unit set enabled=true where id=?", id);

            logService.info("sys-unit", StringKit.format("启用机构[{0}]", id));
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
            unitService.executeBatch("update Unit set enabled=false where id=?", id);

            logService.info("sys-unit", StringKit.format("禁用机构[{0}]", id));
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }

    /**
     * 机构设置菜单
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/res/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String res(Model model, @PathVariable String id) {
        if (!JRelaxSystemConfigHelper.getBoolean("system.unit.res", false)) {
            return WebApplicationCommon.ERROR.UNAUTHORIZED_ACCESS;
        }
        unitService.setLazyInitializer(new ILazyInitializer<Unit>() {
            @Override
            public void init(Unit unit) {
                Hibernate.initialize(unit.getResources());
            }
        });
        Unit unit = unitService.getById(id);
        if (ObjectKit.isNotNull(unit) && !unit.isSystem()) {
            model.addAttribute("unit", unit);
            model.addAttribute("currentUserUnitId", getCurrentUser().getDefaultUnit().getId());
            JSONArray array = new JSONArray();
            for (Resource res : unit.getResources()) {
                array.add(res.getId());
            }
            model.addAttribute("data", array);
            return "system/unit/res";
        } else {
            return WebApplicationCommon.ERROR.UNAUTHORIZED_ACCESS;
        }
    }

    /**
     * 执行机构设置菜单
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/res/do", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject doRes(String id, String resIds) {
        if (!JRelaxSystemConfigHelper.getBoolean("system.unit.res", false)) {
            return WebResult.error("机构独立菜单功能未启用");
        }
        try {
            unitService.setLazyInitializer(new ILazyInitializer<Unit>() {
                @Override
                public void init(Unit unit) {
                    Hibernate.initialize(unit.getResources());
                }
            });
            Unit unit = unitService.getById(id);
            if (ObjectKit.isNotNull(unit) && !unit.isSystem()) {
                unit.getResources().clear();
                if ("all".equals(resIds)) {
                    unit.setResources(resService.list());
                } else {
                    String[] ids = resIds.split(",");
                    for (String rid : ids) {
                        Resource res = new Resource();
                        res.setId(rid);
                        unit.getResources().add(res);
                    }
                    unitService.merge(unit);
                }

                logService.info("sys-unit", StringKit.format("编辑机构-菜单权限[{0}, {1}, {2}]", unit.getId(), unit.getName(), unit.getParentId()));
                return WebResult.success();
            } else {
                return WebResult.error("非法访问");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }


    @RequestMapping(value = "/res/tree/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONArray roleResTree(@PathVariable String id) {
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
            if (getCurrentUser().isSystemAdmin()) {
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
    @RequestMapping(value = "/res/tree/{id}/{pid}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONArray roleResTreeChild(@PathVariable String id, @PathVariable String pid) {
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
            if (getCurrentUser().isSystemAdmin()) {
                list = resService.list(Condition.NEW().eq("enabled", true).eq("parentId", pid).asc("location"));
            } else {
                list = unit.getResources();
            }
            for (Resource res : list) {
                if (!getCurrentUser().isSystemAdmin())
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

    @RequestMapping(value = "/edit/name", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject editName(String pk, String name, String value) {
        try {
            unitService.executeBatch("update Unit set name=? where id=?", value, pk);

            logService.info("sys-unit", StringKit.format("编辑机构-名称[{0}, {1}]", pk, value));
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e.getMessage());
        }
    }

    @RequestMapping(value = "/edit/address", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject editAddress(String pk, String name, String value) {
        try {
            unitService.executeBatch("update Unit set address=? where id=?", value, pk);

            logService.info("sys-unit", StringKit.format("编辑机构-地址[{0}, {1}]", pk, value));
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e.getMessage());
        }
    }

    /**
     * 获取上级机构的菜单，并复制到给本身
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/copyres/parent", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject copyResFromParent(String id) {
        try {
            unitService.setLazyInitializer(new ILazyInitializer<Unit>() {
                @Override
                public void init(Unit unit) {
                    Hibernate.initialize(unit.getResources());
                }
            });
            //获取机构
            Unit unit = unitService.getById(id);
            if (ObjectKit.isNull(unit)) return WebResult.error("机构不存在");
            if (unit.getParentId().equals("-1")) return WebResult.error("无上级机构");

            Unit pUnit = unitService.getById(unit.getParentId());

            unit.getResources().clear();
            unit.getResources().addAll(pUnit.getResources());

            unitService.update(unit);

            logService.info("sys-unit", StringKit.format("复制上级机构菜单[{0}]", id));
            return WebResult.success().element("count", unit.getResources().size());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }


    /**
     * 复制资源到子机构
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/copy/res/children", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject copyResToChildren(String id) {
        try {
            unitService.setLazyInitializer(new ILazyInitializer<Unit>() {
                @Override
                public void init(Unit unit) {
                    Hibernate.initialize(unit.getResources());
                }
            });
            //获取机构
            Unit unit = unitService.getById(id);
            if (ObjectKit.isNull(unit)) return WebResult.error("机构不存在");
            if (unit.getResources().size() == 0) return WebResult.error("机构下无菜单权限");
            List<Unit> cUnits = unitService.list(Condition.NEW().eq("parentId", id));
            if (cUnits.size() == 0) return WebResult.error("此机构下无子机构");
            //将此机构的菜单复制到下级机构
            for (Unit cUnit : cUnits) {
                List<Resource> pResList = unit.getResources();
                List<Resource> cResList = cUnit.getResources();

                for (Resource res : pResList) {
                    //判断菜单是否存在于此机构下
                    boolean eqRes = false;
                    for (Resource cRes : cResList) {
                        if (cRes.getId().equals(res.getId())) {
                            eqRes = true;
                            break;
                        }
                    }
                    if (!eqRes) {
                        cResList.add(res);
                    }
                }
            }
            unitService.update(cUnits);

            logService.info("sys-unit", StringKit.format("菜单复制给下级机构[{0}]", id));
            return WebResult.success().element("count", unit.getResources().size()).element("count2", cUnits.size());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }

    /**
     * 显示组织结构图
     *
     * @return
     */
    @RequestMapping("/chart")
    public String chart(Model model) {
        model.addAttribute("list", unitService.list());

        logService.info("sys-unit", "查询机构结构图");
        return "system/unit/chart";
    }
}
