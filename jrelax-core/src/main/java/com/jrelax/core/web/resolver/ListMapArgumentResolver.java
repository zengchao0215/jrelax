package com.jrelax.core.web.resolver;

import com.jrelax.core.web.annotation.RequestListMap;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.*;

/**
 * ListBean映射
 * Created by zengchao on 2017-02-17.
 */
public class ListMapArgumentResolver implements HandlerMethodArgumentResolver {
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(RequestListMap.class) && List.class.isAssignableFrom(methodParameter.getParameterType());
    }

    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        List<Map<String, String>> listMap = new ArrayList<>();
        RequestListMap requestListBean = methodParameter.getParameterAnnotation(RequestListMap.class);
        if (requestListBean != null && nativeWebRequest.getParameterMap().size() > 0) {
            //找到数量最多的
            int maxCount = 0;
            Iterator<String> iterator = nativeWebRequest.getParameterNames();
            while (iterator.hasNext()) {
                int len = nativeWebRequest.getParameterValues(iterator.next()).length;
                if (len > maxCount)
                    maxCount = len;
            }
            for (int i = 0; i < maxCount; i++) {
                listMap.add(new LinkedHashMap<>());
            }
            //赋值
            iterator = nativeWebRequest.getParameterNames();
            while (iterator.hasNext()) {
                String name = iterator.next();
                String[] values = nativeWebRequest.getParameterValues(name);
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];

                    if (requestListBean.ignoreEmpty()) {
                        if (!StringUtils.isEmpty(value)) {
                            listMap.get(i).put(name, values[i]);
                        }
                    } else {
                        listMap.get(i).put(name, values[i]);
                    }
                }
            }
            if (requestListBean.ignoreEmpty()) {
                //清理空行
                List<Map<String, String>> newListMap = new ArrayList<>();
                for (int i = 0; i < listMap.size(); i++) {
                    if (!listMap.get(i).isEmpty()) newListMap.add(listMap.get(i));
                }
                listMap = newListMap;
            }
        }
        return listMap;
    }
}
