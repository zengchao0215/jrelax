//注册到容器中
ns.component("form.uploader", new function () {
    var $url = ns.getBasePath() + "/common/upload";
    var $blockUrl = ns.getBasePath() + "/common/upload/block/do";
    var $id = "__divUploader" + (new Date().getTime());
    var $modal = null;
    this.type = {
        image: ".jpg,.jpeg,.png,.gif,.bmp",
        word: ".doc,.docx",
        excel: ".xls,.xlsx",
        ppt: ".ppt,.pptx",
        office: ".doc,.docx,.xls,.xlsx,.ppt,.pptx",
        pdf: ".pdf,.pdfx",
        video: ".mp4,.avi,.mkv,.mov,.rm,.wmv,.flv",
        audio: ".mp3,.amr,.ogg,.wav,.acc"
    };
    var $options = {};
    var _def = {
        icon: "fa fa-cloud-upload",
        title: "上传文件",
        filter: "",// 文件类型过滤，可传入 *.jpg,*.png，也可以传入快捷类型：参见this.type
        autoClose: true, //上传完成后，自动关闭
        autoName: true, // 文件上传后是否自动重命名
        savePath: "",// 服务器端保存路径
        showFilename: false,
        showSavePath: false, //显示文件上次保存路径
        multiple: true,//多文件选择
        selectAndSubmit: false,//选择文件后立即上传
        maxSize: 50 * 1024 * 1024,//最大上传文件大小
        drop: true,//开启拖拽上传
        autoBlockUpload: false,//自动切换大文件上传
        blockUpload: false,//是否分块上传
        blockSize: 2 * 1024 * 1024,//分块大小，默认2M
        max: -1,//最大上传文件数量
        min: -1,//最小上传文件数量
        fileBrowser: true,//是否显示文件浏览器
        onLoad: function () {
        },
        success: function () {
        },
        error: function (msg) {
            ns.tip.toast.error(msg);
        }
    }

    this.close = function () {
        $modal.modal("hide");
    };
    this.openImage = function (opt) {
        opt.filter = this.type.image;
        this.open(opt);
    }
    this.openVideo = function (opt) {
        opt.filter = this.type.video;
        this.open(opt);
    }
    this.open = function (opt) {
        $options = $.extend(_def, opt);
        if ($options.blockUpload) {
            //开启分块上传时，不开启拖拽
            $options.drop = false;
            //关闭多文件上传
            $options.multiple = false;
            if (!opt.title)
                $options.title = "超大文件上传";
            //引入队列
            ns.requireJS("/framework/js/util/queue.js");
        }
        if (this.type[$options.filter]) {
            $options.filter = this.type[$options.filter];
        }

        if ($options.autoBlockUpload) {
            //引入队列
            ns.requireJS("/framework/js/util/queue.js");
        }
        $modal = ns.view.showModal($url, {
            onLoaded: function ($modal) {
                var $form = $modal.find("form");
                $form.ajaxForm({
                    beforeSubmit: function (arr, $form, op) {
                        var fileList = $form.find("input:file")[0].files;
                        if (fileList.length === 0) {
                            showError("请先选择文件再点击上传");
                            return false;
                        }
                        if ($options.filter.length > 0) {
                            var name = $form.find("input:file").val();
                            var isMatch = checkFilter(name);
                            if (!isMatch) return false;
                        }
                        doUpload($form, fileList);
                        return false;
                    }
                });
                //设置标题
                $modal.find(".modal-title>span").text($options.title);
                $modal.find(".modal-title>i").attr("class", $options.icon);
                //自动获取名字
                if ($options.autoName === false) {
                    $form.find("input:file").bind("change", function () {
                        var name = $(this).val();

                        name = name.substring(name.lastIndexOf("\\") + 1);
                        name = name.substring(name.lastIndexOf("/") + 1);
                        name = name.substring(0, name.lastIndexOf("."));
                        $form.find("input[name='filename']").val(name);
                    });
                }
                //存放路径
                if ($options.savePath) {
                    $form.find("input[name='savePath']").val($options.savePath);
                }
                //是否显示文件名
                if ($options.showFilename === true) {
                    $form.find("input[name='filename']").parent().show();
                }
                //是否显示存放路径
                if ($options.showSavePath === true) {
                    $form.find("input[name='savePath']").parent().show();
                }
                //文件选择框美化
                var input_file = $form.find("input[name='file']");
                //取消多文件选择
                if ($options.multiple === false) {
                    input_file.removeAttr("multiple");
                }
                input_file.bind("change", function () {
                    if (this.files) {
                        var path = this.value.replaceAll("\\", "/");
                        var dir = path.substring(0, path.lastIndexOf("/"));
                        var paths = [];
                        $.each(this.files, function (i, n) {
                            // paths.push(dir + "/" + n.name);
                            paths.push(n.name);
                        });
                        input_file.next().find("input").val(paths.toString());
                    } else {
                        input_file.next().find("input").val($(this).val());
                    }
                    if ($options.selectAndSubmit)
                        $form.submit();
                });
                input_file.next().find(".btn").bind("click", function () {
                    input_file.click();
                });
                //处理filter中的*号
                $options.filter = $options.filter.replaceAll("*", "");
                input_file.attr("accept", $options.filter);

                if ($options.drop)
                    initDropUpload($form, $options);

                //文件选择器
                if ($options.fileBrowser) {
                    $form.find("#btnSelect").on("click", function () {
                        ns.requireJS("/framework/js/view/filebrowser.js");
                        ns.view.filebrowser.open({
                            filter: $options.filter,
                            onSelect: function (path, data) {
                                var files = {
                                    success: true,
                                    files: [
                                        {
                                            path: path,
                                            size: data.size,
                                            original: data.name,
                                            uploadPrefixPath: data.uploadPrefixPath
                                        }
                                    ]
                                };
                                onSuccess(files);
                            }
                        });
                    }).show();
                }

                $options.onLoad($modal);
            }
        });

        return $modal;
    };

    //初始化拖拽上传功能
    function initDropUpload($form, $options) {
        $(document).on({
            //拖离
            dragleave: function (e) {
                e.preventDefault();
            },
            //拖后放
            drop: function (e) {
                e.preventDefault();
            },
            //拖进
            dragenter: function (e) {
                e.preventDefault();
            },
            //拖来拖去
            dragover: function (e) {
                e.preventDefault();
            }
        });
        $form.find("#dropArea").parents(".form-group").show();
        var dropArea = $form.find("#dropArea")[0];
        var dropTip = $(dropArea).find("h3").text();
        if (dropArea) {
            dropArea.addEventListener("drop", function (e) {
                e.preventDefault();
                var fileList = e.dataTransfer.files; //获取文件对象

                if (fileList.length === 0) return false;
                if ($options.autoName === false) {
                    $form.find("input[name='filename']").val(fileList[0].name);
                }
                doUpload($form, fileList);
            });
            dropArea.addEventListener("dragover", function () {
                $(dropArea).addClass("bg-default");
                $(dropArea).find("h3").text("松开鼠标以上传");
            });
            dropArea.addEventListener("dragleave", function () {
                $(dropArea).removeClass("bg-default");
                $(dropArea).find("h3").text(dropTip);
            });
            dropArea.addEventListener("drop", function () {
                $(dropArea).removeClass("bg-default");
                $(dropArea).find("h3").text(dropTip);
            });
        }
    }

    //执行上传
    function doUpload(form, fileList) {
        if ($options.blockUpload) {
            doBlockUpload(fileList);
        } else {
            //先删除表单中的file,防止文件重复
            var copyForm = form.clone();
            copyForm.find("input[type='file']").remove();
            var formData = new FormData(copyForm[0]);
            //再添加file到FormData
            if (fileList) {
                if ($options.max > 0 && fileList.length > $options.max) {
                    showError("最多允许上传" + $options.max + "个文件");
                    return;
                }
                if ($options.min > 0 && fileList.length < $options.min) {
                    showError("至少需要上传" + $options.min + "个文件");
                    return;
                }
                var fileSize = 0;
                for (var i = 0; i < fileList.length; i++) {
                    var file = fileList[i];
                    fileSize += file.size;
                    if (checkFilter(file.name)) {
                        formData.append('file', file);
                    } else {
                        return;
                    }
                }
                if (fileSize > $options.maxSize) {
                    if ($options.autoBlockUpload) {
                        doBlockUpload(fileList);
                    } else {
                        showError("文件大小限制为：" + sizeFormat($options.maxSize) + ", 当前文件大小为：" + sizeFormat(fileSize));
                    }
                    return;
                }

                form.find("#ok").button("loading");
                //显示进度条
                var xhr = $.ajaxSettings.xhr();
                if (xhr.upload) {
                    xhr.upload.addEventListener('progress', function (event) {
                        var percent = 0;
                        var position = event.loaded || event.position;
                        var total = event.total;
                        if (event.lengthComputable) {
                            percent = Math.ceil(position / total * 100);
                        }
                        showProgressBar(position, total, percent);
                    }, false);
                }
                xhr.open("post", form.attr("action"));
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        var data = jQuery.parseJSON(xhr.responseText);
                        onSuccess(data);
                        form.find("#ok").button("reset");
                    }
                };
                xhr.error = function () {
                    showError("网络拥堵，文件上传失败！");
                };
                xhr.send(formData);
            } else {
                showError("上传文件为空");
            }

            // $.ajax({
            //     url: form.attr("action"),
            //     type: "post",
            //     data: formData,
            //     processData: false,
            //     contentType: false,
            //     success: function (data) {
            //         onSuccess(data);
            //         form.find("#ok").button("reset");
            //     }
            // });
        }
    }

    function doBlockUpload(files) {
        var data = {
            success: true,
            files: []
        };
        var fileQueue = new Queue();
        var totalFileSize = 0, currentFileSize = 0;
        for (var n = 0; n < files.length; n++) {
            fileQueue.add({
                file: files[n]
            });
            totalFileSize += files[n].size;
        }
        fileQueue.onQueue = function (e) {
            var file = e.file;
            var blockSize = $options.blockSize;
            var fileSize = file.size;
            var partCount = fileSize / blockSize;
            partCount = Math.floor(partCount);//取整数
            var uploadPath = "", uploadFileName = "", savePath = "", uploadPrefixPath;

            var queue = new Queue();
            for (var i = 0; i <= partCount; i++) {
                queue.add({
                    index: i
                });
            }
            queue.onQueue = function (element) {
                var i = element.index;
                var partSize = blockSize;//计算每个分片的大小
                if ((i + 1) * blockSize >= fileSize) {
                    partSize = fileSize - (i * blockSize);
                }
                if (partSize > 0) {//如果正好
                    var partStart = i * blockSize;
                    var partEnd = partStart + partSize;

                    var blockFile = file.slice(partStart, partEnd);
                    var formData = new FormData();
                    formData.append("file", blockFile);
                    formData.append("index", i);
                    formData.append("start", partStart);
                    formData.append("filename", file.name);
                    formData.append("uploadFileName", uploadFileName);
                    formData.append("savePath", savePath);

                    $.ajax({
                        url: $blockUrl,
                        type: "post",
                        data: formData,
                        processData: false,
                        contentType: false,
                        timeout: 0,
                        success: function (data) {
                            if (data.uploadFileName)
                                uploadFileName = data.uploadFileName;
                            if (data.savePath)
                                savePath = data.savePath;
                            if (data.uploadPath)
                                uploadPath = data.uploadPath;
                            if (data.uploadPrefixPath)
                                uploadPrefixPath = data.uploadPrefixPath;
                            // console.dir("send:" + partStart + "-----" + partEnd);
                            currentFileSize += partEnd - partStart;
                            showProgressBar(currentFileSize, totalFileSize, Math.floor((currentFileSize / totalFileSize) * 100));
                            queue.next();
                        },
                        error: function () {
                            queue.cancel();
                            fileQueue.cancel();
                            showError("网络拥堵，文件上传失败！");
                        }
                    });
                }
            };
            queue.onComplete = function () {
                showProgressBar(currentFileSize, totalFileSize, Math.floor((currentFileSize / totalFileSize) * 100));
                if (uploadPath.length > 0) {
                    data.files.push({
                        original: file.name,//原文件名
                        size: file.size,//文件大小
                        path: uploadPath,
                        uploadPrefixPath: uploadPrefixPath
                    });
                }

                fileQueue.next();
            };
            queue.execute();
        };

        fileQueue.onComplete = function () {
            onSuccess(data);
        };

        fileQueue.execute();
    }

    function checkFilter(name) {
        if ($options.filter.length === 0) return true;
        try {
            var ext = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
            var result = false;
            var filters = $options.filter.split(",");
            for (var i = 0; i < filters.length; i++) {
                var filter = $.trim(filters[i].toLowerCase());
                if (filter.indexOf(".") > -1) {
                    filter = filter.substring(filter.indexOf(".") + 1);
                }
                if (filter === $.trim(ext)) {
                    result = true;
                    break;
                }
            }
            if (!result) {
                showError("文件类型限制为：" + $options.filter + "");
                return false;
            }
            return true;
        } catch (e) {
            showError("文件类型限制为：" + $options.filter + "");
            return false;
        }
    }

    function onSuccess(data) {
        if (data.success === true) {
            if (typeof($options.success) === "function") {
                var files = data.files;
                var names = [], paths = [];
                $.each(files, function (i, n) {
                    var path = n.path;
                    n.name = path.substring(path.lastIndexOf("/") + 1);
                    names.push(n.name);
                    paths.push(path);
                });
                $options.success(paths.toString(), names.toString(), files);
            }
        } else {
            $options.error(data.message);
        }
        //默认自动关闭
        if ($options.autoClose === true) {
            ns.form.uploader.close();
        }
    }

    function sizeFormat(size) {
        size = size / 1024;
        if (size < 1024) return parseFloat(size).toFixed(1) + "KB";
        size = size / 1024;
        if (size < 1024) return parseFloat(size).toFixed(1) + "MB";
        size = size / 1024;
        if (size < 1024) return parseFloat(size).toFixed(1) + "GB";
    }

    function showProgressBar(postion, total, percentComplete) {
        console.log(percentComplete);
        $modal.find("#cur").text(sizeFormat(postion));
        $modal.find("#total").text(sizeFormat(total));
        var procbar = $modal.find(".progress-bar");
        procbar.css("width", percentComplete + "%");
        procbar.parents("div").show();
    }

    function showError(msg) {
        ns.tip.toast.errorCenter(msg);
    }
});