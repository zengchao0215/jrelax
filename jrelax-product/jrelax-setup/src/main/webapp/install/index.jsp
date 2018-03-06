<%@ page import="com.jrelax.core.support.ApplicationCommon" %>
<%@ page import="com.mchange.io.FileUtils" %>
<%@ page import="java.io.File" %>
<%@include file="config.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //0. 判断是否已经安装，如果已经安装则跳转到index.jsp页面
    if (ApplicationCommon.SYSTEM_INSTALLED) {
        response.sendRedirect("../index");
    }
    String projectName = props.getProperty("project.name");
    //1. 获取许可信息
    File fileLicense = new File(request.getServletContext().getRealPath("/install/license.lic"));
    String license = "";
    if (fileLicense.exists()) {
        license = FileUtils.getContentsAsString(fileLicense, "UTF-8");
    }
    //1. 检测系统环境
    //2. 配置数据库等安装参数
    //3. 执行安装

%>
<html>
<head>
    <title><%=projectName%> 安装向导</title>
    <link rel="stylesheet" href="static/css/bootstrap.min.css">
    <link rel="stylesheet" href="static/css/smart_wizard.css">
    <link rel="stylesheet" href="static/css/smart_wizard_theme_dots.css">
    <link rel="shortcut icon" href="static/img/logo.png">
    <style>
        ::-webkit-scrollbar-track { background-color: #F5F5F5 }
        ::-webkit-scrollbar { width: 6px; background-color: #F5F5F5; opacity: 0.4; }
        ::-webkit-scrollbar-thumb { background-color: #bdbdbd; opacity: 0.4; }
        body {
            background: #c1c1c1;
        }

        #smartwizard li {
            width: 20%;
        }

        .bordered {
            border: 1px solid #ddd;
            background: white;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-lg-10 col-lg-offset-1 bordered" style="margin-top: 10%;">
            <h1 class="text-center text-capitalize">
                <img src="static/img/logo.png" alt="Logo">
                安装向导
            </h1>
            <hr>
            <div id="smartwizard">
                <ul>
                    <li><a href="#license">&nbsp;&nbsp;阅读许可协议&nbsp;&nbsp;<br/>
                        <small>&nbsp;</small>
                    </a></li>
                    <li><a href="#detect">&nbsp;&nbsp;检查运行环境&nbsp;&nbsp;<br/>
                        <small>&nbsp;</small>
                    </a></li>
                    <li><a href="#database">&nbsp;&nbsp;数据库配置&nbsp;&nbsp;<br/>
                        <small>&nbsp;</small>
                    </a></li>
                    <li><a href="#admin">&nbsp;&nbsp;管理员账户&nbsp;&nbsp;<br/>
                        <small>&nbsp;</small>
                    </a></li>
                    <li><a href="#complete">&nbsp;&nbsp;执行安装&nbsp;&nbsp;<br/>
                        <small>&nbsp;</small>
                    </a></li>
                </ul>

                <div>
                    <div id="license">
                        <textarea class="form-control" rows="15"><%=license%></textarea>
                        <br>
                        <label>
                            <input type="checkbox" onchange="checkLicense(this)">
                            我已阅读并同意此协议
                        </label>
                    </div>
                    <div id="detect" class="">
                        <div class="text-center">
                            <h2>正在检测当前系统环境</h2>
                            <img src="static/img/refresh.gif" alt="">
                        </div>
                    </div>
                    <div id="database">
                        <form id="databaseForm" method="post" class="form-horizontal">
                            <div class="form-group">
                                <label class="col-lg-4 control-label">数据库类型：</label>
                                <div class="col-lg-6">
                                    <select name="type" class="form-control">
                                        <option value="mysql">MySQL</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-lg-4 control-label">数据库主机：</label>
                                <div class="col-lg-6">
                                    <input type="text" class="form-control" name="host" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-lg-4 control-label">数据库端口：</label>
                                <div class="col-lg-6">
                                    <input type="text" class="form-control" name="port" placeholder="默认：3306"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-lg-4 control-label">数据库名称：</label>
                                <div class="col-lg-6">
                                    <input type="text" class="form-control" name="name" />
                                    <span>请预先创建好数据库</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-lg-4 control-label">数据库用户名：</label>
                                <div class="col-lg-6">
                                    <input type="text" class="form-control" name="user" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-lg-4 control-label">数据库密码：</label>
                                <div class="col-lg-6">
                                    <input type="password" class="form-control" name="password"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-lg-4 control-label">数据库编码：</label>
                                <div class="col-lg-6">
                                    <label>
                                        <input type="radio" name="charset" value="UTF-8" checked="checked" />
                                        UTF-8
                                    </label>
                                    <div class="alert alert-warning">
                                        <p>MySQL数据库版本需>=5.6</p>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-lg-4 control-label"></label>
                                <div class="col-lg-6">
                                    <button class="btn btn-warning" type="button" data-loading-text="正在测试..." onclick="checkDatabase(this)">测试连接</button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div id="admin">
                        <form id="adminForm" method="post" class="form-horizontal">
                            <div class="form-group">
                                <label class="col-lg-4 control-label">管理员登录名：</label>
                                <div class="col-lg-6">
                                    <input type="text" class="form-control" name="admin_username" value="superadmin" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-lg-4 control-label">管理员登录密码：</label>
                                <div class="col-lg-6">
                                    <input type="password" class="form-control" name="admin_password" minlength="1" maxlength="20">
                                </div>
                            </div>
                        </form>
                    </div>
                    <div id="complete">
                        <div id="load" class="text-center">
                            <h2>正在执行安装，请稍后...</h2>
                            <img src="static/img/refresh.gif" alt="">
                        </div>
                        <div id="ok" class="text-center" style="display: none;">
                            <i class="glyphicon glyphicon-ok-circle text-success" style="font-size: 50px;"></i>
                            <h2>安装成功，正在转向系统首页...</h2>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<script src="static/js/jquery-1.11.1.min.js"></script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/jquery.smartWizard.js"></script>
<script>
    var btnNext, btnPrev;
    $(function () {
        $('#smartwizard').smartWizard({
            selected: 0,
            theme: 'dots',
            transitionEffect: 'fade',
            showStepURLhash: false,
            lang: {
                next: "下一步",
                previous: "上一步"
            },
            onStep : function(idx, wizard){
                if(btnPrev && btnNext){
                    if(idx == 0){
                        btnPrev.hide();
                    }else{
                        btnPrev.show();
                    }
                    if(idx == 1){//检查系统环境
                        nextDisable();
                        $("#detect").load("check.jsp", {}, function(){
                            wizard._fixHeight(idx);
                            if($("#detect").find("no-pass").length == 0){
                                nextEnable();
                            }
                        });
                    }else if(idx == 2){//检测数据库配置
                        nextDisable();
                    }else if(idx == 3){//配置管理员

                    }else if(idx == 4){//执行安装
                        complete();
                    }
                }
            }
        });
        btnNext = $(".sw-btn-next");
        btnPrev = $(".sw-btn-prev");
        btnPrev.hide();
    });

    function nextEnable(){
        btnNext.removeAttr("disabled");
    }

    function nextDisable(){
        btnNext.prop("disabled", "disabled");
    }

    //检查授权确认
    function checkLicense(chk) {
        if (chk.checked) {
            nextEnable();
        } else {
            nextDisable();
        }
    }
    //检查数据库配置是否可用
    function checkDatabase(btn){
        var params = getDatabaseParams();
        $(btn).button("loading");
        jQuery.post("database.jsp", params, function(data){
            if(data.success == true){
                alert("连接成功，请点击下一步");
                nextEnable();
            }else{
                alert("连接失败");
                nextDisable();
            }
            $(btn).button("reset");
        });
    }

    function getDatabaseParams(para){
        var params = para || {};
        var elements = $("#databaseForm").serializeArray();
        $.each(elements, function() {
            params[this.name] = this.value;
        });
        return params;
    }

    function getAdminParams(para){
        var params = para || {};
        var elements = $("#adminForm").serializeArray();
        $.each(elements, function() {
            params[this.name] = this.value;
        });
        return params;
    }

    function complete(){
        var params = {};
        params = getDatabaseParams(params);
        params = getAdminParams(params);

        nextDisable();
        jQuery.post("install.jsp", params, function(data){
            if(data.success == true){
                var div = $("#complete");
                div.find("#load").hide();
                div.find("#ok").show();

                setTimeout(function(){
                    window.location.href = "../index";
                }, 1000);
            }else{
                alert("安装失败，请重试！");
                window.location.reload();
            }
        });
    }
</script>
</html>