/**
 * 模态窗口
 * Created by zengc on 2016/8/19.
 */
ns.component("view.Dialog", function (options) {
    var dialog = null;
    var _def = {
        id: "__divDialog" + new Date().getTime(),
        title: "",//标题
        content: "",
        size: "",// modal-lg modal-md modal-sm
        border: "",
        theme: "",//皮肤样式： modal-primary modal-danger modal-info modal-warning
        showHeader: false,
        showFooter: false,
        showOk: true,
        showCancel: false,
        showClose: true,
        remote: false,//远程访问
        remoteUrl: null,//远程URL
        loadText: "正在加载...",
        iframe: false,//用iframe来访问
        iframeHeight: 300,
        cache: false,//是否缓存,
        okText: "确 定",
        cancelText: "取 消",
        headerClass: "no-b",
        headerAlign: "",
        bodyClass: "",
        footerClass: "p10 no-b",
        footerAlign: "",//底部按钮对齐
        backdrop: true,//显示底部遮罩，设置为static时点击弹窗外不关闭
        keyboard: true,//按esc关闭
        fullScreen: false,//是否全屏显示
        params: null,//窗口参数传递
        animated: true,//动画效果
        onShow: function (dialog, handler) {

        },//先试试触发
        onShown: function (dialog, handler) {
        },//显示完成触发
        onHidden: function (dialog, handler) {
        },//隐藏后触发
        onClose: function (dialog, handler) {
        },//关闭时触发
        onLoaded: function (dialog, handler) {
        },
        _onLoaded: function (dialog, handler) {
        },//加载完成后触发，内部使用
        onOk: function (dialog, handler) {//点击OK

        },
        onCancel: function (dialog, handler) {//点击取消

        }
    };

    options = $.extend(_def, options);
    if (!options.border) options.border = "no-b";
    else options.border = "bordered";

    //前置
    function getPrefix() {
        var prefix = "<div id='" + options.id + "' class='modal " + (options.animated ? "fade " : " ") + options.theme + "' role='dialog' aria-hidden='true'>";
        prefix += "<div class='modal-dialog " + options.size + "'>";
        prefix += "<div class='modal-content " + options.border + "'>";
        return prefix;
    }

    //后置
    function getSuffix() {
        return "</div></div></div>";
    }

    //模态窗头部
    function getHeader() {
        var header = "<div class='modal-header " + options.headerClass + " " + options.headerAlign + "'>";
        if (options.showClose)
            header += "<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>×</button>";
        header += "<h4 class='modal-title'>" + options.title + "</h4>";
        header += "</div>";
        return header;
    }

    //模态窗主体
    function getBody() {
        var body = "<div class='modal-body " + options.bodyClass + "'><div class='row'>";
        if (options.remote) {
            if (options.iframe) {
                body += getIFrame();
            } else {
                //显示进度
                body += getLoading();
            }
        } else {
            body += options.content;
        }
        body += "</div></div>";
        return body;
    }

    //底部
    function getFooter() {
        //模态窗底部
        var footer = "<div class='modal-footer " + options.footerClass + " " + options.footerAlign + "'>";
        if (options.showOk)
            footer += "<button id='ok' type='button' class='btn btn-primary' autofocus>" + options.okText + "</button>";
        if (options.showCancel)
            footer += "<button id='cancel' type='button' class='btn btn-default btn-outline' data-dismiss='modal'>" + options.cancelText + "</button>";
        footer += "</div>";
        return footer;
    }

    //加载显示
    function getLoading() {
        var loading = "<div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\">";
        loading += "<center>" + ns.tip.progress.circle() + "<h4>" + options.loadText + "</h4></center>";
        loading += "</div>";
        return loading;
    }

    //iframe
    function getIFrame() {
        var iframe = "<div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\">";
        iframe += "<iframe width='100%' height='" + options.iframeHeight + "' src='" + options.remoteUrl + "'></iframe>";
        iframe += "</div>";
        return iframe;
    }

    //获取当前最大的提示框z-index值
    function getMaxZIndex(dialog) {
        var zIndex = 0;
        $(".modal").each(function (i, modal) {
            var t = parseInt($(modal).css("zIndex"));
            if (zIndex < t) zIndex = t;
        });
        return zIndex;
    }

    this.html = function () {
        var html = getPrefix();
        if (options.showHeader) {
            html += getHeader();
        }
        html += getBody();
        if (options.showFooter) {
            html += getFooter();
        }
        html += getSuffix();

        return html;
    };

    //启用拖曳功能
    function initDrag(dialog) {
        var $ele = dialog;
        var mouseOffset;
        var $modalDialog = $ele.find(".modal-dialog");
        var $modalHeader = $ele.find(".modal-header");
        var dialogOffset;

        $modalHeader.on('mousedown', function (event) {
            dialogOffset = $modalDialog.offset();
            mouseOffset = {
                top: event.pageY - dialogOffset.top,
                left: event.pageX - dialogOffset.left
            };
            $("body").on("mousemove", function (event) {
                var left = event.pageX - mouseOffset.left;
                var top = event.pageY - mouseOffset.top;
                if (left < 10) {
                    left = 0;
                } else if (left > $(window).width() - $modalDialog.width()) {
                    left = $(window).width() - $modalDialog.width();
                }
                if (top < 10) {
                    top = 0;
                } else if (top > $(window).height() - $modalHeader.height()) {
                    top = $(window).height() - $modalHeader.height();
                }
                $modalDialog.offset({
                    top: top,
                    left: left
                });
            });
        });

        $(document).on("mouseup mouseleave", function () {
            $('body').off("mousemove");
        });
    }

    this.show = function () {
        $("body").append(this.html());

        dialog = $("#" + options.id);
        dialog.on("loaded.bs.modal", function (e, a, b) {
            //设置窗口参数
            dialog.params = options.params;
            //设置焦点
            dialog.find("[autofocus]:first").focus();
            options.onLoaded(dialog, this);
            options._onLoaded(dialog, this);
        });
        dialog.on("show.bs.modal", function () {
            //防止遮罩层重叠
            dialog.css("zIndex", getMaxZIndex() + 2);
            //改动：修改bootstrap.js文件，直接在生成遮罩层时添加z-index值，浏览效果更好
            dialog.next().css("zIndex", parseInt(dialog.css("zIndex")) - 1);
            // $(".app").addClass("blur");
            if (options.fullScreen) {
                var dialogModal = dialog.find(".modal-dialog");

                dialogModal.css("width", "100%").css("margin", 0);
            }
            options.onShow(dialog, this);
        });
        dialog.on("shown.bs.modal", function () {
            //设置焦点
            dialog.find("[autofocus]:first").focus();
            if (options.fullScreen) {
                var dialogContent = dialog.find(".modal-content");
                var dialogContentBody = dialogContent.find(".modal-body");
                var headerHeight = dialogContent.find(".modal-header").outerHeight(true);
                var footerHeight = dialogContent.find(".modal-footer").outerHeight(true);
                dialogContentBody.css("overflow", "auto").css("height", ns.lastDocumentHeight - headerHeight - footerHeight);
            }
            dialog.find("#ok").on("click", function () {
                options.onOk(dialog, this);
            });
            dialog.find("#cancel").on("click", function () {
                options.onCancel(dialog, this);
            });
            initDrag($(this));
            options.onShown(dialog, this);
        });
        dialog.on("hidden.bs.modal", function () {
            // $(".app").removeClass("blur");
            options.onHidden(dialog, this);
            options.onClose(dialog, this);
            if (!options.cache) {
                $(this).remove();
                delete top._openDialogs_[dialog.attr("id")];
            }

            //修复子弹窗关闭后，页面无法滚动的问题
            var dialogs = $(top.window.document).find("div.modal");
            if (dialogs.length > 0) {
                $(top.window.document).find("body").addClass("modal-open");
            }
        });
        dialog.close = function (options) {
            if (options) {
                for (var op in options) {
                    dialog[op] = options[op];
                }
            }
            dialog.modal("hide");
        };
        //远程显示
        if (options.remote && !options.iframe) {
            dialog.modal({
                show: true,
                remote: options.remoteUrl,
                backdrop: options.backdrop,
                ignoreBackdropClick: true
            });
        } else {
            dialog.modal({
                show: true,
                backdrop: options.backdrop
            });
        }
        if (!top._openDialogs_)
            top._openDialogs_ = {};

        top._openDialogs_[options.id] = dialog;
        return dialog;
    }
});
//获取当前窗口
ns.view.Dialog.getCur = function (element) {
    var modal = $(element).parents(".modal");
    if (modal.length > 0) {
        var id = modal.attr("id");
        return top._openDialogs_[id];
    }
    return undefined;
};

//关闭窗口，并传递参数
ns.view.Dialog.close = function (element, options) {
    var dialog = ns.view.Dialog.getCur(element);
    if (dialog) {
        dialog.close(options);
    }
};
