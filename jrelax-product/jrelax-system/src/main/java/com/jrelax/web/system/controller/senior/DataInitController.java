package com.jrelax.web.system.controller.senior;

import com.jrelax.core.web.support.http.HandlerRequest;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jrelax.core.web.support.WebResult;
import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.service.LogService;

@Controller
@RequestMapping("/system/senior/init")
public class DataInitController extends BaseController<Object>{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private final String TPL = "system/senior/init/";
	
	@Autowired
	private LogService logService;
	
	@RequestMapping(method={RequestMethod.GET})
	public String index(Model model){
		logger.info("访问数据初始化");
		return TPL + "index";
	}
	
	/**
	 * 清理日志
	 * @return
	 */
	@RequestMapping(value="/clear/log", method={RequestMethod.POST})
	@ResponseBody
	public JSONObject clearLog(){
		try{
			logService.executeBatch("delete from Log");
			logService.info("数据初始化", "清除日志", getCurrentUser().getUserName(), HandlerRequest.fromWebRequest(getRequest()));
			return WebResult.success();
		}catch(Exception e){
			logger.error(e.toString());
			return WebResult.error(e);
		}
	}
	
	/**
	 * 清理历史通知
	 * @return
	 */
	@RequestMapping(value = "/clear/notify/history", method = { RequestMethod.POST })
	@ResponseBody
	public JSONObject clearHistoryNotify() {
		try {
			logService.executeBatch("delete from UserNotify where recread = true");
			logService.info("数据初始化", "清除历史通知", getCurrentUser().getUserName(), HandlerRequest.fromWebRequest(getRequest()));
			return WebResult.success();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return WebResult.error(e.getMessage());
		}
	}
	
	/**
	 * 清理所有通知
	 * @return
	 */
	@RequestMapping(value = "/clear/notify/all", method = { RequestMethod.POST })
	@ResponseBody
	public JSONObject clearNotify() {
		try {
			logService.executeBatch("delete from UserNotify");
			logService.executeBatch("delete from Notify");
			logService.info("数据初始化", "清除历史通知", getCurrentUser().getUserName(), HandlerRequest.fromWebRequest(getRequest()));
			return WebResult.success();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return WebResult.error(e.getMessage());
		}
	}

	/**
	 * 清理无用用户账号
	 * @return
	 */
	@RequestMapping(value = "/clear/user", method = { RequestMethod.POST })
	@ResponseBody
	public JSONObject clearUser() {
		try {
			logService.executeBatch("delete from sys_user_role where userid in (select id from sys_user where id not in (select userid from sys_user_unit))");
			logService.executeBatch("delete from sys_user_config where userid in (select id from sys_user where id not in (select userid from sys_user_unit))");
			logService.executeBatch("delete from sys_user_email where userid in (select id from sys_user where id not in (select userid from sys_user_unit))");
			logService.executeBatch("delete from sys_user_quick_menu where userid in (select id from sys_user where id not in (select userid from sys_user_unit))");
			logService.executeBatch("delete from sys_user where id in (select id from sys_user where id not in (select userid from sys_user_unit))");
			logService.info("数据初始化", "清除无用的用户账号", getCurrentUser().getUserName(), HandlerRequest.fromWebRequest(getRequest()));
			return WebResult.success();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return WebResult.error(e.getMessage());
		}
	}
	
}
