/**
 * 双向选择器
 * Created by zengchao on 2017/6/16.
 */
ns.component("form.selection", function (options) {
    if (!ns.view.Dialog) {
        ns.requireJS("/framework/js/view/dialog.js");
    }
    var _def = {
        title: "双向选择",//标题
        items: [],//待选
        selected: [],//已选
        itemsText: "待选择项",
        selectedText: "已选择项",
        valueField: "value",
        textField: "text",
        height: "300px",
        allowEmpty: true,
        emptyMessage: "已选项项不允许为空",
        min : 0,
        minMessage : "至少选择{length}项",
        max : 0,
        maxMessage : "最多选择{length}项",
        onAdd: function (data) {//选中事件

        },
        onRemove: function (data) {

        },
        onComplete: function (data) {//完成选中事件

        }
    }
    options = $.extend(_def, options);
    var buttonPaddingTop = (parseInt(options.height) - (34 * 4) - 30) / 2;
    //创建结构
    var content = '<div class="col-lg-4 col-md-4 col-sm-4">';
    content += '<div>' + options.itemsText + "</div>";
    content += '<select multiple="multiple" class="form-control items" style="height:' + options.height + '"></select>';
    content += '</div>';
    content += '<div class="col-lg-4 col-md-4 col-sm-4 text-center" style="padding-top: ' + buttonPaddingTop + 'px">';
    content += '<button type="button" class="btn btn-default add mb10">添加选中>></button><br>';
    content += '<button type="button" class="btn btn-default remove mb10"><<移除选中</button><br>';
    content += '<button type="button" class="btn btn-default add-all mb10">添加全部>></button>';
    content += '<button type="button" class="btn btn-default remove-all"><<移除全部</button>';
    content += '</div>';
    content += '<div class="col-lg-4 col-md-4 col-sm-4">';
    content += '<div>' + options.selectedText + "</div>";
    content += '<select multiple="multiple" class="form-control selected" style="height:' + options.height + '"></select>';
    content += '</div>';

    //创建弹窗
    var dialog = new ns.view.Dialog({
        title: options.title,
        showHeader: true,
        showFooter: true,
        showClose: true,
        showCancel: true,
        headerClass: "",
        footerClass: "",
        content: content,
        onShown: function (dg) {
            var items = dg.find(".items");
            var selected = dg.find(".selected");
            //设置待选项
            for (var i = 0; i < options.items.length; i++) {
                var value = options.items[i][options.valueField];
                var text = options.items[i][options.textField];
                if (value && text) {
                    addTo(items, value, text);
                }
            }
            //设置已选项
            if (options.selected && options.selected.length > 0) {
                for (var i = 0; i < options.selected.length; i++) {
                    var value = options.selected[i];
                    items.find("option[value='" + value + "']").prop("selected", "selected");
                }
                //执行添加
                add();
            }

            function addTo(target, value, text) {
                var option = $("<option>");
                option.val(value);
                option.text(text);

                target.append(option);
            }

            function add() {
                //获取选中的
                var selectedOptions = items.find("option:selected");
                if (selectedOptions.length) {
                    for (var i = 0; i < selectedOptions.length; i++) {
                        var option = $(selectedOptions[i]);
                        var data = {};
                        data[options.valueField] = option.val();
                        data[options.textField] = option.text();
                        var result = options.onAdd(data);
                        if (result !== false) {
                            addTo(selected, option.val(), option.text());
                            option.remove();
                        }
                    }
                }
            }

            function remove() {
                var selectedOptions = selected.find("option:selected");
                if (selectedOptions.length) {
                    for (var i = 0; i < selectedOptions.length; i++) {
                        var option = $(selectedOptions[i]);
                        var data = {};
                        data[options.valueField] = option.val();
                        data[options.textField] = option.text();
                        var result = options.onRemove(data);
                        if (result !== false) {
                            addTo(items, option.val(), option.text());
                            option.remove();
                        }

                    }
                }
            }

            function addAll() {
                //选中所有
                items.find("option").prop("selected", "selected");
                add();
            }

            function removeAll() {
                selected.find("option").prop("selected", "selected");
                remove();
            }

            //绑定事件
            dg.find(".add").on("click", add);
            dg.find(".remove").on("click", remove);
            dg.find(".add-all").on("click", addAll);
            dg.find(".remove-all").on("click", removeAll);
            items.on("dblclick", add);
            selected.on("dblclick", remove);
        },
        onOk: function (dg) {
            var selected = dg.find(".selected");
            var selectedOptions = selected.find("option");
            var array = [];
            if (selectedOptions.length) {
                for (var i = 0; i < selectedOptions.length; i++) {
                    var option = $(selectedOptions[i]);
                    var data = {};
                    data[options.valueField] = option.val();
                    data[options.textField] = option.text();

                    array.push(data);
                }
            }

            if (array.length > 0 || options.allowEmpty) {
                if(array.length < options.min){
                    return showError(options.minMessage.replace("{length}", options.min));
                }else if(array.length > options.max){
                    return showError(options.maxMessage.replace("{length}", options.max));
                }
                options.onComplete(array);
                dg.close();
            } else {
                return showError(options.emptyMessage);
            }

            function showError(msg){
                try {
                    ns.tip.toast.warningCenter(msg);
                } catch (e) {
                    alert(msg);
                }
            }
        }
    });
    dialog.show();
});