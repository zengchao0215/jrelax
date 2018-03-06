/**
 * 首页Tab相关
 * Created by zengchao on 2016-09-21.
 */
var Tabs;

function createFrame(src) {
    return "<iframe src=\"" + src + "\" width=\"100%\" height=\"100%\" frameborder=\"0\"></iframe>"
}

$(function () {
    function hash(str) {
        var hash = 0;
        if (str.length == 0) return hash;
        for (i = 0; i < str.length; i++) {
            char = str.charCodeAt(i);
            hash = ((hash << 5) - hash) + char;
            hash = hash & hash; // Convert to 32bit integer
        }
        return Math.abs(hash);
    }

    function headerTabHandler(first) {
        var navs = $(".app>.header>.tab-nav ul");
        if (navs.length > 0) {
            var html = "";
            var len = Tabs.tabs.length;
            for (var i = 0; i < len; i++) {
                var tab = Tabs.tabs[i];
                var title = tab.title.text();
                var id = tab.id;
                html += "<li>";
                html += "<a href=\"javascript:;\" id='" + id + "'>" + title + "</a>";
                html += "<span class=\"point\"></span>";
                if (i > 0)
                    html += "<span class=\"closer\"><i class='fa fa-times-circle'></i></span>";
                html += "</li>";
            }
            navs.html(html);
            //处理事件
            navs.find("li>a").on("click", function () {
                var index = $(this).parent().index();
                Tabs.show(index);
            });
            navs.find("li>.closer").on("click", function () {
                var index = $(this).parent().index();
                Tabs.kill(index);
            });
            //处理默认焦点
            headerTabActiveHandler(Tabs.getActiveIndex());
        }
        if (first) {
            //设置滚动条样式
            navs.slimScroll({
                height: '41px',
                wheelStep: 40,
                size: '3px'
            });
            $(window).on("resize", function () {
                headerTabWidthHandler();
            });
            headerTabWidthHandler();
        }
    }

    function headerTabActiveHandler(pos) {
        var navList = $(".app>.header>.tab-nav ul>li");
        navList.removeClass("active");
        var cur = navList.eq(pos).addClass("active");
        var offset = cur.position();
        if (offset) {
            var navs = $(".app>.header>.tab-nav ul");
            navs.animate({
                scrollTop: cur.offset().top - navs.offset().top + navs.scrollTop()
            }, 500);
        }
    }

    function headerTabWidthHandler() {
        var header = $(".app>.header");
        var width = header.width();
        var children = header.children();
        for (var i = 0; i < children.length; i++) {
            var child = $(children[i]);
            if (!child.hasClass("tab-nav") && !child.hasClass("nav-menus") && !child.is("script")) {
                width -= child.width();
            }
        }
        header.find(".tab-nav").css("maxWidth", width + "px").css("width", width + "px");
        header.find(".nav-menus").css("maxWidth", width + "px").css("width", width + "px");
    }

    //获取第一个资源
    var navbar = $(".app>header>.nav.navbar-nav.nav-menus,.app>.layout>aside.sidebar .nav");
    var welcomeRes = {
        name: navbar.find("a:first").attr("title") || "仪表板",
        path: navbar.find("a:first").attr("href") || ns.getBasePath() + "/welcome"
    };
    Tabs = new TabPanel({
        renderTo: 'tab',
        width: "100%",
        height: $(".wrapper").height(),
        border: 'none',
        autoResizable: true,
        widthResizable: true,
        active: 0,
        items: [{
            id: 'tab_' + hash(welcomeRes.path),
            icon2: "<i class='glyphicon glyphicon-home'></i>",
            title: welcomeRes.name,
            html: createFrame(welcomeRes.path),
            closable: false
        }],
        onActive: function (pos) {
            if (Tabs) {
                if (Tabs.setWindowHash)
                    Tabs.setWindowHash();
                if (Tabs.setActiveMenu) {
                    if (Tabs.getActiveTab().url)
                        Tabs.setActiveMenu(Tabs.getActiveTab().url);
                }
                headerTabActiveHandler(pos);
            }
        },
        onUpdate: function () {
            if (Tabs) {
                headerTabHandler(false);
            }
        }
    });
    headerTabHandler(true);
    Tabs.toggle = function () {
        $(".tabpanel_tab_content").toggle("display");
        Tabs.resize();
    }
    //增加Tab
    Tabs.add = function (url, title, icon) {
        var id = "tab_" + hash(url);
        Tabs.addTab({
            id: id,
            icon2: "<i class='" + icon + "'></i>",
            title: title,
            html: createFrame(url),
            closable: true,
            url: url
        });
        Tabs.bindContextMenu();
        Tabs.setActiveMenu(url);
        Tabs.setWindowHash();
        return Tabs.getActiveIndex();
    }
    //定位显示当前菜单
    Tabs.setActiveMenu = function (url) {
        //纵向菜单
        var sidebar = $(".sidebar a[data-tabs-link='" + url + "']");
        if (sidebar.length > 0) {
            sidebar.parents("nav").find("li.active").removeClass("active");
            sidebar.parent().addClass("active");
        }
        //横向菜单
        sidebar = $(".nav.vertical a[data-tabs-link='" + url + "']");
        if (sidebar.length > 0) {
            sidebar.parents(".nav").find("li.active").removeClass("active");
            sidebar.parent().addClass("active");
            sidebar.parents(".nav").find("li[data-first-res].selected").removeClass("selected");
            sidebar.parents("li[data-first-res]").addClass("selected");
        }
    }
    Tabs.setWindowHash = function () {
        var tab = Tabs.getActiveTab();
        if (tab && tab.url)
            window.location.hash = tab.url.replace(ns.getBasePath(), "");
        else window.location.hash = "";
    }
    var contextMenu = $("#tab_context_menu");
    Tabs.bindContextMenu = function () {//右键菜单
        var tab_control = $(".tabpanel_tab_content .tabpanel_mover>li,.header>.tab-nav ul>li");
        tab_control.unbind("contextmenu");
        tab_control.bind("contextmenu", function (e) {
            var pageX = e.pageX;
            var pageY = e.pageY;

            contextMenu.css("position", "absolute");
            contextMenu.css("left", pageX).css("top", pageY);

            contextMenu.fadeIn("fast");

            Tabs.curIndex = $(this).index();
            Tabs.cur = $(".tabpanel_tab_content .tabpanel_mover>li").eq(Tabs.curIndex);

            if (Tabs.curIndex === 0) {
                contextMenu.find("li:first").addClass("disabled");
            } else {
                contextMenu.find("li:first").removeClass("disabled");
            }

            return false;
        });

        $(document).on("click", function () {
            contextMenu.fadeOut("fast");
        });
    }
    Tabs.bindContextMenu();
    // $(".app a[data-pjax]").each(function (i, n) {
    //     var a = $(n);
    //     var href = a.attr("href");
    //     if (href != "javascript:;" && a.attr("target") != "_blank") {
    //         a.bind("click", function () {
    //             var url = a.attr("url");
    //             if (url == ns.getBasePath() + "/index") {
    //                 this.show("index", false);
    //                 return;
    //             }
    //             var title = a.attr("title");
    //             var icon = a.attr("icon");
    //             if (icon == "") icon = "fa fa-file";
    //             Tabs.add(url, title, icon);
    //         });
    //         a.attr("href", "javascript:;");
    //         a.attr("url", href);
    //     }
    // });
    ns.view.initTabsLink();
    //关闭当前
    Tabs.closeCur = function () {
        if (Tabs.curIndex != 0)
            Tabs.kill(Tabs.curIndex);
    }
    //全部关闭
    Tabs.closeAll = function () {
        var len = Tabs.tabs.length;
        for (var i = 1; i < len; i++) Tabs.kill(1);
    }
    //关闭其他
    Tabs.closeOther = function () {
        Tabs.closeLeft();
        Tabs.curIndex = Tabs.cur.index();
        Tabs.closeRight();
    }
    //关闭左侧
    Tabs.closeLeft = function () {
        for (var i = 1; i < Tabs.curIndex; i++) Tabs.kill(1);
        Tabs.curIndex = Tabs.cur.index();
        Tabs.show(Tabs.curIndex);
    }
    //关闭右侧
    Tabs.closeRight = function () {
        var len = Tabs.tabs.length;
        for (var i = Tabs.curIndex + 1; i < len; i++) Tabs.kill(Tabs.curIndex + 1);
        Tabs.curIndex = Tabs.cur.index();
        Tabs.show(Tabs.curIndex);
    }
    //重新加载
    Tabs.reload = function (position) {
        var iframe = this.tabs[position].content.find('iframe');
        if (iframe.length > 0) iframe.attr("src", iframe.attr("src"));
    }
    //重新加载当前
    Tabs.reloadCur = function () {
        Tabs.reload(Tabs.curIndex);
    }
    //添加到快捷菜单
    Tabs.addToQuickMenu = function () {
        ns.view.showModal(ns.getBasePath() + "/profile/quickmenu/add", {
            onShown: function (dialog) {
                var context = Tabs.cur;
                var iframe = Tabs.tabs[context.index()].content.find('iframe')[0];
                if (iframe) {
                    var name = context.text();
                    var url = iframe.contentWindow.location.href;

                    dialog.find("input[name='name']").val(name);
                    dialog.find("input[name='url']").val(url);
                }
            },
            onHidden: function (dialog, obj) {

            }
        });
    }

    //获取hash
    openTabWithHash(window.location.hash);

    window.onhashchange = function () {
        openTabWithHash(window.location.hash);
    }

    function openTabWithHash(hash) {
        if (hash.length > 1) {
            hash = hash.substring(1);
            var sidea = $(".sidebar a[data-tabs-link='" + ns.getBasePath() + hash + "'],.nav.vertical a[data-tabs-link='" + ns.getBasePath() + hash + "']");
            if (sidea.length > 0) {
                Tabs.add(sidea.attr("data-tabs-link"), sidea.attr("title"), sidea.attr("icon"));
            }
        }

    }

});