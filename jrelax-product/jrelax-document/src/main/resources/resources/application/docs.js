ns.requireJS("/framework/js/view/highlighter.js");
$(document).ready(function(){

    $("figure").on("click", function(e){
        $(this).addClass("active");
    });
});