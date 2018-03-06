package com.jrelax.core.web.export;

import com.jrelax.core.web.support.http.ContentType;
import com.jrelax.kit.FileKit;

import java.io.*;

/**
 * PDF导出
 * Created by zengchao on 2017-03-15.
 */
public class Pdf implements IExport {
    private String name = null;
    private File file = null;
    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSuffix() {
        return ".pdf";
    }

    @Override
    public String getContentType() {
        return ContentType.PDF;
    }

    @Override
    public void write(OutputStream outputStream) throws IOException {
        if(file != null && file.exists()){
            String suffix = FileKit.getSuffix(file);
            if(!".pdf".equals(suffix.toLowerCase())){
                throw new FileNotFoundException("文件类型错误");
            }

            FileInputStream fis = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int len = 0;
            while((len = fis.read(bytes)) != -1){
                outputStream.write(bytes, 0, len);
            }
            fis.close();
        }else{
            throw new FileNotFoundException("文件不存在");
        }
    }

    public static Pdf fromFile(File file){
        return fromFile(file, null);
    }

    public static Pdf fromFile(File file, String name){
        Pdf pdf = new Pdf();
        pdf.setName(name);
        pdf.file = file;
        return pdf;
    }
}
