/**
 * 基于JqGrid的DataGrid组件
 * 修改JqGird.js 中的sidx为sort、sort为order
 */
(function(){
    ns.component("view.datagrid", {
        opClickEvents: {},//操作栏事件
        counter: 0,
        offsetHeight: 77,
        init: function () {
            //引入必要的样式+JS文件
            ns.requireCSS("/framework/plugins/jqgrid/ui.jqgrid-bootstrap.css");
            ns.requireJS("/framework/plugins/jqgrid/grid.locale-zh_CN.js");
            ns.requireJS("/framework/plugins/jqgrid/jquery.jqGrid.min.js");
            $.jgrid.extend({
                reload: function (params) {//从当前页
                    var dg = $(this);
                    if (params) {
                        dg = dg.setGridParam({
                            datatype: 'json',
                            postData: params
                        });
                    }
                    dg.trigger("reloadGrid");
                },
                reloadFirst: function (params) {//从第一页
                    var dg = $(this);
                    if (params) {
                        dg = dg.setGridParam({
                            datatype: 'json',
                            postData: params, //发送数据
                            page: 1
                        });
                    }
                    dg.trigger("reloadGrid");
                },
                getSelectRows: function () {//多选模式下生效
                    return $(this).getGridParam("selarrrow");
                },
                getSelectRow: function () {//单选模式下生效
                    return $(this).getGridParam("selrow");
                }
            });
            $.jgrid.styleUI.Bootstrap.base.headerTable = "table bg-gray-light";
            $.jgrid.styleUI.Bootstrap.base.rowTable = "table";
            $.jgrid.styleUI.Bootstrap.base.footerTable = "table";
            $.jgrid.styleUI.Bootstrap.base.pagerTable = "table";
            $.jgrid.styleUI.Bootstrap.base.rownumBox = "";
            $.jgrid.styleUI.Bootstrap.treegrid.icon_plus = "fa fa-caret-right";
            $.jgrid.styleUI.Bootstrap.treegrid.icon_minus = "fa fa-caret-down";
            $.jgrid.styleUI.Bootstrap.treegrid.icon_leaf = "fa fa-circle-o";
            $.jgrid.defaults = $.extend($.jgrid.defaults, {
                mtype: "POST",
                datatype: "json",
                styleUI: 'Bootstrap',
                viewrecords: true,
                autowidth: true,
                autoheight: true,
                forceFit: true,
                shrinkToFit: true,
                pagerpos: 'right',
                recordpos: 'left',
                rowNum: 20,
                rowList: [10, 20, 50, 100],
                loadui: "disable",
                xeditable: undefined,//xeditable初始化函数
                icheckbox: false,//chekcbox使用icheck
                searchForm: undefined,//xeditable初始化函数
                selectedToggle: undefined,//当有选择时，控制控件的显示和隐藏
                treeGridModel: "adjacency",
                treeReader: {
                    parent_id_field: "parentId"
                },
                formatter:function(rowId, element, rowData){},//行格式化
                beforeRequest: function () {
                    var grid = $(this).jqGrid();
                    if (grid.getGridParam("datatype") !== "local")// 本地数据不显示进度条
                        ns.showLoadingbar();
                    //清空事件缓存
                    if(!grid.getGridParam("treeGrid"))// treegrid会产生二次加载的问题，如果clear会导致原先的事件无法触发
                        clearEvents();
                },
                _gridComplete: function () {
                    ns.closeLoadingbar();
                    var grid = $(this).jqGrid();
                    //下拉菜单位置
                    if (ns.view.initDropdownMenuDirection) ns.view.initDropdownMenuDirection();
                    if (grid.getGridParam("radiobutton")) {
                        //移除顶部复选框
                        var chkAll = $(this.grid.hDiv).find("th input[type='checkbox']");
                        chkAll.remove();
                        var chk = $(this.grid.bDiv).find("td input[type='checkbox'], td input[type='radio']");
                        chk.attr("type", "radio").attr("name", this.id);
                    }
                    if (grid.getGridParam("icheckbox")) {
                        ns.view.datagrid.enableICheck(this);
                    }
                    if (grid.getGridParam("xeditable")) {
                        grid.getGridParam("xeditable")();
                    }
                    if (grid.getGridParam("searchForm")) {
                        $(grid.getGridParam("searchForm")).searchForm({
                            target: {
                                type: "jqgrid",
                                handler: grid
                            }
                        });
                    }
                    //初始化标签页连接按钮
                    ns.initTabsLink();
                    //双击列自动调整列的宽度
                    ns.view.datagrid.enableDblResize(this);
                    //默认控制关联控件的显示和隐藏
                    onSelectRowHandler(grid);

                    var rowIds = grid.getDataIDs();
                    var fun = grid.getGridParam("formatter");
                    if(rowIds && fun){
                        for(var i=0;i<rowIds.length;i++){
                            var rowId = rowIds[i];
                            var rowData = grid.getRowData(rowId, true);
                            var element = grid.find("tr#"+rowId);

                            fun(rowId, rowData, element);
                        }
                    }
                },
                _onSelectRow: function () {
                    onSelectRowHandler($(this).jqGrid());
                }
            });

            //默认选中行处理程序
            function onSelectRowHandler(grid) {
                var controls = grid.getGridParam("selectedToggle");
                if (controls) {
                    if (grid.getSelectRows().length > 0) {
                        $(controls).show();
                    } else {
                        $(controls).hide();
                    }
                }
                //处理icheck
                if (grid.getGridParam("icheckbox")) {
                    var chkAll = $(grid[0].grid.hDiv).find("th input[type='checkbox']");
                    var chk = $(grid[0].grid.bDiv).find("td input[type='checkbox'], td input[type='radio']");
                    chkAll.iCheck("update");
                    chk.iCheck("update");
                }

                if (grid.getGridParam("radiobutton")) {
                    var id = grid.getSelectRow();
                    grid.resetSelection();
                    grid.setSelection(id, false);
                }
            }

            //自适应宽度
            $(window).resize(function () {
                $(".ui-jqgrid").each(function () {
                    var target = $(this);
                    var id = target.attr("id").replace("gbox_", "");

                    var grid = $("#" + id);
                    if (grid.getGridParam("autowidth")) {//设置为自动计算宽度 才生效
                        //自适应宽度
                        grid.setGridWidth(target.parent().width(), true);
                        var bdiv = target.find(".ui-jqgrid-bdiv");
                        bdiv.width(bdiv.width() + 2);
                    }

                    if (grid.getGridParam("autoheight")) {//设置为自动计算宽度 才生效
                        var bdiv = target.find(".ui-jqgrid-bdiv");

                        var resizeHeight = $(window).height() - ns.lastDocumentHeight;
                        bdiv.height(bdiv.height() + resizeHeight);
                    }
                });
                //记录窗口最后高度
                ns.lastDocumentHeight = $(window).height();
            });

            ns.view.datagrid.inited = true;
        },
        enableICheck: function (target) {//启用iCheck
            var grid = $(target).jqGrid();
            var chkAll = $(target.grid.hDiv).find("th input[type='checkbox']");
            chkAll.attr("data-icheck", true);
            chkAll.attr("data-check-all", "data-check-item-" + target.id);
            var chk = $(target.grid.bDiv).find("td input[type='checkbox'], td input[type='radio']");
            chk.attr("data-check-item-" + target.id, "");
            ns.form.initCheckbox(chkAll);
            ns.form.initCheckbox(chk);

            chk.off("ifChanged");
            chk.on("ifChanged", function () {
                var isChecked = chkAll.is(":checked");
                var id = $(this).parents("tr").attr("id");
                if (id)
                    grid.setSelection(id);
                if (isChecked)
                    chkAll.prop("checked", true);
            });
        },
        enableDblResize: function (target) {//双击调整列宽
            var th = $(target.grid.hDiv).find("th");
            th.on("dblclick", function () {
                alert(2);
            })
        },
        renderEditable: function (title, classname, val, pk, placement) {// 标题 样式名 显示值 主键值 显示方向
            placement = placement || "right";
            return "<a data-type=\"text\" data-title=\"" + title + "\" data-pk=\"" + pk + "\" data-placement=\"" + placement + "\" class=\"jgrid-xeditable " + classname + "\">" + val + "</a>"
        },
        renderEnabled: function (val) {//启用，禁用
            if (val)
                return "<i class=\"fa fa-circle text-success ml5\">";
            else
                return "<i class=\"fa fa-circle text-danger ml5\">";
        },
        renderYesOrNo: function (val) {//是否
            if (val) return "是";
            else return "否";
        },
        renderOp: function (ops) {//操作菜单
            /**
             * ops为数组
             * 参数说明：
             * title 标题
             * divider 分隔线，如果设置了分割线，其他参数会被忽略
             * onclick 执行的代码
             * url 跳转的链接地址
             * target target属性
             * tab 标签页中打开
             * @type {string}
             */
            var html = "<div class=\"btn-group\">" +
                "<button type=\"button\" class=\"btn btn-default btn-xs dropdown-toggle\" data-toggle=\"dropdown\">" +
                "<i class=\"ti-settings\"></i>" +
                " <span class=\"ti-angle-down\"></span>" +
                "</button>";
            html += "<ul class=\"dropdown-menu\" role=\"menu\">";
            if (ops && ops.length > 0) {
                for (var i = 0; i < ops.length; i++) {
                    var id = "jqgrid-op-" + ns.view.datagrid.counter + "-" + i;
                    var op = ops[i];
                    if (op.divider) {//分割线
                        html += "<li class=\"divider\"></li>";
                    } else {
                        if (op.onclick) {
                            setEvent(id, op.onclick);
                            html += "<li><a href=\"javascript:;\" onclick=\"ns.view.datagrid._click('" + id + "')\">" + op.title + "</a></li>";
                        } else if (op.url) {
                            html += "<li><a href=\"" + op.url + "\"";
                            if (op.target)
                                html += " target='" + op.target + "'";
                            if (op.tab)
                                html += " data-tabs-link";
                            html += ">" + op.title + "</a></li>";
                        } else {
                            html += "<li><a href=\"javascript:;\">" + op.title + "</a></li>";
                        }
                    }
                }
            }
            html += "</ul></div>";
            return html;
        },
        renderLink: function (links) {
            /**
             * links 为数组
             * 参数说明：
             * title 标题
             * href 链接地址
             * target target属性
             * class 属性
             * onclick 点击事件
             */
            var defaultClass = "text-link ml5";
            var html = "";
            if (links && links.length > 0) {
                for (var i = 0; i < links.length; i++) {
                    var id = "jqgrid-link-" + ns.view.datagrid.counter + "-" + i;
                    var link = links[i];
                    html += "<a";
                    if (link.onclick) {
                        setEvent(id, link.onclick);
                        html += " href='javascript:;' onclick=\"ns.view.datagrid._click('" + id + "')\"";
                    } else {
                        html += " href='" + link.href + "'";
                    }
                    if (link.target) {
                        html += " target='" + link.target + "'";
                    }
                    if (link.class) {
                        link.class = defaultClass + " " + link.class;
                    } else {
                        link.class = defaultClass;
                    }
                    html += "class='" + link.class + "'";
                    html += ">" + link.title + "</a>";
                }
            }
            return html;
        },
        renderButton: function (buttons) {
            /**
             * buttons 为数组
             * 参数说明
             * title 标题
             * onclick 点击事件
             * class 样式
             */
            var defaultClass = "btn btn-xs ml5";
            var html = "";
            if (buttons && buttons.length > 0) {
                for (var i = 0; i < buttons.length; i++) {
                    var button = buttons[i];
                    var id = "jqgrid-btn-" + ns.view.datagrid.counter + "-" + i;
                    html += "<button type='button' ";
                    //样式
                    if (!button.class) {
                        button.class = "btn-default";
                    }
                    button.class = defaultClass + " " + button.class;
                    html += "class='" + button.class + "'";
                    //点击事件
                    if (button.onclick) {
                        setEvent(id, button.onclick);
                        html += " href='javascript:;' onclick=\"ns.view.datagrid._click('" + id + "')\"";
                    }
                    html += ">" + button.title + "</button>";
                }
            }
            return html;
        },
        renderProgressBar: function (opt) {
            /**
             * 参数说明：
             * value 最大值为1
             * class 样式
             */
            var oDiv = $("<div>");
            oDiv.css("height", "22px");
            oDiv.attr("class", "bg-primary");

            if (opt.class)
                oDiv.attr("class", opt.class);

            if (opt.value) {
                oDiv.css("width", (parseInt(opt.value * 100)) + "%");
            }
            return oDiv[0].outerHTML;
        },
        renderPie: function (opt) {
            /**
             * 参数说明：
             * value 最大值为1
             * class 样式
             */
            var oSvg = $("<svg>");
            oSvg.attr("width", 22).attr("height", 22);
            oSvg.css("transform", "rotate(-90deg)").css("border-radius", "50%");
            var oCircle = $("<circle>");
            oCircle.css("fill", "transparent").css("stroke", "#ccc").css("stroke-width", "50");
            oCircle.attr("r", 25).attr("cx", 11).attr("cy", 11);
            oSvg.append(oCircle);

            oSvg.attr("class", "bg-primary");
            if (opt.class)
                oSvg.attr("class", opt.class);
            if (opt.value) {
                oCircle.css("stroke-dasharray", (parseInt(opt.value * 158)) + " 158")
            }

            return oSvg[0].outerHTML;
        },
        _click: function (id) {//操作菜单方法执行
            var fun = ns.view.datagrid.opClickEvents[id];
            if (fun) fun();
        },
        autoHeight: function () {
            return $(".main-content .wrapper").height() - ns.view.datagrid.offsetHeight;
        }
    });

    //绑定事件
    function setEvent(id, fun) {
        ns.view.datagrid.opClickEvents[id] = fun;
        ns.view.datagrid.counter++;
    }

    //清理
    function clearEvents() {
        ns.view.datagrid.opClickEvents = {};
        ns.view.datagrid.counter = 0;
    }
})();
