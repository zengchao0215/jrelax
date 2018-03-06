package com.jrelax.core.web.upload;

import com.jrelax.config.JRelaxCoreConfigHelper;
import com.jrelax.config.JRelaxIOHelper;
import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.core.web.IFileStore;
import com.jrelax.core.web.support.WebApplicationCommon;
import com.jrelax.kit.DateKit;
import com.jrelax.kit.FileKit;
import com.jrelax.kit.StringKit;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Date;

/**
 * 本地文件上传
 */
public class LocalFileStore implements IFileStore {
    @Override
    public String upload(String dir, String filename, byte[] data, boolean rename) throws IOException {
        //解析保存地址
        String ROOT = getUploadFolder();
        //获取文件后缀名
        String suffix = FileKit.getSuffix(filename);
        //生成新文件名（日期+随机数）
        String newFileName = rename ? FileKit.getRandomFilename(suffix, JRelaxSystemConfigHelper.get("upload.file.name", "hex36")) : filename;
        //判断文件类型，选择存放位置
        //如果用户填写了存放位置，那么以用户填写的存放位置为准
        String saveDir = StringKit.isNotEmpty(dir) ? dir : getFileSaveDir(suffix);
        if (!saveDir.endsWith("/")) saveDir = saveDir + "/";
        //存放文件夹，如果不存在，则创建
        File fileDir = new File(ROOT + saveDir);
        if (!fileDir.exists()) {
            if (!fileDir.mkdirs()) {
                throw new IOException("文件夹创建失败");
            }
        }
        //判断文件是否存在
        File f = new File(fileDir.getAbsolutePath() + "/" + newFileName);
        if (!f.exists()) {
            FileCopyUtils.copy(data, f);
        }
        return formatPath(saveDir + newFileName);
    }

    @Override
    public String upload(String filename, byte[] data, boolean rename) throws IOException {
        return upload(null, filename, data, rename);
    }

    @Override
    public String upload(String dir, String filename, InputStream inputStream, long start) throws IOException {
        //解析保存地址
        String ROOT = getUploadFolder();
        //获取文件后缀名
        String suffix = FileKit.getSuffix(filename);
        if (StringKit.isEmpty(dir)) {
            dir = getFileSaveDir(suffix);
        }
        File file = new File(ROOT + dir + filename);
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                throw new IOException("文件夹创建失败");
            }
        }
        if (!file.exists())
            if (!file.createNewFile())
                throw new IOException("文件创建失败");

        if (file.exists()) {
            RandomAccessFile raf = null;
            try {
                raf = new RandomAccessFile(file, "rw");
                //获取文件输入流
                byte[] bytes = new byte[1024];
                int len = -1;
                while ((len = inputStream.read(bytes)) != -1) {
                    raf.seek(start);
                    raf.write(bytes, 0, len);
                    start += len;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (raf != null) {
                    try {
                        raf.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return formatPath(dir + filename);
    }

    /**
     * 上传文件根目录
     *
     * @return
     */
    private String getUploadFolder() {
        String root = JRelaxSystemConfigHelper.get("upload.folder.root", JRelaxCoreConfigHelper.getProperty("resource.upload.path"));
        root = JRelaxIOHelper.getInstance().resolvePath(root);
        //创建文件夹
        File rootFile = new File(root);
        if (!rootFile.exists())
            rootFile.mkdirs();
        return root;
    }

    /**
     * 按照文件类型，生成文件保存路径
     *
     * @param suffix 文件后缀名
     * @return
     */
    private String getFileSaveDir(String suffix) {
        String saveDir;
        if (".jpg".equals(suffix) || ".jpeg".equals(suffix) || ".gif".equals(suffix) || ".png".equals(suffix) || ".bmp".equals(suffix))
            saveDir = "image";
        else if (".mp4".equals(suffix) || ".mov".equals(suffix) || ".flv".equals(suffix))
            saveDir = "video";
        else
            saveDir = "file";
        // 在类型文件夹下新增日期文件夹
        saveDir += "/" + DateKit.format(new Date(), "yyyyMMdd") + "/";
        return saveDir;
    }

    /**
     * 处理上传文件路径
     *
     * @param path
     * @return
     */
    public static String formatPath(String path) {
        path = path.replaceAll("//", "/");
        if (!path.startsWith("/"))
            path = "/" + path;
        return path;
    }

    @Override
    public String getUrl(String path) {
        return WebApplicationCommon.getUploadPrefixPath() + path;
    }

    @Override
    public String getAbsolutePath(String path) {
        //解析保存地址
        String ROOT = getUploadFolder();
        return ROOT + path;
    }
}
