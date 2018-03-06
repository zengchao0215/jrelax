//Map对象
function Map(){
	this.elements = [];
	 
    //获取MAP元素个数
     this.size = function() {
         return this.elements.length;
     };
 
    //判断MAP是否为空
     this.isEmpty = function() {
         return (this.elements.length < 1);
     };
 
    //删除MAP所有元素
     this.clear = function() {
         this.elements = [];
     };
 
    //向MAP中增加元素（key, value)
     this.put = function(_key, _value) {
    	 if(this.containsKey(_key)){
    		 this.remove(_key);
    	 }
         this.elements.push( {
             key : _key,
             value : _value
         });
     };
 
    //删除指定KEY的元素，成功返回True，失败返回False
     this.remove = function(_key) {
         var bln = false;
         try {
             for (i = 0; i < this.elements.length; i++) {
                 if (this.elements[i].key == _key) {
                     this.elements.splice(i, 1);
                     return true;
                 }
             }
         } catch (e) {
             bln = false;
         }
         return bln;
     };
 
    //获取指定KEY的元素值VALUE，失败返回NULL
     this.get = function(_key) {
         try {
        	 if(this.isEmpty())
        		 return null;
             for (i = 0; i < this.elements.length; i++) {
                 if (this.elements[i].key == _key) {
                     return this.elements[i].value;
                 }
             }
         } catch (e) {
             return null;
         }
     };
 
    //获取指定索引的元素（使用element.key，element.value获取KEY和VALUE），失败返回NULL
     this.element = function(_index) {
         if (_index < 0 || _index >= this.elements.length) {
             return null;
         }
         return this.elements[_index];
     };
 
    //判断MAP中是否含有指定KEY的元素
     this.containsKey = function(_key) {
         var bln = false;
         try {
             for (i = 0; i < this.elements.length; i++) {
                 if (this.elements[i].key == _key) {
                     bln = true;
                 }
             }
         } catch (e) {
             bln = false;
         }
         return bln;
     };
 
    //判断MAP中是否含有指定VALUE的元素
     this.containsValue = function(_value) {
         var bln = false;
         try {
             for (i = 0; i < this.elements.length; i++) {
                 if (this.elements[i].value == _value) {
                     bln = true;
                 }
             }
         } catch (e) {
             bln = false;
         }
         return bln;
     };
 
    //获取MAP中所有VALUE的数组（ARRAY）
     this.values = function() {
         var arr = [];
         for (i = 0; i < this.elements.length; i++) {
             arr.push(this.elements[i].value);
         }
         return arr;
     };
    //获取MAP中所有KEY的数组（ARRAY）
     this.keys = function() {
         var arr = [];
         for (i = 0; i < this.elements.length; i++) {
             arr.push(this.elements[i].key);
         }
         return arr;
     };
     /*this.toString = function(){
    	 return "{keys:$0,values:$1}".replace("$0", this.toKeyString()).replace("$1", this.toValueString());
     }*/
     this.toValueString = function(){
    	 var s = "[";
    	 for(var i=0;i<this.values().length;i++){
    		 var val = this.values()[i];
    		 if(val instanceof Object){
    			 s += this.eachObject(val)+",";
    		 }else{
    			 s += val+",";
    		 }
    	 }
    	 if(s.length>1){
    		 s = s.substring(0, s.length-1);
    	 }
    	 return s+"]";
     };
     this.toKeyString = function(){
    	 var s = "[";
    	 for(var i=0;i<this.keys().length;i++){
    		 s += this.keys()[i]+",";
    	 }
    	 if(s.length>1){
    		 s = s.substring(0, s.length-1);
    	 }
    	 return s+"]";
     };
     this.eachArray = function(array){
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
    	this.eachObject = function(o){
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
}