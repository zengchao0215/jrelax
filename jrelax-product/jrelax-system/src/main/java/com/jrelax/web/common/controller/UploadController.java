package com.jrelax.web.common.controller;

import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.core.web.support.http.HandlerRequest;
import com.jrelax.kit.FileKit;
import com.jrelax.kit.Md5Kit;
import com.jrelax.kit.StringKit;
import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.entity.NFile;
import com.jrelax.web.system.service.FileService;
import com.jrelax.web.system.service.LogService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Controller
@RequestMapping("/common/upload")
public class UploadController extends BaseController<Object> {
    @Resource
    private LogService logService;
    @Resource
    private FileService fileService;

    @RequestMapping
    public String index() {
        return "common/upload/index";
    }

    @RequestMapping(value = "/do")
    @ResponseBody
    public JSONObject doUpload(@RequestParam("file") MultipartFile[] uploadFiles, @RequestParam(required = false) String filename, @RequestParam(required = false) String savePath) {
        if (uploadFiles.length > 0) {
            JSONArray fileArray = new JSONArray();
            for (MultipartFile file : uploadFiles) {
                if (!file.isEmpty()) {
                    JSONObject json = new JSONObject();
                    //根据MD5值获取文件，如果存在直接返回
                    try {
                        String md5 = Md5Kit.md5Hex(file.getBytes());
                        JSONObject jFile = check(md5);
                        if (jFile.getBoolean("success")) {
                            json = jFile.getJSONObject("file");

                            fileArray.add(json);
                            continue;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    json.element("original", file.getOriginalFilename());
                    json.element("size", file.getSize());
                    //保存文件
                    String path = "";
                    try {
                        path = ApplicationCommon.getFileStore().upload(savePath, StringKit.isEmpty(filename) ? file.getOriginalFilename() : filename, file.getBytes(), StringKit.isEmpty(filename));
                    } catch (IOException e) {
                        e.printStackTrace();
                        continue;//跳过此文件
                    }
                    String suffix = FileKit.getSuffix(path);
                    String name = FileKit.getFilename(path);
                    json.element("path", path);
                    json.element("suffix", suffix);
                    json.element("name", name);
                    json.element("uploadPrefixPath", WebApplicationCommon.getUploadPrefixPath());
                    fileArray.add(json);

                    //保存文件到数据库中
                    try {
                        NFile nFile = new NFile();
                        nFile.setName(name);
                        nFile.setPath(path);
                        nFile.setAbsolutePath(ApplicationCommon.getFileStore().getAbsolutePath(path));
                        nFile.setPrefix(WebApplicationCommon.getUploadPrefixPath());
                        nFile.setOriginal(file.getOriginalFilename());
                        nFile.setMd5(Md5Kit.md5Hex(file.getBytes()));
                        nFile.setSuffix(suffix);
                        nFile.setType(FileKit.getType(path));
                        nFile.setSize(file.getSize());
                        nFile.setDisplaySize(FileKit.getDisplaySize(file.getSize()));

                        fileService.save(nFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            //记录日志
            logService.info("上传文件", fileArray.toString(), getCurrentUser().getUserName(), HandlerRequest.fromWebRequest(getRequest()));
            return WebResult.success().element("files", fileArray);
        } else {
            return WebResult.error("请选择文件后再上传");
        }
    }

    /**
     * 分片上传，用于大文件上传
     *
     * @param uploadFile     文件
     * @param filename       文件名
     * @param start          开始文件头位置
     * @param uploadFileName 上传文件名，用于续传
     * @param savePath       保存路径
     * @return
     */
    @RequestMapping("/block/do")
    @ResponseBody
    public JSONObject doBlockUpload(@RequestParam("file") MultipartFile uploadFile, String filename, long start, String uploadFileName, String savePath) {
        //生成文件名
        String suffix = FileKit.getSuffix(filename);
        if (StringKit.isEmpty(uploadFileName)) {
            uploadFileName = FileKit.getRandomFilename(suffix, JRelaxSystemConfigHelper.get("upload.file.name", "hex36"));
        }
        String path = "";
        try {
            path = ApplicationCommon.getFileStore().upload(savePath, uploadFileName, uploadFile.getInputStream(), start);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject result = WebResult.success();
        result.element("uploadFileName", uploadFileName);
        result.element("savePath", savePath);
        result.element("uploadPath", path);
        result.element("uploadPrefixPath", WebApplicationCommon.getUploadPrefixPath());
        result.element("original", filename);
        return result;
    }

    /**
     * 根据MD5值查询文件
     *
     * @param md5
     * @return
     */
    @RequestMapping("/check")
    @ResponseBody
    public JSONObject check(String md5) {
        NFile nFile = fileService.getByMd5(md5);
        if (nFile != null) {
            JSONObject json = new JSONObject();
            //如果文件存在
            if (FileKit.exists(nFile.getAbsolutePath())) {
                json.put("original", nFile.getOriginal());
                json.put("size", nFile.getSize());
                json.put("path", nFile.getPath());
                json.put("suffix", nFile.getSuffix());
                json.put("name", nFile.getName());
                json.put("uploadPrefixPath", nFile.getPrefix());

                return WebResult.success().element("file", json);
            } else {//删除
                fileService.delete(nFile);
            }
        }
        return WebResult.error("not found");
    }
}
