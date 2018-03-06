//时间格式化类
function SimpleDateFormat(pattern){
	var p = pattern;
	this.format = function(d){
		var strDate = p;
		var year = d.getFullYear();
		var shortYear = year.toString().substr(2,4);
		var month = d.getMonth()+1;
		var date = d.getDate();
		var hours = d.getHours();
		var minutes = d.getMinutes();
		var sec = d.getSeconds();
		var msec = d.getMilliseconds();
		
		if(p.indexOf("yyyy")>-1){
			strDate = strDate.replace("yyyy",year);
		}else if(p.indexOf("yy")>-1){
			strDate = strDate.replace("yy",shortYear);
		}
		if(p.indexOf("MM")>-1){
			if(month<10){
				month = "0" + month;
			}
			strDate = strDate.replace("MM",month);
		}else if(p.indexOf("M")>-1){
			strDate = strDate.replace("M",month+1);
		}
		if(p.indexOf("dd")>-1){
			if(date<10)
				date = "0"+ date;
			strDate = strDate.replace("dd",date);
		}else if(p.indexOf("d")>-1){
			strDate = strDate.replace("d",date);
		}
		if(p.indexOf("hh")>-1){
			strDate = strDate.replace("hh",hours);
		}
		if(p.indexOf("mm")>-1){
			strDate = strDate.replace("mm",minutes);
		}
		if(p.indexOf("ss")>-1){
			strDate = strDate.replace("ss",sec);
		}
		if(p.indexOf("sss")>-1){
			strDate = strDate.replace("sss",msec);
		}
		return strDate;
	};
	this.formatNow = function(){
		return this.format(new Date());
	};
}