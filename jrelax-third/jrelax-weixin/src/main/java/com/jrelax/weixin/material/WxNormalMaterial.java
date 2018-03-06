package com.jrelax.weixin.material;

import java.io.File;

public class WxNormalMaterial extends WxMaterial {
    private File file;
    private String url;
    private String name;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
