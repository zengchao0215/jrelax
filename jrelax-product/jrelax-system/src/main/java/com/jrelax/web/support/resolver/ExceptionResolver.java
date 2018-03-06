package com.jrelax.web.support.resolver;

import com.jrelax.core.web.model.User;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.core.web.support.http.HandlerRequest;
import com.jrelax.kit.HttpKit;
import com.jrelax.kit.ObjectKit;
import com.jrelax.web.system.entity.Log;
import com.jrelax.web.system.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class ExceptionResolver implements HandlerExceptionResolver {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private LogService logService;

    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object obj, Exception e) {
        if (response.isCommitted()) return null;
        User user = WebApplicationCommon.getSessionAdminUser(request.getSession());
        String userName = user == null ? Log.DEFAULT_USER : user.getUserName();
        try {
            int errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            String errorMsg = "服务器繁忙，请稍后再试！";
            if (e instanceof ConversionNotSupportedException) {//Web服务器内部异常
                errorMsg = "服务器内部异常！";
                errorCode = HttpStatus.NOT_ACCEPTABLE.value();
            } else if (e instanceof HttpMediaTypeNotAcceptableException) {//无和请求Accept匹配的MIME类型
                errorMsg = "无和请求Accept匹配的MIME类型！";
                errorCode = HttpStatus.NOT_ACCEPTABLE.value();
            } else if (e instanceof HttpMediaTypeNotSupportedException) {//不支持的MIME类型
                errorMsg = "不支持的MIME类型！";
                errorCode = HttpStatus.UNSUPPORTED_MEDIA_TYPE.value();
            } else if (e instanceof HttpMessageNotReadableException) {//Bad Request
                errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            } else if (e instanceof HttpMessageNotWritableException) {//406
                errorMsg = "消息转换异常！";
                errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
                ;
            } else if (e instanceof HttpRequestMethodNotSupportedException) {//不支持的请求方法
                errorMsg = "不支持的请求方法:" + request.getMethod();
                errorCode = HttpStatus.METHOD_NOT_ALLOWED.value();
                ;
            } else if (e instanceof MissingServletRequestParameterException) {//400
                errorMsg = "请求出错！";
                errorCode = HttpStatus.BAD_REQUEST.value();
            } else if (e instanceof NoSuchRequestHandlingMethodException) {//找不到匹配资源
                errorMsg = "找不到请求的资源！";
                errorCode = HttpStatus.NOT_FOUND.value();
            } else if (e instanceof TypeMismatchException) {//400
                errorMsg = "类型转换错误";
                errorCode = HttpStatus.BAD_REQUEST.value();
            } else if (e instanceof SQLException) {
                errorMsg = "数据库操作异常！";
                errorCode = HttpStatus.SERVICE_UNAVAILABLE.value();
            }
            logger.error(e.getMessage(), e);
            logService.error("系统异常", "错误代码:" + errorCode + "，访问路径:" + request.getAttribute("javax.servlet.forward.servlet_path"), userName, HandlerRequest.fromWebRequest(request));
            //判断是否是ajax请求
            if (HttpKit.isAjaxWithRequest(request)) {
                response.reset();
                response.setContentType("text/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(WebResult.error(errorMsg).element("code", errorCode));
                response.getWriter().close();
                return null;
            } else if (ObjectKit.isNull(request.getHeader("x-requested-with"))) {
                return new ModelAndView("_error/" + errorCode);
            }
        } catch (Exception e2) {
            logService.error("异常处理出错", e2.toString(), userName, HandlerRequest.fromWebRequest(request));
            logger.error(e2.getMessage(), e2);
        }
        return new ModelAndView("_error/500");
    }

}
