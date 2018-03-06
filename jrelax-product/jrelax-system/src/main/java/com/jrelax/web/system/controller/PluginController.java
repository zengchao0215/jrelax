package com.jrelax.web.system.controller;

import com.jrelax.core.plugin.*;
import com.jrelax.core.web.annotation.ViewPrefix;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.kit.ObjectKit;
import com.jrelax.kit.StringKit;
import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.service.LogService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

@Controller
@RequestMapping("/system/plugin")
@ViewPrefix("/system/plugin/")
public class PluginController extends BaseController<Object> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private LogService logService;
    private final String jarSavePath = "/WEB-INF/lib/";

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        //插件Map，Key为插件的group
        Map<String, List<PluginBean>> pluginMap = new HashMap<>();
        //获取系统插件目录下的所有插件
        PluginRegister pr = new PluginRegister();
        Set<Class<?>> plugins = pr.getAllPluginClasses();

        for (Class<?> cls : plugins) {
            PluginBean pluginBean = new PluginBean(cls);
            //获取分组
            String group = StringKit.isEmpty(pluginBean.getGroup()) ? "未分组" : pluginBean.getGroup();
            List<PluginBean> list = pluginMap.get(group);
            if (ObjectKit.isNull(list)) list = new ArrayList<PluginBean>();
            list.add(pluginBean);
            pluginMap.put(group, list);
        }
        model.addAttribute("pluginMap", pluginMap);
        model.addAttribute("PluginPool", PluginPool.getAllPlugins());
        model.addAttribute("savePath", jarSavePath);
        return "index";
    }

    /**
     * 装载插件
     *
     * @param className
     * @return
     */
    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject register(String className) {
        try {
            PluginRegister pr = new PluginRegister();
            if (pr.register(Class.forName(className))) {
                logService.info("sys-plugin", StringKit.format("安装插件[{0}, 成功]", className));
                return WebResult.success();
            } else {
                logService.error("sys-pligun", StringKit.format("安装插件[{0}, 失败]", className));
                return WebResult.error("插件装载失败！");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logService.error("sys-plugin", StringKit.format("安装插件[{0}, 失败]", className));
            return WebResult.error(e);
        }
    }

    /**
     * 卸载插件
     *
     * @param className
     * @return
     */
    @RequestMapping(value = "/unregister", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject unregister(String className) {
        PluginRegister pr = new PluginRegister();
        PluginBean pluginBean = PluginPool.getPlugin(className);
        try {
            pr.unregister(pluginBean.getBean());
            logService.info("sys-plugin", StringKit.format("卸载查看[{0}, 成功]", pluginBean.getName()));
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logService.error("sys-plugin", StringKit.format("卸载查看[{0}, 失败]", pluginBean.getName()));
            return WebResult.error(e);
        }
    }

    /**
     * 加载新插件
     *
     * @param path
     * @return
     */
    @RequestMapping(value = "/load")
    @ResponseBody
    public JSONObject load(String path) {
        String jarPath = getRequest().getServletContext().getRealPath(jarSavePath + path);
        File jar = new File(jarPath);
        if (!jar.exists()) {
            return WebResult.error("文件不存在");
        }
        try {
            URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            Method addUrl = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            addUrl.setAccessible(true);
            addUrl.invoke(classLoader, jar.toURI().toURL());
            classLoader.close();
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return WebResult.success();
    }

    /**
     * 插件配置读取
     *
     * @param className
     * @return
     */
    @RequestMapping(value = "/config")
    public String config(Model model, String className) {
        PluginBean pluginBean = PluginPool.getPlugin(className);
        if (pluginBean != null && pluginBean.hasProperty()) {
            IPluginProperty pluginProperty = (IPluginProperty) pluginBean.getBean();
            //获取参数映射关系
            Map<String, String> mapping = pluginProperty.getPropertyMapping();
            //获取当前参数
            Properties props = pluginProperty.getProperties();

            model.addAttribute("mapping", mapping);
            model.addAttribute("props", props);
            model.addAttribute("className", className);

            logService.info("sys-plugin", StringKit.format("加载参数[{0}]", pluginBean.getName()));
        }
        return "config";
    }

    /**
     * 插件配置
     *
     * @param className
     * @param configMap
     * @return
     */
    @RequestMapping(value = "/config/do")
    @ResponseBody
    public JSONObject doConfig(String className, @RequestParam Map configMap) {
        configMap.remove("className");
        Properties properties = new Properties();
        for (Object key : configMap.keySet()) {
            properties.setProperty(key.toString(), configMap.get(key).toString());
        }
        PluginBean pluginBean = PluginPool.getPlugin(className);
        if (pluginBean != null && pluginBean.hasProperty()) {
            IPluginProperty pluginProperty = (IPluginProperty) pluginBean.getBean();
            pluginProperty.setProperties(properties);//设置参数
            pluginProperty.afterPropertiesSet();//使参数生效
            PluginConfig.getInstance().writeProperties(className, properties);//持久化参数

            logService.info("sys-plugin", StringKit.format("配置参数[{0}]", pluginBean.getName()));
        }
        return WebResult.success();
    }
}
