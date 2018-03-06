ns.component("countTo", {
    init: function () {
        if (!$.fn.countTo) {
            //引入必要的样式+JS文件
            $("body").append("<script src=\"" + ns.getBasePath() + "/framework/plugins/count-to/jquery.countTo.js\"></script>");
        }
        var counters = $(".count, .counter");
        if (!$.browser.mobile && $.fn.appear) {
            counters.appear();
            counters.on("appear", function () {
                if (!$(this).hasClass("done")) {
                    $(this).addClass("done").countTo();
                }
            });
        } else {
            counters.each(function () {
                if (!$(this).hasClass("done")) {
                    $(this).addClass("done").countTo();
                }
            });
        }
    }
});