ns.component("form.combogrid", {
    init: function () {
        //引入必要的样式+JS文件
        ns.requireCSS("/framework/plugins/combogrid/combogrid.css");
        ns.requireJS("/framework/js/view/datagrid.js");
        ns.requireJS("/framework/plugins/combogrid/combogrid.js");
    }
});
