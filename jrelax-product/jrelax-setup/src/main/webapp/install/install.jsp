<%@ page import="org.apache.tools.ant.taskdefs.SQLExec" %>
<%@ page import="org.apache.tools.ant.Project" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page import="com.mchange.v2.c3p0.ComboPooledDataSource" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="com.jrelax.kit.Md5Kit" %>
<%@ page import="com.jrelax.core.support.ApplicationCommon" %>
<%@ page import="com.jrelax.kit.security.DecKit" %>
<%@ page import="com.jrelax.config.SafeProperties" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="config.jsp" %>
<%
    //获取数据库参数
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

    //获取管理人信息
    String adminUser = request.getParameter("admin_username");
    String adminPass = request.getParameter("admin_password");

    JSONObject result = new JSONObject();
    try {
        //1. 执行数据库脚本文件
        System.out.println("导入数据库脚本...");
        SQLExec exec = new SQLExec();
        exec.setDriver(driver);
        exec.setUrl(url);
        exec.setUserid(user);
        exec.setPassword(password);
        exec.setEncoding("UTF-8");
        File sql = new File(request.getServletContext().getRealPath("/install/database/" + type + ".sql"));
        exec.setSrc(sql);
        exec.setPrint(false);
        exec.setProject(new Project());
        exec.setOnerror(new SQLExec.OnError());
        exec.execute();

        //2.修改配置文件
        System.out.println("修改配置文件...");
        DecKit decKit = new DecKit();
        decKit.genKey(ApplicationCommon.DEC_KEY);
        SafeProperties jdbcProp = new SafeProperties();
        File fileJdbc = new File(request.getServletContext().getRealPath("/WEB-INF/classes/jdbc_mysql.properties"));
        jdbcProp.load(new FileReader(fileJdbc));
        jdbcProp.setProperty("jdbc.master.driver", driver);
        jdbcProp.setProperty("jdbc.master.url", url);
        jdbcProp.setProperty("jdbc.master.username", decKit.getEncString(user));
        jdbcProp.setProperty("jdbc.master.password", decKit.getEncString(password));
        FileOutputStream fos = new FileOutputStream(fileJdbc);
        jdbcProp.store(fos, null);

        //3.执行管理员脚本
        System.out.println("执行初始化脚本...");
        String adminSql = props.getProperty("database.admin.sql");
        adminSql = adminSql.replace("{username}", adminUser).replace("{password}", Md5Kit.encode(adminPass));
        ComboPooledDataSource c3p0 = new ComboPooledDataSource();
        c3p0.setDriverClass(driver);
        c3p0.setJdbcUrl(url);
        c3p0.setUser(user);
        c3p0.setPassword(password);
        c3p0.setCheckoutTimeout(10000);//10秒超时
        Statement statement = c3p0.getConnection().createStatement();
        statement.executeUpdate(adminSql);
        statement.close();
        c3p0.close();

        //4.生成安装成功标记文件
        System.out.println("生成安装文件...");
        File fileLock = new File(request.getServletContext().getRealPath("/WEB-INF/classes/" + ApplicationCommon.SYSTEM_INSTALLED_FILE));
        if (!fileLock.exists())
            fileLock.createNewFile();

        //5.重新初始化
        System.out.println("系统初始化...");
        ApplicationCommon.SYSTEM_CONTEXT_LOADER_LISTENER.reInit();
        ApplicationCommon.SYSTEM_DISPATCHER_SERVLET.reInit();

        result.element("success", true);
    } catch (Exception e) {
        e.printStackTrace();
        result.element("success", false);
    }

    //输出结果
    PrintWriter writer = response.getWriter();
    response.setContentType("text/json;charset=UTF-8");
    writer.print(result.toString());
    writer.close();
%>
