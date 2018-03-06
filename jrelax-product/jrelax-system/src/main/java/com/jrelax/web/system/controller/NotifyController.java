package com.jrelax.web.system.controller;

import com.jrelax.core.web.annotation.Open;
import com.jrelax.core.web.annotation.OpenScope;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.core.web.transform.DataGridTransforms;
import com.jrelax.orm.query.Condition;
import com.jrelax.orm.query.PageBean;
import com.jrelax.web.common.entity.notify.NotifyMessage;
import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.service.NotifyService;
import com.jrelax.web.system.service.UserNotifyService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/system/notify")
@Open(scope = OpenScope.SESSION)
public class NotifyController extends BaseController<Object> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String TPL = "system/notify/";
    @Autowired
    private UserNotifyService userNotifyService;
    @Autowired
    private NotifyService notifyService;

    /**
     * 获取通知（未读/已读）
     *
     * @param model
     * @param pageBean
     * @return
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model, PageBean pageBean) {
        model.addAttribute("unreadCount", userNotifyService.count(Condition.NEW().eq("user", getCurrentUser()).eq("recread", false)));
        return TPL + "index";
    }

    /**
     * 数据
     *
     * @param pageBean
     * @param type     1：新通知 2：历史通知
     * @return
     */
    @RequestMapping(value = "/data", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> data(PageBean pageBean, int type) {
        List<NotifyMessage> list = null;
        if(type == 1)
            list = userNotifyService.getUnReadNotifyMessage(pageBean);
        else if (type == 2)
            list = userNotifyService.getReadNotifyMessage(pageBean);
        return DataGridTransforms.JQGRID.transform(list, pageBean);
    }

    /**
     * 获取未读通知数量
     */
    @RequestMapping(value = "/count", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String notifyCount() {
        String html = "";
        int count = userNotifyService.count(Condition.NEW().eq("user", getCurrentUser()).eq("recread", false));
        if (count > 0) {
            html += "document.write(\"<div class='badge badge-top bg-danger animated flash'><span>" + count + "</span></div>\");";
        }
        return html;
    }

    /**
     * 标题用未读通知内容
     *
     * @return
     */
    @RequestMapping(value = "/header", method = {RequestMethod.GET, RequestMethod.POST})
    public String header(Model model) {
        List<NotifyMessage> list = userNotifyService.getUnReadNotifyMessage();
        model.addAttribute("list", list);
        logger.debug(getCurrentUser().getUserName() + "获取通知!");
        return TPL + "header";
    }

    /**
     * 阅读通知
     */
    @RequestMapping(value = "/read", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject read(String id) {
        try {
            userNotifyService.executeBatch("update UserNotify set recread=true, readTime=? where id=?", getCurrentTime(), id);
            logger.debug(getCurrentUser().getUserName() + "阅读了通知:" + id);
            return WebResult.success();
        } catch (Exception e) {
            return WebResult.error(e);
        }
    }
}
