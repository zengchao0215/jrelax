package com.jrelax.web.open.controller;

import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.service.ResourceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统图标
 *
 * @author zengc
 */
@Controller
@RequestMapping("/open/icon")
public class IconController extends BaseController<Object> {
    @Resource
    private ResourceService resService;
    public final String TPL = "/open/icon/";


    @RequestMapping(method = {RequestMethod.GET})
    public String index(Model model) {
        List[] icons = resService.getIconList();

        model.addAttribute("fa", icons[0]);
        model.addAttribute("ti", icons[1]);
        model.addAttribute("gly", icons[2]);
        model.addAttribute("sli", icons[3]);
        return TPL + "index";
    }
}
