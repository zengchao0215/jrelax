package com.jrelax.web.system.service.data;

import com.jrelax.web.system.service.DataBaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 数据库备份
 * Created by zengchao on 2017-03-17.
 */
@Service
public class BackupService {
    @Resource
    private DataBaseService dataBaseService;

    /**
     * 判断数据库类型是否被支持
     * @param type 数据库类型
     * @return
     */
    public boolean isSupport(String type){
        return false;
    }
}
