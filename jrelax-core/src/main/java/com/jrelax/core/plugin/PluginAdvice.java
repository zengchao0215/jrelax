package com.jrelax.core.plugin;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

public class PluginAdvice implements MethodBeforeAdvice,AfterReturningAdvice{
	private Map<IPluginBefore,String> beforeAdvices = new LinkedHashMap<IPluginBefore,String>();
	private Map<IPluginAfter,String> afterAdvices = new LinkedHashMap<IPluginAfter,String>();
	public void addBeforeAdvices(IPluginBefore plugin,String expression){
		this.beforeAdvices.put(plugin,expression);
	}
	public void addAfterAdvices(IPluginAfter plugin,String expression){
		this.afterAdvices.put(plugin,expression);
	}
	public void removeBeforeAdvices(IPlugin plugin){
		this.beforeAdvices.remove(plugin);
	}
	public void removeAfterAdvices(IPlugin plugin){
		this.afterAdvices.remove(plugin);
	}
	
	public void before(Method method, Object[] params, Object obj)
			throws Throwable {
		for(IPluginBefore plugin : beforeAdvices.keySet()){
			String exp = beforeAdvices.get(plugin);
			AspectJExpressionPointcut ajp = new AspectJExpressionPointcut();
			ajp.setExpression(exp);
			if(ajp.matches(method,obj.getClass(),params)){
				plugin.before(method, params, obj);
			}
		}
	}
	
	public void afterReturning(Object rv, Method method, Object[] params,
			Object obj) throws Throwable {
		for(IPluginAfter plugin : afterAdvices.keySet()){
			String exp = afterAdvices.get(plugin);
			AspectJExpressionPointcut ajp = new AspectJExpressionPointcut();
			ajp.setExpression(exp);
			if(ajp.matches(method,obj.getClass(),params)){
				plugin.afterReturning(rv, method, params, obj);
			}
		}
	}
}
