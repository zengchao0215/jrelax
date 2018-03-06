/**
 * 依赖Bootstrap、JSTree
 * Created by zengc on 2016-09-01.
 */
(function ($) {
    $.fn.combotree = function (options) {
        options = options || {};
        return $(this).each(function () {
            var settings = $.extend({}, jQuery.fn.combotree.defaults, options);

            var element = $(this);
            element.hide();
            if (options.value)
                element.val(options.value);

            var parent = element.parent();
            var id = "combotree-tree-" + (new Date().getTime());
            var html = "<div class='input-group combotree-controls'>";
            html += "<input class='form-control combotree-input'>";
            html += "<span class='input-group-btn'> <button class='btn btn-default' type='button'><span class='caret'></span></button></span>";
            html += "</div>";
            html += "<div id='" + id + "' class='bordered combotree-treeview' style='display:none;'></div>";

            parent.append(html);

            if (settings.text) {//初始化显示
                parent.find(".combotree-input").val(settings.text);
            }

            var treeview = parent.find("#" + id);
            var controls = parent.find(".combotree-controls input,.combotree-controls button");

            //输入框设为只读
            controls.filter("input").attr("readonly", "readonly");

            var plugins = ['wholerow'];
            if (settings.multiple) {
                plugins.push("checkbox");
            }
            //初始化事件
            controls.on("click", function () {
                var width = parent.find(".combotree-controls").outerWidth();
                //设置样式
                treeview.css("height", settings.height);
                if (settings.width == "auto")
                    treeview.css("width", width);
                else
                    treeview.css("width", settings.width);
                if (!treeview.inited) {
                    treeview.jstree({
                        'plugins': plugins,
                        'core': {
                            'data': {
                                type: "POST",
                                url: settings.url
                            }
                        }
                    }).on("changed.jstree", function (node, action, selected) {
                        var instance = $.jstree.reference(treeview);
                        var nodes = instance.get_selected();
                        var id = [];
                        var text = [];
                        $(nodes).each(function (i, node) {
                            var data = instance.get_json(node);
                            id.push(data[settings.valueField]);
                            text.push(data[settings.textField]);

                        });
                        element.val(id.toString());
                        $(controls[0]).val(text.toString());
                        if (!settings.multiple) treeview.hide();
                    }).on("select_node.jstree", function (node, selected) {
                        var tree = $.jstree.reference(treeview);
                        var text = tree.get_text(id);
                        if (!settings.multiple) {
                            var id = selected.selected[0];
                            settings.onSelected({id: id, text: text, original: tree.get_node(id).original});
                        } else {
                            var array = [];
                            var ids = selected.selected;
                            for (var i = 0; i < ids.length; i++) {
                                var id = ids[i];
                                array.push({id: id, text: text, original: tree.get_node(id).original});
                            }
                            settings.onSelected(array);
                        }

                    });
                    treeview.inited = true;
                }
                if (!treeview.is(":visible"))
                    treeview.show();
            });

            $(document).on("mouseup", function (e) {
                if (treeview.is(e.target) || controls.is(e.target) || treeview.has(e.target).length > 0) return;
                treeview.hide();
            });
        });
    }
    $.fn.combotree.defaults = {
        url: function () {
        },
        width: "auto",
        height: 200,
        multiple: false, // 支持多选
        valueField: "id",//值字段
        textField: "text",//显示字段
        value: undefined, //值
        text: undefined,//显示值
        onSelected: function (data) {
        }
    }
}(jQuery));
