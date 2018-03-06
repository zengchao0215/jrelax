package com.jrelax.core.web.export;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 文件导出接口
 * Created by zengchao on 2017-03-13.
 */
public interface IExport {
    /**
     * 获取文件名，不包含后缀
     * @return
     */
    String getName();

    /**
     * 获取后缀名，带.
     * 如：.xls
     * @return
     */
    String getSuffix();

    /**
     * 响应文件类型
     * @return
     */
    String getContentType();

    /**
     * 向客户端输出
     * @param outputStream
     */
    void write(OutputStream outputStream) throws IOException;
}
