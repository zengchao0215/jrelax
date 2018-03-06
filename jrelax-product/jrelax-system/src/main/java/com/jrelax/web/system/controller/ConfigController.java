package com.jrelax.web.system.controller;

import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.core.web.annotation.ViewPrefix;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.kit.StringKit;
import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.entity.Config;
import com.jrelax.web.system.service.ConfigService;
import com.jrelax.web.system.service.LogService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 系统配置
 * Created by zengchao on 2016-11-11.
 */
@Controller
@RequestMapping("/system/config")
@ViewPrefix("system/config")
public class ConfigController extends BaseController<Config>{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ConfigService configService;
    @Resource
    private LogService logService;

    @RequestMapping
    public String index(Model model){
        configService.flush();
        model.addAttribute("configMap", JRelaxSystemConfigHelper.getConfigMap());

        logService.info("sys-config", "查询系统参数");
        return "index";
    }

    @RequestMapping("/save")
    @ResponseBody
    public JSONObject save(@RequestParam  Map<String, String> params){
        configService.update(params);

        logService.info("sys-config", "配置系统参数");
        return WebResult.success();
    }


}
