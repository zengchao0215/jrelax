package com.jrelax.web.support;

import com.jrelax.cache.ICacheProvider;
import com.jrelax.cache.IDataDict;
import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.core.web.model.FrontUser;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.event.ApplicationEventManager;
import com.jrelax.kit.HttpKit;
import com.jrelax.kit.ObjectKit;
import com.jrelax.web.system.entity.Role;
import com.jrelax.web.system.entity.Unit;
import com.jrelax.web.system.entity.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * web包中所有类的父类
 *
 * @author zengchao
 */
public class BaseObject {
    /**
     * 当前用户是否是超级管理员
     *
     * @return
     */
    protected boolean isSuperadmin() {
        boolean isSuperadmin = false;
        User currentUser = getCurrentUser();
        if (ObjectKit.isNotNull(currentUser)) {
            isSuperadmin = currentUser.getUserName().equals(ApplicationCommon.SYSTEM_ADMIN);
        }
        return isSuperadmin;
    }

    /**
     * 当前用户是否是系统管理员
     * 权限低于超级管理员
     *
     * @return
     */
    @Deprecated
    protected boolean isSystemadmin() {
        boolean isSystemadmin = false;
        User currentUser = getCurrentUser();
        if (ObjectKit.isNotNull(currentUser)) {
            if (isSuperadmin()) {//如果是超级管理员，也就拥有了系统管理员的角色
                isSystemadmin = true;
            } else {
                //判断角色列表中是否拥有系统管理员角色
                for (Role role : currentUser.getRoles()) {
                    if (role.getName().equals(getSystemConfig("SystemRole"))) {
                        isSystemadmin = true;
                        break;
                    }
                }
            }
        }
        return isSystemadmin;
    }

    /**
     * 判断当前用户所在单位是否是系统单位
     * 系统单位拥有全部权限
     *
     * @return
     */
    protected boolean isSystemUnit() {
        boolean isSystemUnit = false;
        User currentUser = getCurrentUser();
        if (ObjectKit.isNotNull(currentUser)) {
            List<Unit> units = currentUser.getUnits();
            if (ObjectKit.isNotNull(units)) {
                for (Unit unit : units) {
                    if (unit.isSystem()) {
                        isSystemUnit = true;
                        break;
                    }
                }
            }
        }
        return isSystemUnit;
    }

    /**
     * 获取数据字典
     *
     * @return
     */
    protected IDataDict getDataDict() {
        return ApplicationCommon.getDataDict();
    }

    /**
     * 获取系统缓存提供者
     *
     * @return
     */
    protected ICacheProvider getApplicationCache() {
        return ApplicationCommon.getCacheProvider();
    }

    /**
     * 获取系统配置信息
     *
     * @param key
     * @return
     */
    protected String getSystemConfig(String key) {
        return JRelaxSystemConfigHelper.get(key);
    }

    /**
     * 获取系统配置信息
     *
     * @param key
     * @param defaultValue
     * @return
     */
    protected String getSystemConfig(String key, String defaultValue) {
        return JRelaxSystemConfigHelper.get(key, defaultValue);
    }

    /**
     * 获取当前登录用户
     * 不存在返回Null
     *
     * @return
     */
    protected User getCurrentUser() {
        HttpSession session = WebApplicationCommon.getSession();
        return session == null ? null : (User) WebApplicationCommon.getSessionAdminUser(session);
    }

    /**
     * 获取当前登录前端用户
     *
     * @return
     */
    protected FrontUser getCurrentFrontUser() {
        HttpSession session = WebApplicationCommon.getSession();
        return session == null ? null : WebApplicationCommon.getSessionFrontUser(session);
    }

    /**
     * 获取当前用户的机构ID集合
     *
     * @return ID集合
     */
    protected List<String> getCurrentUnitIds() {
        List<String> lstUnitIds = new ArrayList<String>();
        List<Unit> units = getCurrentUser().getUnits();
        for (Unit unit : units) {
            lstUnitIds.add(unit.getId());
        }
        return lstUnitIds;
    }

    /**
     * 获取当前用户的机构名称集合
     *
     * @return
     */
    protected List<String> getCurrentUnitNames() {
        List<String> names = new ArrayList<>();
        List<Unit> units = getCurrentUser().getUnits();
        for (Unit unit : units) {
            names.add(unit.getName());
        }
        return names;
    }

    /**
     * 获取当前登录用户的角色ID集合
     *
     * @return
     */
    protected List<String> getCurrentRoleIds() {
        List<String> ids = new ArrayList<>();
        List<Role> roleList = getCurrentUser().getRoles();
        for (Role role : roleList) {
            ids.add(role.getId());
        }
        return ids;
    }

    /**
     * 获取当前用户的角色名称集合
     *
     * @return
     */
    protected List<String> getCurrentRoleNames() {
        List<String> names = new ArrayList<>();
        List<Role> roleList = getCurrentUser().getRoles();
        for (Role role : roleList) {
            names.add(role.getName());
        }
        return names;
    }

    /**
     * 获取Session
     *
     * @return
     */
    public HttpSession getSession() {
        return WebApplicationCommon.getSession();
    }

    /**
     * 获取Request
     *
     * @return
     */
    public HttpServletRequest getRequest() {
        return WebApplicationCommon.getRequest();
    }

    /**
     * 获取Response
     *
     * @return
     */
    public HttpServletResponse getResponse() {
        return WebApplicationCommon.getResponse();
    }

    /**
     * 获取当前时间 格式为 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    protected String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * 获取当前时间 格式为 pattern
     *
     * @param pattern 时间格式
     * @return
     */
    protected String getCurrentTime(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date());
    }

    /**
     * 获取访问着的IP地址
     *
     * @return
     */
    protected String getRequestAddr() {
        return HttpKit.getRequestAddr(getRequest());
    }

    /**
     * 判断集合中是否确定的属性及属性值
     *
     * @param array
     * @param propertyName
     * @param propertyValue
     * @return
     */
    @SuppressWarnings("unchecked")
    protected boolean hasPropertyValueInArray(Object array, Object propertyName, Object propertyValue) {
        if (array instanceof JSONArray) {
            JSONArray jArray = (JSONArray) array;
            int len = jArray.size();
            for (int i = 0; i < len; i++) {
                JSONObject jObj = jArray.getJSONObject(i);
                if (propertyValue.equals(jObj.get(propertyName))) {
                    return true;
                }
            }
        } else if (array instanceof List<?>) {
            List<JSONObject> jList = (List<JSONObject>) array;
            for (JSONObject jObj : jList) {
                if (propertyValue.equals(jObj.get(propertyName))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 对比验证码
     *
     * @param token
     * @return
     */
    public boolean VerificationCode(String token) {
        HttpSession session = getSession();
        Object _token = session.getAttribute(ApplicationCommon.SESSION_CAPTCHA);
        if (ObjectKit.isNotNull(_token)) {
            return token.equals(_token.toString());
        }
        return false;
    }

    /**
     * 获取国际化
     *
     * @param code
     * @return
     */
    public String getI18nMessage(String code) {
        if (getRequest() != null) {
            return WebApplicationCommon.getRequestContext(getRequest()).getMessage(code);
        }
        return code;
    }

    /**
     * 获取系统事件管理器
     *
     * @return
     */
    public ApplicationEventManager getEventManager() {
        return ApplicationCommon.getEventManager();
    }
}
