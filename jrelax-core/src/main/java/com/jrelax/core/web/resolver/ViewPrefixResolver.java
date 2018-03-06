package com.jrelax.core.web.resolver;

import com.jrelax.core.web.annotation.ViewPrefix;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 视图前缀处理器
 * Created by zengchao on 2017-02-24.
 */
public class ViewPrefixResolver {
    private static ViewPrefixResolver instance = new ViewPrefixResolver();

    private ViewPrefixResolver() {
    }

    public static ViewPrefixResolver getInstance() {
        return instance;
    }

    public void resolver(HandlerMethod method, ModelAndView mav) {
        if (mav != null && mav.getViewName() != null) {
            ViewPrefix viewPrefix = method.getBeanType().getAnnotation(ViewPrefix.class);
            if (viewPrefix != null && !mav.getViewName().startsWith("redirect:")) {
                String viewName = viewPrefix.value() + mav.getViewName();
                if (!viewPrefix.value().endsWith("/"))//如果结尾没有／则自动追加
                    viewName = viewPrefix.value() + "/" + mav.getViewName();
                mav.setViewName(viewName);
            }
            //自动去掉／
            if(mav.getViewName().startsWith("/")){
                mav.setViewName(mav.getViewName().substring(1));
            }
        }
    }
}
