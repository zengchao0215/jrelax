/**
 * Created by zengchao on 2017/6/25.
 */
ns.component("form.switch", {
    init: function () {
        ns.requireJS("/framework/plugins/jquery.form.js");
        ns.requireJS("/framework/plugins/switchery/switchery.js");
        var defaultColor = $(".bg-primary,.btn-primary").css("backgroundColor");
        var colorMap = {
            blue: "#1582dc",
            pink: "#ff7791",
            green: "#15db81",
            red: "#FF604F",
            secondary: "#FFB244"
        };

        var elements = $(".switch,input[data-switch]");
        $.each(elements, function (i, n) {
            var color = $(n).data("switch-color");
            if (!color) color = defaultColor;
            if (color.indexOf("#") < 0) {
                color = colorMap[color];
            }
            new Switchery(n, {
                color: color
            });
        });
    }
}).mapping("/framework/js/form/switch.js");