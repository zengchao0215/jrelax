/**
 * 用户选择组件
 * 依赖main.js
 * Created by zengc on 2016-06-02.
 */
ns.component("common.user", {
    select : function(options){
        var _def = {
            multi:false,
            callback : function(data){}
        };
        options = $.extend(_def, options);

        var modal = ns.view.showModal(ns.getBasePath()+"/common/user/select",{onShown:function(){
            //初始化grid
            var dgUserSelect = modal.find("#dgUserSelect").jqGrid({
                url: ns.getBasePath() + '/common/user/select/data',
                colModel: [
                    { label: 'ID', name: 'id', key: true, hidden:true, align:"left" },
                    { label: '真实姓名', name: 'realName', width:100},
                    { label: '登录名', name: 'userName', width:100},
                    { label: '联系方式', name: 'mobile', width:100}
                ],
                height: 300,
                pager: "#dgUserSelectPager",
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
                dgUserSelect.reload({key : sText});
            });

            //ok按钮
            var btnOk = modal.find("#ok");
            btnOk.bind("click", function(){
                var ids = dgUserSelect.getSelectRows();
                var data = [];

                $.each(ids, function(i, id){
                    var rowData = dgUserSelect.getRowData(id);
                    var obj = {};
                    obj.id = id;
                    obj.username = rowData.userName;
                    obj.realname = rowData.realName;
                    obj.mobile = rowData.mobile;

                    data.push(obj);
                });

                options.callback(data);
                modal.close();
            });
        }});
    },
    selectByUnit : function(options){
        var _def = {
            multi:false,
            callback : function(data){}
        };
        options = $.extend(_def, options);

        var modal = ns.view.showModal(ns.getBasePath()+"/common/user/select-unit?multi="+options.multi,{onShown:function(){
            //初始化结构树
            modal.find("#jsTreeUserUnit").jstree({
                plugins: ["wholerow"],
                core: {
                    data: {
                        url: function (node) {
                            return node.id === "#" ? ns.getBasePath()+"/common/user/select-unit/unit/tree" : ns.getBasePath()+"/common/user/select-unit/unit/tree/" + node.id
                        }
                    },
                    multiple: false
                }
            }).on("select_node.jstree", function (node, selected) {
                dgUserSelectUnit.setGridParam({
                    datatype : "json",
                    postData : {unitId : selected.selected[0]}
                }).trigger("reloadGrid");
            }).on("loaded.jstree", function (node, jstree) {
                var id = $(node.target).find("li:first").attr("id");
                if (id)
                    jstree.instance.select_node(id);
            });
            //初始化grid
            var dgUserSelectUnit = modal.find("#dgUserSelectUnit").jqGrid({
                url: ns.getBasePath() + '/common/user/select-unit/user/data',
                datatype : "local",
                colModel: [
                    { label: 'ID', name: 'id', key: true, hidden:true, align:"left" },
                    { label: '登录名', name: 'userName', width:100},
                    { label: '真实姓名', name: 'realName', width:100},
                    { label: '联系方式', name: 'mobile', width:100}
                ],
                height: 300,
                pager: "#dgUserSelectUnitPager",
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
                var ids = dgUserSelectUnit.getSelectRows();
                var data = [];

                $.each(ids, function(i, id){
                    var rowData = dgUserSelectUnit.getRowData(id);
                    var obj = {};
                    obj.id = id;
                    obj.username = rowData.userName;
                    obj.realname = rowData.realName;
                    obj.mobile = rowData.mobile;

                    data.push(obj);
                });

                options.callback(data);
                modal.close();
            });
        }});
    },
    selectByUnitAndRole : function(options){
        var _def = {
            multi:false,
            callback : function(data){}
        };
        options = $.extend(_def, options);

        var modal = ns.view.showModal(ns.getBasePath()+"/common/user/select-unit-role?multi="+options.multi,{size:"modal-lg",onShown:function(){
            var dgUserSelectUnitRole;
            //初始化机构树
            modal.find("#jsTreeUserUnit").jstree({
                plugins: ["wholerow"],
                core: {
                    data: {
                        url: function (node) {
                            return node.id === "#" ? ns.getBasePath()+"/common/user/select-unit/unit/tree" : ns.getBasePath()+"/common/user/select-unit/unit/tree/" + node.id
                        }
                    },
                    multiple: false
                }
            }).on("select_node.jstree", function (node, selected) {
                var unitId = selected.selected[0];
                var tree = modal.find("#jsTreeUserUnitRole").jstree(true);
                if(tree){
                    tree.destroy();
                }
                modal.find("#jsTreeUserUnitRole").jstree({
                    plugins: ["wholerow"],
                    core: {
                        data: {
                            url: function (node) {
                                return ns.getBasePath()+"/common/user/select-unit-role/role/tree/"+unitId;
                            }
                        },
                        multiple: false
                    }
                }).on("select_node.jstree", function (node, selected) {
                    var roleId = selected.selected[0];
                    if(!dgUserSelectUnitRole){
                        //初始化grid
                        dgUserSelectUnitRole = modal.find("#dgUserUnitRoleSelect").jqGrid({
                            url: ns.getBasePath() + '/common/user/select-unit-role/user/data',
                            colModel: [
                                { label: 'ID', name: 'id', key: true, hidden:true, align:"left" },
                                { label: '登录名', name: 'userName', width:100},
                                { label: '真实姓名', name: 'realName', width:100},
                                { label: '联系方式', name: 'mobile', width:100}
                            ],
                            height: 300,
                            pager: "#dgUserUnitRoleSelectPager",
                            multiselect : true,
                            multiboxonly : !options.multi,
                            radiobutton : !options.multi,
                            icheckbox : true,
                            pagerpos:'center',
                            viewrecords : false,
                            postData : {roleId : roleId}
                        });
                    }else{
                        dgUserSelectUnitRole.reload({roleId : roleId});
                    }
                })
            })
            //ok按钮
            var btnOk = modal.find("#ok");
            btnOk.bind("click", function(){
                if(!dgUserSelectUnitRole) return;
                var ids = dgUserSelectUnitRole.getSelectRows();
                var data = [];

                $.each(ids, function(i, id){
                    var rowData = dgUserSelectUnitRole.getRowData(id);
                    var obj = {};
                    obj.id = id;
                    obj.username = rowData.userName;
                    obj.realname = rowData.realName;
                    obj.mobile = rowData.mobile;

                    data.push(obj);
                });

                options.callback(data);
                modal.close();
            });
        }});
    }
});
