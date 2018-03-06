ns.component("form.summernote", {
    init: function () {
        ns.requireCSS("/framework/plugins/summernote/summernote.css");
        ns.requireJS("/framework/plugins/summernote/summernote.js");
        ns.requireJS("/framework/plugins/summernote/summernote-zh-CN.js");

        if (console)
            console.log("请在页面上自行初始化,$('').summernote(options)");

        $.summernote.onImageBeforeUpload = function () {
            ns.view.showProgressbar("正在上传图片...");
        }
        $.summernote.onImageAfterUpload = function () {
            ns.view.closeProgressbar();
        }
    }
});