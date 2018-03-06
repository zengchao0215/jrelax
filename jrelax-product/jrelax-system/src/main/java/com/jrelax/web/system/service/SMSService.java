package com.jrelax.web.system.service;

import com.jrelax.web.support.BaseService;
import com.jrelax.web.system.entity.SMS;
import org.springframework.stereotype.Service;

@Service
public class SMSService extends BaseService<SMS>{


    /**
     * 保存短信到数据库中
     * SMSSenderPlugin插件自动会调用短信中心配置的短信接口进行短信发送
     * @param sms
     */
    @Override
    public void save(SMS sms) {
        sms.setCreateTime(getCurrentTime());
        sms.setCreateUser(getCurrentUser());
        sms.setState(1);
        sms.setWeight(1);
        super.save(sms);
    }
}
