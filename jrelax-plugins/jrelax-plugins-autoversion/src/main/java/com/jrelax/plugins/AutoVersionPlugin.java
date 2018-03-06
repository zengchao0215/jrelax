package com.jrelax.plugins;

import com.jrelax.core.plugin.IPlugin;
import com.jrelax.core.plugin.annotation.Plugin;
import com.jrelax.kit.FileKit;
import com.jrelax.kit.Md5Kit;
import com.jrelax.kit.StringKit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;

/**
 * 资源自动添加版本号插件
 * Created by zengchao on 2016/7/19.
 * 依赖于Jsoup、Md5Kit
 */
@Plugin(value = "资源自动添加版本号插件", group = "系统", loadOnStartup = false)
public class AutoVersionPlugin implements IPlugin{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean init() {
        try{
            //执行自动版本号计算
            String htmlDir = this.getClass().getResource("/").getPath();
            String resDir = "";
            if(htmlDir.indexOf("classes") >0){
                File dir = new File(htmlDir);
                htmlDir = dir.getParent()+"/tpl/";
                resDir = dir.getParentFile().getParent()+"/resources/";

                Collection<File> htmlList = FileKit.listFiles(new File(htmlDir), new String[]{"html"}, false);
                for(File page : htmlList){
                    try {
                        Document root = Jsoup.parse(page, "UTF-8");

                        //js
                        Elements scripts = root.getElementsByTag("script");
                        for(Element script : scripts){
                            String src = script.attr("src");
                            if(StringKit.isEmpty(src)) continue;
                            if(src.indexOf("?")>0) src = src.substring(0, src.indexOf("?"));

                            String src2 = src.replace("$!basePath", "");
                            src2 = src2.replace("/app", "/application");

                            //计算文件MD5值
                            File res = new File(resDir + src2);
                            if(res.exists() && res.isFile()){
                                String md5 = Md5Kit.file(res).substring(0, 6);
                                script.attr("src", src+"?v="+md5);
                            }
                        }

                        //css
                        Elements links = root.getElementsByTag("link");
                        for(Element link : links){
                            String href = link.attr("href");
                            if(StringKit.isEmpty(href)) continue;
                            if(href.indexOf("?")>0) href = href.substring(0, href.indexOf("?"));

                            String href2 = href.replace("$!basePath", "");
                            href2 = href2.replace("/app", "/application");


                            //计算文件MD5值
                            File res = new File(resDir + href2);
                            if(res.exists() && res.isFile()){
                                String md5 = Md5Kit.file(res).substring(0, 6);

                                link.attr("href", href+"?v="+md5);
                            }
                        }

                        //保存
                        FileOutputStream fos = new FileOutputStream(page);
                        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
                        osw.write(root.html());
                        osw.close();
                        fos.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                logger.info("自动添加版本号结束");
            }else{
                logger.info("开发模式下不执行自动添加版本号");
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void destroy() {

    }
}
