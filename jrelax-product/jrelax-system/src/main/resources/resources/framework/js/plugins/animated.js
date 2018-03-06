(function($){
   $.fn.startAnimated = function(animated,finish){
	   var $obj = $(this);
	   var cls = $obj.attr("class");
	   $obj.removeClass().addClass(animated+" animated").one(
		"webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend", function () {
            if(finish){
            	finish($obj);
            }else{
            	$obj.removeClass().addClass(cls);
            }
        });
   }
})(jQuery);