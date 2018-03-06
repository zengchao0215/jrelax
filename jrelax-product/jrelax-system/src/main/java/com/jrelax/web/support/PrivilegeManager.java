package com.jrelax.web.support;

import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.kit.StringKit;
import com.jrelax.orm.query.Condition;
import com.jrelax.core.web.session.SessionAttributeManager;
import com.jrelax.web.system.entity.*;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限验证管理器
 * 包含验证是否是开放链接
 * Created by zengchao on 2017-02-23.
 */
public class PrivilegeManager {
    /**
     * 判断当前用户是否拥有链接访问权限
     *
     * @param user       用户
     * @param requestUrl 请求链接
     * @return 0:有权限 1：无权限 2：资源禁用
     */
    public static PrivilegeResult hasPrivilege(User user, String requestUrl, HandlerMethod handlerMethod) {
        PrivilegeResult result = new PrivilegeResult(false, false, null);
        if (WebApplicationCommon.isOpenRequest(requestUrl, handlerMethod)) {
            result.setHasPrivilege(true);
            return result;
        }
        //系统级别用户拥有所有权限
        for (Unit unit : user.getUnits()) {
            if (unit.isSystem()) {
                result.setHasPrivilege(true);
                return result;
            }
        }
        for (Resource res : user.getResources()) {
            if (!StringKit.isBlank(res.getUrl()) && !res.getUrl().equals("/") && requestUrl.startsWith(res.getUrl())) {
                result.setHasPrivilege(true);
                if (!res.isEnabled()) {
                    result.setDisabled(true);
                    result.setResource(res);
                }
                break;
            }
        }

        return result;
    }

    /**
     * 匹配资源，有缓存
     *
     * @param resources
     * @param requestUrl
     * @return
     */
    public static Resource matchResource(List<Resource> resources, String requestUrl) {
        String prefix = "CR_";
        Resource currentResource = null;//当前机构，用于按钮权限控制
        if (SessionAttributeManager.has(prefix + requestUrl)) {
            currentResource = SessionAttributeManager.get(prefix + requestUrl);
        } else {
            int currentLevenshtein = 1000;//相似度，值越小相似度越高
            for (Resource res : resources) {//一级菜单
                int levenshtein = StringUtils.getLevenshteinDistance(requestUrl, res.getUrl());
                if (levenshtein < currentLevenshtein) {
                    currentLevenshtein = levenshtein;
                    currentResource = res;//设置当前资源
                }
                //如果完全匹配，则直接结束
                if (currentLevenshtein == 0) break;
            }
            if (currentLevenshtein > 3) currentResource = null;
            SessionAttributeManager.put(prefix + requestUrl, currentResource);
        }
        return currentResource;
    }

    /**
     * 获取资源按钮权限，有缓存
     *
     * @param resource
     * @return
     */
    public static Map<String, ResourceButton> getResourceButtonPrivilege(Resource resource) {
        //按钮权限
        Map<String, ResourceButton> btnMap = new HashMap<>();
        if (SessionAttributeManager.has(ApplicationCommon.AUTH_BUTTON + resource.getId())) {
            btnMap = SessionAttributeManager.get(ApplicationCommon.AUTH_BUTTON + resource.getId());
        } else {
            for (ResourceButton button : resource.getPrivilegeButtons()) {
                btnMap.put(button.getCode(), button);
            }
            SessionAttributeManager.put(ApplicationCommon.AUTH_BUTTON + resource.getId(), btnMap);
        }
        return btnMap;
    }

    /**
     * 获取资源字段权限，有缓存
     *
     * @param resource
     * @return
     */
    public static Map<String, ResourceColumn> getResourceColumnPrivilege(Resource resource) {
        Map<String, ResourceColumn> colMap = new HashMap<>();
        if (SessionAttributeManager.has(ApplicationCommon.AUTH_COLUMNS + resource.getId())) {
            colMap = SessionAttributeManager.get(ApplicationCommon.AUTH_COLUMNS + resource.getId());
        } else {
            for (ResourceColumn col : resource.getPrivilegeColumns()) {
                colMap.put(col.getCode(), col);
            }
            SessionAttributeManager.put(ApplicationCommon.AUTH_COLUMNS + resource.getId(), colMap);
        }
        return colMap;
    }

