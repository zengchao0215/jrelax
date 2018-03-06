package com.jrelax.web.system.service;

import com.jrelax.kit.ObjectKit;
import com.jrelax.kit.StringKit;
import com.jrelax.web.support.BaseService;
import com.jrelax.web.system.entity.DataDict;
import com.jrelax.web.system.entity.DataDictItem;
import org.springframework.stereotype.Service;

@Service
public class DataDictItemService extends BaseService<DataDictItem>{

	public void updateItem(String id, String[] key, String[] value, DataDict dd) {
		//删除原配置项
		this.executeBatch("delete from DataDictItem where dataDict.id='"+id+"'");
		if(ObjectKit.isNull(key) && ObjectKit.isNull(value))// key、value全部为null时不执行增加操作
			return ;
		for(int i = 0;i<value.length;i++){
			String k = (ObjectKit.isNull(key)||key.length==0)?"":key[i];
			String v = value[i];
			
			//保证k和v只有一个是有效值
			if(!StringKit.isEmpty(v)){
				DataDictItem ddItem = new DataDictItem();
				
				ddItem.setDataDict(dd);
				ddItem.setK(k);
				ddItem.setV(v);
				ddItem.setLocation(i+1);
				ddItem.setCreateUser(getCurrentUser().getUserName());
				ddItem.setCreateTime(getCurrentTime());
				
				this.save(ddItem);
			}
		}
	}

}
