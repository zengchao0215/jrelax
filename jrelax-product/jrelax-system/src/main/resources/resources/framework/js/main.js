(function (a) {
    (jQuery.browser = jQuery.browser || {}).mobile =
        /(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i
            .test(a) ||
        /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i
            .test(a.substr(0, 4))
})(navigator.userAgent || navigator.vendor || window.opera);
Modernizr.addTest("retina", window.devicePixelRatio > 1);
Modernizr.addTest("webkit", navigator.userAgent.match(/AppleWebKit/i));
Modernizr.addTest("ipad", navigator.userAgent.match(/iPad/i));
Modernizr.addTest("iphone", navigator.userAgent.match(/iPhone/i));
Modernizr.addTest("ipod", navigator.userAgent.match(/iPod/i));
Modernizr.addTest("ios", Modernizr.ipad || Modernizr.ipod || Modernizr.iphone);
Modernizr.addTest("android", navigator.userAgent.match(/Android/i));
var offscreen = function () {
    var container = $(".app"), canvasDirection, direction;

    function hide() {
        container.removeClass("move-left move-right");
        canvasDirection = "";
        setTimeout(function () {
            container.removeClass("offscreen")
        }, 300)
    }

    function toggle(direction) {
        if (direction !== undefined && direction === "rtl") {
            container.addClass("offscreen move-right").removeClass("move-left");
            canvasDirection = "rtl"
        } else {
            container.addClass("offscreen move-left").removeClass("move-right");
            canvasDirection = "ltr"
        }
    }

    function fixSlimScroll() {
        if (!$.browser.mobile && !ns.view.checkBreakout()) {
            var offscreenLeft = $(".offscreen-left");
            var offscreenRight = $(".offscreen-right");
            if (canvasDirection === "ltr") {
                if (offscreenLeft.find(".slimScrollDiv").length !== 0) {
                    offscreenLeft.find(".main-navigation, .slimscroll").slimScroll({height: "auto"})
                }
            }
            if (canvasDirection === "rtl") {
                if (offscreenRight.find(".slimScrollDiv").length !== 0) {
                    offscreenRight.find(".main-navigation, .slimscroll").slimScroll({height: "auto"})
                }
            }
        }
    }

    function events() {
        $("[data-toggle=offscreen]").on("click touchstart", function (e) {
            e.preventDefault();
            e.stopPropagation();
            direction = $(this).data("move");
            if (direction === canvasDirection) {
                hide();
                return
            }
            toggle(direction);
            fixSlimScroll()
        });
        $(".exit-offscreen").on("click touchstart", function (e) {
            e.preventDefault();
            e.stopPropagation();
            hide()
        })
    }

    return {
        hide: hide, init: function () {
            events()
        }
    }
}();
var ns = function () {
    var searchOpen = false,
        app = $(".app"),
        maxHeight = 0;

    function events() {
        $(".offscreen-left, .main-navigation").ontouchstart = function () {
        };
        $(".accordion > dd").hide();
        $(window).on("resize", function () {
            equalHeightWidgets();
            if (!$.browser.mobile && !checkBreakout()) {
                $(".no-touch .main-navigation").slimScroll({
                    height: "auto"
                });
                $(".no-touch .slimscroll").slimScroll({
                    height: "auto"
                });
                initFooterFix();
            }
            ns.view.initContentWrap();
            ns.view.initDropdownMenuDirection();
        });
        $(document).mouseup(function () {
            if (searchOpen === true) {
                $(".toggle-search").click()
            }
        });
        $(".toggle-search").mouseup(function () {
            return false
        });
        $(".header-search").mouseup(function () {
            return false
        });

        //定义全局异步请求
        $(document).bind("ajaxComplete", function (event, xhr, settings) {
            //判断响应内容是否是json格式
            if (/^\[|^\{/m.test(xhr.responseText)) {
                try {
                    var data = jQuery.parseJSON(xhr.responseText);
                    if (data.type === "sessionTimeout") {//响应超时
                        ns.alert(data.message, function () {
                            top.window.location.reload();
                        });
                        return false;
                    } else if (data.type == "noPermission" || data.type == "needPassword" || data.type == "notEnable" || data.type == "notAllow") {
                        settings.success({success: false, message: data.message}, xhr.status, xhr);
                        return false;
                    }
                } catch (e) {
                    console.dir(e);
                }
            }
        });
        $(document).bind("ajaxError", function (event, xhr, settings) {
            if ($.isFunction(settings.success))
                settings.success({success: false, message: '网络请求异常：' + xhr.status}, xhr.status, xhr);
            else
                ns.error('网络请求异常：' + xhr.status);
        });
    }

    function checkBreakout() {//界面样式检测
        var state = false;
        if (app.hasClass("small-menu") || app.hasClass("fixed-scroll")) {
            state = true;
        }
        return state;
    }

    function initAnimationAPI() {//UI动画
        if (!$.browser.mobile && $.fn.appear) {
            var animations = $("[data-animation]");
            animations.appear({interval: 100});
            animations.on("appear", function () {
                var elm = $(this),
                    animation = elm.data("animation") || "fadeIn",
                    delay = elm.data("delay") || 0;
                if (!elm.hasClass("done")) {
                    setTimeout(function () {
                        elm.addClass("animated " + animation + " done")
                    }, delay)
                }
            })
        } else {
            $("[data-animation]").each(function () {
                var elm = $(this),
                    animation = elm.data("animation") || "fadeIn";
                if (!elm.hasClass("done")) {
                    elm.addClass("animated " + animation + " done");
                }
            })
        }
    }

    function initAnimateProgressBars() {//进度条动画
        if (!$.browser.mobile && $.fn.appear) {
            var progressBar = $(".progress-bar");
            progressBar.appear();
            progressBar.on("appear", function () {
                var elm = $(this),
                    percent = elm.data("percent");
                if (!elm.hasClass("done")) {
                    elm.addClass("done").css("width", Math.ceil(percent) + "%")
                }
            })
        } else {
            $(".progress-bar").each(function () {
                var elm = $(this),
                    percent = elm.data("percent");
                if (!elm.hasClass("done")) {
                    elm.addClass("done").css("width", Math.ceil(percent) + "%")
                }
            })
        }
    }

    // function initBrowserFix() {//修复火狐样式
    //     if (navigator.userAgent.search("Firefox") >= 0) {
    //         $(".layout > aside, .layout > section").wrapInner(
    //             '<div class="fffix"/>')
    //     }
    // }

    function equalHeightWidgets() {//计算高度
        $(".equal-height-widgets").each(function () {
            maxHeight = 0;
            $(this).find(".widget").each(function () {
                if ($(this).innerHeight() > maxHeight) {
                    maxHeight = $(this).innerHeight();
                }
            });
            $(this).find(".widget").each(function () {
                $(this).height(maxHeight);
            });
        });
    }

    // function initPlacehoderFallback() {//初始化placeholder
    //     $("input, textarea").placeholder()
    // }

    function initHeaderSearch() {//初始化头部搜索
        $(document).on("click", ".toggle-search", function () {
            if (!searchOpen) {
                $(".header-search").addClass("open");
                $(".search-container .search").focus();
                searchOpen = true
            } else {
                $(".header-search").removeClass("open");
                $(".search-container .search").focusout();
                searchOpen = false
            }
        });
    }

    function initMenuCollapse() {//初始化系统菜单
        $(document).on("click", ".main-navigation a", function (e) {
            var links = $(this).parents('li'),
                parentLink = $(this).closest("li"),
                otherLinks = $('.main-navigation li').not(links),
                subMenu = $(this).next();
            if (!subMenu.hasClass("sub-menu")) {
                offscreen.hide();
                return;
            }
            if (app.hasClass("small-menu") && parentLink.parent().hasClass("nav") && $(window).width() > 767) {
                return;
            }
            otherLinks.removeClass('open');
            otherLinks.find('.sub-menu').slideUp();
            if (subMenu.is("ul") && (!subMenu.is(":visible")) && (!app.hasClass("small-sidebar"))) {
                subMenu.slideDown();
                parentLink.addClass("open");
            } else if (subMenu.is("ul") && (subMenu.is(":visible")) && (!app.hasClass("small-sidebar"))) {
                parentLink.removeClass("open");
                subMenu.slideUp();
            }
            if ($(this).hasClass('active') === false) {
                $(this).parents("ul.dropdown-menu").find('a').removeClass('active');
                $(this).addClass('active');
            }
            if ($(this).attr('href') === '#') {
                e.preventDefault();
            }
            if (subMenu.is("ul")) {
                return false;
            }
            e.stopPropagation();
            return true;
        });
        $(".main-navigation > .nav > li.open").each(function () {
            $(".sub-menu").hide();
            $(this).children(".sub-menu").show();
            $(this).find("li.open").children(".sub-menu").show();
        });
        $(".no-touch .main-navigation, .no-touch .slimscroll").each(function () {
            if (checkBreakout() || app.hasClass("fixed-scroll") || $.browser.mobile) {
                return;
            }
            var data = $(this).data();
            $(this).slimScroll(data);
        });
        if (!$.browser.mobile) {
            //自动添加滚动条，除非有no-scroll样式
            $(".notifications .dropdown-menu .panel .content").each(function () {
                if (!$(this).hasClass("no-scroll")) {
                    $(this).slimScroll({
                        alwaysVisible: false
                    });
                }
            });
        }

    }

    function initToggleActiveState() {//切换active样式
        $(document).on("click touchstart", ".toggle-active", function (e) {
            $(this).toggleClass("active");
            e.preventDefault();
            e.stopPropagation()
        })
    }

    function initToggleSidebar() {//展开/收缩菜单
        $(document).on("click touchstart", ".toggle-sidebar", function (e) {
            e.preventDefault();
            e.stopPropagation();
            if (app.hasClass("small-menu")) {
                app.removeClass("small-menu");
                if (!$.browser.mobile && !checkBreakout() && $.fn.slimScroll) {
                    $(".no-touch .main-navigation").each(function () {
                        var data = $(this).data();
                        $(this).slimScroll(data)
                    })
                }
            } else {
                if (!app.hasClass("small-menu")) {
                    app.addClass("small-menu");
                    $(".main-navigation").each(function () {
                        $(this).slimScroll({
                            destroy: true
                        }).removeAttr("style")
                    })
                }
            }
            saveLayout(false);
            if (top.Tabs) {
                //Tabs自适应宽高
                setTimeout(function () {
                    top.Tabs.resize();
                }, 500);
            }
        })
    }

    //保存
    function saveLayout(reload) {
        var cls = "";
        if (app.hasClass("small-menu")) {
            cls = "small-menu";
        } else if (app.hasClass("layout-v")) {
            cls = "layout-v";
        } else if (app.hasClass("layout-v2")) {
            cls = "layout-v2";
        } else {
            cls = "";
        }
        ns.post(ns.getBasePath() + "/index/change/layout", {layout: cls}, function () {
            if (reload) window.location.reload();
        });
    }

    function initToggleVertical() {// 切换菜单显示模式
        $(document).on("click touchstart", ".toggle-vertical", function (e) {
            app.attr("class", "app " + $(this).attr("layout"));
            e.preventDefault();
            e.stopPropagation();
            saveLayout(true);
        });
        //初始化二级菜单显示
        $("ul.vertical li>ul>li a").hover(function () {
            $(this).parent().parent().find(".open:first").removeClass("open");
            if ($(this).attr("data-toggle")) {
                var submenu = $(this).next();
                submenu.css("left", submenu.width());
                submenu.css("top", $(this).offset().top - 52);
                $(this).parent().addClass("open");
            }
        }, function () {
        });
    }

    function initContentWrap() {//自适应高度
        $("aside, .main-content").each(function () {
            var height = $(window).height();
            height -= $(".app>header").height();
            //自适应高度
            if ($(this).find("header").length > 0) {
                height -= $(this).find("header").height();
            }
            if ($(this).find("footer").length > 0) {
                height -= $(this).find("footer").height();
            }

            $(this).find(".content-wrap .wrapper").css("minHeight", height);
            $(this).find(".content-wrap .wrapper").css("top", $(this).find("header").height());
        });
        //全屏
        $(".content-wrap .wrapper-full").css("minHeight", $(window).height());
    }

    function initFooterFix() {//footer底部固定
        $("footer").each(function () {
            var footerHeight = $(this).outerHeight();
            if ($(this).prev().hasClass("content-wrap")) {
                $(this).prev().find(".wrapper").css("bottom",
                    footerHeight)
            } else {
                if ($(this).prev().hasClass("slimScrollDiv")) {
                    $(this).prev().find(".main-navigation").css(
                        "padding-bottom", footerHeight)
                } else {
                    if ($(this).prev().hasClass("main-navigation")) {
                        $(this).prev().css("bottom", footerHeight)
                    }
                }
            }
        })
    }

    function initExtPrototype() {
        Array.prototype.contains = function (item) {
            return this.indexOf(item) >= 0;
        };
        String.prototype.startsWith = function (str) {
            var reg = new RegExp("^" + str);
            return reg.test(this);
        };
        String.prototype.endsWith = function (str) {
            var reg = new RegExp(str + "$");
            return reg.test(this);
        };
        String.prototype.trim = function () {
            return this.replace(/(^\s*)|(\s*$)/g, "");
        };
        String.prototype.replaceAll = function (source, target) {
            var val = this;
            while (val.indexOf(source) > -1) {
                val = val.replace(source, target);
            }
            return val;
        };
        Array.prototype.remove = function (dx) {
            if (isNaN(dx) || dx > this.length) {
                return false;
            }
            for (var i = 0, n = 0; i < this.length; i++) {
                if (this[i] != this[dx]) {
                    this[n++] = this[i]
                }
            }
            this.length -= 1
        }
    }

    function initDropdownMenuDirection() {// 初始化下拉菜单方向，上下左右
        $(".btn-group>.dropdown-menu").each(function () {
            var w = $(this).width() + 20;//15为padding
            var h = $(this).height();
            var l = $(this).prev().offset().left;
            var t = $(this).prev().offset().top;
            var sw = $(window).width();
            var sh = $(window).height();

            if (w + l > sw) {//控制横向
                if (!$(this).hasClass("pull-right"))
                    $(this).addClass("pull-right");
            } else {
                $(this).removeClass("pull-right");
            }
            if (h + t + 15 > sh) {//控制纵向
                if (!$(this).parent().hasClass("dropup"))
                    $(this).parent().addClass("dropup");
            } else {
                $(this).parent().removeClass("dropup");
            }
            //控制子项
            if (w + l + 160 > sw) {
                $(this).find("li.dropdown-submenu").addClass("left");
            } else {
                $(this).find("li.dropdown-submenu").removeClass("left");
            }
        });
    }

    //选项卡链接
    function initTabsLink(links) {
        if (top.Tabs) {
            links = links || $("a[data-tabs-link], a.tabs-link");
            links.each(function (i, n) {
                var a = $(n);
                if (a.attr("target") !== "_blank") {
                    a.unbind("click");
                    a.bind("click", function () {
                        var target = $(this);
                        var link = target.attr("data-tabs-link") || target.attr("href");
                        var title = target.attr("data-tabs-title") || target.attr("title") || target.text();
                        var icon = target.attr("data-tabs-icon") || target.attr("icon") || "fa fa-file";
                        top.Tabs.add(link, title, icon);
                    });
                    var link = a.attr("data-tabs-link") || a.attr("href");
                    a.attr("data-tabs-link", link);
                    a.attr("href", "javascript:;");
                    a.removeAttr("target");
                }
            });
        }
    }

    //box展开/收缩、关闭功能
    function initBoxTrigger() {
        $("[data-box-trigger='collapse']").on("click", function () {
            var a = $(this),
                c = $(this).parents(".box").first(),
                d = c.find("> .box-body, > .box-footer, > form  >.box-body, > form > .box-footer");
            c.hasClass("collapsed-box") ? (a.children(":first").removeClass("fa-plus").addClass("fa-minus"), d
                .slideDown(500, function () {
                    c.removeClass("collapsed-box")
                })) : (a.children(":first").removeClass("fa-minus").addClass("fa-plus"), d.slideUp(500, function () {
                c.addClass("collapsed-box")
            }))
        });
        $("[data-box-trigger='remove']").on("click", function () {
            $(this).parents(".box").remove();
        });
    }

    //立即执行的方法
    initExtPrototype();

    return {
        readyEvents: [],
        getBasePath: function () { //项目路径
            var path = $("#__basePath").val();
            if (path == undefined)
                path = "/nspms";
            return path;
        },
        checkBreakout: checkBreakout,
        initContentWrap: initContentWrap,
        initDropdownMenuDirection: initDropdownMenuDirection,//下拉菜单方向
        initTabsLink: initTabsLink,
        init: function () {
            events();
            initAnimationAPI();
            initAnimateProgressBars();
            initHeaderSearch();
            // initBrowserFix();
            initMenuCollapse();
            initToggleActiveState();
            initToggleSidebar();
            initToggleVertical();
            initContentWrap();
            initFooterFix();
            equalHeightWidgets();
            // initPlacehoderFallback();
            initDropdownMenuDirection();//下拉菜单方向
            initTabsLink();
            initBoxTrigger();
        },
        initCustomControls: function () {//页面自定义组件初始化
            if (ns.view.initDropdownMenuDirection) ns.view.initDropdownMenuDirection();
            if (ns.view.initTabsLink) ns.view.initTabsLink();
            if (ns.form.initChosen) ns.form.initChosen();
            if (ns.form.initDatePicker) ns.form.initDatePicker();
            if (ns.form.initCheckbox) ns.form.initCheckbox();
        },
        showProgressbar: function (title) {
            if (!top.ns.view.Dialog)
                top.ns.requireJS("/framework/js/view/dialog.js");
            var id = "__divAlert" + new Date().getTime();
            var content = "<div class='pb10'>" + ns.tip.progress.circle() + "</div>";
            var options = {
                id: id,
                title: "",
                theme: "modal-progressbar",
                size: "modal-sm",
                content: "<div class='p10'>" + content + "</div><h4 class=\"modal-title text-center pb10\">" + title + "</h4>",
                showHeader: false,
                keyboard: false,
                backdrop: "static"
            };
            var modal = new top.ns.view.Dialog(options);
            var $obj = modal.show();
            //100秒后自动关闭
            setTimeout(function () {
                $obj.close();
            }, 1000 * 100);
            //修改提示语
            $obj.setText = function (text) {
                $obj.find(".modal-title").text(text);
            };
            return $obj;
        },
        closeProgressbar: function (bar) {
            if (bar) {
                bar.close();
            } else {
                $(".modal-progressbar").each(function (i, bar) {
                    $(bar).modal("hide");
                });
            }
        },
        showLoading: function (obj) { //显示等待
            obj = obj || top.$("body");
            obj.prepend(
                "<div class=\"gallery-loader\" style=\"background-color:transparent;\"><div class=\"loader\"></div></div>");

            setTimeout(function () {
                ns.closeLoading(obj);
            }, 5000);//5s后自动关闭
        },
        closeLoading: function (obj) { //显示关闭
            obj = obj || top.$("body");
            obj.find(".gallery-loader").fadeOut(function () {
                obj.find(".gallery-loader").remove();
            });
        },
        showLoadingbar: function (obj) { //显示顶部进度条
            var clz = "waiting";
            if (!obj) {
                clz += " full";
            }
            if (typeof (obj) == "string")
                obj = $(obj);
            obj = obj || $("body");
            obj.prepend("<div id=\"loadingbar\" class='loadingbar'></div>");
            $("#loadingbar").addClass(clz).append($("<dt/><dd/>"));
            var width = (15 + Math.random() * 30);
            $("#loadingbar").width(width + "%");
            var interval = setInterval(function () {
                width = width + 10;
                if (width < 100) {
                    $("#loadingbar").width(width + "%");
                } else {
                    $("#loadingbar").width("101%");
                    clearInterval(interval);
                }
            }, 1000);
            $("#loadingbar").attr("interval", interval);

            //超时自动隐藏
            setTimeout(function () {
                ns.closeLoadingbar(obj);
            }, 5000);
        },
        closeLoadingbar: function (obj) { //关闭顶部进度条
            if (typeof (obj) == "string")
                obj = $(obj);
            obj = obj || $("body");
            obj.find("#loadingbar").width("101%").delay(200).fadeOut(400, function () {
                var interval = $(this).attr("interval");
                clearInterval(interval);
                obj.find(".loadingbar").remove();
            });
        },
        alert: function (content, ok, options) { //提示
            if (!top.ns.view.Dialog)
                top.ns.requireJS("/framework/js/view/dialog.js");
            var id = "__divAlert" + new Date().getTime();
            var def = {
                id: id,
                content: "<div class='modal-alert col-lg-12 col-md-12 col-xs-12'>" + content + "</div>",
                showFooter: true
            };
            options = $.extend(def, options);
            var maxSize = 18;
            if (options.theme === "modal-board") {
                options.okText = "关闭公告板";
                options.size = "modal-sm";
                options.backdrop = false;
                maxSize = 100;
            }
            if (ns.String.html_decode(content).length <= maxSize)
                options.size = "modal-sm";

            options.onShown = function (dialog, target) {
                dialog.find("#ok").focus();
                dialog.find("#ok").click(function () {
                    dialog.modal("hide");
                    dialog.ok = true;
                });
            }
            options.onHidden = function (dialog, target) {
                if (dialog.ok && typeof (ok) === "function") {
                    ok();
                }
            };
            var modal = new top.ns.view.Dialog(options);
            return modal.show();
        },
        confirm: function (content, ok, cancel) { //确认框
            if (!top.ns.view.Dialog)
                top.ns.requireJS("/framework/js/view/dialog.js");
            var id = "__divConfirm" + new Date().getTime();
            var options = {
                id: id,
                content: "<div class='modal-alert col-lg-12 col-md-12 col-xs-12'>" + content + "</div>",
                showFooter: true,
                showCancel: true
            };
            if (ns.String.html_decode(content).length <= 18)
                options.size = "modal-sm";

            options.onShown = function (dialog, target) {
                dialog.find("#ok").focus();
                dialog.find("#ok").click(function () {
                    var closeDialog = true;
                    if (typeof (ok) === "function") {
                        var btnOk = $(this);
                        btnOk.loading = function (text) {
                            btnOk.data('loading-text', text);
                            btnOk.button("loading");
                        };
                        btnOk.reset = function () {
                            btnOk.button("reset");
                        };
                        btnOk.close = function () {
                            dialog.close();
                        };
                        if (ok(btnOk) === false) {
                            closeDialog = false;
                        }
                    }
                    if (closeDialog)
                        dialog.close();
                });
                if (typeof (cancel) === "function") {
                    dialog.find("#cancel").bind("click", function () {
                        if (typeof(cancel) === "function")
                            cancel();
                        dialog.close();
                    });
                }
            };
            var modal = new top.ns.view.Dialog(options);
            return modal.show();
        },
        confirmPopover: function (target, title, ok, cancel, opt) {
            target = $(target);
            if (!target.attr("aria-describedby")) target.popover("destroy");
            var _def = {
                title: title,
                html: true,
                okText: "确定",
                cancelText: "取消",
                okIcon: "fa fa-check",
                cancelIcon: "fa fa-times"
            };
            opt = jQuery.extend(_def, opt);

            var content = "<div>";
            content += "<button id='btnOk' type='button' class='btn btn-primary mr5'><i class='" + opt.okIcon + "'></i> " + opt.okText + "</button>";
            content += "<button id='btnCancel' type='button' class='btn btn-default'><i class='" + opt.cancelIcon + "'></i> " + opt.cancelText + "</button>";
            content += "</div>";

            var pop = target.popover({
                title: "确定要删除吗？",
                html: true,
                content: content
            });
            pop.on("shown.bs.popover", function () {
                var popover = $(this).next();
                popover.find("#btnOk").on("click", function () {
                    if (typeof (ok) === "function") {
                        ok();
                    }
                    pop.popover("destroy");
                });
                popover.find("#btnCancel").on("click", function () {
                    if (typeof (cancel) === "function") {
                        cancel();
                    }
                    pop.popover("destroy");
                });
            });
            target.popover("show");
        },
        prompt: function (title, ok, cancel, opt) {
            if (!top.ns.view.Dialog)
                top.ns.requireJS("/framework/js/view/dialog.js");
            var id = "__divPrompt" + new Date().getTime();
            var _def = {
                password: false,//密码输入
                number: false,//纯数字输入
                email: false,//邮件输入
                textarea: false,//多行文本输入
                size: 'modal-sm',
                value: "", //默认值
                select: true //是否选中值
            };
            opt = jQuery.extend(_def, opt);
            var content = "<input name='content' type='text' class='form-control' autofocus />";
            if (opt.password)
                content = "<input name='content' type='password' class='form-control' autofocus />";
            if (opt.number)
                content = "<input type='number' class='form-control' autofocus />";
            if (opt.email)
                content = "<input type='email' class='form-control' autofocus />";
            if (opt.textarea)
                content = "<textarea name='content' class='form-control' autofocus rows='5'></textarea>";

            var options = {
                id: id,
                title: title,
                content: "<div class='col-lg-12 col-md-12'>" + content + "</div>",
                showHeader: true,
                showFooter: true,
                showCancel: true,
                size: opt.size
            };
            options.onShow = function (dialog, target) {
                if (opt.value)
                    dialog.find("textarea,input").val(opt.value);
                if (opt.select)
                    dialog.find("textarea,input").select();
            };
            options.onShown = function (dialog, target) {
                var control = dialog.find("textarea,input");
                control.focus();
                dialog.find("#ok").click(function () {
                    var closeDialog = true;
                    if (typeof (ok) === "function") {
                        var btnOk = $(this);
                        btnOk.loading = function (text) {
                            btnOk.data('loading-text', text);
                            btnOk.button("loading");
                            control.prop("disabled", true);
                        };
                        btnOk.reset = function () {
                            btnOk.button("reset");
                            control.prop("disabled", false);
                            control.select();
                        };
                        btnOk.close = function () {
                            dialog.close();
                        };
                        if (ok(control.val(), btnOk) === false) {
                            closeDialog = false;
                        }
                    }
                    if (closeDialog)
                        dialog.close();
                });
                dialog.find("input").on("keyup", function (event) {
                    if (event.keyCode === 13) {
                        dialog.find("#ok").click();
                    }
                });
                dialog.find("textarea").on("keydown", function (event) {
                    if (event.keyCode === 13 && event.ctrlKey) {
                        dialog.find("#ok").click();
                    }
                });
                if (typeof(cancel) === "function") {
                    dialog.find("#cancel").click(function () {
                        if (typeof(cancel) === "function")
                            cancel();
                        dialog.close();
                    });
                }
            };
            var modal = new top.ns.view.Dialog(options);
            return modal.show();
        },
        success: function (title, msg, fun) {
            if (typeof(top.swal) !== "function") {
                top.ns.requireCSS("/framework/plugins/sweetalert/sweetalert.css");
                top.ns.requireJS("/framework/plugins/sweetalert/sweetalert.min.js");
            }
            if ($.isFunction(msg)) {
                fun = msg;
                msg = "";
            }
            top.swal({
                title: title,
                text: msg,
                type: "success"
            }, fun)
        },
        error: function (title, msg, fun) {
            if (typeof(top.swal) != "function") {
                top.ns.requireCSS("/framework/plugins/sweetalert/sweetalert.css");
                top.ns.requireJS("/framework/plugins/sweetalert/sweetalert.min.js");
            }
            if ($.isFunction(msg)) {
                fun = msg;
                msg = "";
            }
            top.swal({
                title: title,
                text: msg,
                type: "error"
            }, fun)
        },
        __showImage: function (images) {
            if (!top.ns.view.Dialog)
                top.ns.requireJS("/framework/js/view/dialog.js");
            var imgs = [];
            if (typeof(images) == "string") {
                imgs.push(images);
            } else {
                imgs = images;
            }
            var w = 4;
            if (imgs.length < 3)
                w = 12 / imgs.length;
            var content = "<div class='image-modal p10'>";
            for (var i = 0; i < imgs.length; i++) {
                content += "<div class='col-xs-" + w + "'><img class='m3' src='" + imgs[i] + "'/></div>";
            }
            content += "</div>";
            var id = "__divImageModal" + new Date().getTime();
            var options = {
                id: id,
                content: content,
                showFooter: true
            };

            options.onShown = function (dialog, target) {
                dialog.find("#ok").focus();
                dialog.find("#ok").click(function () {
                    dialog.modal("hide");
                });
            }
            var modal = new top.ns.view.Dialog(options);
            return modal.show();
        },
        __showModal: function (url, options) { //显示弹窗
            if (!top.ns.view.Dialog)
                top.ns.requireJS("/framework/js/view/dialog.js");
            var id = "__divModal" + new Date().getTime();
            var _def = {
                id: id,
                remote: true,
                remoteUrl: url,
                size: "",
                border: false,
                theme: "",
                cache: false,
                onShown: function () {
                },
                onHidden: function () {
                }
            };
            options = $.extend(_def, options);
            options._onLoaded = function (dialog, target) {
                // $(target).find("script[src]").each(function (i, n) {
                //     ns.requireJS($(n).attr("src"));
                // });
                top.ns.execReadyEvents(dialog);
                top.ns.initCustomControls();
            }
            var modal = new top.ns.view.Dialog(options);
            return modal.show();
        },
        get: function (url, callback) {
            jQuery.ajax({
                type: "GET",
                url: url,
                success: function (data) {
                    if (typeof(callback) != "function") return;
                    var success = true;
                    if (data.success == false) {
                        success = false;
                    }
                    callback(success, data);
                },
                error: function (xhr, msg, e) {
                    if (typeof(callback) != "function") return;
                    var data = {success: false, error: "请求异常:" + xhr.status + " " + e};
                    callback(false, data);
                }
            });
        },
        /**
         * 异步提交 POST方式
         * @param url地址
         * @param param 参数列表
         * @param callback 回调函数，成功时第一个参数为true，失败是false， 第二个参数为服务器返回的数据
         * @returns
         */
        post: function (url, param, callback) { //异步Post提交
            jQuery.ajax({
                type: "POST",
                url: url,
                data: param,
                success: function (data) {
                    if (typeof(callback) != "function") return;
                    var success = true;
                    if (data.success == false) {
                        success = false;
                    }
                    callback(success, data);
                },
                error: function (xhr, msg, e) {
                    if (typeof(callback) != "function") return;
                    var data = {success: false, error: "请求异常:" + xhr.status + " " + e};
                    callback(false, data);
                }
            });
        },
        //同步get请求方法
        syncGet: function (url, callback) {
            jQuery.ajax({
                type: "GET",
                url: url,
                cache: false,
                async: false,
                success: function (data) {
                    if (typeof(callback) != "function") return;
                    var success = true;
                    if (data.success == false) {
                        success = false;
                    }
                    callback(success, data);
                },
                error: function (xhr, msg, e) {
                    if (typeof(callback) != "function") return;
                    var data = {success: false, error: "请求异常:" + xhr.status + " " + e};
                    callback(false, data);
                }
            });
        },
        //同步post请求
        syncPost: function (url, param, callback) {
            jQuery.ajax({
                type: "POST",
                url: url,
                data: param,
                cache: false,
                async: false,
                success: function (data) {
                    if (typeof(callback) != "function") return;
                    var success = true;
                    if (data.success == false) {
                        success = false;
                    }
                    callback(success, data);
                },
                error: function (xhr, msg, e) {
                    if (typeof(callback) != "function") return;
                    var data = {success: false, error: "请求异常:" + xhr.status + " " + e};
                    callback(false, data);
                }
            });
        },
        load: function (elementId, url, param, callback) {
            ns.showLoadingbar(".main-content");
            var timer = setTimeout(function () {
                $(elementId).html(ns.tip.progress.wave());
            }, 100);//延迟显示，提高网速顺畅时的浏览体验
            $(elementId).load(url, param, function (response, status, xhr) {
                clearTimeout(timer);
                if ($.isFunction(callback))
                    callback(response, status, xhr);
                ns.closeLoadingbar(".main-content");
            });
        },
        /**
         * 异步获取内容，并加载到页面上
         * @param url 地址
         * @param param 参数列表
         * @param elementId 目标对象ID
         * @param fn 加载完成后的回调函数
         * @returns
         */
        asyncRequest: function (url, param, elementId, fn) {
            ns.showLoadingbar(".main-content");
            var timer = setTimeout(function () {
                $(elementId).html(ns.tip.progress.wave());
            }, 100);//延迟显示，提高网速顺畅时的浏览体验
            jQuery.post(url, param, function (data) {
                clearTimeout(timer);
                if (typeof(data) === "string") {
                    var idx = data.indexOf("<HTML".toLowerCase());
                    if (idx >= 0)
                        data = data.substring(data.indexOf("<HTML".toLowerCase()));
                    if (elementId) {
                        var content = idx >= 0 ? $(elementId, data).html() : data;
                        $(elementId).html(content);
                        if ($.support.pjax) {
                            $(elementId).pjax('a[data-pjax]', '.container', {
                                maxCacheLength: 0,
                                push: false,
                                replace: true,
                                fragment: '.container',
                                timeout: 8000
                            })
                        }
                        if (typeof (fn) === "function")
                            fn(true);
                    } else {
                        if (typeof (fn) === "function")
                            fn(false);
                    }
                    ns.execReadyEvents();
                    ns.initCustomControls();
                } else {
                    $(elementId).html("<i class='fa fa-close text-danger'></i> 请求地址返回数据格式不被支持！");
                }
                ns.closeLoadingbar(".main-content");
            });
        },
        back: function (url, delay) { //延迟返回指定路径
            setTimeout(function () {
                window.location.href = url;
            }, delay);
        },
        requireJS: function (url) {//导入js
            var urls = [];
            if (typeof(url) === "string")
                urls.push(url);
            else
                urls = url;
            while (urls.length > 0) {
                url = urls.shift();
                if (url && url.startsWith("http")) {
                    url = url.substring(url.indexOf(ns.getBasePath()));
                }
                if (url && !url.startsWith(ns.getBasePath())) {
                    if (!url.startsWith("/"))
                        url = "/" + url;
                    url = ns.getBasePath() + url;
                }
                $.ajax({
                    url: url,
                    async: false,
                    cache: true,
                    type: "GET",
                    dataType: "script"
                });
            }
        },
        requireCSS: function (url) {//导入css
            var urls = [];
            if (typeof(url) === "string")
                urls.push(url);
            else
                urls = url;
            while (urls.length > 0) {
                url = urls.shift();
                if (url.startsWith("http")) {
                    url = url.substring(url.indexOf(ns.getBasePath()));
                }
                if (!url.startsWith(ns.getBasePath())) {
                    if (!url.startsWith("/"))
                        url = "/" + url;
                    url = ns.getBasePath() + url;
                }
                var isLoaded = false;
                $("link").each(function () {
                    var href = $(this).attr("href") + "";
                    if (href.endsWith(url)) {
                        isLoaded = true;
                        return false;
                    }
                });
                if (!isLoaded)
                    $("head").prepend("<link rel=\"stylesheet\" href=\"" + url + "\">");
            }
        },
        require: function (url, type) {//自动识别
            var urls = [];
            if (typeof(url) === "string")
                urls.push(url);
            else
                urls = url;
            while (urls.length > 0) {
                url = urls.shift();
                if (!type) {
                    var p1 = /\.js\b/;
                    var p2 = /\.css\b/;

                    if (p1.test(url)) type = 1;
                    if (p2.test(url)) type = 2;
                }
                switch (type) {
                    case 1:
                        ns.requireJS(url);
                        break;
                    case 2:
                        ns.requireCSS(url);
                        break;
                }
            }
        },
        ready: function (fun) {
            if (typeof(fun) === "function") {
                top.ns.readyEvents.push(fun);
            }
        },
        execReadyEvents: function (params) {
            while (top.ns.readyEvents.length > 0) {
                try {
                    top.ns.readyEvents.shift()(params);
                } catch (e) {
                    console.error(e);
                    alert("main:" + e);
                }
            }
        }
    }
}();
ns.tip = { //提示组件
    alert: ns.alert,
    confirm: ns.confirm,
    confirmPopover: ns.confirmPopover,
    prompt: ns.prompt,
    success: ns.success,
    error: ns.error,
    tooltip: {},
    toast: {},
    progress: {//进度提示
        wave: function () {
            var html = "<div class='sk-wave'>";
            for (var i = 0; i < 5; i++) html += "<div class='sk-rect sk-rect" + (i + 1) + "'></div>";
            return html + "</div>";
        },
        circle: function () {
            var html = "<div class='sk-circle'>";
            for (var i = 0; i < 12; i++) html += "<div class='sk-circle" + (i + 1) + " sk-child'></div>";
            return html + "</div>";
        }
    }
};
ns.view = { //视图组件
    checkBreakout: ns.checkBreakout,
    initContentWrap: ns.initContentWrap, //自适应高度
    initDropdownMenuDirection: ns.initDropdownMenuDirection,//自适应下拉菜单方向
    initTabsLink: ns.initTabsLink,//选项卡链接
    showLoading: ns.showLoading, //显示进度提示
    closeLoading: ns.closeLoading, //显示进度提示
    showProgressbar: ns.showProgressbar,//显示进度条
    closeProgressbar: ns.closeProgressbar,//显示进度条
    showLoadingbar: ns.showLoadingbar, //显示顶部进度提示
    closeLoadingbar: ns.closeLoadingbar, //关闭顶部进度提示
    showModal: ns.__showModal,//显示模态窗口
    showImage: ns.__showImage,//显示图片
    tooltip: function (element, msg) {
        element.tooltip({title: msg});
        element.tooltip("show");
    },
    showSidebarStatic: function (elementId, options) {//显示页面上已有的侧边栏
        if (!ns.view.Sidebar)
            ns.requireJS("/framework/js/view/sidebar.js");
        options = options || {};
        options.target = elementId;
        var sidebar = new ns.view.Sidebar(options);
        return sidebar.show();
    },
    showSidebarRemote: function (url, params, options) {//创建并加载远程侧边栏
        if (!ns.view.Sidebar)
            ns.requireJS("/framework/js/view/sidebar.js");
        options = options || {};
        options.url = url;
        options.params = params;
        var sidebar = new ns.view.Sidebar(options);
        return sidebar.show();
    },
    animate: function (obj, animate) {//动画
        $(obj).addClass(animate + " animated").one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
            $(this).removeClass(animate + " animate");
        });
    }
};
ns.form = { //form组件
    get: ns.get,
    post: ns.post,
    asyncRequest: ns.asyncRequest,
    serialize: function (form) {//表单参数序列化为json
        var attrs = {};
        if (typeof(form) === "string")
            form = $(form);
        var elements = form.serializeArray();
        $.each(elements, function () {
            attrs[this.name] = this.value;
        });
        return attrs;
    },
    load: function (form, data) {//加载JSON数据到form表单中
        if (typeof(form) === "string")
            form = $(form);
        $.each(data, function (i, n) {
            var element = form.find("[name='" + i + "']");
            if (element[0]) {
                if (element.is(":checkbox") || element.is(":radio")) {
                    form.find("[name='" + i + "'][value='" + n + "']").prop("checked", "checked");
                } else {
                    element.val(n);
                }
            }
        });
    },
    check: function (target) {
    }/*checkbox选择、全选/反选，具体实现在form/checkbox.js中*/
};
ns.data = { //数据存储
    localStorage: {//本地存储，永久有效
        set: function (key, value) {
            if (window.localStorage) {
                key = "ns$ls$" + key;
                window.localStorage.removeItem(key);
                window.localStorage.setItem(key, value);
            }
        },
        get: function (key) {
            if (window.localStorage) {
                key = "ns$ls$" + key;
                return window.localStorage.getItem(key);
            }
        },
        remove: function (key) {
            if (window.localStorage) {
                key = "ns$ls$" + key;
                window.localStorage.removeItem(key);
            }
        },
        clear: function () {
            if (window.localStorage) {
                window.localStorage.clear();
            }
        }
    },
    sessionStorage: {//本地存储，会话有效
        set: function (key, value) {
            if (window.sessionStorage) {
                key = "ns$ss$" + key;
                window.sessionStorage.removeItem(key);
                window.sessionStorage.setItem(key, value);
            }
        },
        get: function (key) {
            if (window.sessionStorage) {
                key = "ns$ss$" + key;
                return window.sessionStorage.getItem(key);
            }
        },
        remove: function (key) {
            if (window.sessionStorage) {
                key = "ns$ss$" + key;
                window.sessionStorage.removeItem(key);
            }
        },
        clear: function () {
            if (window.sessionStorage) {
                window.sessionStorage.clear();
            }
        }
    },
    cookie: {
        _init: function () {
            if (typeof(setCookie) != "function") {
                ns.requireJS("/framework/js/plugins/zframe.cookie.js")
            }
        },
        set: function (name, value, time) {
            ns.data.cookie._init();
            name = "ns$cookie$" + name;
            delCookie(name);
            setCookie(name, value, time);
            return true;
        },
        get: function (name) {
            ns.data.cookie._init();
            name = "ns$cookie$" + name;
            return getCookie(name);
        },
        remove: function (name) {
            ns.data.cookie._init();
            name = "ns$cookie$" + name;
            delCookie(name);
            return true;
        }
    },
    copy: function (data) {
        if (window.clipboardData) {
            window.clipboardData.setData("text", data);
            ns.tip.alert("已复制！");
        } else {
            // ns.tip.alert("您的浏览器不支持复制到粘贴板！");
            prompt("由于浏览器的限制，请您手动复制：", data);
        }
    }
};
ns.event = {// events
    move: function (target) {
        var header = target.find(".modal-header");
        header.css("cursor", "move");
        var move = false;//移动标记
        var _x, _y;//鼠标离控件左上角的相对位置
        header.bind("mousedown", function (e) {
            move = true;
            _x = e.pageX - parseInt(target.css("left"));
            _y = e.pageY - parseInt(target.css("top"));
        });
        header.bind("mouseup", function (e) {
            move = false;
        });
        header.bind("mousemove", function (e) {
            if (move) {
                var x = e.pageX - _x;//控件左上角到屏幕左上角的相对位置
                var y = e.pageY - _y;
                target.css({"top": y, "left": x});
            }
        });
    }
};
ns.debug = { //debug
    each: function (obj) {
        $.each(obj, function (i, n) {
            alert(i + ":" + n);
        });
    }
};
ns.String = {
    //字符串格式化，占位符
    //str：字符串
    //params Array
    format: function (str, params) {
        for (var i = 0; i < params.length; i++) {
            str = str.replace("%s", params[i])
        }
        return str;
    },
    //去除html标签
    html_decode: function (str) {
        str = str + "";
        return str.replace(/<\/?[^>]*>/g, "");
    },
    //去空格
    trim: function (str, type) {
        type = type || 1
        switch (type) {
            case 1:
                return str.replace(/\s+/g, "");
            case 2:
                return str.replace(/(^\s*)|(\s*$)/g, "");
            case 3:
                return str.replace(/(^\s*)/g, "");
            case 4:
                return str.replace(/(\s*$)/g, "");
            default:
                return str;
        }
    }
};
ns.browser = {
    isIOS: function () {
        var u = navigator.userAgent;
        return u.indexOf('iPhone') > -1 || u.indexOf('iPad') > -1;

    },
    isAndroid: function () {
        var u = navigator.userAgent;
        return u.indexOf('Android') > -1 || u.indexOf('Linux') > -1;

    },
    isWP: function () {
        var u = navigator.userAgent;
        return u.indexOf('Windows Phone') > -1;

    },
    isPC: function () {
        return !ns.browser.isIOS() && !ns.browser.isAndroid() && !ns.browser.isWP();
    },
    isWeixin: function () {
        var u = navigator.userAgent;
        return u.indexOf("MicroMessenger") > -1;
    }
};
ns.location = {
    getPathVariable: function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return r[2];
        return null;
    },
    getAllPathVariables: function (url) {
        url = url ? url : window.location.href;
        var _pa = url.substring(url.indexOf('?') + 1),
            _arrS = _pa.split('&'),
            _rs = {};
        for (var i = 0, _len = _arrS.length; i < _len; i++) {
            var pos = _arrS[i].indexOf('=');
            if (pos === -1) {
                continue;
            }
            var name = _arrS[i].substring(0, pos);
            _rs[name] = window.decodeURIComponent(_arrS[i].substring(pos + 1));
        }
        return _rs;
    }
};
//组件池
ns.components = {
    init: function (name) {
        var keys = [];
        if (name) keys.push(name);
        else keys = Object.keys(ns.components.modules);
        $.each(keys, function (i, key) {
            var com = ns.components.modules[key];
            try {
                if (com && com.init) {
                    com.init();
                }
            } catch (e) {
                console.error(e);
                alert(key + "组件初始化失败");
            }
        });
    },
    modules: {},
    urlMapping: {//组件地址映射
        load: function (url) {
            var name = ns.components.urlMapping[url];
            if (name) {
                ns.components.init(name);
                return true;
            }
            return false;
        }
    }
};
//组件注册
ns.component = function (name, context) {
    function set(obj, field, value) {
        var idx = field.indexOf(".");

        if (idx > 0) {
            var f1 = field.substring(0, idx);
            var f2 = field.substring(idx + 1);

            var obj2 = obj[f1];
            if (!obj2) obj2 = {};

            if (f2.indexOf(".") > 0) {
                set(obj2, f2, value);
            } else {
                obj2[f2] = value;
            }
            obj[f1] = obj2;
        } else {
            obj[field] = value;
        }
    }

    if (name.split(".").length > 0) {
        set(ns, name, context);
    } else {
        if (!ns[name])
            ns[name] = context;
    }

    if (!ns.components.modules[name]) {
        ns.components.modules[name] = context;

        // console.debug("[" + window.location.pathname + "]  " + "component init: " + name);
    }

    ns.components.init(name);

    return {
        mapping: function (url) {
            if (url && !url.startsWith(ns.getBasePath())) {
                if (!url.startsWith("/"))
                    url = "/" + url;
                url = ns.getBasePath() + url;
            }
            ns.components.urlMapping[url] = name;
        }
    }
};

