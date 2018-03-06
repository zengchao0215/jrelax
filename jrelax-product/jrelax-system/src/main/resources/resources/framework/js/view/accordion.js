ns.component("view.accordion", {
    init: function (target) {
        target = target || ".accordion,[data-accordion]";
        $(target + " > dd").first().show();
        $(target + " > dt > a").first().addClass("active");
        $(document).on("click touchstart", target + " > dt > a", function (e) {
            e.preventDefault();
            e.stopPropagation();
            $(this).closest(target).find("dd").slideUp(800, "easeInOutExpo");
            $(this).closest(target).find("a").removeClass("active");
            if ($(this).parent().next().css("display") !== "block") {
                $(this).parent().next().slideDown();
                $(this).addClass("active");
                return false;
            }
            return false;
        });
    }
});