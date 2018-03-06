package com.jrelax.core.rio.impl;

import com.jrelax.core.rio.IUploader;

/**
 * FTP上传
 * Created by Administrator on 2016-08-24.
 */
public class FTPUploader implements IUploader{
    @Override
    public String upload(String app, String dir, String filename, byte[] stream) throws Exception {
        throw new UnsupportedOperationException("目前尚未实现！");
    }
}
