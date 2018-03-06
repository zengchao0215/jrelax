package com.jrelax.orm.datasource;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.jrelax.core.support.ApplicationContextHelper;

public class DataSourceSwitcherAdvice implements MethodBeforeAdvice,AfterReturningAdvice,ThrowsAdvice{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private boolean autoSwitch = true;//自动切换主从数据源
	
	public boolean isAutoSwitch() {
		return autoSwitch;
	}
	public void setAutoSwitch(boolean autoSwitch) {
		this.autoSwitch = autoSwitch;
	}
	/**
	 * Service层所有方法方法调用前
	 * 主要用于判断方法是读操作还是写操作
	 * 数据库的读写分离
	 */
	public void before(Method method, Object[] param, Object obj)
			throws Throwable {
		if(!autoSwitch)return;
		TransactionInterceptor ti = ApplicationContextHelper.getInstance().getBean("transactionAdvice");
		DataSourceSwitcher mds = DataSourceSwitcher.getInstance();
		//根据食物是否是只读来判断是读操作还是写操作
		if(ti.getTransactionAttributeSource().getTransactionAttribute(method, obj.getClass()).isReadOnly()){
			if(mds.switchToSlave())
				logger.debug("切换到slave服务器");
		}else{
			if(mds.switchToMaster())
				logger.debug("切换到master服务器");
		}
	}
	/**
	 * 方法结束后，自动切回默认配置的数据源
	 * 一般默认为写数据源
	 */
	public void afterReturning(Object obj, Method method, Object[] param,
			Object arg3) throws Throwable {
		if(!autoSwitch)return;
		DataSourceSwitcher mds = DataSourceSwitcher.getInstance();
		mds.restoreDefaultDataSource();
	}
	/**
	 * 产生异常时，自动切回默认数据源
	 * @param method
	 * @param args
	 * @param target
	 * @param ex
	 * @throws Throwable
	 */
	public void afterThrowing(Method method, Object[] args, Object target, Exception ex) throws Throwable {  
		logger.debug("数据源切换器异常，切回默认数据源!");
		if(!autoSwitch)return;
		DataSourceSwitcher mds = DataSourceSwitcher.getInstance();
		mds.restoreDefaultDataSource();
    }  
}
