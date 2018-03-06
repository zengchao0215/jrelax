package com.jrelax.core.rio.impl;

import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.core.rio.IUploader;
import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.third.fs.IRemoteFS;

import java.rmi.Naming;

/**
 * RMI服务器上传版本
 * Created by zengc on 2016-08-24.
 */
public class RmiUploader implements IUploader {
    @Override
    public String upload(String app, String dir, String filename, byte[] stream) throws Exception {
        boolean enabled = Boolean.parseBoolean(JRelaxSystemConfigHelper.get("upload.remote.enabled", "false"));
        if(enabled){
            try {
                String rmi = JRelaxSystemConfigHelper.get("upload.remote.rmi");
                IRemoteFS rfs = (IRemoteFS) Naming.lookup(rmi);

                return rfs.upload(ApplicationCommon.APP, filename, stream);
            } catch (Exception e) {
                throw new Exception("远程文件服务器访问失败！");
            }
        }
        return null;
    }
}
