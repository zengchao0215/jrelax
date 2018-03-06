<%@ page import="net.sf.json.JSONObject" %>
<%@ page import="com.mchange.v2.c3p0.ComboPooledDataSource" %>
<%@ page import="java.beans.PropertyVetoException" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="config.jsp" %>
<%

    //检查数据库是否可用
    String type = request.getParameter("type");//数据库类型
    String host = request.getParameter("host");//主机地址
    String port = request.getParameter("port");//端口号
    String name = request.getParameter("name");//数据库名
    String user = request.getParameter("user");//用户名
    String password = request.getParameter("password");//密码
    String charset = request.getParameter("charset");//编码

    String driver = props.getProperty("database." + type + ".driver");
    String url = props.getProperty("database." + type + ".url");

    url = url.replace("{host}", host).replace("{port}", port).replace("{db}", name);

    //测试数据库连接
    JSONObject result = new JSONObject();

    ComboPooledDataSource c3p0 = new ComboPooledDataSource();
    try {
        c3p0.setDriverClass(driver);
    } catch (PropertyVetoException e) {
        e.printStackTrace();
    }
    c3p0.setJdbcUrl(url);
    c3p0.setUser(user);
    c3p0.setPassword(password);
    c3p0.setCheckoutTimeout(10000);//10秒超时
    try {
        Statement statement = c3p0.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("select 1");
        rs.close();
        statement.close();

        result.element("success", true);
    } catch (SQLException e) {
        e.printStackTrace();
        result.element("success", false);
    }
    c3p0.close();

    PrintWriter writer = response.getWriter();
    response.setContentType("text/json;charset=UTF-8");
    writer.print(result.toString());
    writer.close();
%>
