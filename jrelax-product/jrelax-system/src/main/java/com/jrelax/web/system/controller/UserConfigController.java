package com.jrelax.web.system.controller;

import com.jrelax.config.JRelaxUserConfigHelper;
import com.jrelax.core.web.annotation.ViewPrefix;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.kit.StringKit;
import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.entity.Config;
import com.jrelax.web.system.service.LogService;
import com.jrelax.web.system.service.UserConfigService;
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
 * Created by zengchao on 2018-01-05.
 */
@Controller
@RequestMapping("/system/user/config")
@ViewPrefix("system/user/config")
public class UserConfigController extends BaseController<Config> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserConfigService userConfigService;
    @Resource
    private LogService logService;

    @RequestMapping
    public String index(Model model) {
        userConfigService.flush();
        model.addAttribute("configMap", JRelaxUserConfigHelper.getConfigMap());

        logService.info("sys-user-config", StringKit.format("查询用户参数：[{0}]", getCurrentUser().getUserName()));
        return "index";
    }

    @RequestMapping("/save")
    @ResponseBody
    public JSONObject save(@RequestParam Map<String, String> params) {
        userConfigService.update(params);

        logService.info("sys-user-config", StringKit.format("配置用户参数：[{0}]", getCurrentUser().getUserName()));
        logger.info(StringKit.format("配置用户参数：[{0}]", getCurrentUser().getUserName() + "," + getCurrentUser().getId()));
        return WebResult.success();
    }


}
