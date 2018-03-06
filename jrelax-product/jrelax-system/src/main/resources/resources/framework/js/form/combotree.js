ns.component("form.combotree", {
    init : function(){
        //引入必要的样式+JS文件
        ns.requireJS("/framework/js/view/tree.js");
        ns.requireCSS("/framework/plugins/combotree/combotree.css");
        ns.requireJS("/framework/plugins/combotree/combotree.js");
    }
});