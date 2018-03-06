ns.component("form.date", {
    init: function () {
        if (!$.isFunction($.fn.datepicker)) {
            ns.requireCSS("/framework/plugins/datepicker/datepicker.css");
            ns.requireJS("/framework/plugins/datepicker/bootstrap-datepicker.js");
        }
        ns.form.initDatePicker = function (obj) {//初始化控件
            if ($.isFunction($.fn.datepicker)) {
                var elements = [];
                if (obj)
                    elements.push(obj);
                else
                    elements = $(".date,.datepicker,[data-datepicker]");

                $.each(elements, function (i, n) {
                    var el = $(n);
                    el.datepicker();

                    //如果设置了下一个显示，那么在当前控件设置完成之后，自动focus绑定的控件
                    if (el.is("[data-datepicker-next]")) {
                        el.on("change", function () {
                            var next = $(this).attr("data-datepicker-next");
                            $(next).focus();
                        })
                    }
                })
            }
        };
        ns.form.initDatePicker();
    }
});