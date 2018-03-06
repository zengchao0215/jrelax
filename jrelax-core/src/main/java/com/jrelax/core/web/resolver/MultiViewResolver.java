package com.jrelax.core.web.resolver;

import com.jrelax.kit.FileKit;
import com.jrelax.kit.StringKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 多视图自由切换
 * Created by zengchao on 2017-01-24.
 */
public class MultiViewResolver implements ViewResolver{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //默认视图
    private ViewResolver defaultViewResolver = null;
    //后缀名映射
    Map<String, ViewResolver> viewResolverMap = new HashMap<>();

    public ViewResolver getDefaultViewResolver() {
        return defaultViewResolver;
    }

    public void setDefaultViewResolver(ViewResolver defaultViewResolver) {
        this.defaultViewResolver = defaultViewResolver;
    }

    public Map<String, ViewResolver> getViewResolverMap() {
        return viewResolverMap;
    }

    public void setViewResolverMap(Map<String, ViewResolver> viewResolverMap) {
        this.viewResolverMap = viewResolverMap;
    }

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        String suffix = StringKit.getSuffix(viewName);
        if(StringKit.isNotEmpty(suffix)){
            ViewResolver viewResolver = viewResolverMap.get(suffix);
            if(viewResolver != null){
                logger.debug(viewName + " -> "+viewResolver.getClass());
                return viewResolver.resolveViewName(viewName, locale);
            }
        }
        logger.debug(viewName + " -> "+defaultViewResolver.getClass());
        return defaultViewResolver.resolveViewName(viewName, locale);
    }
}
