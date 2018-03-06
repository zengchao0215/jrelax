/**
 * Created by zengchao on 2017/3/12.
 */
$(function () {
    var iframe = $("#indexFrame");

    //计算iframe高度
    function autoHeight() {
        var height = $(document).height() - 54;
        iframe.height(height);
    }

    autoHeight();
    window.onresize = autoHeight;

    var Page = {
        navigateTo: function (url) {
            iframe.attr("src", url);

            //纵向菜单
            var sidebar = $(".sidebar a[data-tabs-link='" + url + "']");
            if (sidebar.length > 0) {
                sidebar.parents("nav").find("li.active").removeClass("active");
                sidebar.parent().addClass("active");
                initBreadcrumb(sidebar);
            }
            //横向菜单
            sidebar = $(".nav.vertical a[data-tabs-link='" + url + "']");
            if (sidebar.length > 0) {
                sidebar.parents(".nav").find("li.active").removeClass("active");
                sidebar.parent().addClass("active");
                sidebar.parents(".nav").find("li[data-first-res].selected").removeClass("selected");
                sidebar.parents("li[data-first-res]").addClass("selected");
                initBreadcrumb(sidebar);
            }

        }
    };

    //获取第一个资源
    var navbar = $(".app>header>.nav.navbar-nav.nav-menus,.app>.layout>aside.sidebar .nav");
    var welcomeRes = {
        name: navbar.find("a:first").attr("title") || "仪表板",
        path: navbar.find("a:first").attr("href") || ns.getBasePath() + "/welcome"
    };

    function initMenuLink() {
        $("a[data-tabs-link], a.tabs-link").each(function (i, n) {
            var a = $(n);
            if (a.attr("target") !== "_blank") {
                a.unbind("click");
                a.bind("click", function () {
                    var target = $(this);
                    var link = target.attr("data-tabs-link") || target.attr("href");
                    Page.navigateTo(link);
                });
                var link = a.attr("data-tabs-link") || a.attr("href");
                a.attr("data-tabs-link", link);
                a.attr("href", "javascript:;");
                a.removeAttr("target");
            }
        });
    }

    initMenuLink();

    //获取和监听hash
    window.onhashchange = function () {
        openTabWithHash(window.location.hash);
    };

    function openTabWithHash(hash) {
        if (hash.length > 1) {
            hash = hash.substring(1);
            var url = ns.getBasePath() + hash;
            var sider = $(".sidebar a[data-tabs-link='" + url + "'],.nav.vertical a[data-tabs-link='" + url + "']");
            if (sider.length > 0) {
                Page.navigateTo(url);
                return true;
            }
        }
        return false;
    }

    if (!openTabWithHash(window.location.hash)) {
        Page.navigateTo(welcomeRes.path);
    }

    function initBreadcrumb(sidebar, elements) {
        var name = jQuery.trim(sidebar.text());
        if (!elements) elements = [];
        elements.push({name: name, url: sidebar.data("tabs-link")});

        var parent = sidebar.parent().parents("li");
        if (parent.length > 0) {
            initBreadcrumb(parent.find("a:first"), elements);
        } else {
            elements.push({name: welcomeRes.name, url: welcomeRes.path});
            elements.reverse();

            var breadcrumb = "";
            for (var i = 0; i < elements.length; i++) {
                var el = elements[i];

                breadcrumb += "<li><a href=\"javascript:;\">" + el.name + "</a></li>";
            }

            $("#indexNav").html(breadcrumb);
            //选中最后一个节点
            $("#indexNav li").eq(elements.length - 1).addClass("active");
        }
    }
});
