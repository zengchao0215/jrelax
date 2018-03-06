package com.jrelax.plugins;

import java.lang.reflect.Method;
import java.util.*;

import com.jrelax.core.plugin.IPlugin;
import com.jrelax.core.plugin.IPluginAfter;
import com.jrelax.core.plugin.IPluginProperty;
import com.jrelax.core.plugin.annotation.Plugin;
import com.jrelax.core.plugin.annotation.PluginAfterAdvice;
import com.jrelax.web.system.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Plugin(value="用户过滤插件", loadOnStartup=false, order = 3)
@PluginAfterAdvice(expression="execution(* com.jrelax.web.system.controller.UserController.unitUserData(..)) ")
public class UserFilterPlugin implements IPlugin, IPluginAfter, IPluginProperty{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private Properties props = new Properties();
	private String rules = "superadmin";
	public boolean init() {
		logger.info("用户过滤插件已加载");
		return true;
	}

	public void destroy() {
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public void afterReturning(Object returnValue, Method method,
			Object[] params, Object obj) {
		if(returnValue instanceof Map){
			Map<String,Object> map = (Map<String, Object>) returnValue;
			List<User> list = (List<User>) map.get("rows");
			List<User> newList = new ArrayList<>();
			for (User user : list){
				if(!isMatch(user.getUserName(), rules)) {
					newList.add(user);
				}
			}
			map.put("rows", newList);
		}
	}


	private boolean isMatch(String name, String rules){
		String[] ruleArray = rules.split(",");
		for(String rule : ruleArray){
			if(rule.equals(name)){
				return true;
			}
		}
		return false;
	}

	@Override
	public void setProperties(Properties properties) {
		props = properties;
	}

	@Override
	public Properties getProperties() {
		if(!props.containsKey("rules")){
			props.put("rules", rules);
		}
		return props;
	}

	@Override
	public void afterPropertiesSet() {
		rules = props.getProperty("rules");
	}

	@Override
	public LinkedHashMap<String, String> getPropertyMapping() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("rules", "用户名(多个用逗号隔开)");
		return map;
	}
}
