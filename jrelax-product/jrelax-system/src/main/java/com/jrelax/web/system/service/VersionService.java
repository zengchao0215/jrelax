package com.jrelax.web.system.service;

import com.jrelax.web.support.BaseService;
import com.jrelax.web.system.entity.Version;
import org.springframework.stereotype.Service;

@Service
public class VersionService extends BaseService<Version>{
	
	/**
	 * 获取系统版本
	 * @return
	 */
	public Version getVersion(){
		return get("from Version order by version desc");
	}
}
