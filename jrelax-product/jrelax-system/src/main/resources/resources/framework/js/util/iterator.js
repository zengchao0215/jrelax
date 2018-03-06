function Iterator(arr){
	var array = arr;
	var cIdx = -1;
	this.hasNext = function(){
		if(!array){
			return false;
		}else if(array.size() == 0){
			return false;
		}else if(cIdx+1 == array.size()){
			return false;
		}
		return true;
	};
	this.next = function(){
		cIdx++;
		return array.get(cIdx);
	};
	this.remove = function(){
		
	}
}