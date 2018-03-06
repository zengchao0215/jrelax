<%@ page import="java.net.InetAddress" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="config.jsp"%>
<%
    //获取当前系统参数
    InetAddress address = InetAddress.getLocalHost();
    String serverAddress = address.getHostAddress();
    String osName = System.getProperty("os.name");
    String jdk = System.getProperty("java.vm.name") + " " + System.getProperty("java.version");
    String home = System.getProperty("catalina.base");

    //对比参数
    String requireJdk = props.getProperty("runtime.jdk");
    String targetJdk = System.getProperty("java.version");
    boolean check_jdk = Double.parseDouble(requireJdk.replaceAll("\\.", "")) <= Double.parseDouble(targetJdk.replaceAll("\\.", "").replaceAll("_", "."));
%>
<div class="panel panel-default">
    <div class="panel-heading">服务器信息</div>
    <div class="panel-body">
        <table class="table table-bordered table-condensed">
            <thead>
            <tr>
                <th class="col-lg-4 col-md-4 col-sm-4">参数</th>
                <th>值</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>服务器域名/IP地址</td>
                <td><%=serverAddress%>
                </td>
            </tr>
            <tr>
                <td>服务器操作系统</td>
                <td><%=osName%>
                </td>
            </tr>
            <tr>
                <td>JDK版本</td>
                <td><%=jdk%>
                </td>
            </tr>
            <tr>
                <td>系统安装目录</td>
                <td><%=home%>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="panel panel-default">
    <div class="panel-heading">系统环境检测</div>
    <div class="panel-body">
        <table class="table table-bordered table-condensed">
            <thead>
            <tr>
                <th class="col-lg-4 col-md-4 col-sm-4">参数</th>
                <th>要求</th>
                <th>实际状态</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>JDK版本</td>
                <td>>=<%=requireJdk%></td>
                <td>
                    <%
                        if(check_jdk){
                    %>
                    <i class="glyphicon glyphicon-ok-circle text-success"></i>
                    <%
                        }else{
                    %>
                    <i class="glyphicon glyphicon-remove-circle text-danger no-pass"></i>
                    <%
                        }
                    %>
                    <%=targetJdk%>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
