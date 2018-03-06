package com.jrelax.core.factory;

import com.jrelax.core.support.ApplicationCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Springc初始化
 * Created by zengchao on 2017-03-03.
 */
public class SpringContextLoaderListener extends ContextLoaderListener {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ServletContextEvent servletContextEvent = null;
    @Override
    public void contextInitialized(ServletContextEvent event) {
        this.servletContextEvent = event;
        //判断程序是否已经执行过安装向导
        if (isInstalled()) {
            restore();
        }else{
            ApplicationCommon.SYSTEM_INSTALLED = false;
            logger.debug("系统尚未初始化");
        }
        ApplicationCommon.SYSTEM_CONTEXT_LOADER_LISTENER = this;
    }

    /**
     * 判断系统是否存在
     */
    public boolean isInstalled() {
        ClassPathResource resource = new ClassPathResource(ApplicationCommon.SYSTEM_INSTALLED_FILE);
        return resource.exists();
    }

    /**
     * 重新初始化
     */
    public void restore(){
        super.contextInitialized(this.servletContextEvent);
        ApplicationCommon.SYSTEM_INSTALLED = true;
    }
}
