ns.component("view.tree", {
    init: function () {
        //引入必要的样式+JS文件
        if(!$.jstree){
            ns.requireCSS("/framework/plugins/jstree/themes/default/style.min.css");
            ns.requireJS("/framework/plugins/jstree/jstree.min.js");
        }
    },
    contextmenu : {
        renderCheckItems : function(trigger){
            trigger = !trigger;
            function clicked(node){
                $(node).find("div").addClass("jstree-wholerow-clicked");
            }
            function unClicked(node){
                $(node).find("div").removeClass("jstree-wholerow-clicked");
            }
            return {
                checkAll : {
                    label : "选择下级",
                    action : function(data){
                        var tree = $.jstree.reference(data.reference);

                        var node = tree.get_node(data.reference);
                        tree.open_node(node);
                        tree.select_node(node, trigger);
                        //选中子节点
                        var items = tree.get_children_dom(node);

                        for(var i=0;i<items.length;i++){
                            tree.select_node(items[i], trigger);
                            clicked(items[i]);
                        }
                        tree.open_node(node);
                    }
                },
                unCheckAll : {
                    label : "取消选择下级",
                    action : function(data){
                        var tree = $.jstree.reference(data.reference);

                        var node = tree.get_node(data.reference);
                        var items = tree.get_children_dom(node);

                        for(var i=0;i<items.length;i++){
                            tree.deselect_node(items[i], trigger);
                            unClicked(items[i]);
                        }

                        tree.deselect_node(node, trigger);
                    }
                },
                revertCheck : {
                    label : "反选下级",
                    action : function(data){
                        var tree = $.jstree.reference(data.reference);

                        var node = tree.get_node(data.reference);
                        var items = tree.get_children_dom(node);

                        for(var i=0;i<items.length;i++){
                            if(tree.is_selected(items[i])){
                                tree.deselect_node(items[i], trigger);
                                unClicked(items[i]);
                            }else{
                                tree.select_node(items[i], trigger);
                                clicked(items[i]);
                            }
                        }
                    }
                },
                checkAllChildren : {
                    separator_before: true,
                    label : "全选",
                    action : function(data){
                        var tree = $.jstree.reference(data.reference);

                        function selectNode(node){
                            tree.open_node(node);
                            tree.select_node(node, trigger);
                            //选中子节点
                            var items = tree.get_children_dom(node);

                            for(var i=0;i<items.length;i++){
                                var cNode = items[i];
                                tree.open_node(cNode);
                                tree.select_node(cNode, trigger);
                                clicked(cNode);

                                var cNodeItems = tree.get_children_dom(cNode);
                                if(cNodeItems.length > 0)
                                    selectNode(cNode);
                            }
                            tree.open_node(node);
                        }

                        selectNode(tree.get_node(data.reference));
                    }
                },
                unCheckAllChildren : {
                    label : "全不选",
                    action : function(data){
                        var tree = $.jstree.reference(data.reference);

                        function unSelectNode(node){
                            var items = tree.get_children_dom(node);

                            for(var i=0;i<items.length;i++){
                                var cNode = items[i];
                                tree.deselect_node(cNode, trigger);
                                unClicked(cNode);

                                var cNodeItems = tree.get_children_dom(cNode);
                                if(cNodeItems.length > 0)
                                    unSelectNode(cNode);
                            }

                            tree.deselect_node(node, trigger);
                        }

                        unSelectNode(tree.get_node(data.reference));
                    }
                },
                revertCheckAll : {
                    label : "反选",
                    action : function(data){
                        var tree = $.jstree.reference(data.reference);

                        function revertCheck(node){
                            var items = tree.get_children_dom(node);

                            for(var i=0;i<items.length;i++){
                                var cNode = items[i];
                                if(tree.is_selected(items[i])){
                                    tree.deselect_node(items[i], trigger);
                                    unClicked(items[i]);
                                }else{
                                    tree.select_node(items[i], trigger);
                                    clicked(items[i]);
                                }

                                var cNodeItems = tree.get_children_dom(cNode);
                                if(cNodeItems.length > 0)
                                    revertCheck(cNode);
                            }
                        }

                        revertCheck(tree.get_node(data.reference));
                    }
                }
            }
        }
    }
}).mapping("/framework/js/view/tree.js");
