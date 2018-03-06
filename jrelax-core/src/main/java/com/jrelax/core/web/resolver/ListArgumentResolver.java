package com.jrelax.core.web.resolver;

import com.jrelax.core.web.annotation.RequestList;
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
public class ListArgumentResolver implements HandlerMethodArgumentResolver {
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(RequestList.class) && List.class.isAssignableFrom(methodParameter.getParameterType());
    }

    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        List<String> list = new ArrayList<>();
        RequestList requestList = methodParameter.getParameterAnnotation(RequestList.class);
        if (requestList != null && nativeWebRequest.getParameterMap().size() > 0) {
            String[] values = nativeWebRequest.getParameterValues(requestList.value());
            if(values != null && values.length > 0){
                if(values.length == 1){
                    String param = nativeWebRequest.getParameter(requestList.value());
                    if (param != null) {
                        Collections.addAll(list, param.split(requestList.separator()));
                    }
                }else{
                    Collections.addAll(list, values);
                }
            }

        }
        return list;
    }
}
