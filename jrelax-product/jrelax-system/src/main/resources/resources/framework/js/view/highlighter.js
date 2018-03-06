ns.component("view.hightlighter", {
    init: function () {
        ns.requireCSS("/framework/plugins/syntax-highlighter/shThemeDefault.css");
        ns.requireJS("/framework/plugins/syntax-highlighter/shCore.js");
        ns.requireJS("/framework/plugins/syntax-highlighter/shBrushAll.js");

        SyntaxHighlighter.all();
    }
});
