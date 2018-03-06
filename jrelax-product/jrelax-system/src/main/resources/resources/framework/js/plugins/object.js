Object.prototype.eachArray = function(array){
	var s = "[";
	for(var i =0;i<array.length;i++){
		var val = array[i];
		if(val instanceof Array)
			s += eachArray(val)+",";
		else if(val instanceof Object)
			s += eachObject(val)+",";
		else
			s += "\""+ val +"\",";
	}
	if(s.length>1){
		s = s.substring(0, s.length-1);
	}
	s += "]";
	return s;
};
Object.prototype.eachObject = function(o){
	var s = "{";
	for(var k in o){
		var v = o[k];
		if(v instanceof Object)
			s += k + ":\"" + eachObject(v) + "\",";
		else if(v instanceof Array)
			 	s += k + ":" + eachArray(v) + ",";
		else
			s += k + ":\"" + v + "\",";
	}
	if(s.length>1){
		s = s.substring(0, s.length-1);
	}
	s += "}";
	return s;
};