/**
 * Created by zengc on 2016-07-31.
 */
// if (typeof(ns) == "undefined") var ns = {};
// if (typeof(ns.view) == "undefined") ns.view = {};
ns.component("view.pager", function (pageCount, currentPage, formId, hash) {
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
        if (this.pageCount == 0)
            return;
        //判断是否是当前页
        if (this.form) {
            if (this.form.find("input[name='page']:hidden").length > 0) {
                this.form.find("input[name='page']:hidden").val(page);
                this.form.submit();
            } else {
                this.form.append("<input type=\"hidden\" name=\"page\" value=\"" + page + "\"/>");
                this.form.submit();
            }
        }
        else {
            this.pageControl.parents("form").find("input[name='page']:hidden").val(page);
            this.pageControl.parents("form").submit();
        }
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
