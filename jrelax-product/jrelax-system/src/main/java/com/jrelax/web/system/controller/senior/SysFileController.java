package com.jrelax.web.system.controller.senior;

import com.jrelax.config.JRelaxIOHelper;
import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.core.web.annotation.ViewPrefix;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.core.web.transform.TreeTransforms;
import com.jrelax.core.web.transform.tree.TreeNode;
import com.jrelax.kit.DateKit;
import com.jrelax.kit.FileKit;
import com.jrelax.kit.ObjectKit;
import com.jrelax.kit.StringKit;
import com.jrelax.web.support.BaseController;
import com.jrelax.web.common.entity.NSFile;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/system/senior/sysfile")
@ViewPrefix("/system/senior/sysfile/")
public class SysFileController extends BaseController<Object> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        String ROOT_FOLDER = JRelaxSystemConfigHelper.get("system.file.folder.root");//文件浏览器的根目录
        try {
            List<NSFile> list = new ArrayList<NSFile>();
            File root = new File(JRelaxIOHelper.getInstance().resolvePath(ROOT_FOLDER));
            if (root.exists()) {
                File[] files = root.listFiles();
                listFile(files, list, "", true);
                listFile(files, list, "", false);
            }
            model.addAttribute("list", list);
            logger.info("访问系统文件管理");
            return "index";
        } catch (Exception e) {
            return WebApplicationCommon.ERROR.ERROR;
        }
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
        String ROOT_FOLDER = JRelaxSystemConfigHelper.get("system.file.folder.root");//文件浏览器的根目录
        try {
            if (!StringKit.isBlank(path) && !path.endsWith("/")) {
                path = path + "/";
            }
            List<NSFile> list = new ArrayList<NSFile>();
            File root = new File(JRelaxIOHelper.getInstance().resolvePath(ROOT_FOLDER) + "/" + path);
            if (root.exists()) {
                File[] files = root.listFiles();
                listFile(files, list, path, true);
                listFile(files, list, path, false);
            }
            return WebResult.success().element("data", JSONArray.fromObject(list).toString());
        } catch (Exception e) {
            return WebResult.error(e);
        }
    }

    /**
     * 遍历文件
     *
     * @param files
     * @param list
     * @param path
     * @param dir
     */
    private void listFile(File[] files, List<NSFile> list, String path, boolean dir) {
        //先显示文件夹
        for (File f : files) {
            if (dir) {
                if (!f.isDirectory())
                    continue;
            } else {
                if (!f.isFile())
                    continue;
            }

            NSFile uf = new NSFile();
            uf.setName(f.getName());
            uf.setPath(path + f.getName());
            uf.setType(f.isFile() ? 1 : 2);
            uf.setIcon(JRelaxIOHelper.getInstance().getFileIcon(f));
            if (f.isFile())
                uf.setSize(f.length());
            uf.setModifyTime(DateKit.format(new Date(f.lastModified()), "yyyy-MM-dd HH:mm:ss"));

            list.add(uf);
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
        String ROOT_FOLDER = JRelaxSystemConfigHelper.get("system.file.folder.root");//文件浏览器的根目录
        if (!StringKit.isBlank(path) && !path.endsWith("/")) {
            path = path + "/";
        } else {
            path = "";
        }
        List<NSFile> list = new ArrayList<NSFile>();
        File root = new File(JRelaxIOHelper.getInstance().resolvePath(ROOT_FOLDER) + "/" + path);
        if (root.exists()) {
            File[] files = root.listFiles();
            //先显示文件夹
            listFile(files, list, path, true);
        }

        JSONArray array = TreeTransforms.JSTree.transform2(list, (file, treeNode) -> {
            treeNode.setId(file.getPath());
            treeNode.setText(file.getName());
            treeNode.setIcon(file.getIcon());
            treeNode.setLeaf(false);
        });
        if (StringKit.isBlank(path)) {
            JSONArray nodes = new JSONArray();
            JSONObject rootNode = new JSONObject();
            rootNode.put("id", " ");
            rootNode.put("text", "所有文件");
            rootNode.put("icon", "fa fa-home");
            rootNode.put("children", array);
            rootNode.put("state", "{opened : true}");

            nodes.add(rootNode);
            return nodes;
        }
        return array;
    }

    /**
     * 编辑文件
     *
     * @param model
     * @param path  文件路径
     * @return
     */
    @RequestMapping(value = "/edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String edit(Model model, String path) {
        String ROOT_FOLDER = JRelaxSystemConfigHelper.get("system.file.folder.root");//文件浏览器的根目录
        File file = new File(JRelaxIOHelper.getInstance().resolvePath(ROOT_FOLDER) + "/" + path);
        if (file.exists()) {
            String view = "text", type = "text/html";
            String suffix = FileKit.getSuffix(file).toLowerCase();

            //文本显示
            switch (suffix) {
                case ".jsp":
                case ".java":
                    type = "text/x-java";
                    break;
                case ".html":
                case ".htm":
                case ".xhtml":
                    type = "text/html";
                    break;
                case ".xml":
                    type = "text/xml";
                    break;
                case ".css":
                    type = "text/css";
                    break;
                case ".js":
                    type = "text/javascript";
                    break;
                case ".mf":
                    type = "text/plain";
                    break;
                case ".png":
                case ".jpg":
                case ".jpeg":
                case ".gif":
                    view = "media";
                    type = "image";
                    previewFile("image/*", file);
                    break;
                case ".mp3":
                case ".amr":
                    view = "media";
                    type = "audio";
                    previewFile("audio/*", file);
                    break;
                case ".mp4":
                    view = "media";
                    type = "video";
                    previewFile("video/*", file);
                    break;
                default:
                    view = "file";
                    renderFile("*/*", file);//下载文件
                    break;
            }

            if (view.equals("text")) {
                try {
                    String content = FileUtils.readFileToString(file, "utf-8");
                    model.addAttribute("content", content);
                    model.addAttribute("path", path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            model.addAttribute("type", type);
            return view;
        }
        return "none";
    }

    /**
     * 媒体文件预览
     *
     * @param contentType
     * @param file
     */
    private void previewFile(String contentType, File file) {
        HttpServletRequest request = getRequest();
        HttpServletResponse response = getResponse();
        response.setContentType(contentType);
        OutputStream fos = null;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            if (!file.exists())
                return;
            //以流的形式下载
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            String name = java.net.URLEncoder.encode(file.getName(), "UTF-8");
            //清空response
            response.reset();
            response.addHeader("Content-Length", "" + file.length());
            fos = new BufferedOutputStream(response.getOutputStream());
            fos.write(buffer);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ObjectKit.isNotNull(fos)) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ObjectKit.isNotNull(fis)) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ObjectKit.isNotNull(bis)) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存文件
     *
     * @param path
     * @param content
     * @return
     */
    @RequestMapping(value = "/save", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject save(HttpServletRequest request, String path, String content) {
        try {
            content = content.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
            //先备份文件
            path = request.getServletContext().getRealPath(path);
            File file = new File(path);
            if (file.exists()) {
                File descFile = new File(path + ".bak");
                FileUtils.copyFile(file, descFile);
                //更新文件
                FileUtils.writeStringToFile(file, content, "utf-8");
            } else {
                return WebResult.error("文件不存在！");
            }
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }
}
