ns.component("tip.tooltip", {
    init: function () {
        try {
            $("[data-toggle=tooltip]").tooltip();
            $("[data-toggle=popover]").popover().click(function (e) {
                e.preventDefault()
            })
        } catch (e) {

        }
    }
});