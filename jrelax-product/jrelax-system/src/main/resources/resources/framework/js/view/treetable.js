ns.component("view.treetable", {
    init: function () {
        ns.requireCSS("/framework/plugins/treetable/jquery.treetable.css");
        ns.requireJS("/framework/plugins/treetable/jquery.treetable.js");
    },
    loading: function (treetable, node) {
        var rows = "<tr data-tt-id=\"loading\" data-tt-parent-id=\"" + node.id + "\" data-tt-branch=\"false\"><td colspan='4'><img src=\"" + ns.getBasePath() + "/framework/img/loading.gif\"/>正在加载...</td></tr>";
        treetable.treetable("loadBranch", node, rows);
    }
});