package com.jrelax.core.rio;

/**
 * 上传接口
 * Created by Administrator on 2016-08-24.
 */
public interface IUploader {
    String upload(String app, String dir, String filename, byte[] stream) throws Exception;
}
