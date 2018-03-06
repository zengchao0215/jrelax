package com.jrelax.plugins;

import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.core.plugin.IPlugin;
import com.jrelax.core.plugin.annotation.Plugin;
import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.core.web.IFileStore;
import com.jrelax.third.fs.IRemoteFS;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.Naming;

@Plugin(value = "RMI文件存储", group = "文件上传", loadOnStartup = false)
public class RmiFileStorePlugin implements IPlugin, IFileStore {
    @Override
    public boolean init() {
        ApplicationCommon.setFileStore(this);
        return true;
    }

    @Override
    public void destroy() {
        //恢复默认
        ApplicationCommon.setFileStore(null);
    }

    @Override
    public String upload(String dir, String filename, byte[] data, boolean rename) throws IOException {
        String path = null;
        try {
            String rmi = JRelaxSystemConfigHelper.get("upload.remote.rmi");
            IRemoteFS rfs = (IRemoteFS) Naming.lookup(rmi);

            if (dir != null)
                path = rfs.upload(ApplicationCommon.APP, dir, filename, data);
            else
                path = rfs.upload(ApplicationCommon.APP, filename, data);

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("文件上传失败");
        }
        return path;
    }

    @Override
    public String upload(String filename, byte[] data, boolean rename) throws IOException {
        return upload(null, filename, data, rename);
    }


    @Override
    public String upload(String dir, String filename, InputStream inputStream, long start) throws IOException {
        throw new IOException("暂时不支持断点续传");
    }

    @Override
    public String getUrl(String path) {
        return JRelaxSystemConfigHelper.get("upload.remote.view") + path;
    }

    @Override
    public String getAbsolutePath(String path) {
        return null;
    }
}
