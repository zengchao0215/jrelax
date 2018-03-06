/**
 * 侧边栏
 * Created by zengchao on 2017/7/7.
 */
ns.component("view.Sidebar", function (options) {
    var _def = {
        direction: "right",//来源方向
        title: "标题",//标题
        width: "400px",
        url: undefined,//链接地址
        params: {},//参数
        target: undefined,//目标
        onShown: function () {//打开后
        },
        onHidden: function (data) {

        }
    }

    options = $.extend(_def, options);

    this.show = function () {
        if (options.target) return showLocal(options.target);
        else if (options.url) return showRemote(options.url, options.params);
        else alert("参数错误");
    }

    function showLocal(element) {
        if ($.browser.mobile) {
            options.width = "300px";
        }
        if (!top._openSidebars_) {
            top._openSidebars_ = {};
        }
        var sidebar = $(element);
        var container = sidebar.find(".sidebar-panel-container");
        container.css("width", options.width);
        container.find(".sidebar-panel-content").css("height", container.height() - container.find(".sidebar-panel-header").height());
        if ($(".main-content>header").length === 0)
            sidebar.find(".sidebar-panel-container").css("marginTop", "0px");

        sidebar.addClass('is-visible');
        sidebar.find("[autofocus]:first").focus();

        bindFrameEvent(sidebar);
        bindContentEvent(sidebar);
        return sidebar;
    }

    //绑定外层事件
    function bindFrameEvent(sidebar) {
        sidebar.unbind("click");
        sidebar.bind("click", function (event) {
            if ($(event.target).is('.sidebar-panel') || $(event.target).is('.sidebar-panel-close')) {
                sidebar.close();
            }
        });

        sidebar.hide = function () {
            sidebar.removeClass("is-visible");
        };
        sidebar.close = function (data) {
            data = data || {};
            sidebar.hide();
            if (options.url) {
                setTimeout(function () {
                    options.onHidden(data);
                    delete top._openSidebars_ [sidebar.attr("id")];
                    sidebar.remove();
                }, 500)
            }
        };
        sidebar.find("a.sidebar-panel-close").on("click", function () {
            sidebar.close();
        });
    }

    //绑定内容事件
    function bindContentEvent(sidebar) {
        sidebar.find(".sidebar-close").bind("click", function () {
            sidebar.close();
        });
    }

    function showRemote(url, params) {
        var id = "sidebar-panel-" + (new Date()).getTime();
        var panel = "<div id=\"" + id + "\" class=\"sidebar-panel from-" + options.direction + "\">";
        panel += "<div class=\"sidebar-panel-container shadow\" style=\"width:" + options.width + "\">";
        panel += "<header class=\"sidebar-panel-header\"><h4>" + options.title + "</h4><a href=\"javascript:;\" class=\"sidebar-panel-close text-center\"><i class=\"ti-close\"></i> </a></header>";
        panel += "<div class=\"sidebar-panel-content\">";
        panel += ns.tip.progress.circle();
        panel += "</div></div></div>";

        $("body").append(panel);
        var sidebar = showLocal("#" + id);
        // //延迟显示
        // setTimeout(function () {
        //     $("#" + id + " header>a").bind("click", function () {
        //         $("#" + id).click();
        //         setTimeout(function () {
        //             $("#" + id).remove();
        //         }, 500);
        //     });
        //     sidebar = showLocal(sidebar);
        // }, 100);

        ns.load("#" + id + " .sidebar-panel-content", url, params, function () {
            ns.execReadyEvents();
            bindContentEvent(sidebar);
            options.onShown();
        });
        top._openSidebars_[id] = sidebar;
        return sidebar;
    }
});

ns.view.Sidebar.getCur = function (element) {
    var sidebar = $(element).parents(".sidebar-panel");
    if (sidebar.length > 0) {
        var id = sidebar.attr("id");
        return top._openSidebars_ [id];
    }
    return undefined;
}

ns.view.Sidebar.close = function (element, data) {
    var sidebar = ns.view.Sidebar.getCur(element);
    if (sidebar) {
        sidebar.close(data);
    }
}