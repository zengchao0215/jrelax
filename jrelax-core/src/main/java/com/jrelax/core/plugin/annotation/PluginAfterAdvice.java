package com.jrelax.core.plugin.annotation;
/**任意公共方法的执行：
execution(public * *(..)) 
任何一个以“set”开始的方法的执行：
execution(* set*(..)) 
AccountService 接口的任意方法的执行：
execution(* com.xyz.service.AccountService.*(..)) 
定义在service包里的任意方法的执行：
execution(* com.xyz.service.*.*(..)) 
定义在service包或者子包里的任意方法的执行：
execution(* com.xyz.service..*.*(..)) **/
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PluginAfterAdvice {
	String expression();
}
