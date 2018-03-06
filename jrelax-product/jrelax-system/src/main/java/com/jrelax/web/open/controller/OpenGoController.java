package com.jrelax.web.open.controller;

import com.jrelax.core.web.support.WebApplicationCommon;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 跳转到外站
 * Created by zengchao on 2017/7/4.
 */
@Controller
@RequestMapping(WebApplicationCommon.GO_URL)
public class OpenGoController {

    @RequestMapping
    public String index(Model model, String url){
        model.addAttribute("url", url);
        return WebApplicationCommon.GO_URL;
    }
}
