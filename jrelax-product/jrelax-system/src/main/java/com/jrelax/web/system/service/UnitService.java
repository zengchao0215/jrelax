package com.jrelax.web.system.service;

import com.jrelax.core.web.transform.TreeTransforms;
import com.jrelax.kit.ObjectKit;
import com.jrelax.orm.query.Condition;
import com.jrelax.orm.query.HQLBuilder;
import com.jrelax.web.support.BaseService;
import com.jrelax.web.system.entity.Role;
import com.jrelax.web.system.entity.Unit;
import com.jrelax.web.system.entity.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class UnitService extends BaseService<Unit> {
    @Resource
    private RoleService roleService;

    /**
     * 删除机构及机构相关信息
     *
     * @param id 机构id
     */
    public void deleteAndRelated(String id) {
        Unit unit = this.getById(id);
        String code = unit.getCode() + "%";
        //删除机构相关数据
        this.executeSqlBatch("delete from sys_unit_res where unit_id in (select id from sys_unit where code like ?)", code);//机构分配的菜单
        this.executeSqlBatch("delete from sys_user_unit where unit_id in (select id from sys_unit where code like ?)", code);//机构下的用户

        //机构对应角色相关信息
        this.executeSqlBatch("delete from sys_role_res where role_id in (select id from sys_role where unit_id in (select id from sys_unit where code like ?))", code);
        this.executeSqlBatch("delete from sys_role_res_btn where role_id in (select id from sys_role where unit_id in (select id from sys_unit where code like ?))", code);
        this.executeSqlBatch("delete from sys_role_res_column where role_id in (select id from sys_role where unit_id in (select id from sys_unit where code like ?))", code);

        //机构对应用户组信息
        this.executeSqlBatch("delete from sys_group_role where group_id in (select id from sys_group where unit_id in (select id from sys_unit where code like ?))", code);
        this.executeSqlBatch("delete from sys_group_user where group_id in (select id from sys_group where unit_id in (select id from sys_unit where code like ?))", code);

        this.executeSqlBatch("delete from sys_role where unit_id in (select id from sys_unit where code like ?)", code);
        this.executeSqlBatch("delete from sys_group where unit_id in (select id from sys_unit where code like ?)", code);
        this.executeBatch("delete from Unit where code like ?", code);

        //判断是否拥有子节点
        int count = this.count(Condition.NEW().eq("parentId", unit.getParentId()));
        if (count == 0) {
            this.executeBatch("update Unit set hasChildren=false where id = ?", unit.getParentId());
        }

        getEventManager().trigger("onUnitRemoved", this, id);
    }

    /**
     * 获取当前机构下的所有用户
     *
     * @return
     */
    public List<User> listUser() {
        List<Unit> units = this.list(Condition.NEW().in("id", getCurrentUnitIds()));
        List<User> userList = new ArrayList<User>();
        for (Unit unit : units) {
            Hibernate.initialize(unit.getUsers());
            List<User> users = unit.getUsers();
            for (User user : users) {
                if (!userList.contains(user))
                    userList.add(user);
            }
        }
        return userList;
    }

    /**
     * 获取当前机构下所有角色
     *
     * @return
     */
    public List<Role> listRole() {
        HQLBuilder builder = new HQLBuilder("select id,name from Role");
        builder.in("unitId", getCurrentUnitIds());
        return roleService.listToEntity(builder.getHQL(), builder.getParams());
    }

    /**
     * 获取机构树
     *
     * @return
     */
    public JSONArray tree() {
        HQLBuilder builder = new HQLBuilder("select id, name, hasChildren from Unit");
        if (getCurrentUser().isSystemAdmin())
            builder.eq("parentId", "-1");
        else
            builder.in("id", getCurrentUnitIds());
        builder.asc("code");
        List<Unit> list = super.listToEntity(builder.getHQL(), builder.getParams());
        //只展开第一个节点
        String openUnitId = "";
        for (Unit unit : list) {
            if (unit.hasChildren()) {
                openUnitId = unit.getId();
                break;
            }
        }
        final String openNodeId = openUnitId;
        return TreeTransforms.JSTree.transform2(list, (unit, treeNode) -> {
            treeNode.setLeaf(!unit.hasChildren());
            treeNode.setText(unit.getName());
            treeNode.setId(unit.getId());
            treeNode.setIcon("fa fa-sitemap");

            if (unit.hasChildren() && openNodeId.equals(unit.getId()))
                treeNode.setOpened(true);
        });
    }

    /**
     * 获取机构树
     *
     * @param pid
     * @return
     */
    public JSONArray tree(@PathVariable String pid) {
        List<Unit> list = super.listToEntity("select id, name, hasChildren from Unit where parentId=? order by code asc", pid);
        JSONArray data = new JSONArray();
        for (Unit unit : list) {
            JSONObject node = new JSONObject();

            node.element("id", unit.getId());
            node.element("text", unit.getName());
            node.element("children", unit.hasChildren());
            node.element("icon", "fa fa-sitemap");

            data.add(node);
        }
        return data;
    }

    /**
     * 生成编码
     *
     * @param parentId 上级机构ID
     */
    public String generateCode(String parentId) {
        String code = null;
        // 生成unit的code
        Unit lastUnit = super.get("select code from Unit where parentId=? order by code desc", parentId);
        if (ObjectKit.isNull(lastUnit)) {
            lastUnit = new Unit();
            lastUnit.setCode("000");
        }
        if ("-1".equals(parentId)) {// 顶级机构
            int count = Integer.parseInt(lastUnit.getCode());
            count = count + 1;
            DecimalFormat df = new DecimalFormat("000");
            code = df.format(count);
        } else {// 非顶级机构
            code = lastUnit.getCode();
            int count = Integer.parseInt(code.substring(code.length() - 3));
            Unit parentUnit = super.get("select code from Unit where id=?", parentId);
            count = count + 1;
            DecimalFormat df = new DecimalFormat("000");
            code = parentUnit.getCode() + df.format(count);
        }

        return code;
    }
}
