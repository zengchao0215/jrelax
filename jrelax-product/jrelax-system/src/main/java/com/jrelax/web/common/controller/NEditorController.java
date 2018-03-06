package com.jrelax.web.common.controller;

import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.kit.FileKit;
import com.jrelax.kit.StringKit;
import com.jrelax.web.support.BaseController;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@RestController
@RequestMapping("/common/neditor")
public class NEditorController extends BaseController {
    @Resource
    private UploadController uploadController;

    @RequestMapping
    public JSONObject index(String action, HttpServletRequest request) {
        JSONObject config = config();
        JSONObject result = new JSONObject();
        switch (action) {
            case "config":
                result = config;
                break;
            case "uploadimage"://上传图片
            case "uploadvideo"://上传视频
            case "uploadfile"://上传文件
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                JSONObject files = uploadController.doUpload(multipartRequest.getFiles("file").toArray(new MultipartFile[]{}), null, null);
                JSONArray fileArray = files.getJSONArray("files");
                JSONObject file = fileArray.getJSONObject(0);

                result.put("state", "SUCCESS");
                result.put("url", file.getString("uploadPrefixPath") + file.getString("path"));
                result.put("title", file.getString("original"));
                result.put("original", file.getString("original"));
                result.put("type", FileKit.getSuffix(file.getString("path")));
                result.put("size", file.get("size"));
                break;
            case "catchimage"://抓取网络图片
                String[] sources = (String[]) getParameterMap().get("source[]");
                if (sources != null) {
                    JSONArray list = new JSONArray();
                    for (String source : sources) {
                        String url = source.substring(0, source.indexOf("?"));
                        String original = FileKit.getFilename(url);
                        //抓取图片
                        String path = "";
                        InputStream in = null;
                        try {
                            URL netUrl = new URL(source);
                            URLConnection conn = netUrl.openConnection();
                            in = conn.getInputStream();

                            path = ApplicationCommon.getFileStore().upload("", FileKit.getRandomFilename(FileKit.getSuffix(original), JRelaxSystemConfigHelper.get("upload.file.name", "hex36")), in, 0);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (in != null) {
                                try {
                                    in.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        JSONObject img = new JSONObject();

                        path = StringKit.isEmpty(path) ? source : WebApplicationCommon.getUploadPrefixPath() + path;
                        img.put("state", "SUCCESS");
                        img.put("url", path);
                        img.put("title", FileKit.getFilename(path));
                        img.put("original", original);
                        img.put("type", FileKit.getSuffix(path));
                        img.put("source", source);

                        list.add(img);
                    }
                    result.put("state", "SUCCESS");
                    result.put("list", list);
                }
                break;
        }
        return result;
    }

    public JSONObject config() {

        org.springframework.core.io.Resource configFile = FileKit.getClassPathResource("/resources/framework/plugins/neditor/config.json");
        try {
            String content = FileKit.readToString(configFile.getInputStream(), "UTF-8");

            return JSONObject.fromObject(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return WebResult.success();
    }
}
