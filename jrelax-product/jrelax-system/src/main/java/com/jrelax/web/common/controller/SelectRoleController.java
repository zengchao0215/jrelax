package com.jrelax.web.common.controller;

import com.jrelax.core.web.transform.DataGridTransforms;
import com.jrelax.kit.StringKit;
import com.jrelax.orm.query.PageBean;
import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.entity.Role;
import com.jrelax.web.system.entity.User;
import com.jrelax.web.system.service.RoleService;
import com.jrelax.web.system.service.UnitService;
import com.jrelax.web.system.service.UserService;
import net.sf.json.JSONArray;
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

import java.util.List;
import java.util.Map;

/**
 * 选择角色
 * Created by zengc on 2017-02-07.
 */
@Controller
@RequestMapping("/common/role")
public class SelectRoleController extends BaseController<User>{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public final String TPL = "/common/role/";

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UnitService unitService;

    /**
     * 按登录名/真实姓名/联系方式 检索角色
     * @param model
     * @return
     */
    @RequestMapping(value="/select", method={RequestMethod.GET, RequestMethod.POST})
    public String select(Model model){
    	return TPL + "select";
    }

    /**
     * 数据
     * @param pageBean
     * @param key
     * @return
     */
    @RequestMapping(value="/select/data")
    @ResponseBody
    public Map<String, Object> selectData(PageBean pageBean, String key){
        if(StringKit.isNotEmpty(key))
            pageBean.addCriterion(Restrictions.like("name", key, MatchMode.ANYWHERE));
        List<Role> list = roleService.list(pageBean);

        return DataGridTransforms.JQGRID.transform(list, pageBean);
    }

    /**
     * 按机构选择用户
     * @param model
     * @return
     */
    @RequestMapping(value="/select-unit", method={RequestMethod.GET})
    public String selectByUnit(Model model){
    	return TPL + "select-unit";
    }

    /**
     * 获取机构树
     * @return
     */
    @RequestMapping(value="/select-unit/unit/tree", method={RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONArray unitTree(){
        return unitService.tree();
    }

    /**
     * 获取机构是树子节点
     * @param pid
     * @return
     */
    @RequestMapping(value="/select-unit/unit/tree/{pid}", method={RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONArray unitTree(@PathVariable String pid){
        return unitService.tree(pid);
    }

    /**
     * 根据机构ID获取用户列表
     * @param model
     * @param pageBean
     * @param unitId 机构ID
     * @return
     */
    @RequestMapping(value="/select-unit/role/data", method={RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> selectByUnitUsers(Model model, PageBean pageBean, String unitId){
        if(StringKit.isNotEmpty(unitId))
            pageBean.addCriterion(Restrictions.like("unit.id", unitId, MatchMode.ANYWHERE));
        List<Role> list = roleService.list(pageBean);

        return DataGridTransforms.JQGRID.transform(list, pageBean);
    }
}
