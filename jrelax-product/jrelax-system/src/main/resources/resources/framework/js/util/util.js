ns.ready(function () {
    ns.util = {
        //产生随机数
        random: {
            nextInt: function (max) {

            },
            nextFloat: function (max) {
               return Math.random();
            }
        },
        listToTree : function(list){//将平面化的数据转换为tree类型，前提是必须存在parentId字段
            function exists(list, parentId) {
                for (var i = 0; i < list.length; i++) {
                    if (list[i].id == parentId) return true;
                }
                return false;
            }

            var nodes = [];
            // 得到顶层节点
            for (var p = 0; p < list.length; p++) {
                var row = list[p];
                if (!exists(list, row.parentId)) {
                    nodes.push(row);
                }
            }

            var toDo = [];
            for (var n = 0; n < nodes.length; n++) {
                toDo.push(nodes[n]);
            }
            while (toDo.length) {
                var node = toDo.shift();    // 父节点
                // 得到子节点
                for (var j = 0; j < list.length; j++) {
                    var row = list[j];
                    if (row.parentId === node.id) {
                        var child = row;
                        if (node.children) {
                            node.children.push(child);
                        } else {
                            node.children = [child];
                        }
                        toDo.push(child);
                    }
                }
            }
            return nodes;
        }
    }
});