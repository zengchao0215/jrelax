//登录脚本
var login = (function () {
    var def = {
        basePath: "",
        success: function (data) {
        },
        error: function (msg, type) {
        },
        onCheck: function (el, type) {
            if (jQuery.trim(el.val()).length === 0) {
                var msg = "请输入";
                if (type === "username") msg += "用户名";
                else if (type === "password") msg += "密码";

                el.focus();
                return {pass: false, msg: msg};
            }
            return {pass: true};
        }
    };
    var options = null;

    //获取转向URL
    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg); //匹配目标参数
        if (r != null) return unescape(r[2]);
        return null; //返回参数值
    }

    var redirectUrl = getUrlParam("redirect_url");
    if (!redirectUrl) redirectUrl = "";

    return {
        init: function (form, opt) {//初始化登录
            options = $.extend(def, opt);

            var loginForm = $(form);
            loginForm.attr("action", options.basePath + "/signin/do");
            loginForm.ajaxForm({
                beforeSubmit: function (arr, form, opt) {
                    var username = options.onCheck(loginForm.find("input[name='username']"), "username");
                    if (!username.pass) {
                        options.error(username.msg, "invalid-username");
                        return false;
                    }
                    var password = options.onCheck(loginForm.find("input[name='password']"), "password");
                    if (!password.pass) {
                        options.error(password.msg, "invalid-password");
                        return false;
                    }
                },
                success: function (data, statusText, xhr, $form) {
                    if (data.success === true) {
                        options.success(data);
                    } else {
                        options.error("用户名或密码错误", "invalid");
                    }
                }
            });
        },
        redirect: function () {//转向
            if (redirectUrl && redirectUrl.length > 0) {
                window.location.href = redirectUrl;
            } else {
                window.location.href = options.basePath + "/index";
            }
        }
    }
})();