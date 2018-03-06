package com.jrelax.core.web.resolver;

import com.jrelax.core.web.annotation.RequestJson;
import com.jrelax.core.web.annotation.RequestList;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * List映射,用于解析用分隔符隔开的参数
 * Created by zengchao on 2017-02-17.
 */
public class JsonArgumentResolver implements HandlerMethodArgumentResolver {
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(RequestJson.class);
    }

    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Object obj = null;
        RequestJson requestJson = methodParameter.getParameterAnnotation(RequestJson.class);
        if (requestJson != null && nativeWebRequest.getParameterMap().size() > 0) {
            String value = nativeWebRequest.getParameter(requestJson.value());
            if(value != null){
                if(JSONObject.class.isAssignableFrom(methodParameter.getParameterType())){
                    obj = JSONObject.fromObject(value);
                }else if(JSONArray.class.isAssignableFrom(methodParameter.getParameterType())){
                    obj = JSONArray.fromObject(value);
                }
            }
        }
        return obj;
    }
}
