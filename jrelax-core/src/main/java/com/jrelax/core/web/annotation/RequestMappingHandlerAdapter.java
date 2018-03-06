package com.jrelax.core.web.annotation;

import org.springframework.web.method.support.HandlerMethodReturnValueHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义RequestMappingHanlderAdapter
 * Created by zengchao on 2017-02-16.
 */
public class RequestMappingHandlerAdapter extends org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter {
    /**
     * 前置返回值处理
     */
    private List<HandlerMethodReturnValueHandler> prefixReturnValueHandlers;
    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();

        //添加前置返回值处理
        if(prefixReturnValueHandlers != null){
            List<HandlerMethodReturnValueHandler> returnValueHandlers = this.getReturnValueHandlers();
            List<HandlerMethodReturnValueHandler> newReturnValueHandlers = new ArrayList<>();
            newReturnValueHandlers.addAll(prefixReturnValueHandlers);
            newReturnValueHandlers.addAll(returnValueHandlers);
            super.setReturnValueHandlers(newReturnValueHandlers);
        }
    }

    public List<HandlerMethodReturnValueHandler> getPrefixReturnValueHandlers() {
        return prefixReturnValueHandlers;
    }

    public void setPrefixReturnValueHandlers(List<HandlerMethodReturnValueHandler> prefixReturnValueHandlers) {
        this.prefixReturnValueHandlers = prefixReturnValueHandlers;
    }
}
