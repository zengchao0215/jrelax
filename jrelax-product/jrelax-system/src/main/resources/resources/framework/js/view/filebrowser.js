ns.component("view.filebrowser", new function () {
    var $options = {};
    var $modal = null;
    var $url = ns.getBasePath() + "/common/filebrowser";
    var $path = "/";//当前文件路径
    var itemClass = "col-lg-3 col-md-3 col-sm-3";
    var uploadPrefix = "";//上传地址前缀
    var _def = {
        size: " modal-md",
        filter: "",//文件筛选
        autoclose: true,//自动关闭
        onSelect: function () {
        } //选择事件
    }
    //过滤类型
    this.type = {
        image: "jpg, jpeg, png, gif, bmp",
        office: "doc, docx, xls, xlsx, ppt, pptx",
        video: "mp4, avi, mkv, mov, rm, wmv, flv"
    };

    this.open = function (opt) {
        $options = $.extend(_def, opt);
        var url = $url;
        if ($options.filter) {
            //处理filter
            $options.filter = $options.filter.replaceAll(" ", "");
            url = $url + "?filter=" + $options.filter;
        }
        var instance = this;
        $modal = ns.view.showModal(url, {
            onLoaded: function (modal) {
                if ($options.size === "modal-lg") {
                    itemClass = "col-lg-1 col-md-1 col-sm-1"
                } else if ($options.size === "modal-md") {
                    itemClass = "col-lg-3 col-md-3 col-sm-3"
                } else if ($options.size === "modal-sm") {
                    itemClass = "col-lg-4 col-md-4 col-sm-4";
                } else if ($options.size === "modal-xs") {
                    itemClass = "col-lg-6 col-md-6 col-sm-6";
                }
                modal.find("#fileList .col-lg-3").attr("class", itemClass);

                if ($path.length > 0)
                    instance.load($path);
                else
                    instance.load("/");

                //获取上传地址前缀
                uploadPrefix = modal.find("#uploadPrefix").val();

                if ($options.filter.length > 0) {
                    modal.find(".modal-title span").text("（限定类型）");
                }
            },
            onShown: function () {
                $.proxy(init, instance)();
            },
            onHidden: function () {
                $options.filter = "";
            }
        });
    };
    this.close = function () {
        $modal.close();
    };
    this.load = function (selectPath) {
        $path = selectPath;
        var fileList = $modal.find("#fileList");
        ns.showLoadingbar(fileList);

        var instance = this;
        $.post($url + "/folder", {path: $path, filter: $options.filter}, function (data) {
            if (data.success === true) {
                if (data.data.length === 0) {
                    fileList.html("<center class='pb15'><img src='" + ns.getBasePath() + "/framework/img/icon/empty.png'/><br><br>此文件夹是空的。</center>");
                } else {
                    fileList.html("");
                    for (var i = 0; i < data.data.length; i++) {
                        var file = data.data[i];
                        var item = "<div class=\"" + itemClass + "\">";
                        if (file.type === 1) {//文件
                            var name = file.name, title = file.modifyTime;
                            if (name.length > 20) {
                                title = name;
                                name = name.substring(0, 18) + '...';
                            }
                            item += "<div class=\"widget\" data-type=\"select\" data-path=\"" + file.path + "\" data-file-size='" + file.size + "' data-file-name='" + file.name + "'>";
                            var img = ns.getBasePath() + "/framework/img/icon/file.png";
                            var suffix = getSuffix(file.name);
                            if (suffix === ".jpg" || suffix === ".jpeg" || suffix === ".png" || suffix === ".gif" || suffix === ".bmp") {
                                img = uploadPrefix + "/" + file.path;
                            } else {
                                img = ns.getBasePath() + file.icon
                            }
                            item += "<div class=\"widget-body\" title=\"" + file.size + "\"><img src=\"" + img + "\" height=\"56\"></div>";
                            item += "<div class=\"widget-footer\" title=\"" + title + "\">" + name + "</div>";
                        } else if (file.type === 2) {
                            item += "<div class=\"widget\" data-type=\"load\" data-path=\"" + file.path + "\">";
                            item += "<div class=\"widget-body\" title=\"" + file.size + "\"><img src=\"" + ns.getBasePath() + "/framework/img/icon/folder.png\" height=\"56\"></div>";
                            item += "<div class=\"widget-footer\" title=\"" + file.modifyTime + "\">" + file.name + "</div>";
                        } else if (file.type === 3) {
                            item += "<div class=\"widget\" data-type=\"load\" data-path=\"" + file.path + "\">";
                            item += "<div class=\"widget-body\" title=\"" + file.size + "\"><img src=\"" + ns.getBasePath() + "/framework/img/icon/folder2.png\" height=\"56\"></div>";
                            item += "<div class=\"widget-footer\" title=\"" + file.modifyTime + "\">" + file.name + "</div>";
                        }
                        item += "</div>";

                        fileList.append(item);
                    }
                }
                $.proxy(init, instance)();
            } else {
                alert("加载失败,请稍后重试！");
            }

            ns.closeLoadingbar(fileList);
        });
    };
    this.reload = function () {
        this.load($path);
    };
    this.download = function (path) {
        alert("暂不提供下载！");
    };
    this.preview = function (path) {//文件预览
        var previewId = "__divFilePreview" + (new Date().getTime());
        var oDiv = "<div id=\"" + previewId + "\" class=\"modal fade bs-modal-sm\" tabindex=\"2\" role=\"dialog\" aria-hidden=\"true\"><div class=\"modal-dialog\"><div class=\"modal-content\"></div></div></div>";
        $("body").append(oDiv);
        var preview = $("#" + previewId);
        $.post(ns.getBasePath() + "/common/filebrowser/preview", {path: path}, function (data) {
            preview.find(".modal-content").html(data);
            preview.modal("show");
            //关闭窗口后
            preview.on("hidden.bs.modal", function () {
                preview.remove();
            });
        });
    };
    this.select = function (path, data) {
        if (!path.startsWith("/"))
            path = "/" + path;
        $options.onSelect(path, data);
        if ($options.autoclose === true)
            this.close();
    };
    this.prev = function () {
        var path = $path;
        var idx = path.lastIndexOf("/");
        if (idx === -1)
            path = "/";
        else
            path = path.substring(0, idx);
        this.load(path);
    };
    this.root = function () {
        if ($path.length > 0) {
            this.load("/");
        }
    };

    function init() {
        initNav(this);
        initEvents(this);
    }

    //获取后缀名
    function getSuffix(name) {
        var idx = name.lastIndexOf(".");
        if (idx < 0)
            return "";
        return name.substring(name.lastIndexOf(".")).toLowerCase();
    }

    function initEvents(instance) {
        //select
        $modal.find("[data-type='select']").off("click");
        $modal.find("[data-type='select']").on("click", function () {
            var item = $(this);
            var path = item.data("path");
            var data = {
                path: path,
                size: item.data("file-size"),
                name: item.data("file-name"),
                uploadPrefixPath: uploadPrefix
            };
            instance.select(path, data);
        });
        //preview
        $modal.find("[data-type='preview']").off("click");
        $modal.find("[data-type='preview']").on("click", function () {
            var path = $(this).data("path");
            instance.preview(path);
        });
        //load
        $modal.find("[data-type='load']").off("click");
        $modal.find("[data-type='load']").on("click", function () {
            var path = $(this).data("path");
            instance.load(path);
        });
        //reload
        $modal.find("[data-type='reload']").off("click");
        $modal.find("[data-type='reload']").on("click", function () {
            instance.reload();
        });
        //root
        $modal.find("[data-type='root']").off("click");
        $modal.find("[data-type='root']").on("click", function () {
            instance.root();
        });
    }

    function initNav(instance) {
        //初始化返回上一级按钮
        var btnPrev = $modal.find("#prev");
        if ($path.length === 0) {
            btnPrev.attr("disabled", "disabled");
            btnPrev.unbind("click");
        } else {
            btnPrev.removeAttr("disabled");
            btnPrev.unbind("click");
            btnPrev.bind("click", function () {
                instance.prev();
            });
        }
        //初始化导航
        var nameMap = {
            "$IMG$": "最新图片",
            "$FILE$": "最新文件",
            "file": "文件目录",
            "image": "图片目录",
            "temp": "临时目录"
        };
        var nav = "<li><i class=\"ti-home\"></i> <a href=\"javascript:;\" data-type='root'>根目录</a></li>";
        if ($path.length > 0) {
            var folders = $path.split("/");
            for (var i = 0; i < folders.length - 1; i++) {
                var cPath = "";
                for (var j = 0; j <= i; j++) {
                    cPath += "/" + folders[j];
                }
                cPath = cPath.substring(1);
                if (cPath.length > 0) {
                    var name = folders[i];
                    if (nameMap[name])
                        name = nameMap[name];
                    nav += "<li><a href=\"javascript:;\" data-type='load' data-path=\"" + cPath + "\">" + name + "</a></li>";
                }
            }
            var name = folders[folders.length - 1];

            if (nameMap[name])
                name = nameMap[name];
            nav += "<li class=\"active\">" + name + "</li>";
        }
        $modal.find("#nav").html(nav);
    }
});