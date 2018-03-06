package com.jrelax.web.common.controller;

import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.entity.User;
import com.jrelax.web.system.service.UnitService;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 选择机构
 * Created by zengc on 2016-06-02.
 */
@Controller
@RequestMapping("/common/unit")
public class SelectUnitController extends BaseController<User>{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public final String TPL = "/common/unit/";

    @Autowired
    private UnitService unitService;

    /**
     * 选择机构
     * @param model
     * @return
     */
    @RequestMapping(value="/select", method={RequestMethod.GET, RequestMethod.POST})
    public String select(Model model){
    	return TPL + "select";
    }
    
    @RequestMapping(value="/select/tree", method={RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONArray unitTree(){
        return unitService.tree();
    }

    @RequestMapping(value="/select/tree/{pid}", method={RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONArray unitTree(@PathVariable String pid){
        return unitService.tree(pid);
    }


}
