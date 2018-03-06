package com.jrelax.core.plugin;

public interface IPlugin {
	/**
	 * 插件初始化
	 * @return
	 */
	boolean init();
	/**
	 * 插件销毁
	 * @return
	 */
	void destroy();
	
}
