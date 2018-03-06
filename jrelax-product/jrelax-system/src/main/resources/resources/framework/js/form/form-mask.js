ns.component("form.masks", {
    init: function (element) {
        //示例：999-9999999、+999 999999、9999-99-99、a*-999-9999
        if (typeof(Inputmask) == "undefined") {
            ns.requireJS("/framework/plugins/inputmask/jquery.inputmask.bundle.min.js");
        }
        Inputmask.prototype.defaults.aliases.currency.prefix = "￥";
        if (element) {
            $(element).each(function (i, n) {
                $(n).inputmask({alias: $(n).attr("data-mask-alias")});
            });
        } else {
            $("*[data-mask]").each(function (i, n) {
                $(this).inputmask({mask: $(this).attr("data-mask")});
            });
            $("*[data-mask-alias]").each(function (i, n) {
                $(this).inputmask({alias: $(this).attr("data-mask-alias")});
            });
            $("*[data-mask-regex]").each(function (i, n) {
                $(this).inputmask({alias: $(this).attr("data-mask-regex")});
            });
        }

    },
    parseCurrency: function (val) {
        val = val + "";
        val = val.replace('￥', "");
        val = val.replace('$', "");
        val = val.replaceAll(",", "");

        return parseFloat(val);
    }
});