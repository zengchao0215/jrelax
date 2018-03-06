//附件上传，多选
//依赖bootstrap-tagsinput
//依赖uploader
(function ($) {
    // if(!ns.form.uploader) return alert("需要引入/framework/js/form/uploader.js");
    $.fn.tagsinputImage = function (options) {
        var def = {
            buttonText : "添加图片",
            max: -1, //最大数量，-1表示不限制
            items : [],
            //上传相关
            // filter: ns.form.uploader.type.image,// 文件类型过滤，传入 *.jpg,*.png
            autoName: true, // 文件上传后是否自动重命名
            savePath: "",// 服务器端保存路径
            selectAndSubmit : true,
            autoClose: true //上传完成后，自动关闭
        }
        options = $.extend(def, options);
        return $(this).each(function () {
            var el = $(this);
            var btnAdd = $("<button class='btn btn-default btn-outline text-primary' type='button'><i class='fa fa-image'></i> "+options.buttonText+"</button>");
            el.tagsinput({
                maxTags: options.max,
                readonly: true,
                containerClass: "image",
                itemValue : "path",
                itemText : "name"
            });
            el.prev().append("<br/>").append(btnAdd);
            //绑定事件
            btnAdd.bind("click", function(){
                var size = el.tagsinput('items').length;
                if(options.max !== -1 && size >= options.max)
                    return ns.alert("最多允许上传"+options.max+"个图片");

                options.success = function(path, name, files){
                    for(var i=0;i<files.length;i++){
                        var file = files[i];
                        var src = file.uploadPrefixPath + file.path;
                        el.tagsinput("add", {name : file.name, path :file.path, size: file.size, image: true, src : src});
                    }

                }
                ns.form.uploader.openImage(options);
            });

            //初始化
            if(options.items && options.items.length > 0){
                for(var i=0;i<options.items.length;i++){
                    el.tagsinput("add", options.items[i]);
                }
            }
        });
    }

    ns.component("form.upload.image", $.fn.tagsinputImage);
}(jQuery));