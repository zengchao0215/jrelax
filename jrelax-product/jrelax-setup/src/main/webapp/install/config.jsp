<%@ page import="java.util.Properties" %>
<%@ page import="java.io.FileReader" %>
<%@ page import="java.io.File" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //配置
    File fileConfig = new File(request.getServletContext().getRealPath("/install/config.properties"));
    Properties props = new Properties();
    FileReader fr = new FileReader(fileConfig);
    props.load(fr);
    fr.close();
%>