//按顺序逐步加载
$(function () {
    $.ajaxSetup({
        cache: true
    });
    offscreen.init();
    //记录窗口大小初始值
    ns.lastDocumentHeight = $(window).height();
    ns.lastDocumentWidth = $(window).width();
    ns.resizeHeight = 0;//高度变化量
    ns.headerHeight = $(".app>.header").height();

    ns.init();//页面加载完初始化

    /**************业务相关**************/
    ns.userCenter = function () { //个人中心
        ns.__showModal(ns.getBasePath() + "/profile", {border: false});
    };
    ns.setting = function () { //系统设置（登陆用户）
        ns.view.showModal(ns.getBasePath()+"/system/user/config");
    };
    ns.checkUpdate = function () {//检查更新
        ns.tip.alert("当前无可用更新！");
    };
    ns.exit = function () {//退出
        var exitObj = ns.tip.confirm("您确定要退出系统吗？", function () {
            window.location.href = ns.getBasePath() + "/logout";
        });
        var dialog = exitObj.find(".modal-dialog");
        dialog.addClass("modal-sm");
        dialog.css("padding-top", "200px");
    };
    ns.__notify = function () {//获取通知
        $.get(ns.getBasePath() + "/system/notify/header", {}, function (data) {
            var oNotify = $(".notifications ul");
            oNotify.html(data);

            ns.initTabsLink(oNotify.find("a.notify-link"));
        });
        //移除点击方法
        //$(".notifications a:first").removeAttr("onclick");
    };
    ns.__readNotify = function (id, obj) {//阅读通知
        $.post(ns.getBasePath() + "/system/notify/read", {
            id: id
        }, function (data) {
            if (obj) {
                $(obj).find("a").css("color", "#ccc");
                $(obj).removeAttr("onclick");
            }

            // 提示数字减1
            var number = $(".notifications").find(".badge span").text();
            number = parseInt(number);
            number = number - 1;
            if (number <= 0) {
                $(".notifications .badge").hide();
            } else {
                $(".notifications .badge span").text(number);
            }
        });
    };
    var offset = $(".sub-menu li.active").offset();
    if (offset) {
        var top = offset.top;
        if (top > $(".sidebar").height() * 0.8) {
            $(".sidebar .main-navigation").animate({scrollTop: (top * 0.4) + 'px'}, 800, function () {
                if ($(window).width() > 1200)
                    $(this).slimScroll({scrollTo: (top * 0.4) + "px"});
            });
        }
    }
    ns.initCustomControls();
    setTimeout(function () {
        if (top && !top.swal) {//位置问题，页面加载完成后，预加载
            top.ns.requireCSS("/framework/plugins/sweetalert/sweetalert.css");
            top.ns.requireJS("/framework/plugins/sweetalert/sweetalert.min.js");
        }
    }, 300);

    ns.execReadyEvents(window.document);
});
