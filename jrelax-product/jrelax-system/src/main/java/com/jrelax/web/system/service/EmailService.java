package com.jrelax.web.system.service;

import com.jrelax.kit.ObjectKit;
import com.jrelax.web.support.BaseService;
import com.jrelax.web.system.entity.Email;
import org.springframework.stereotype.Service;

@Service
public class EmailService extends BaseService<Email>{
	/**
	 * 保存到待发邮箱中
	 */
	@Override
	public void save(Email email) {
		if(ObjectKit.isNull(email.getCreateUser()))
			email.setCreateUser(getCurrentUser());
		if(ObjectKit.isNull(email.getCreateTime()))
			email.setCreateTime(getCurrentTime());
		super.save(email);
	}
}
