package com.jrelax.token.springmvc;

import com.jrelax.token.TokenSession;
import com.jrelax.token.TokenSessionConfig;
import com.jrelax.token.TokenSessionUtil;
import com.jrelax.token.springmvc.annotation.TokenSessionAttribute;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * TokenSession绑定
 * Created by zengchao on 2017-03-23.
 */
public class TokenSessionArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return TokenSession.class.isAssignableFrom(parameter.getParameterType())
                || parameter.hasParameterAnnotation(TokenSessionAttribute.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        TokenSession session = TokenSessionUtil.getSession(request);
        if(TokenSession.class.isAssignableFrom(parameter.getParameterType())){
            return session;
        }
        if(parameter.hasParameterAnnotation(TokenSessionAttribute.class) && session != null){
            TokenSessionAttribute sessionAttribute = parameter.getParameterAnnotation(TokenSessionAttribute.class);
            return session.getAttribute(sessionAttribute.value());
        }
        return null;
    }
}
