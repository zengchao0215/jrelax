package com.jrelax.core.web.handler;

import com.jrelax.core.web.export.IExport;
import com.jrelax.core.web.support.WebResult;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Excel导出
 * Created by zengchao on 2017/3/12.
 */
public class ExportReturnValueHandler implements HandlerMethodReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return IExport.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        if (returnValue != null) {
            mavContainer.setRequestHandled(true);

            IExport export = (IExport) returnValue;
            response.reset();
            String name = export.getName();
            if (name == null) {//如果未命名，则使用当前时间戳作为文件名
                name = System.currentTimeMillis() + "";
            }
            name += export.getSuffix();
            name = new String(name.getBytes("UTF-8"), "ISO8859-1");
            response.setHeader("Content-disposition", "attachment; filename=" + name);
            response.setContentType(export.getContentType());
            OutputStream output = response.getOutputStream();
            export.write(output);
            output.close();
        } else {
            PrintWriter out = response.getWriter();
            out.write(WebResult.error("导出失败！").toString());
            out.close();
        }
    }
}
