package com.jrelax.core.web.resolver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.jrelax.core.web.annotation.RequestBean;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class BeanArgumentResolver implements HandlerMethodArgumentResolver {

	public boolean supportsParameter(MethodParameter parameter) {
		return false;
	}

	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		RequestBean requestBean = parameter.getParameterAnnotation(RequestBean.class);
		if (requestBean != null) {
			String _param = requestBean.value();
			if (_param.equals("_def_param_name")) {
				_param = parameter.getParameterName();
			}
			Class<?> clazz = parameter.getParameterType();
			Object object = clazz.newInstance();
			HashMap<String, String[]> paramsMap = new HashMap<String, String[]>();
			Iterator<String> itor = webRequest.getParameterNames();
			while (itor.hasNext()) {
				String webParam = itor.next();
				String[] webValue = webRequest.getParameterValues(webParam);
				if (webParam.startsWith(_param)) {
					paramsMap.put(webParam, webValue);
				}
			}
			BeanWrapper obj = new BeanWrapperImpl(object);
			obj.registerCustomEditor(Date.class, null, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
			for (String propName : paramsMap.keySet()) {
				String[] propVals = paramsMap.get(propName);
				String[] props = propName.split("\\.");
				if (props.length == 2) {
					obj.setPropertyValue(props[1], propVals);
				} else if (props.length == 3) {
					Object tmpObj = obj.getPropertyValue(props[1]);
					if (tmpObj == null)
						obj.setPropertyValue(props[1], obj.getPropertyType(props[1]).newInstance());
					obj.setPropertyValue(props[1] + "." + props[2], propVals);
				}

			}
			return object;
		} else {
			return WebArgumentResolver.UNRESOLVED;
		}
	}
}
