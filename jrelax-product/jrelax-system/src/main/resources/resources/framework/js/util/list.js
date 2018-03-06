function List(){
	var list = [];
	this.add = function(i,e){
		if(e){//传2个参数
			list[i] = e;
		}else{//传1个参数
			list.push(i);
		}
		return true;
	};
	this.addAll = function(i, arr){
		if(arr){
			
		}else{
			
		}
		return true;
	};
	this.clear = function(){
		list.length = 0;
	};
	//是否存在指定元素
	this.contains = function(o){
		for(var i=0;i<list.length;i++){
			if(list[i] == o){
				return true;
			}
		}
		return false;
	};
	this.get = function(i){
		return list[i];
	};
	this.isEmpty = function(){
		if(list.length == 0)
			return true;
		return false;
	};
	this.iterator = function(){
		try{
			return new Iterator(this);
		}catch(e){
			top.Dialog.error("请先引入Itertor.js文件!");
		}
	};
	this.lastIndexOf = function(o){
		for(var i=list.length-1;i<-1;i--){
			if(list[i] == o){
				return i;
			}
		}
		return -1;
	};
	this.remove = function(i){
		list.splice(i, 1);
		return true;
	};
	this.set = function(i,e){
		list[i] = e;
		return true;
	};
	this.size = function(){
		return list.length;
	}
}