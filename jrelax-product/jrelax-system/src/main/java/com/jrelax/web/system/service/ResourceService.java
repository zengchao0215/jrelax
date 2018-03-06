package com.jrelax.web.system.service;

import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.orm.query.Condition;
import com.jrelax.web.support.BaseService;
import com.jrelax.web.system.entity.Resource;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ResourceService extends BaseService<Resource> {
    /**
     * 保存并更新父节点的是否有子节点状态
     *
     * @param res
     */
    public void saveAndUpdateParent(Resource res) {
        //计算当前资源的位置
        int count = this.count(Condition.NEW().eq("parentId", res.getParentId()));
        //设置code
        String code = String.format("%03d", count + 1);
        if (res.getParentId().equals("-1")) {
            res.setCode(code);
        } else {
            Resource pRes = this.getById(res.getParentId());
            res.setCode(pRes.getCode() + code);
        }
        res.setLocation(count + 1);
        this.save(res);
        if (!res.getParentId().equals("-1")) {
            this.executeBatch("update Resource set hasChildren=true where id='" + res.getParentId() + "'");
        }
        getEventManager().trigger("onResourceAdd", this, res);
    }

    public void deleteAndRelated(String id) {
        //获取当前菜单的父级菜单
        Resource pRes = this.get("select parentId, code from Resource where id=?", id);
        String code = pRes.getCode();
        //删除关系
        this.executeSqlBatch("delete from sys_role_res_btn where res_id in (select id from sys_res where code like '" + code + "%')");
        this.executeSqlBatch("delete from sys_role_res where res_id in (select id from sys_res where code like '" + code + "%')");
        this.executeSqlBatch("delete from sys_unit_res where res_id in (select id from sys_res where code like '" + code + "%')");
        //删除此菜单下的一级菜单
        this.executeBatch("delete from Resource where code like ?", code + "%");
        //判断
        int count = this.count(Condition.NEW().eq("parentId", pRes.getParentId()));
        if (count == 0) {
            this.executeBatch("update Resource set hasChildren=false where id = ?", pRes.getParentId());
        }
        getEventManager().trigger("onResourceRemoved", this, id);
    }

    /**
     * 执行排序
     *
     * @param data
     */
    public void executeSort(String data) {
        _executeSort(JSONArray.fromObject(data), "-1", "");
        getEventManager().trigger("onResourceSorted", this, null);
    }

    /**
     * 菜单排序，支持无线级
     *
     * @param resList    菜单集合
     * @param parentId   上级菜单ID
     * @param codePrefix 菜单编号前缀
     */
    private void _executeSort(JSONArray resList, String parentId, String codePrefix) {
        if (resList.size() > 0) {
            for (int i = 0; i < resList.size(); i++) {
                JSONObject res = resList.getJSONObject(i);
                String id = res.getString("id");
                String code = codePrefix + String.format("%03d", i + 1);
                if (res.has("children")) {
                    this.executeBatch("update Resource set hasChildren=true, code=?, location=?, parentId=? where id=?", code, i + 1, parentId, id);
                    _executeSort(res.getJSONArray("children"), id, code);
                }else{
                    this.executeBatch("update Resource set hasChildren=false, code=?, location=?, parentId=? where id=?", code, i + 1, parentId, id);
                }
            }
        }
    }

    /**
     * 获取图标列表
     * @return
     */
    public List[] getIconList(){
        try {
            List<String> fa = new ArrayList<>();
            List<String> ti = new ArrayList<>();
            List<String> gly = new ArrayList<>();
            List<String> sli = new ArrayList<>();
            ClassPathResource faResource = new ClassPathResource("/resources/framework/css/icon/icon-fa.css");
            BufferedReader br = new BufferedReader(new InputStreamReader(faResource.getInputStream()));
            while(br.ready()){
                fa.add(br.readLine());
            }
            br.close();

            ClassPathResource tiResource = new ClassPathResource("/resources/framework/css/icon/icon-ti.css");
            br = new BufferedReader(new InputStreamReader(tiResource.getInputStream()));
            while(br.ready()){
                ti.add(br.readLine());
            }
            br.close();

            ClassPathResource glyResource = new ClassPathResource("/resources/framework/css/icon/icon-gly.css");
            br = new BufferedReader(new InputStreamReader(glyResource.getInputStream()));
            while(br.ready()){
                gly.add(br.readLine());
            }
            br.close();

            ClassPathResource sliResource = new ClassPathResource("/resources/framework/css/icon/icon-sli.css");
            br = new BufferedReader(new InputStreamReader(sliResource.getInputStream()));
            while(br.ready()){
                sli.add(br.readLine());
            }
            br.close();

            Collections.sort(fa);
            Collections.sort(ti);
            Collections.sort(gly);
            Collections.sort(sli);

            return new List[]{fa, ti, gly, sli};
        }catch (IOException e){
            e.printStackTrace();
        }

        return new List[]{};
    }
}
