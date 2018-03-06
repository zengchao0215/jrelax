package com.jrelax.web;

import javax.servlet.http.HttpServletRequest;

import com.jrelax.core.web.annotation.Open;
import com.jrelax.core.web.annotation.OpenScope;
import com.jrelax.core.web.support.WebApplicationCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jrelax.kit.ObjectKit;
import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.entity.User;
import com.jrelax.web.system.service.LogService;

/**
 * 错误处理控制类
 * @author ZENGCHAO
 *
 */
@Controller
@RequestMapping(WebApplicationCommon.UrlPrefix.ERROR)
@Open(scope = OpenScope.ALL)
public class ErrorController extends BaseController<Object>{
	@RequestMapping("/{errorCode}")
	public String processingErrors2(@PathVariable String errorCode){
		return "_error/"+errorCode;
	}
}
