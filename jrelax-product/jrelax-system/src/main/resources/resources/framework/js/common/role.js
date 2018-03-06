/**
 * 角色选择组件
 * 依赖main.js
 * Created by zengc on 2016-06-02.
 */
ns.component("common.role", {
    select: function (options) {
        var _def = {
            multi: false,
            callback: function (data) {
                //alert(JSON.stringify(data));
            }
        };
        options = $.extend(_def, options);

        var modal = ns.view.showModal(ns.getBasePath() + "/common/role/select", {
            onShown: function () {
                //初始化grid
                var dgRoleSelect = modal.find("#dgRoleSelect").jqGrid({
                    url: ns.getBasePath() + '/common/role/select/data',
                    colModel: [
                        { label: 'ID', name: 'id', key: true, hidden:true, align:"left" },
                        { label: '角色名', name: 'name', width:100},
                        { label: '描述', name: 'descript', width:200}
                    ],
                    height: 300,
                    pager: "#dgRoleSelectPager",
                    multiselect : true,
                    multiboxonly : !options.multi,
                    radiobutton : !options.multi,
                    icheckbox : true,
                    pagerpos:'center',
                    viewrecords : false
                });
                var sBtn = modal.find("#btnSearch");
                sBtn.bind("click", function(){
                    var sText = modal.find("#searchText").val();
                    dgRoleSelect.reload({key : sText});
                });
                //ok按钮
                var btnOk = modal.find("#ok");
                btnOk.bind("click", function () {
                    var ids = dgRoleSelect.getSelectRows();
                    var data = [];

                    $.each(ids, function(i, id){
                        var rowData = dgRoleSelect.getRowData(id);
                        var obj = {};
                        obj.id = id;
                        obj.name = rowData.name;

                        data.push(obj);
                    });

                    options.callback(data);
                    modal.close();
                });
            }
        });
    },
    selectByUnit : function(options){
        var _def = {
            multi:false,
            callback : function(data){}
        };
        options = $.extend(_def, options);

        var modal = ns.view.showModal(ns.getBasePath()+"/common/role/select-unit",{onShown:function(){
                //初始化结构树
                modal.find("#jsTreeRoleUnit").jstree({
                    plugins: ["wholerow"],
                    core: {
                        data: {
                            url: function (node) {
                                return node.id === "#" ? ns.getBasePath()+"/common/role/select-unit/unit/tree" : ns.getBasePath()+"/common/role/select-unit/unit/tree/" + node.id
                            }
                        },
                        multiple: false
                    }
                }).on("select_node.jstree", function (node, selected) {
                    dgRoleSelectUnit.setGridParam({
                        datatype : "json",
                        postData : {unitId : selected.selected[0]}
                    }).trigger("reloadGrid");
                }).on("loaded.jstree", function (node, jstree) {
                    var id = $(node.target).find("li:first").attr("id");
                    if (id)
                        jstree.instance.select_node(id);
                });
                //初始化grid
                var dgRoleSelectUnit = modal.find("#dgRoleSelectUnit").jqGrid({
                    url: ns.getBasePath() + '/common/role/select-unit/role/data',
                    datatype : "local",
                    colModel: [
                        { label: 'ID', name: 'id', key: true, hidden:true, align:"left" },
                        { label: '角色名', name: 'name', width:100},
                        { label: '描述', name: 'descript', width:200}
                    ],
                    height: 300,
                    pager: "#dgRoleSelectUnitPager",
                    multiselect : true,
                    multiboxonly : !options.multi,
                    radiobutton : !options.multi,
                    icheckbox : true,
                    pagerpos:'center',
                    viewrecords : false
                });
                //ok按钮
                var btnOk = modal.find("#ok");
                btnOk.bind("click", function(){
                    var ids = dgRoleSelectUnit.getSelectRows();
                    var data = [];

                    $.each(ids, function(i, id){
                        var rowData = dgRoleSelectUnit.getRowData(id);
                        var obj = {};
                        obj.id = id;
                        obj.name = rowData.name;

                        data.push(obj);
                    });

                    options.callback(data);
                    modal.close();
                });
            }});
    },
});