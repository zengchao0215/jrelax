package com.jrelax.web.system.controller.senior;

import com.jrelax.config.JRelaxIOHelper;
import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.core.web.annotation.ViewPrefix;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.core.web.transform.TreeTransforms;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件管理器
 * <p>
 * 针对上传的文件
 */
@Controller
@RequestMapping("/system/senior/file")
@ViewPrefix("/system/senior/file/")
public class FileController extends BaseController {
    @Resource
    private FileService fileService;

    @RequestMapping
    public String index(Model model) {
        model.addAttribute("uploadPrefix", WebApplicationCommon.getUploadPrefixPath());
        return "index";
    }

    /**
     * 子目录
     *
     * @param path
     * @return
     */
    @RequestMapping(value = "/folder", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject children(String path) {
        String ROOT_FOLDER = JRelaxIOHelper.getInstance().resolvePath(JRelaxSystemConfigHelper.get("upload.folder.root"));
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
                if (StringKit.isBlank(path)) {
                    path = "/";
                }
                File root = new File(ROOT_FOLDER + path);
                if (root.exists()) {
                    File[] files = root.listFiles();
                    fileService.listFile(files, list, path, true);
                    if (path.equals("/")) {
                        for (NSFile file : list) {
                            file.setName(fileService.folderMapping(file.getName()));
                        }
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
     * 文件目录树
     *
     * @param path
     * @return
     */
    @RequestMapping("/tree")
    @ResponseBody
    public JSONArray tree(String path) {
        String ROOT_FOLDER = JRelaxIOHelper.getInstance().resolvePath(JRelaxSystemConfigHelper.get("upload.folder.root"));
        if (StringKit.isBlank(path)) {
            path = "/";
        }
        List<NSFile> list = new ArrayList<NSFile>();
        File root = new File(ROOT_FOLDER + "/" + path);
        if (root.exists()) {
            File[] files = root.listFiles();
            //先显示文件夹
            fileService.listFile(files, list, path, true);
        }

        JSONArray array = TreeTransforms.JSTree.transform2(list, (file, treeNode) -> {
            file.setName(fileService.folderMapping(file.getName()));

            treeNode.setId(file.getPath());
            treeNode.setText(file.getName());
            treeNode.setIcon("");
            treeNode.setLeaf(false);
        });
        if (path.equals("/")) {
            JSONArray nodes = new JSONArray();
            if (StringKit.isBlank(path) || path.equals("/")) {
                JSONObject imgNode = new JSONObject();
                imgNode.put("id", "$IMG$");
                imgNode.put("text", "最新图片");
                imgNode.put("icon", "fa fa-star-o");

                JSONObject fileNode = new JSONObject();
                fileNode.put("id", "$FILE$");
                fileNode.put("text", "最新文件");
                fileNode.put("icon", "fa fa-star-o");

                nodes.add(imgNode);
                nodes.add(fileNode);
            }

            JSONObject rootNode = new JSONObject();
            rootNode.put("id", "/");
            rootNode.put("text", "根目录");
            rootNode.put("children", array);
            rootNode.put("state", "{opened : true}");

            nodes.add(rootNode);
            return nodes;
        }
        return array;
    }

    /**
     * 网络下载
     *
     * @param dir
     * @param url
     * @return
     */
    @RequestMapping("/download")
    @ResponseBody
    public JSONObject download(String dir, String url) {
        fileService.download(dir, url);
        return WebResult.success();
    }

    /**
     * 创建文件夹
     *
     * @param dir
     * @param name
     * @return
     */
    @RequestMapping("/create/folder")
    @ResponseBody
    public JSONObject createFolder(String dir, String name) {
        String ROOT_FOLDER = JRelaxIOHelper.getInstance().resolvePath(JRelaxSystemConfigHelper.get("upload.folder.root"));
        String folder = ROOT_FOLDER + dir + "/" + name;
        File file = new File(folder);
        if (!file.exists()) file.mkdirs();
        return WebResult.success();
    }
}
