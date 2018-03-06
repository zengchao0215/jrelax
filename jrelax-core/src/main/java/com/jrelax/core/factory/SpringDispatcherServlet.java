package com.jrelax.core.factory;

import com.jrelax.core.support.ApplicationCommon;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * SpringMVC DispatcherServlet
 * 增加安装向导过滤
 * Created by zengchao on 2017-03-03.
 */
public class SpringDispatcherServlet extends DispatcherServlet {
    private ServletConfig servletConfig;
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.servletConfig = servletConfig;
        if (ApplicationCommon.SYSTEM_INSTALLED) {
            restore();
        }
        ApplicationCommon.SYSTEM_DISPATCHER_SERVLET = this;;
    }

    public void restore(){
        try {
            super.init(this.servletConfig);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        if (ApplicationCommon.SYSTEM_INSTALLED) {
            super.service(req, res);
        }else{
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;

            doDefaultService(request, response);
        }
    }

    /**
     * 执行默认的Servlet方法
     * @param request
     * @param response
     * @throws IOException
     */
    private void doDefaultService(HttpServletRequest request, HttpServletResponse response) throws IOException{
        OutputStream out = response.getOutputStream();
        File file = new File(request.getServletContext().getRealPath(request.getServletPath()));
        if(file.exists()){
            String name = file.getName();
            if(name.contains(".css")){
                response.setContentType("text/css; charset=UTF-8");
            }else if(name.contains(".js")){
                response.setContentType("text/javascript; charset=UTF-8");
            }else if(name.contains(".gif")){
                response.setContentType("image/gif");
            }else if(name.contains(".png")){
                response.setContentType("image/png");
            } else{
                response.setContentType("text/html; charset=UTF-8");
            }
            FileInputStream fis = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int len = 0;
            while((len=fis.read(bytes)) != -1){
                out.write(bytes, 0, len);
            }
        }

        out.close();
    }
}
