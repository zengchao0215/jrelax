package com.jrelax.web.common.controller;

import com.jrelax.config.JRelaxIOHelper;
import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.kit.DateKit;
import com.jrelax.kit.StringKit;
import com.jrelax.orm.query.PageBean;
import com.jrelax.web.common.entity.NSFile;
import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.entity.NFile;
import com.jrelax.web.system.service.FileService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文件浏览器
 *
 * @author zengchao
 */
@Controller
@RequestMapping("/common/filebrowser")
public class FileBrowserController extends BaseController<Object> {
    private final String ROOT_FOLDER = JRelaxIOHelper.getInstance().resolvePath(JRelaxSystemConfigHelper.get("upload.folder.root"));//文件浏览器的根目录
    @Resource
    private FileService fileService;

    /**
     * 上传目录首页
     *
     * @param model
     * @return
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model, @RequestParam(required = false) final String filter) {
        model.addAttribute("uploadPrefix", WebApplicationCommon.getUploadPrefixPath());
        return "common/filebrowser/index";
    }

    /**
     * 子目录
     *
     * @param path
     * @param filter 过滤
     * @return
     */
    @RequestMapping(value = "/folder", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject children(String path, @RequestParam(required = false) final String filter) {
        try {
            List<NSFile> list = new ArrayList<NSFile>();
            PageBean pageBean = new PageBean();
            pageBean.setRows(50);
            if (path.equals("$IMG$")) {
                pageBean.getCondition().eq("type", "image").desc("createTime");
                List<NFile> fileList = fileService.list(pageBean);

                fileService.listFile(fileList, list);
            } else if (path.equals("$FILE$")) {
                pageBean.getCondition().not(Restrictions.eq("type", "image")).desc("createTime");
                List<NFile> fileList = fileService.list(pageBean);

                fileService.listFile(fileList, list);
            } else {
                if (!StringKit.isBlank(path) && !path.endsWith("/")) {
                    path = path + "/";
                }
                File root = new File(ROOT_FOLDER + path);
                if (root.exists()) {
                    File[] files = root.listFiles(new FileFilter() {
                        //文件筛选
                        public boolean accept(File file) {
                            if (file.isDirectory())
                                return true;
                            if (StringKit.isBlank(filter))
                                return true;
                            String[] filters = filter.split(",");
                            for (String f : filters) {
                                f = f.replace("*", "");
                                if (file.getName().toLowerCase().endsWith(f))
                                    return true;
                            }
                            return false;
                        }
                    });
                    fileService.listFile(files, list, path, true);
                    if (StringKit.isBlank(path) || path.equals("/")) {
                        for (NSFile file : list) {
                            file.setName(fileService.folderMapping(file.getName()));
                        }
                        //最新图片
                        NSFile newImage = new NSFile();
                        newImage.setName("最新图片");
                        newImage.setPath("$IMG$");
                        newImage.setType(3);

                        //最新文件
                        NSFile newFile = new NSFile();
                        newFile.setName("最新文件");
                        newFile.setPath("$FILE$");
                        newFile.setType(3);

                        list.add(0, newImage);
                        list.add(1, newFile);
                    }
                    fileService.listFile(files, list, path, false);
                }
            }
            return WebResult.success().element("data", JSONArray.fromObject(list).toString());
        } catch (Exception e) {
            return WebResult.error(e);
        }
    }

    /**
     * 文件预览
     *
     * @param model
     * @param path
     * @return
     */
    @RequestMapping(value = "/preview", method = {RequestMethod.GET, RequestMethod.POST})
    public String preview(Model model, String path) {
        File file = new File(ROOT_FOLDER + path);
        if (file.exists()) {//判断文件是否存在
            if (file.isFile()) {//判断是否是文件
                String name = file.getName().toLowerCase();
                if (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".gif") || name.endsWith(".png") || name.endsWith(".bmp"))
                    model.addAttribute("obj", "<center><img src='" + ApplicationCommon.BASEPATH + "/dl/" + path + "'/></center>");
                else if (name.endsWith(".mp3"))
                    model.addAttribute("obj", "<center><video controls autoplay name=\"media\"><source src=\"" + ApplicationCommon.BASEPATH + "/dl/" + path + "\" type=\"audio/mpeg\"></video></center>");
                else
                    model.addAttribute("obj", "<center>暂不支持此文件预览！</center>");
            } else {
                model.addAttribute("obj", "<center>文件夹不支持预览！</center>");
            }
        } else {
            model.addAttribute("obj", "<center>文件不存在！</center>");
        }
        return "common/filebrowser/preview";
    }
}
