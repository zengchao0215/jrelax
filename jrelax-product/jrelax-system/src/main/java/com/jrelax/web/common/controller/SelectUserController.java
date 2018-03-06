package com.jrelax.web.common.controller;

import com.jrelax.core.web.transform.DataGridTransforms;
import com.jrelax.kit.ObjectKit;
import com.jrelax.kit.StringKit;
import com.jrelax.orm.query.Condition;
import com.jrelax.orm.query.PageBean;
import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.entity.Role;
import com.jrelax.web.system.entity.User;
import com.jrelax.web.system.service.RoleService;
import com.jrelax.web.system.service.UnitService;
import com.jrelax.web.system.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
 * 选择用户
 * Created by zengc on 2016-06-02.
 */
@Controller
@RequestMapping("/common/user")
public class SelectUserController extends BaseController<User>{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public final String TPL = "/common/user/";

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UnitService unitService;

    /**
     * 按登录名/真实姓名/联系方式 检索用户
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
        List<User> list = null;
        if(StringKit.isNotEmpty(key)){
            key = "%"+key+"%";
            list = userService.listToEntity(pageBean, "select id, userName, realName, mobile from User where (userName like ? or realName like ? or mobile like ?)", key, key, key);
        }else{
            list = userService.listToEntity(pageBean, "select id, userName, realName, mobile from User");
        }

        return DataGridTransforms.JQGRID.transform(list, pageBean);
    }

    /**
     * 按机构选择用户
     * @param model
     * @return
     */
    @RequestMapping(value="/select-unit", method={RequestMethod.GET})
    public String selectByUnit(Model model, boolean multi){
        model.addAttribute("multi", multi);
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
    @RequestMapping(value="/select-unit/user/data", method={RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> selectByUnitUsers(Model model, PageBean pageBean, String unitId){
        List<Map<String, Object>> listMap = userService.nativeListToMap(pageBean, "SELECT id, user_name as userName, real_name as realName, mobile, email, enabled, online FROM sys_user WHERE id IN (SELECT user_id FROM sys_user_unit WHERE unit_id=?) order by user_name asc", unitId);
        List<User> userList = ObjectKit.mapToList(User.class, listMap);

        return DataGridTransforms.JQGRID.transform(userList, pageBean);
    }

    /**
     * 根据机构+角色选择用户
     * @param model
     * @param multi
     * @return
     */
    @RequestMapping(value="/select-unit-role", method={RequestMethod.GET})
    public String selectByUnitAndRole(Model model, boolean multi){
        model.addAttribute("multi", multi);
    	return TPL + "select-unit-role";
    }

    @RequestMapping(value="/select-unit-role/role/tree/{unitId}", method={RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONArray roleTree(@PathVariable String unitId){
        List<Role> list = roleService.list(Condition.NEW().eq("unit.id", unitId));
        JSONArray data = new JSONArray();
        for (Role unit : list) {
            JSONObject node = new JSONObject();

            node.element("id", unit.getId());
            node.element("text", unit.getName());
            node.element("children", false);
            node.element("icon", "fa fa-users");

            data.add(node);
        }
        return data;
    }

    @RequestMapping(value="/select-unit-role/user/data", method={RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> selectByUnitAndRoleUsers(Model model, PageBean pageBean, String roleId){
        List<Map<String, Object>> listMap = userService.nativeListToMap(pageBean, "SELECT id, user_name as userName, real_name as realName, mobile, email, enabled, online FROM sys_user WHERE id IN (SELECT user_id FROM sys_user_role WHERE role_id=?) order by user_name asc",roleId);
        List<User> userList = ObjectKit.mapToList(User.class, listMap);

        return DataGridTransforms.JQGRID.transform(userList, pageBean);
    }
}
