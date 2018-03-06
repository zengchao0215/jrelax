/**
 * 基于EasyUI的Datagrid组件
 */
ns.component("view.edatagrid", {
    init : function(){
        //引入必要的样式+JS文件
        ns.requireCSS("/framework/plugins/edatagrid/style.css");
        ns.requireJS("/framework/plugins/edatagrid/e.datagrid.js");
    }
});
