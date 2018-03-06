package com.jrelax.web.system.service;

import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.web.support.BaseService;
import com.jrelax.web.system.entity.Route;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author zengchao
 * @version 1.0
 * @since 1.0
 */
@Service
public class RouteService extends BaseService<Route> {
    /**
     * 同步缓存
     */
    public void sync() {
        List<Route> list = super.listToEntity("select sourceUrl, targetUrl, redirect, internal from Route where enabled=true");
        getApplicationCache().put(ApplicationCommon.CACHE_URL_ROUTE, list);
        //清空已匹配的规则
        getApplicationCache().remove(ApplicationCommon.CACHE_URL_ROUTE_MATCHED);
    }

    /**
     * 获取路由
     * @param url 请求地址
     * @return
     */
    public Route getRoute(String url){
        Route route = null;//匹配规则
        //已匹配的规则缓存
        Map<String, Route> routeMap = getApplicationCache().get(ApplicationCommon.CACHE_URL_ROUTE_MATCHED);
        if(routeMap != null && routeMap.containsKey(url)) return routeMap.get(url);
        //获取规则列表
        List<Route> list = getApplicationCache().get(ApplicationCommon.CACHE_URL_ROUTE);
        for(Route r : list){
            if(matchOfSingle(r.getSourceUrl(), url) || matchOfLike(r.getSourceUrl(), url) || matchOfRegex(r.getSourceUrl(), url)){
                route = r;
                break;
            }
        }
        if (route != null) {
            if (routeMap == null) {
                routeMap = new HashMap<>();
                getApplicationCache().put(ApplicationCommon.CACHE_URL_ROUTE_MATCHED, routeMap);
            }
            routeMap.put(url, route);
        }
        return route;
    }

    /**
     * 单个匹配
     * @param rule
     * @param url
     * @return
     */
    private boolean matchOfSingle(String rule, String url){
        return url.equals(rule);
    }

    /**
     * 模糊匹配
     * @param rule
     * @param url
     * @return
     */
    private boolean matchOfLike(String rule, String url){
        rule = rule.replaceAll("\\*", ".*");
        return false;
    }

    /**
     * 正则匹配
     * @param rule
     * @param url
     * @return
     */
    private boolean matchOfRegex(String rule, String url){
        boolean isMatch = false;
        try {
            isMatch = url.matches(rule);
        } catch (Exception e) {
            isMatch = false;
        }
        return isMatch;
    }
}
