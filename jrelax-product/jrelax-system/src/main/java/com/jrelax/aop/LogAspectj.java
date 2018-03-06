package com.jrelax.aop;

import com.jrelax.aop.annotation.Log;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.core.web.support.http.HandlerRequest;
import com.jrelax.web.system.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 日志记录切面，记录非Controller日志
 * Created by zengchao on 2017-02-25.
 */
@Aspect
@Component
public class LogAspectj{
    @Autowired
    private LogService logService;

    @After("execution (* com.jrelax..*.*(..))")
    public void after(JoinPoint point){
        MethodSignature signature = (MethodSignature) point.getSignature();
        Log log = signature.getMethod().getAnnotation(Log.class);
        if(log != null){
            logService.log(signature.getMethod(), log, HandlerRequest.fromWebRequest(WebApplicationCommon.getRequest()), WebApplicationCommon.getSession());
        }
    }
}
