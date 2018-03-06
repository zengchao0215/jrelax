/*
 *  文本框自动补全组件 v1.0.0
 *  依赖 plugins/typeahead目录下所有文件
 */
ns.component("form.typeahead", {
    init: function (element, options) {
        if(!$.fn.typeahead){
            ns.requireCSS("/framework/plugins/typeahead/typeahead.js-bootstrap.css");
            ns.requireJS("/framework/plugins/typeahead/typeahead.bundle.js");
        }
        if (element) {
            options = options || {};
            options.idField = options.idField || "id";//数据集中的id字段名
            options.idEl = options.idEl || undefined;//id字段值对应元素
            options.autoselect = true;
            var url = element.attr("data-url");
            if (url) {
                element.typeahead(options, {
                    source: function (query, process) {
                        url = options.url || element.attr("data-url");
                        if (!url) return alert("请配置远程数据源地址");
                        jQuery.post(url, {query: query}, function (data) {
                            process(data);
                        });
                    }
                });
                var width = element.attr("data-width");//宽度
                if (width)
                    element.parent().find(".tt-dropdown-menu").css("minWidth", width);
            }
        } else {
            $.each($(".typeahead"), function (i, element) {
                element = $(element);
                ns.form.typeahead.init(element);
            });
        }
    }
});