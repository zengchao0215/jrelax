package com.jrelax.web.system.controller.senior;

import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.core.web.transform.DataGridTransforms;

import com.jrelax.kit.ObjectKit;

import com.jrelax.orm.query.PageBean;

import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.entity.Route;
import com.jrelax.web.system.service.RouteService;

import net.sf.json.JSONObject;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


/**
 * @author zengchao
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping(value = "/system/senior/route")
public class RouteController extends BaseController<Route> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String TPL = "/system/senior/route/";
    @Autowired
    private RouteService routeService;

    /**
     * 首页
     *
     * @param model
     * @param pageBean
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model, PageBean pageBean) {

        return TPL + "index";
    }

    /**
     * 数据
     *
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/data", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> data(PageBean pageBean) {
        List<Route> list = routeService.list(pageBean);

        return DataGridTransforms.JQGRID.transform(list, pageBean);
    }

    /**
     * 转向新增页面
     *
     * @param model
     * @param pid
     */
    @RequestMapping(value = "/add", method = {RequestMethod.GET})
    public String add(Model model, String pid) {
        return TPL + "add";
    }

    /**
     * 执行新增
     *
     * @param route
     * @return
     */
    @RequestMapping(value = "/add/do", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject doAdd(Route route) {
        try {
            route.setCreateUser(getCurrentUser().getRealName());
            route.setCreateTime(getCurrentTime());
            routeService.save(route);

            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            return WebResult.error(e.toString());
        }
    }

    /**
     * 转向编辑页面
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String edit(Model model, @PathVariable String id) {
        Route route = routeService.getById(id);

        if (!ObjectKit.isNotNull(route)) {
            return WebApplicationCommon.ERROR.UNAUTHORIZED_ACCESS;
        }

        model.addAttribute("route", route);

        return TPL + "edit";
    }

    /**
     * 执行编辑
     *
     * @param route
     * @return
     */
    @RequestMapping(value = "/edit/do", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject doEdit(Route route) {
        try {
            //从数据库中获取最新数据
            Route eqRoute = routeService.getById(route.getId());

            if (ObjectKit.isNull(eqRoute)) {
                return WebResult.error("非法操作");
            }

            eqRoute.setSourceUrl(route.getSourceUrl());
            eqRoute.setTargetUrl(route.getTargetUrl());
            eqRoute.setRedirect(route.isRedirect());
            eqRoute.setInternal(route.isInternal());
            eqRoute.setEnabled(route.isEnabled());
            routeService.merge(eqRoute);

            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            return WebResult.error(e.toString());
        }
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject delete(@PathVariable String id) {
        try {
            //删除相关的所有数据
            routeService.delete(id);

            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            return WebResult.error(e.toString());
        }
    }

    /**
     * 查看详情
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String detail(Model model, @PathVariable String id) {
        Route route = routeService.getById(id);

        if (!ObjectKit.isNotNull(route)) {
            return WebApplicationCommon.ERROR.UNAUTHORIZED_ACCESS;
        }

        model.addAttribute("route", route);

        return TPL + "detail";
    }

    /**
     * 启用规则
     * @param id
     * @return
     */
    @RequestMapping(value = "/enable/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject enable(@PathVariable String id){
        routeService.executeBatch("update Route set enabled=true where id=?", id);
        return WebResult.success();
    }

    /**
     * 禁用规则
     * @param id
     * @return
     */
    @RequestMapping(value = "/disable/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject disable(@PathVariable String id){
        routeService.executeBatch("update Route set enabled=false where id=?", id);
        return WebResult.success();
    }

    /**
     * 同步缓存
     * @return
     */
    @RequestMapping(value = "/sync", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject sync(){
        routeService.sync();
        return WebResult.success();
    }
}
