/**
 * 机构选择组件
 * 依赖main.js
 * Created by zengc on 2016-06-02.
 */

ns.component("common.unit", {
    select: function (options) {
        var _def = {
            multi: false,
            showTopUnit : false,
            callback: function (data) {
                // alert(JSON.stringify(data));
            }
        };
        options = $.extend(_def, options);

        var modal = ns.view.showModal(ns.getBasePath() + "/common/unit/select?multi=" + options.multi, {
            onShown: function () {
                //初始化机构树
                modal.find("#__jsTreeUnit").jstree({
                    plugins: options.multi ? ["wholerow", "checkbox"] : ["wholerow"],
                    core: {
                        data: {
                            url: function (node) {
                                return node.id === "#" ? ns.getBasePath() + "/common/unit/select/tree" : ns.getBasePath() + "/common/unit/select/tree/" + node.id
                            }
                        },
                        multiple: options.multi
                    }, checkbox: {
                        three_state: false
                    }
                });
                //ok按钮
                var btnOk = modal.find("#ok");
                btnOk.bind("click", function () {
                    var jstree = modal.find("#__jsTreeUnit").jstree(true);
                    var nodes = jstree.get_selected();

                    var data = [];

                    $.each(nodes, function (i, node) {
                        var obj = {};
                        obj.id = node;
                        obj.name = jstree.get_text(node);
                        data.push(obj);
                    });

                    options.callback(data);
                    modal.close();
                });

                if(options.showTopUnit){
                    var topUnit = modal.find("#topUnit");
                    topUnit.show();
                    var btnTop = topUnit.find("button");
                    btnTop.on("click", function(){
                        options.callback([{
                            id : -1,
                            name : "顶级组织"
                        }]);
                        modal.close();
                    });
                }
            }
        });
    }
});