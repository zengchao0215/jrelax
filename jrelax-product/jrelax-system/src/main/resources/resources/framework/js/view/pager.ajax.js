/**
 * Created by zengc on 2016-07-31.
 */
// if (typeof(ns) == "undefined") var ns = {};
// if (typeof(ns.view) == "undefined") ns.view = {};
// @过时
ns.component("view.pager_ajax", function (pageCount, currentPage, formId, hash) {
    this.form = null;
    this.pageControl = null;
    this.pageCount = pageCount;
    this.currentPage = currentPage;
    this.init = function () {
        if (formId.length > 0)
            this.form = $(formId);
        else {
            //判断是否是当前页
            this.pageControl = $("#__pageControl_" + hash);
            this.form = this.pageControl.parents("form");
            if (this.form) {
                this.form.attr("pager_hash", hash);
            }
        }
    };
    this.reload = function () {
        this.jumpPage(1);
    };
    this.jumpPage = function (page) {
        var pager = this;
        if (this.pageCount == 0)
            return;
        this.currentPage = page;
        //判断是否是当前页
        ns.showLoadingbar($(".main-content"));
        var params = ns.form.serialize(this.form);
        if (this.form.attr("paramsLink")) {//联合其他表单的参数
            var attrs = ns.form.serialize(form.attr("paramsLink"));
            $.each(attrs, function (i, n) {
                params[i] = n;
            });
        }
        params.page = page;
        var timer = setTimeout(function () {
            if(ns.tip)
                pager.form.html(ns.tip.progress.wave());
        }, 500);//延迟显示，提高网速顺畅时的浏览体验
        var fid = this.form.attr("id");
        jQuery.post(this.form.attr("action"), params, function (data) {
            clearTimeout(timer);
            if (typeof(data) == "string") {
                if (data.indexOf("<HTML".toLowerCase()) >= 0)
                    data = data.substring(data.indexOf("<HTML".toLowerCase()));
                else
                    data = "<div>" + data + "</div>";
                if (fid) {
                    var content = $("#" + fid, data).html();
                    pager.form.html(content);
                } else {
                    var action = form.attr("action");
                    var content = $("form[action='" + action + "']", data).html();
                    pager.form.html(content);
                }
                if (pager.form) {
                    var __pager = $("#__pager_" + hash, data).html();
                    $("#__pager_" + hash).html(__pager);
                }
                ns.closeLoadingbar($(".main-content"));
                ns.initCustomControls();
                ns.execReadyEvents();
            }
        });
    };

    this.prevPage = function () {
        if (this.currentPage > 0) {
            this.currentPage--;
        }
        this.jumpPage(this.currentPage);
    };

    this.nextPage = function () {
        if (this.currentPage < this.pageCount) {
            this.currentPage++;
        }
        this.jumpPage(this.currentPage);
    }

    if (typeof($) != "undefined") {
        this.init();
    } else {
        var pager = this;
        window.onload = function () {
            pager.init();
        }
    }
});