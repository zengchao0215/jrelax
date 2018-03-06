package com.jrelax.core.factory;

import com.jrelax.core.web.support.WebResult;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class LicenseContextLoaderListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n======================================================================\r\n");
		sb.append("\r\n    欢迎使用 JRelax  - Powered By ZENGCHAO\r\n");
		sb.append("\r\n        技术支持：zengc@nethsoft.com       \r\n");
		sb.append("\r\n======================================================================\r\n");
		System.out.println(sb.toString());
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {

	}
}
