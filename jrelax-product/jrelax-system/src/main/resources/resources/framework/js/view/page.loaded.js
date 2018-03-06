/**
 * Created by zengc on 2016-07-31.
 */
try {
    $(".gallery-loader").fadeOut(function () {
        $(".gallery-loader").remove();
    });
    $(function () {
        if ($.support.pjax) {
            $(document).pjax('a[data-pjax]', '.container', {
                maxCacheLength: 0,
                push: false,
                replace: true,
                fragment: '.container',
                timeout: 8000
            });
            $('.nav a').on('click', function () {
                $(".sidebar .nav li.active>a.active").removeClass('active');
                $(".sidebar .nav li.active").removeClass('active');
                var firstLi = $(this).parents("ul").children("li.open");
                firstLi.removeClass("open");

                $(this).parents("li:not(.open)").addClass('active');
                var curLi = $(this).parents(".sidebar .nav").children("li.active");
                curLi.addClass("open");
                curLi.find("a:first").addClass("active");
                $(this).addClass('active');
            });
            $(document).on('pjax:send', function () { //pjax链接点击后显示加载动画；
                $("script.pjax-script").remove();
                ns.showLoading();
            });
            $(document).on('pjax:success', function (a, b, c, d, e) {
                var elements = $.parseHTML(b, null, true);
                $.each(elements, function (i, e) {
                    if (e.tagName) {
                        if (e.tagName.toUpperCase() == "LINK") {
                            if (e.className == "no-load") return;
                            if (e.href) {
                                ns.requireCSS(e.href);
                            }
                        } else if (e.tagName.toUpperCase() == "SCRIPT") { //先加载引入的js
                            if (e.className == "no-load") return;
                            if (e.src && e.src.length > 0) {
                                //复制脚本
                                var script = $(e);
                                script.attr("class", "pjax-script");
                                $("body").append(script);
                            }
                        }
                    }
                });
                ns.execReadyEvents();
                $.each(elements, function (i, e) {
                    if (e.tagName) {
                        if (e.tagName.toUpperCase() == "SCRIPT") {// 再加载页面上直接书写的js脚本
                            if (e.className == "no-load") return;
                            if (!e.src || e.src.length == 0) {
                                //复制脚本
                                var script = $(e);
                                script.attr("class", "pjax-script");
                                $("body").append(script);
                            }
                        }
                    }
                });
                ns.execReadyEvents();
//                    //优化滚动条显示
//                    $(".content-wrap .wrapper").each(function () {
//                        if (!$(this).hasClass("no-scroll")) {
//                            $(this).slimScroll({
//                                alwaysVisible: false
//                            });
//                        }
//                    });
//                    ns.initContentWrap();
            });
            $(document).on('pjax:complete', function (data, status, xhr, options) { //pjax链接加载完成后隐藏加载动画；
                ns.closeLoading();
            });
        }
    });
} catch (e) {
}