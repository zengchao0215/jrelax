package com.jrelax.web.system.service;

import com.jrelax.kit.ObjectKit;
import com.jrelax.kit.StringKit;
import com.jrelax.orm.query.Condition;
import com.jrelax.web.support.BaseService;
import com.jrelax.web.system.entity.DataDict;
import com.jrelax.web.system.entity.DataDictItem;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class DataDictService extends BaseService<DataDict> {
	@Autowired
	private DataDictItemService dataDictItemService;

	public List<DataDict> list_NoLazy(Order asc) {
		List<DataDict> list = this.list(Condition.NEW().asc(asc.getPropertyName()));
		for(DataDict dd : list)
			Hibernate.initialize(dd.getItems());
		return list;
	}
	
	public DataDict getById_NoLazy(Serializable id) {
		DataDict dd = super.getById(id);
		if(ObjectKit.isNotNull(dd))
			Hibernate.initialize(dd.getItems());
		return dd;
	}

	public List<DataDict> list_NoLazy(SimpleExpression eq) {
		List<DataDict> list = this.list(Condition.NEW().eq(eq.getPropertyName(), eq.getValue()));
		for(DataDict dd : list)
			Hibernate.initialize(dd.getItems());
		return list;
	}

	public void saveJson(JSONArray array) {
		if(array.size() > 0){
			for(int i=0; i<array.size();i++){
				JSONObject dict = array.getJSONObject(i);
				
				String name = dict.getString("name");
				String category = dict.getString("category");
				String remarks = dict.getString("remarks");

				//判断是否重复
				if(this.count(Condition.NEW().eq("name", name)) > 0){
					continue;
				}
				if(!StringKit.isBlank(name)){
					DataDict dd = new DataDict();
					dd.setName(name);
					dd.setCategory(category);
					dd.setRemarks(remarks);
					dd.setCreateUser(getCurrentUser().getUserName());
					dd.setCreateTime(getCurrentTime());
					
					super.save(dd);
					
					JSONArray items = dict.getJSONArray("items");
					for(int j=0;j<items.size();j++){
						JSONObject item = items.getJSONObject(j);
						
						String k = item.getString("k");
						String v = item.getString("v");
						int location = item.getInt("location");
						if(!StringKit.isBlank(v)){
							DataDictItem ddItem = new DataDictItem();
							
							ddItem.setDataDict(dd);
							ddItem.setK(k);
							ddItem.setV(v);
							ddItem.setLocation(location);
							ddItem.setCreateUser(getCurrentUser().getUserName());
							ddItem.setCreateTime(getCurrentTime());
							
							dataDictItemService.save(ddItem);
						}
					}
				}
			}
		}
	}
	
}
