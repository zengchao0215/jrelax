ns.component("form.select", {
    init: function () {
        if (!$.isFunction($.fn.chosen)) {
            ns.requireCSS("/framework/plugins/chosen/chosen.min.css");
            ns.requireJS("/framework/plugins/chosen/chosen.jquery.min.js");
        }
        ns.form.initChosen = function (obj) {
            if ($.isFunction($.fn.chosen)) {
                var arr = [];
                if (obj)
                    arr.push($(obj));
                else
                    arr = $("select.chosen,select.select,select[data-select]");
                $.each(arr, function (i, n) {
                    var el = $(n);
                    var w = (el.attr("style") && el.attr("style").indexOf("width") >= 0) ? el.css("width") : "100%";
                    el.chosen({
                        no_results_text: "没有结果匹配",
                        placeholder_text_multiple: "请选择...",
                        placeholder_text_single: "请选择...",
                        disable_search_threshold: 10, //少于10个不显示搜索框
                        width: w
                    });

                    //表单reset后同步更新显示
                    el.parents("form").bind("reset", function () {
                        setTimeout(function () {
                            el.trigger("chosen:updated");
                        }, 50);
                    });
                });
            }
        };
        ns.form.initChosen();

        //切换显示
        var selects = $("select[data-toggle]");
        if (selects.length > 0) {
            ns.form.select.toggleShow(selects);
        }
    },
    /**
     * 满足条件切换显示
     * data-toggle-show
     * data-toggle-show-mode
     * data-toggle-show-value
     * @param sel
     */
    toggleShow: function (sel) {
        $(sel).bind("change", function () {
            handler(this)
        });

        function handler(obj) {
            var selValue = $(obj).val();
            var target = $(obj).data("toggle-show");
            var mode = $(obj).data("toggle-show-mode");
            if (mode === "text")
                selValue = $(obj).find("option:selected").text();
            $(target).each(function (i, el) {
                var val = $(el).data("toggle-show-value");
                if (val) {
                    var match = false;
                    var values = val.toString().split(",");
                    for (var i = 0; i < values.length; i++) {
                        var value = values[i];
                        if (value === selValue) {
                            match = true;
                            break;
                        }
                    }
                    if (match)
                        $(el).show();
                    else
                        $(el).hide();
                }
            });
        }

        handler(sel);
    }
});