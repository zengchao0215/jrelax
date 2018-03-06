ns.component("form.multiselect", {
    init: function () {
        ns.requireJS("/framework/plugins/multiselect/js/bootstrap-multiselect.js");
        ns.requireCSS("/framework/plugins/multiselect/css/bootstrap-multiselect.css");

        $(".multiselect,[data-multiselect]").each(function (i, select) {
            select = $(select);
            select.multiselect({
                disableIfEmpty: true,
                disabledText: "无可选项目",
                nonSelectedText: "请选择项目",
                nSelectedText: "未选择项目",
                maxHeight: 300
            });
        });
    }
});