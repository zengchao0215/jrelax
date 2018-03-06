//keyboard event 1.0
var KeyBoard = {
		isPress:false,//是否已经按下
		GridType : 'datagrid',//表格类型
		GridName : null,//表格名称
		lastSelectRowIndex : null,//最后选中的行索引
		onCheck:null,//行选中事件
		onCheckOld:function(){},//行选中事件，如果有绑定这个事件在将之前绑定的存在此处
		singleSelect:true,//单行选中
		Ctrl:false,//开启Ctrl键监听
		Shift:false,//开启Shift键监听
		isClose:false,//监听开关，为true时不启用监听事件
		Init:initKeyBoardEvent,//初始化方法，必须调用
		Dispose:disposeKeyBoard//关闭键盘监听
};
function initKeyBoardEvent(){
	KeyBoard.isClose = false;
	if(!KeyBoard.Ctrl && !KeyBoard.Shift)
		return;
	if(!KeyBoard.GridName){
		top.Notiy.error("请配置表格名称(KeyBoard.GridName)属性值!");
		return ;
	}
	$(document).keydown(function(event){
		if(KeyBoard.isClose)
			return;
		if(event.ctrlKey && KeyBoard.Ctrl && !KeyBoard.isPress){//按下Ctrl键
			if(CtrlKeyEvent){
				CtrlKeyEvent("down");
				KeyBoard.isPress = true;
			}
		}else if(event.shiftKey && KeyBoard.Shift && !KeyBoard.isPress){//按下Shift键
			if(ShiftKeyEvent){
				ShiftKeyEvent("down");
				KeyBoard.isPress = true;
			}
		}else if(event.ctrlKey && event.keyCode === 65){
			//alert("");
		}else if(event.ctrlKey && event.keyCode === 70){//ctrl+F
			
		}
		
		
	});
	$(document).keyup(function(event){
		if(event.keyCode == 17 && KeyBoard.Ctrl && KeyBoard.isPress){//按下Ctrl键
			if(CtrlKeyEvent){
				CtrlKeyEvent("up");
				KeyBoard.isPress = false;
			}
		}else if(event.keyCode == 16 && KeyBoard.Shift && KeyBoard.isPress){//按下Shift键
			if(ShiftKeyEvent){
				ShiftKeyEvent("up");
				KeyBoard.isPress = false;
			}
		}
	});
	//初始化Shift键盘事件，创建代理
	if(KeyBoard.GridType == "datagrid"){
		if(KeyBoard.Shift){
			var onCheck = $("#"+KeyBoard.GridName).datagrid("options").onCheck;
			if(onCheck){
				KeyBoard.onCheckOld = onCheck;
			}
			$("#"+KeyBoard.GridName).datagrid("options").onCheck = function(rowIndex,rowData){
				KeyBoard.lastSelectRowIndex = rowIndex;
				KeyBoard.onCheckOld(rowIndex,rowData);
			};
		}
		KeyBoard.singleSelect = $("#"+KeyBoard.GridName).datagrid("options").singleSelect;
	}
}
function disposeKeyBoard(){
	KeyBoard.isClose = true;
}
//按下Ctrl键之后的回调函数
function CtrlKeyEvent(eventType){
	if(KeyBoard.GridType == 'datagrid'){
		if(eventType == "down"){
			$("#"+KeyBoard.GridName).datagrid("options").singleSelect = false;
		}else if(eventType=="up"){
			$("#"+KeyBoard.GridName).datagrid("options").singleSelect = true;
		}
	}
}
//按下Shift键之后的回调函数
function ShiftKeyEvent(eventType){
	if(KeyBoard.GridType == 'datagrid'){//datagrid类型
		if(!KeyBoard.onCheck){
			KeyBoard.onCheck = $("#"+KeyBoard.GridName).datagrid("options").onCheck;
		}
		if(eventType == "down"){
			if(KeyBoard.lastSelectRowIndex == null){
				//top.Notiy.warning("请先选择一行在按下Shift键!");
				return ;
			}
			$("#"+KeyBoard.GridName).datagrid("options").onCheck = function(rowIndex,rowData){
					$("#"+KeyBoard.GridName).datagrid("options").onCheck = function(){};
					$("#"+KeyBoard.GridName).datagrid("options").singleSelect = false;
					if(rowIndex>KeyBoard.lastSelectRowIndex){
						for(var i=KeyBoard.lastSelectRowIndex;i<rowIndex;i++){
							$("#"+KeyBoard.GridName).datagrid("selectRow",i);
						}
					}else{
						for(var i=rowIndex;i<=KeyBoard.lastSelectRowIndex;i++){
							$("#"+KeyBoard.GridName).datagrid("selectRow",i);
						}
					}
					KeyBoard.onCheckOld(rowIndex,rowData);
			};
		}else if(eventType == "up"){//释放之后还原之前的操作
			$("#"+KeyBoard.GridName).datagrid("options").onCheck = KeyBoard.onCheck;
			$("#"+KeyBoard.GridName).datagrid("options").singleSelect = KeyBoard.singleSelect;
			KeyBoard.onCheck = null;
		}
	}
}