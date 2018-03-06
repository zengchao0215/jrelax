package com.jrelax.core.web.support;

import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.kit.ObjectKit;
import com.jrelax.kit.StringKit;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 异步响应类型
 * success :  true/false 操作结果
 * message : 响应结果描述信息
 * type : 错误类型
 * data : 数据
 *
 * @author zengchao
 */
public class WebResult {

    /**
     * 操作成功
     *
     * @return
     */
    public static JSONObject success() {
        JSONObject obj = new JSONObject();
        obj.element("success", true);
        return obj;
    }

    /**
     * 操作成功
     *
     * @param msg 提示信息
     * @return
     */
    public static JSONObject success(String msg) {
        return WebResult.success().element("message", msg);
    }

    /**
     * 操作失败
     *
     * @param msg 错误提示
     * @return
     */
    public static JSONObject error(String msg) {
        JSONObject obj = new JSONObject();
        obj.element("success", false);
        obj.element("message", msg);
        return obj;
    }

    /**
     * 操作失败
     *
     * @param e 异常
     * @return
     */
    public static JSONObject error(Exception e) {
        Class<?> clazz = null;
        if (ObjectKit.isNull(e.getCause())) clazz = e.getClass();
        else clazz = e.getCause().getClass();
        Map<String, String> exMap = ApplicationCommon.getDataDict().getMap("sys_exception");
        if (ObjectKit.isNotNull(exMap)) {
            String exp = exMap.get(clazz.getName());
            if (StringKit.isBlank(exp)) {
                exp = e.toString();
            }
            return error(exp);
        }
        return error(e.toString());
    }

    /**
     * 会话超时
     *
     * @return
     */
    public static JSONObject sessionTimeout() {
        return WebResult.error("会话超时，请重新登录！").element("type", "sessionTimeout");
    }

    /**
     * 没有任何改变
     *
     * @return
     */
    public static JSONObject noChanges() {
        return WebResult.error("未发现修改项！").element("type", "noChanges");
    }

    /**
     * 需要验证密码
     *
     * @return
     */
    public static JSONObject needPassword() {
        return WebResult.error("需要验证密码！").element("type", "needPassword");
    }

    /**
     * 没有权限
     *
     * @return
     */
    public static JSONObject noPermission() {
        return WebResult.error("您没有权限访问此资源！").element("type", "noPermission");
    }

    /**
     * 资源没有启用
     *
     * @param res 资源名
     * @return
     */
    public static JSONObject notEnable(String res) {
        return WebResult.error(String.format("【%s】已经停止使用！", res)).element("type", "notEnable");
    }

    /**
     * 未经允许的访问
     *
     * @return
     */
    public static JSONObject notAllow() {
        return WebResult.error("非法访问!").element("type", "notAllow");
    }
}

