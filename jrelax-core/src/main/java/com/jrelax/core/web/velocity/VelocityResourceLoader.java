package com.jrelax.core.web.velocity;

import com.jrelax.kit.StringKit;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.ui.velocity.SpringResourceLoader;

import java.io.InputStream;

/**
 * 自定义资源加载器
 */
public class VelocityResourceLoader extends SpringResourceLoader {
    @Override
    public InputStream getResourceStream(String source) throws ResourceNotFoundException {
        if (source != null) {
            //对路径进行格式化
            source = StringKit.normalizePath(source);
            if (source != null) {
                //自动去除头部的'/'，修复在Linux环境下 '//' 导致模板无法找到的问题
                if (source.startsWith("/")) source = source.substring(1);
            }

        }
        return super.getResourceStream(source);
    }
}
