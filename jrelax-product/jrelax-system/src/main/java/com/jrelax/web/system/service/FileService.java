package com.jrelax.web.system.service;

import com.jrelax.config.JRelaxIOHelper;
import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.kit.DateKit;
import com.jrelax.kit.FileKit;
import com.jrelax.kit.Md5Kit;
import com.jrelax.orm.query.Condition;
import com.jrelax.web.common.entity.NSFile;
import com.jrelax.web.support.BaseService;
import com.jrelax.web.system.entity.NFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service("SystemFileService")
public class FileService extends BaseService<NFile> {
    @Override
    public void save(NFile nFile) {
        nFile.setCreateUser(getCurrentUser().getUserName());
        nFile.setCreateTime(DateKit.nowOfDate());
        super.save(nFile);
    }

    public NFile getByMd5(String md5) {
        return super.get(Condition.NEW().eq("md5", md5));
    }

    /**
     * 遍历文件
     *
     * @param files
     * @param list
     * @param path
     * @param dir
     */
    public void listFile(File[] files, List<NSFile> list, String path, boolean dir) {
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
            if (!path.endsWith("/")) path = path + "/";
            uf.setPath(path + f.getName());
            uf.setType(f.isFile() ? 1 : 2);
            uf.setIcon(getIconImg(f));
            if (f.isFile())
                uf.setSize(f.length());
            uf.setModifyTime(DateKit.format(new Date(f.lastModified()), "yyyy-MM-dd HH:mm:ss"));

            list.add(uf);
        }
    }

    /**
     * 文件
     *
     * @param fileList
     * @param list
     */
    public void listFile(List<NFile> fileList, List<NSFile> list) {
        for (NFile f : fileList) {
            NSFile uf = new NSFile();
            uf.setName(f.getOriginal());
            uf.setPath(f.getPath());
            uf.setType(1);
            uf.setIcon(getIconImg(f.getName()));
            uf.setSize(f.getSize());
            uf.setModifyTime(DateKit.format(f.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));

            list.add(uf);
        }
    }

    /**
     * 获取文件图标
     *
     * @param file
     * @return
     */
    public String getIconImg(File file) {
        String prefix = "/framework/img/icon/";
        if (file.isDirectory())
            return prefix + "folder.png";
        String name = file.getName().toLowerCase();
        return getIconImg(name);
    }

    public String getIconImg(String name) {
        String prefix = "/framework/img/icon/";
        if (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".gif") || name.endsWith(".png") || name.endsWith(".bmp"))
            return prefix + "file.png";
        else if (name.endsWith(".doc") || name.endsWith(".docx"))
            return prefix + "word.png";
        else if (name.endsWith(".xls") || name.endsWith(".xlsx"))
            return prefix + "excel.png";
        else if (name.endsWith(".ppt") || name.endsWith(".pptx"))
            return prefix + "ppt.png";
        else if (name.endsWith(".pdf"))
            return prefix + "pdf.png";
        else if (name.endsWith(".rar") || name.endsWith(".zip"))
            return prefix + "zip.png";
        else if (name.endsWith(".js") || name.endsWith(".java") || name.endsWith(".html") || name.endsWith(".htm") || name.endsWith(".xhtml") || name.endsWith(".css"))
            return prefix + "code.png";
        else if (name.endsWith(".mp4") || name.endsWith(".3gp") || name.endsWith(".mkv") || name.endsWith(".wmv") || name.endsWith(".rmvb") || name.endsWith(".mov"))
            return prefix + "word.png";
        else if (name.endsWith(".text"))
            return prefix + "text.png";

        return prefix + "file.png";
    }

    /**
     * 从互联网下载
     *
     * @param dir 文件夹路径
     * @param url 网络文件地址
     */
    @Transactional
    public void download(String dir, String url) {
        String ROOT_FOLDER = JRelaxIOHelper.getInstance().resolvePath(JRelaxSystemConfigHelper.get("upload.folder.root"));
        String folder = ROOT_FOLDER + dir;
        String name = FileKit.getFilename(url);
        String filepath = folder + "/" + name;
        try {
            FileKit.downloadFromInternet(url, filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(filepath);
        //保存到数据库
        NFile nFile = new NFile();
        nFile.setName(name);
        nFile.setPath(dir + "/" + name);
        nFile.setAbsolutePath(file.getAbsolutePath());
        nFile.setPrefix(WebApplicationCommon.getUploadPrefixPath());
        nFile.setOriginal(name);
        try {
            nFile.setMd5(Md5Kit.file(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        nFile.setSuffix(FileKit.getSuffix(file));
        nFile.setType(FileKit.getType(name));
        nFile.setSize(file.length());
        nFile.setDisplaySize(FileKit.getDisplaySize(file.length()));

        this.save(nFile);
    }

    /**
     * 映射文件夹
     * @param name
     * @return
     */
    public String folderMapping(String name){
        String mName = name;
        switch (name) {
            case "temp":
                mName = "临时目录";
                break;
            case "image":
                mName = "图片目录";
                break;
            case "file":
                mName = "文件目录";
                break;
        }
        return mName;
    }
}
