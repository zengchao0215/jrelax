package com.jrelax.web.system.service;

import com.jrelax.core.web.support.http.HandlerRequest;
import com.jrelax.kit.ObjectKit;
import com.jrelax.orm.query.Condition;
import com.jrelax.web.support.BaseService;
import com.jrelax.web.system.entity.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class RoleService extends BaseService<Role> {
    @Autowired
    private RoleResourceButtonService rrbService;
    @Autowired
    private ResourceButtonService btnService;
    @Autowired
    private RoleResourceColumnService rrcService;
    @Autowired
    private LogService logService;

    public void save(Role role, String resourceIds, String userIds, String resourceColIds) {
        JSONArray resources = JSONArray.fromObject(resourceIds);
        List<Resource> resList = new ArrayList<Resource>();//资源列表
        for (int i = 0; i < resources.size(); i++) {
            JSONObject jRes = resources.getJSONObject(i);
            Resource res = new Resource();
            res.setId(jRes.getString("id"));

            resList.add(res);
        }

        role.setResources(resList);
        role.setCreateTime(getCurrentTime());

        this.save(role);

        //保存资源按钮列表
        for (int i = 0; i < resources.size(); i++) {
            JSONObject jRes = resources.getJSONObject(i);
            if (!jRes.getString("btns").startsWith("[")) continue;
            JSONArray btnIds = jRes.getJSONArray("btns");

            for (int j = 0; j < btnIds.size(); j++) {
                String btnId = btnIds.getString(j);
                RoleResourceButton rrb = new RoleResourceButton();
                rrb.setRoleId(role.getId());
                rrb.setResId(jRes.getString("id"));
                rrb.setBtnId(btnId);

                rrbService.save(rrb);
            }
        }

        //保存资源字段列表
        JSONArray resourcesCol = JSONArray.fromObject(resourceColIds);
        for (int i = 0; i < resourcesCol.size(); i++) {
            JSONObject jRes = resourcesCol.getJSONObject(i);
            if (!jRes.getString("cols").startsWith("[")) continue;
            JSONArray colIds = jRes.getJSONArray("cols");

            List<RoleResourceColumn> rrcList = new ArrayList<>();
            for (int j = 0; j < colIds.size(); j++) {
                String colId = colIds.getString(j);
                RoleResourceColumn rrc = new RoleResourceColumn();
                rrc.setColumnId(colId);
                rrc.setResId(jRes.getString("id"));
                rrc.setRoleId(role.getId());

                rrcList.add(rrc);
            }
            rrcService.save(rrcList);
        }
        getEventManager().trigger("onRoleAdd", this, role);
    }

    /**
     * 删除角色
     * 删除角色关联信息，菜单，按钮等。
     *
     * @param id
     */
    public void deleteAsRelated(String id) {
        rrbService.executeBatch("delete from RoleResourceButton where roleId = ?", id);
        rrbService.executeBatch("delete from RoleResourceColumn where roleId = ?", id);
        rrbService.executeSqlBatch("delete from sys_role_res where role_id = ?", id);
        rrbService.executeSqlBatch("delete from sys_user_role where role_id = ?", id);
        this.delete(id);
        getEventManager().trigger("onRoleDelete", this, id);
    }

    /**
     * 修改角色菜单
     *
     * @param id
     * @param data
     */
    public void executeEditRoleRes(String id, String data) {
        Role role = getById(id);
        Hibernate.initialize(role.getResources());
        role.getResources().clear();//清空原有菜单

        JSONObject json = JSONObject.fromObject(data);
        JSONArray resources = json.getJSONArray("res");
        JSONObject buttons = json.getJSONObject("button");
        JSONObject columns = json.getJSONObject("column");
        List<Resource> resList = new ArrayList<Resource>();//资源列表
        for (int i = 0; i < resources.size(); i++) {
            Resource res = new Resource();
            res.setId(resources.getString(i));

            resList.add(res);
        }

        role.setResources(resList);

        this.update(role);
        //删除原有按钮
        rrbService.executeBatch("delete from RoleResourceButton where roleId=?", role.getId());
        //保存资源按钮列表
        Iterator iterator = buttons.keys();
        while (iterator.hasNext()) {
            String resourceId = iterator.next().toString();
            List<RoleResourceButton> rrbList = new ArrayList<>();

            JSONArray buttonArray = buttons.getJSONArray(resourceId);
            for (int j = 0; j < buttonArray.size(); j++) {
                String btnId = buttonArray.getString(j);
                RoleResourceButton rrb = new RoleResourceButton();
                rrb.setRoleId(role.getId());
                rrb.setResId(resourceId);
                rrb.setBtnId(btnId);

                rrbList.add(rrb);
            }
            rrbService.save(rrbList);
        }

        //删除原有字段
        rrcService.executeBatch("delete from RoleResourceColumn where roleId=?", role.getId());
        iterator = columns.keys();
        while (iterator.hasNext()) {
            String resourceId = iterator.next().toString();
            List<RoleResourceColumn> rrcList = new ArrayList<>();

            JSONArray columnArray = columns.getJSONArray(resourceId);
            for (int j = 0; j < columnArray.size(); j++) {
                String columnId = columnArray.getString(j);
                RoleResourceColumn rrc = new RoleResourceColumn();
                rrc.setRoleId(role.getId());
                rrc.setResId(resourceId);
                rrc.setColumnId(columnId);

                rrcList.add(rrc);
            }
            rrcService.save(rrcList);
        }

        logService.info("角色管理", String.format("编辑角色权限：[Id:%s, Name:%s]", role.getId(), role.getName()), getCurrentUser().getUserName(), HandlerRequest.fromWebRequest(getRequest()));
        getEventManager().trigger("onRoleEdit", this, role);
    }

    /**
     * 获取角色信息，立即加载用户
     *
     * @param id
     * @return
     */
    public Role getById_noLazyRes(String id) {
        Role role = getById(id);
        Hibernate.initialize(role.getResources());
        List<RoleResourceButton> rrbList = rrbService.list(Condition.NEW().eq("roleId", role.getId()));
        for (RoleResourceButton rrb : rrbList) {
            //找到菜单
            Resource res = null;
            for (Resource r : role.getResources()) {
                if (r.getId().equals(rrb.getResId())) {
                    res = r;
                    break;
                }
            }
            if (ObjectKit.isNull(res))
                continue;
            Hibernate.initialize(res.getButtons());
            //找到按钮
            ResourceButton btn = null;
            for (ResourceButton b : res.getButtons()) {
                if (b.getId().equals(rrb.getBtnId())) {
                    btn = b;
                    break;
                }
            }
            if (ObjectKit.isNull(btn))
                continue;
            res.getPrivilegeButtons().add(btn);
        }
        return role;
    }

    @Transactional
    public boolean copyRole(String id, String unitId, String name) {
        Role role = super.getById(id);

        //复制角色
        Role newRole = new Role();
        Unit unit = new Unit();
        unit.setId(unitId);
        newRole.setUnit(unit);
        newRole.setName(name);
        newRole.setCreateTime(getCurrentTime());
        newRole.setDescript(role.getDescript());
        newRole.setEnabled(role.isEnabled());
        newRole.setPerm(role.getPerm());

        super.save(newRole);

        //立即提交
        super.getBaseDao().getSession().flush();

        //复制角色权限信息
        List<Map<String, Object>> roleResList = super.nativeListToMap("select role_id, res_id from sys_role_res where role_id = ?", id);
        if(roleResList.size() > 0){
            for (Map<String, Object> map : roleResList){
                super.executeSqlBatch("insert into sys_role_res(role_id, res_id) values(?, ?)", newRole.getId(), map.get("res_id"));
            }
        }

        List<Map<String, Object>> roleResButtonList = super.nativeListToMap("select role_id, res_id, btn_id from sys_role_res_btn where role_id = ?", id);
        if(roleResButtonList.size() > 0){
            for (Map<String, Object> map : roleResButtonList){
                super.executeSqlBatch("insert into sys_role_res_btn(role_Id, res_id, btn_id) values(?, ?)", newRole.getId(), map.get("res_id"), map.get("btn_id"));
            }
        }

        List<Map<String, Object>> roleResColumnList = super.nativeListToMap("select role_id, res_id, column_id from sys_role_res_column where role_id = ?", id);
        if(roleResColumnList.size() > 0){
            for (Map<String, Object> map : roleResColumnList){
                super.executeSqlBatch("insert into sys_role_res_column(role_Id, res_id, column_id) values(?, ?)", newRole.getId(), map.get("res_id"), map.get("column_id"));
            }
        }

        return true;
    }
}
