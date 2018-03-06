package com.jrelax.web.system.service;

import com.jrelax.core.web.model.User;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.core.web.support.http.HandlerRequest;
import com.jrelax.web.support.BaseService;
import com.jrelax.web.system.entity.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * 记录日志到数据库中 数据库中level字段值对应 1:info 2:debug 3:error
 * <p>
 * 日志存储到数据库为异步存储，防止由于日志存储导致服务器功能无法访问
 *
 * @author ZENGCHAO
 */
@Service
@EnableAsync
public class LogService extends BaseService<Log> {
    /**
     * 记录日常日志
     *
     * @param module   模块名称
     * @param msg
     * @param username
     * @param request
     */
    @Async
    @Transactional
    public void info(String module, String msg, String username, HandlerRequest request) {
        Log log = new Log();
        log.setLevel(1);
        log.setModule(getModule(module));
        log.setContent(msg);
        log.setUser(username);
        log.setIp(getRequestAddr());
        log.setBrowser(request.getBroswer());
        log.setPlatform(request.getPlatform());
        log.setTime(new Date());
        log.setSource(getLogClassName());
        super.save(log);

        LoggerFactory.getLogger(log.getSource()).info(msg);
    }

    /**
     * 记录日常日志
     *
     * @param module 模块名称
     * @param msg
     */
    @Transactional
    public void info(String module, String msg) {
        this.info(module, msg, getCurrentUser().getUserName(), HandlerRequest.fromWebRequest(getRequest()));
    }

    /**
     * 记录调试日志
     *
     * @param module
     * @param msg
     * @param username
     * @param request
     */
    @Async
    @Transactional
    public void debug(String module, String msg, String username, HandlerRequest request) {
        Log log = new Log();
        log.setLevel(2);
        log.setContent(msg);
        log.setUser(username);
        log.setIp(getRequestAddr());
        log.setBrowser(request.getBroswer());
        log.setPlatform(request.getPlatform());
        log.setTime(new Date());
        log.setSource(getLogClassName());
        log.setModule(getModule(module));
        super.save(log);

        LoggerFactory.getLogger(log.getSource()).debug(msg);
    }

    /**
     * 记录调试日志
     *
     * @param module
     * @param msg
     */
    @Transactional
    public void debug(String module, String msg) {
        this.debug(module, msg, getCurrentUser().getUserName(), HandlerRequest.fromWebRequest(getRequest()));
    }

    /**
     * 记录错误日志
     *
     * @param module
     * @param msg
     * @param username
     * @param request
     */
    @Async
    @Transactional
    public void error(String module, String msg, String username, HandlerRequest request) {
        Log log = new Log();
        log.setLevel(3);
        log.setUser(username);
        log.setIp(getRequestAddr());
        log.setBrowser(request.getBroswer());
        log.setPlatform(request.getPlatform());
        log.setContent(msg);
        log.setTime(new Date());
        log.setSource(getLogClassName());
        log.setModule(getModule(module));
        super.save(log);

        LoggerFactory.getLogger(log.getSource()).error(msg);
    }

    /**
     * 记录错误日志
     *
     * @param module
     * @param msg
     */
    @Transactional
    public void error(String module, String msg) {
        this.error(module, msg, getCurrentUser().getUserName(), HandlerRequest.fromWebRequest(getRequest()));
    }

    /**
     * 记录注解日志
     *
     * @param method 记录日志的方法
     */
    @Async
    @Transactional
    public void log(Method method, com.jrelax.aop.annotation.Log log, HandlerRequest request, HttpSession session) {
        String content = log.content();
        //记录到Logger日志系统
        if (log.target() == com.jrelax.aop.annotation.Log.Target.ALL || log.target() == com.jrelax.aop.annotation.Log.Target.LOGGER) {
            Logger logger = LoggerFactory.getLogger(method.getDeclaringClass());
            String printContent = log.name() + ":" + content;
            if (log.level() == com.jrelax.aop.annotation.Log.Level.DEBUG) logger.debug(printContent);
            else if (log.level() == com.jrelax.aop.annotation.Log.Level.INFO) logger.info(printContent);
        }

        //记录到数据库
        if (log.target() == com.jrelax.aop.annotation.Log.Target.ALL || log.target() == com.jrelax.aop.annotation.Log.Target.DB) {
            //用户用户名
            String username = Log.DEFAULT_USER;
            if (session != null) {
                User user = WebApplicationCommon.getSessionAdminUser(session);
                if (user != null) {
                    username = user.getUserName();
                }
            }
            Log logInfo = new Log();
            if (log.level() == com.jrelax.aop.annotation.Log.Level.DEBUG)
                logInfo.setLevel(2);
            else if (log.level() == com.jrelax.aop.annotation.Log.Level.INFO)
                logInfo.setLevel(1);
            logInfo.setContent(content);
            logInfo.setUser(username);
            logInfo.setIp(getRequestAddr());
            logInfo.setBrowser(request.getBroswer());
            logInfo.setPlatform(request.getPlatform());
            logInfo.setTime(new Date());
            logInfo.setSource(method.getDeclaringClass().getSimpleName());
            logInfo.setModule(getModule(log.name()));
            super.save(logInfo);
        }
    }

    /**
     * 获取模块名称，用于记录日志
     * 从数据字典中获取，如未获取到，直接范围code值
     *
     * @param code
     * @return
     */
    public String getModule(String code) {
        Map<String, String> map = getDataDict().getMap("sys_modules");
        if (map != null) {
            String name = map.get(code);
            if (name != null) return name;
        }
        return code;
    }

    /**
     * 获取类名称
     *
     * @return
     */
    private String getLogClassName() {
        Throwable ex = new Throwable();
        StackTraceElement[] stackElements = ex.getStackTrace();
        int index = 0;
        for (int i = 0; i < stackElements.length; i++) {
            StackTraceElement e = stackElements[i];
            if (e.isNativeMethod()) {
                index = i - 1;
                break;
            }
        }
        String classname = stackElements[index].getClassName();
        classname = classname.substring(classname.lastIndexOf(".") + 1, classname.length());

        if (classname.indexOf("$$") > 0) {
            classname = classname.substring(0, classname.indexOf("$$"));
        }
        return classname;
    }
}
