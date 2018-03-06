package com.jrelax.core.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
/**
 * 
 * 
 * 创建人：ZENGCHAO
 * 创建时间：2013-1-11 下午5:11:04
 * @version
 *
 */
public class MyBeanFactoryPostProcess implements BeanFactoryPostProcessor{

	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
	}

}
