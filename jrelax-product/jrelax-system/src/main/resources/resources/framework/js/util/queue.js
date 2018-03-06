/** 队列
 * Created by zengchao on 2017/6/15.
 */
function Queue(){
    var list = [];

    this.add = function(el){
        list.push(el);
    }
    this.addAll = function(arr){
        for(var i=0;i<arr.length;i++){
            list.push(arr[i]);
        }
    }
    this.onQueue = function(element){};//每一个队列项目回掉
    this.onComplete = function(){};//完成回掉函数
    this.onCancel = function(){};//取消回掉函数

    this.execute = function(){// 开始执行回掉
        var element = list.shift();
        if(element)
            this.onQueue(element);
        else
            this.onComplete();
    }
    this.next = function(){// 执行下一个队列
        if(list.length > 0){
            this.execute();
        }else{
            this.onComplete();
        }
    }
    this.cancel = function(){//取消队列
        list = [];
    }
}