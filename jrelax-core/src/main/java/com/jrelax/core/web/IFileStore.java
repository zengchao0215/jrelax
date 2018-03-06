package com.jrelax.core.web;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件存储
 */
public interface IFileStore {
    /**
     * 文件上传
     *
     * @param dir      存储目录
     * @param filename 文件名
     * @param data     文件流
     * @param rename   文件是否重命名
     * @return
     */
    String upload(String dir, String filename, byte[] data, boolean rename) throws IOException;

    /**
     * 文件上传，不限定文件存储位置
     *
     * @param filename
     * @param data
     * @return
     */
    String upload(String filename, byte[] data, boolean rename) throws IOException;

    /**
     * 断点续传
     * @param dir 保存文件目录
     * @param filename 文件名
     * @param inputStream 文件流
     * @param start 起始位置
     * @return
     * @throws IOException
     */
    String upload(String dir, String filename, InputStream inputStream, long start) throws IOException;

    /**
     * 获取访问地址
     * @param path
     * @return
     */
    String getUrl(String path);

    /**
     * 获取绝对路径
     * @param path
     * @return
     */
    String getAbsolutePath(String path);
}
