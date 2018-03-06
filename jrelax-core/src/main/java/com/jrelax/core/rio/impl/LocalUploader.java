package com.jrelax.core.rio.impl;

import com.jrelax.core.rio.IUploader;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.kit.DateKit;
import com.jrelax.kit.StringKit;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 本地上传
 * Created by zengc on 2016-08-24.
 */
public class LocalUploader implements IUploader {
    @Override
    public String upload(String app, String dir, String filename, byte[] stream) throws Exception {
        throw new UnsupportedOperationException("目前尚未实现！");
    }
}
