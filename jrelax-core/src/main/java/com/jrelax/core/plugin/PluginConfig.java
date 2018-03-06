package com.jrelax.core.plugin;

import com.jrelax.core.plugin.annotation.Plugin;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.*;

/**
 * 插件配置管理类
 * Created by zengchao on 2017-03-14.
 */
public class PluginConfig {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static PluginConfig pluginConfig = null;
    private Map<String, Boolean> loadConfig = new HashMap<>();//启动配置
    private Map<String, Integer> orderConfig = new HashMap<>();//顺序配置
    private String configPath = "plugins.xml";

    public static PluginConfig getInstance() {
        if (pluginConfig == null)
            pluginConfig = new PluginConfig();
        return pluginConfig;
    }

    private PluginConfig() {
        loadXMLConfig();
    }

    /**
     * 获取文档结构
     *
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    private Document getDocument() throws IOException, DocumentException {
        ClassPathResource resource = new ClassPathResource(configPath);
        if (resource.exists()) {
            SAXReader reader = new SAXReader();
            InputStream in = resource.getInputStream();
            Document document = reader.read(resource.getInputStream());
            in.close();
            return document;
        } else {
            throw new FileNotFoundException("插件配置文件不存在！");
        }
    }

    /**
     * 获取根节点
     *
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    private Element getRootElement() throws IOException, DocumentException {
        return getDocument().getRootElement();
    }

    /**
     * 获取某个插件的配置信息
     * 如果不存在就创建
     *
     * @param className
     * @return
     */
    private Element getPluginElement(Element parentElement, String className) {
        Element plugin = null;
        Iterator iterator = parentElement.elementIterator("plugin");
        while (iterator.hasNext()) {
            Element node = (Element) iterator.next();
            String clazz = node.attributeValue("class");
            if (className.equals(clazz)) {
                plugin = node;
                break;
            }
        }
        return plugin;
    }

    /**
     * 写入
     *
     * @param document
     */
    private void write(Document document) {
        OutputFormat outputFormat = OutputFormat.createPrettyPrint();
        outputFormat.setEncoding("UTF-8");

        try {
            XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(new ClassPathResource(configPath).getFile()), "UTF-8"), outputFormat);
            writer.write(document);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取XML配置文件
     */
    private void loadXMLConfig() {
        try {
            Element rootElement = getRootElement();
            Iterator iterator = rootElement.elementIterator("plugin");
            while (iterator.hasNext()) {
                Element node = (Element) iterator.next();
                String className = node.attributeValue("class");
                Boolean loadOnStartup = Boolean.parseBoolean(node.attributeValue("loadOnStartup"));
                loadConfig.put(className, loadOnStartup);
                orderConfig.put(className, node.attributeValue("order") == null ? 1 : Integer.parseInt(node.attributeValue("order")));
                logger.debug(String.format("load plugins.xml : {class:%s, loadOnStartup:%s}", className, loadOnStartup));
            }
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否启动加载
     * 设置来自插件的注解属性
     * plugins.xml文件配置
     *
     * @param cls
     * @return
     */
    public boolean isLoadOnStartUp(Class<?> cls) {
        Plugin plu = cls.getAnnotation(Plugin.class);
        Boolean loadOnStartup = loadConfig.get(cls.getName());
        if (loadOnStartup != null) {
            if (loadOnStartup) {
                return true;
            }
        }
        //再根据注解配置
        return plu.loadOnStartup();
    }

    /**
     * 获取排序
     * @param cls
     * @return
     */
    public int getOrder(Class<?> cls) {
        Plugin plu = cls.getAnnotation(Plugin.class);
        Integer order = orderConfig.get(cls.getName());
        if (order != null) {
            return order;
        }
        //再根据注解配置
        return plu.order();
    }

    /**
     * 保存配置项目
     *
     * @param className
     * @param props
     */
    public void writeProperties(String className, Properties props) {
        try {
            Document document = getDocument();
            Element rootElement = document.getRootElement();
            Element plugin = getPluginElement(rootElement, className);
            if (plugin == null) {
                plugin = rootElement.addElement("plugin");
                plugin.addAttribute("class", className);
                plugin.addAttribute("loadOnStartup", "false");
                plugin.addAttribute("order", "1");
            }
            //获取属性设置
            //获取配置
            Element properties = plugin.element("properties");
            if (properties != null) {
                plugin.remove(properties);
            }
            properties = plugin.addElement("properties");
            for (Object key : props.keySet()) {
                Element property = properties.addElement("property");
                property.addAttribute("name", key.toString());
                property.addText(props.getProperty(key.toString()));
            }

            write(document);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否有属性
     *
     * @param className
     * @return
     */
    public boolean hasProperties(String className) {
        return getProperties(className) != null;
    }

    /**
     * 获取配置
     *
     * @param className
     * @return
     */
    public Properties getProperties(String className) {
        Properties props = null;
        try {
            Element rootElement = getRootElement();
            Element plugin = getPluginElement(rootElement, className);
            if (plugin != null) {
                Element properties = plugin.element("properties");
                if (properties != null) {
                    List list = properties.elements("property");
                    if (list.size() > 0) {
                        props = new Properties();
                        for (Object obj : list) {
                            Element element = (Element) obj;
                            String name = element.attributeValue("name");
                            String value = element.getText();

                            props.setProperty(name, value);
                        }
                    }
                }
            }
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
        return props;
    }
}