    /**
     * 获取资源权限，纯URL形式
     *
     * @param user 登录用户
     * @return
     */
    public static List<String> getResourcePrivilege(User user) {
        List<String> list = new ArrayList<>();
        if (SessionAttributeManager.has(ApplicationCommon.AUTH_RESOURCE)) {
            list = SessionAttributeManager.get(ApplicationCommon.AUTH_RESOURCE);
        } else {
            for (Resource res : user.getResources()) {
                list.add(res.getUrl());
            }
        }
        return list;
    }

    /**
     * 初始化登录用户的请求必要参数
     *
     * @param user
     * @param request
     */
    public static void initLoginUserParams(User user, HttpServletRequest request) {
        //存储用户的皮肤和布局信息
        String theme = StringKit.isEmpty(user.getPageStyle()) ? ApplicationCommon.SYSTEM_DEFAULT_THEME : user.getPageStyle().trim();
        request.setAttribute(WebApplicationCommon.RequestAttribute.SYSTEM_THEME, theme);
        request.setAttribute(WebApplicationCommon.RequestAttribute.SYSTEM_LAYOUT, user.getLayout() == null ? "" : user.getLayout().trim());
        request.setAttribute(WebApplicationCommon.RequestAttribute.LOGIN_USER_REALNAME, user.getRealName());
        request.setAttribute(WebApplicationCommon.RequestAttribute.LOGIN_USER_HEAD_IMAGE, user.getHeadImage());
        request.setAttribute(WebApplicationCommon.RequestAttribute.LOGIN_USER_QUICK_MENUS, user.getQuickMenus());
        request.setAttribute(WebApplicationCommon.RequestAttribute.LOGIN_USER_USERNAME, user.getUserName());
        request.setAttribute(WebApplicationCommon.RequestAttribute.LOGIN_USER_PASSWORD, user.getPassword());

        //判断是否使用机构名作为内页标题
        if (JRelaxSystemConfigHelper.getBoolean("system.admin.title.unit", false)) {
            request.setAttribute(WebApplicationCommon.RequestAttribute.SYSTEM_ADMIN_TITLE, user.getDefaultUnit().getName());
        }
    }

