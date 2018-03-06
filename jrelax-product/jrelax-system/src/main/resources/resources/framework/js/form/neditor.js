ns.component("form.neditor", {
    init: function () {
        ns.requireJS([
            "/framework/plugins/neditor/neditor.config.js",
            "/framework/plugins/neditor/neditor.all.min.js",
            "/framework/plugins/neditor/lang/zh-cn/zh-cn.js"
        ]);

        if (console)
            console.log("请在页面上自行初始化,UE.getEditor('元素ID');");
    },
    fixedFullScreenInModal: function (modal) {
        modal.addClass("neditor");
    },
    initEditor: function (el, opt) {//初始化编辑器
        var ue = UE.getEditors(el, opt);
        $(el).prop("disabled", "disabled");
        return ue;
    }
});