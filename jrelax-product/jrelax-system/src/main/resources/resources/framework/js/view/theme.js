ns.component("theme", {
    init: function () {
        $(document).on("click", ".pg-toggle", function (e) {
            if ($(".playground").length == 0) {
                var html = $.ajax({
                    url: ns.getBasePath() + '/index/theme',
                    async: false
                }).responseText;
                $("body").append(html);
            }
            e.preventDefault();
            if ($(".playground").hasClass("opened")) {
                $(".playground").removeClass("opened");
                /*$(this).animate({
                    right : 0
                }, 300, "easeInOutExpo")*/
            } else {
                $(".playground").addClass("opened");
                /*$(this).animate({
                    right : -40
                }, 300, "easeInOutExpo")*/
            }
        });
        $(document).on("click", ".pg-close", function (e) {
            e.preventDefault();
            if ($(".playground").hasClass("opened")) {
                $(".playground").removeClass("opened");
                /*$(".pg-toggle").animate({
                    right : 0
                }, 300, "easeInOutExpo")*/
            }
        });
        $(document).on("click", ".playground .font-options a", function (e) {
            e.preventDefault();
            var value = $(this).attr("href");
            $(".font-options").find("a").removeClass("active");
            $(this).addClass("active");
            $("#font").attr("href", value)
        });
        $(document).on("click", ".playground .header-options a", function (e) {
            e.preventDefault();
            var value = $(this).attr("id");
            if (value === "header-dark") {
                $(".header").addClass("header-dark")
            } else {
                $(".header").removeClass("header-dark")
            }
            $(".header-options").find("a").removeClass("active");
            $(this).addClass("active")
        });
        $(document).on("click", ".playground .color-options > a", function (e) {
            e.preventDefault();
            var value = $(this).attr("href");
            $(".color-options").find("a").removeClass("active");
            $(this).addClass("active");
            $("#skin").attr("href", value)
        })
    }
});