    /**
     * 初始化登录用户的资源权限
     *
     * @param user
     * @param request
     */
    public static void initLoginUserResource(User user, HttpServletRequest request) {
        //初始化后台管理系统资源按钮权限
        Map<String, Resource> allRes = new HashMap<>();
        List<Resource> firstRes = new ArrayList<>();
        Map<String, List<Resource>> secondRes = new HashMap<>();
        Map<String, List<Resource>> thirdRes = new HashMap<>();
        Map<String, List<Resource>> fourRes = new HashMap<>();
        //放置于缓存
        if (SessionAttributeManager.has(WebApplicationCommon.RequestAttribute.SYSTEM_RESOURCE_LEVEL_1)) {
            allRes = SessionAttributeManager.get(WebApplicationCommon.RequestAttribute.SYSTEM_RESOURCE);
            firstRes = SessionAttributeManager.get(WebApplicationCommon.RequestAttribute.SYSTEM_RESOURCE_LEVEL_1);
            secondRes = SessionAttributeManager.get(WebApplicationCommon.RequestAttribute.SYSTEM_RESOURCE_LEVEL_2);
            thirdRes = SessionAttributeManager.get(WebApplicationCommon.RequestAttribute.SYSTEM_RESOURCE_LEVEL_3);
            fourRes = SessionAttributeManager.get(WebApplicationCommon.RequestAttribute.SYSTEM_RESOURCE_LEVEL_4);
        } else {
            for (Resource res : user.getResources()) {//一级菜单
                if (res.getParentId().equals("-1")) {
                    firstRes.add(res);
                    List<Resource> firstChildren = new ArrayList<Resource>();
                    //获取子菜单
                    for (Resource second : user.getResources()) {
                        //二级菜单
                        if (second.getParentId().equals(res.getId())) {
                            firstChildren.add(second);

                            //三级菜单
                            List<Resource> secondChildren = new ArrayList<Resource>();
                            for (Resource third : user.getResources()) {
                                if (third.getParentId().equals(second.getId())) {
                                    secondChildren.add(third);

                                    //第四级菜单 start
                                    List<Resource> thirdChildren = new ArrayList<>();
                                    for (Resource four : user.getResources()) {
                                        if (four.getParentId().equals(third.getId())) {
                                            thirdChildren.add(four);
                                        }
                                    }

                                    if (thirdChildren.size() > 0)
                                        fourRes.put(third.getId(), thirdChildren);
                                    //第四级菜单 end
                                }
                            }
                            if (secondChildren.size() > 0)
                                thirdRes.put(second.getId(), secondChildren);
                        }
                    }
                    if (firstChildren.size() > 0)
                        secondRes.put(res.getId(), firstChildren);
                }

                //设置外站链接自动跳转
                if (res.getUrl() != null && (res.getUrl().startsWith("http://") || res.getUrl().startsWith("https://"))) {
                    res.setUrl(WebApplicationCommon.GO_URL + "?url=" + res.getUrl());
                }

                allRes.put(res.getId(), res);
            }
            SessionAttributeManager.put(WebApplicationCommon.RequestAttribute.SYSTEM_RESOURCE, allRes);
            SessionAttributeManager.put(WebApplicationCommon.RequestAttribute.SYSTEM_RESOURCE_LEVEL_1, firstRes);
            SessionAttributeManager.put(WebApplicationCommon.RequestAttribute.SYSTEM_RESOURCE_LEVEL_2, secondRes);
            SessionAttributeManager.put(WebApplicationCommon.RequestAttribute.SYSTEM_RESOURCE_LEVEL_3, thirdRes);
            SessionAttributeManager.put(WebApplicationCommon.RequestAttribute.SYSTEM_RESOURCE_LEVEL_4, fourRes);
        }
        request.setAttribute(WebApplicationCommon.RequestAttribute.SYSTEM_RESOURCE, allRes);
        request.setAttribute(WebApplicationCommon.RequestAttribute.SYSTEM_RESOURCE_LEVEL_1, firstRes);
        request.setAttribute(WebApplicationCommon.RequestAttribute.SYSTEM_RESOURCE_LEVEL_2, secondRes);
        request.setAttribute(WebApplicationCommon.RequestAttribute.SYSTEM_RESOURCE_LEVEL_3, thirdRes);
        request.setAttribute(WebApplicationCommon.RequestAttribute.SYSTEM_RESOURCE_LEVEL_4, fourRes);
    }

    /**
     * 数据权限
     *
     * @param user
     * @param condition
     */
    public static Condition dataPerm(User user, Condition condition) {
        int perm = getPerm(user);
        switch (perm) {
            case 1://所有数据
                break;
            case 2://当前机构及下属机构
                condition.like("unit", user.getDefaultUnit().getCode(), MatchMode.START);
                break;
            case 3://仅当前机构
                condition.eq("unit", user.getDefaultUnit().getCode());
                break;
            case 4://仅本人数据
                condition.eq("user", user.getId());
                break;
        }
        return condition;
    }

    /**
     * 数据权限
     *
     * @param user
     * @param sql
     */
    public static void dataPerm(User user, StringBuffer sql) {
        int perm = getPerm(user);
        switch (perm) {
            case 1://所有数据
                break;
            case 2://当前机构及下属机构
                sql.append(" and unit like '").append(user.getDefaultUnit().getCode()).append("%'");
                break;
            case 3://仅当前机构
                sql.append(" and unit like '").append(user.getDefaultUnit().getCode()).append("'");
                break;
            case 4://仅本人数据
                sql.append(" and user = '").append(user.getId()).append("'");
                break;
        }
    }

    /**
     * 计算数据权限，
     * 在所有角色中，以最小值为准
     *
     * @param user
     * @return
     */
    private static int getPerm(User user) {
        int perm = 99;
        for (Role role : user.getRoles()) {
            if (role.getPerm() < perm) {
                perm = role.getPerm();
            }
        }
        return perm;
    }


}
