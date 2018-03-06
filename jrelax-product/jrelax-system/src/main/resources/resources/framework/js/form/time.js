ns.component("form.time", {
    init: function () {
        if (!$.isFunction($.fn.timepicker)) {
            ns.requireCSS("/framework/plugins/timepicker/jquery.timepicker.css");
            ns.requireJS("/framework/plugins/timepicker/jquery.timepicker.min.js");
        }
        ns.form.initTimePicker = function (obj) {//初始化控件
            if ($.isFunction($.fn.timepicker)) {
                var options = {
                    lang: {am: "上午", pm: "下午"},
                    show2400: true,
                    timeFormat: "H:i",
                    step: 30
                };
                var arr = [];
                if (obj)
                    arr.push($(obj));
                else
                    arr.push($(".time,[data-time]"));
                $.each(arr, function (i, picker) {
                    picker = $(picker);
                    var timeFormat = picker.data("time-format");
                    if (timeFormat)
                        options.timeFormat = timeFormat;
                    var step = picker.data("time-step");
                    if (step)
                        options.step = step;
                    picker.timepicker(options);
                })

            }
        };
        ns.form.initTimePicker();
    }
});