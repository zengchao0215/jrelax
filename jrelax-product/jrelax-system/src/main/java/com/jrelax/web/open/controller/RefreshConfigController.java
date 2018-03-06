package com.jrelax.web.open.controller;

import com.jrelax.config.JRelaxCoreConfigHelper;
import com.jrelax.config.JRelaxJdbcConfigHelper;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.service.ConfigService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/open/refresh")
public class RefreshConfigController extends BaseController {
    @Resource
    private ConfigService configService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/all")
    @ResponseBody
    public JSONObject all() {
        try {
            //刷新系统配置
            JRelaxCoreConfigHelper.reload();
            //刷新数据源配置
            JRelaxJdbcConfigHelper.reload();
            //刷新系统参数配置
            configService.flush();
            //刷新数据字典
            getDataDict().sync();

            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e.toString());
        }
    }
}
