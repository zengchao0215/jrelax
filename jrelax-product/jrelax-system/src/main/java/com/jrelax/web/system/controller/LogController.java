package com.jrelax.web.system.controller;

import com.jrelax.core.web.support.WebResult;
import com.jrelax.core.web.transform.DataGridTransforms;
import com.jrelax.kit.ObjectKit;
import com.jrelax.kit.StringKit;
import com.jrelax.orm.query.PageBean;
import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.entity.Log;
import com.jrelax.web.system.service.LogService;
import net.sf.json.JSONObject;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "/system/log")
public class LogController extends BaseController<Log> {

    @Autowired
    private LogService logService;

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        model.addAttribute("defaultTime", getCurrentTime("yyyy-MM-dd") + " 00:00:00 - " + getCurrentTime("yyyy-MM-dd") + " 23:59:59");
        return "system/log/index";
    }

    @RequestMapping("/data")
    @ResponseBody
    public Map<String, Object> data(PageBean pageBean, Log log, String timeRange) {
        if (ObjectKit.isNotNull(log.getLevel()))
            pageBean.addCriterion(Restrictions.eq("level", log.getLevel()));
        if (StringKit.isNotEmpty(log.getModule()))
            pageBean.addCriterion(Restrictions.like("module", log.getModule(), MatchMode.ANYWHERE));
        if (StringKit.isNotEmpty(log.getContent()))
            pageBean.addCriterion(Restrictions.like("content", log.getContent(), MatchMode.ANYWHERE));
        if (StringKit.isNotEmpty(timeRange)) {
            String[] times = timeRange.split(" - ");
            pageBean.addCriterion(Restrictions.gt("time", times[0].trim()));
            pageBean.addCriterion(Restrictions.lt("time", times[1].trim()));
        }

        return DataGridTransforms.JQGRID.transform(logService.list(pageBean), pageBean);
    }

    @RequestMapping("/data2")
    @ResponseBody
    public Map<String, Object> data2(PageBean pageBean, String name, String value) {
        return DataGridTransforms.EASY_UI.transform(logService.list(pageBean), pageBean);
    }

    @RequestMapping("/charts")
    @ResponseBody
    public JSONObject charts(){
        JSONObject result = WebResult.success();
        PageBean pageBean = new PageBean();
        pageBean.setRows(7);
        //每天日志数
        String sql1 = "SELECT COUNT(*) AS totals, LEFT(log_time, 10) AS logTime FROM sys_log GROUP BY LEFT(log_time, 10) ORDER BY logTime desc";
        //每个模块得访问数
        String sql2 = "SELECT COUNT(*) AS totals, module FROM sys_log GROUP BY module";
        //平台分布
        String sql3 = "SELECT COUNT(*) AS totals, platform FROM sys_log WHERE platform <> '' GROUP BY platform";

        result.element("counter", logService.nativeListToMap(pageBean, sql1));
        result.element("module", logService.nativeListToMap(sql2));
        result.element("platform", logService.nativeListToMap(sql3));

        return result;
    }
}
