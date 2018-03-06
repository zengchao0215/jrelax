ns.component("form.base", {
    init : function() {
        ns.requireJS("/framework/plugins/jquery.form.js");
        ns.requireJS("/framework/plugins/switchery/switchery.js");
        try{
            //开关
            var elems = Array.prototype.slice.call(document.querySelectorAll(".js-switch"));
            var blues = $(".js-switch-blue,[data-switch-blue]");
            $.each(blues, function(i,blue){
                var switchery = new Switchery(blue, {
                    color : $(".bg-primary,.btn-primary").css("backgroundColor")//"#1582dc"
                });
            });
            var pinks = $(".js-switch-pink,[data-switch-pink]");
            $.each(pinks, function(i,pink){
                var switchery = new Switchery(pink, {
                    color : "#ff7791",
                    disabled : true
                });
            });

            var greens = $(".js-switch-green,[data-switch-green]");
            $.each(greens, function(i,green){
                var switchery = new Switchery(green, {
                    color : "#15db81"
                });
            });

            var reds = $(".js-switch-red,[data-switch-red]");
            $.each(reds, function(i, red){
                var switchery = new Switchery(red, {
                    color : "#FF604F"
                });
            });
            var secondarys = $(".js-switch-secondary,[data-switch-secondary]");
            $.each(secondarys, function(i,secondary){
                var switchery = new Switchery(secondary, {
                    color : "#FFB244",
                    secondaryColor : "#ff8787"
                });
            });
            if($('input[maxlength]').length > 0){
                ns.importJs(ns.getBasePath()+"/framework/plugins/boostrap.maxlength.js");
                $('input[maxlength]').maxlength();
            }
        }catch(e){

        }
    }
});