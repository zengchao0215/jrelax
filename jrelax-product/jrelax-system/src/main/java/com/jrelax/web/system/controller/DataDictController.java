package com.jrelax.web.system.controller;

import com.jrelax.core.web.annotation.ViewPrefix;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.kit.ObjectKit;
import com.jrelax.kit.StringKit;
import com.jrelax.orm.query.Condition;
import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.entity.DataDict;
import com.jrelax.web.system.entity.DataDictItem;
import com.jrelax.web.system.service.DataDictItemService;
import com.jrelax.web.system.service.DataDictService;
import com.jrelax.web.system.service.LogService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/system/datadict")
@ViewPrefix("system/datadict/")
public class DataDictController extends BaseController<DataDict> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private DataDictService ddService;
    @Resource
    private DataDictItemService ddiService;
    @Resource
    private LogService logService;

    @RequestMapping(method = {RequestMethod.GET})
    public String index(Model model) {
        return "index";
    }

    /**
     * 字典分类
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/category", method = {RequestMethod.GET})
    public String category(Model model) {
        List<DataDict> list = ddService.listToEntity("select id, category from DataDict where category is not null and category != '' group by category order by createTime asc");
        model.addAttribute("list", list);

        logService.info("sys-datadict", "查询字典分类");
        return "category";
    }

    @RequestMapping(value = "/tree", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONArray tree() {
        JSONArray data = new JSONArray();

        int count = ddService.count();
        if (count == 0) {
            JSONObject node = new JSONObject();

            node.element("id", "-1");
            node.element("text", "无数据字典");
            node.element("icon", "fa fa-tags");

            data.add(node);
        } else {
            List<DataDict> list = ddService.listToEntity("select id, category from DataDict group by category order by createTime asc");
            //获取分类
            for (DataDict dd : list) {
                JSONObject node = new JSONObject();

                if (StringKit.isBlank(dd.getCategory())) {
                    node.element("id", "-1");
                    node.element("text", "未分类");
                    node.element("icon", "fa fa-folder");
                } else {
                    node.element("id", dd.getCategory());
                    node.element("text", dd.getCategory());
                    node.element("icon", "fa fa-folder");
                }
                node.element("children", true);
                data.add(node);
            }
        }
        return data;
    }

    @RequestMapping(value = "/tree/{category}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONArray tree(@PathVariable String category) {
        JSONArray data = new JSONArray();
        List<DataDict> list = null;
        if ("-1".equals(category))
            list = ddService.listToEntity("select id,name,remarks from DataDict where category is null or category='' group by name order by createTime asc");
        else
            list = ddService.listToEntity("select id,name,remarks from DataDict where category=? group by name order by createTime asc", category);

        if (list.size() == 0) {
            JSONObject node = new JSONObject();

            node.element("id", "-1");
            node.element("text", "无数据字典");
            node.element("icon", "fa fa-tags");

            data.add(node);
        }
        for (DataDict dd : list) {
            JSONObject node = new JSONObject();

            node.element("id", dd.getId());
            String text = dd.getRemarks();
            if (!StringKit.isEmpty(dd.getRemarks()))
                text += " [" + dd.getName() + "]";
            node.element("text", text);
            node.element("icon", "fa fa-tags");

            data.add(node);
        }
        return data;
    }


    /**
     * 数据字典子项
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/item/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String item(Model model, @PathVariable String id) {
        if (!StringKit.isEmpty(id)) {
            if (!id.equals("-1")) {
                List<DataDictItem> list = ddiService.list(Condition.NEW().eq("dataDict.id", id).asc("location"));
                model.addAttribute("list", list);

                logService.info("sys-datadict", StringKit.format("查询字典项[{0}]", id));
            }
        }
        return "item";
    }

    /**
     * 数据字典子项(JSON)
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/item/data/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<DataDictItem> item_data(Model model, @PathVariable String id) {
        List<DataDictItem> list = new ArrayList<DataDictItem>();
        if (!StringKit.isEmpty(id)) {
            if (!id.equals("-1")) {
                list = ddiService.listToEntity("select id,k,v from DataDictItem where dataDict.id=? order by location asc", id);
            }
        }

        logService.info("sys-datadict", StringKit.format("查询字典项(JSON)[{0}]", id));
        return list;
    }

    /**
     * 修改字典名称和描述
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String edit(Model model, @PathVariable String id) {
        if (!StringKit.isEmpty(id)) {
            DataDict dd = ddService.getById(id);
            model.addAttribute("dd", dd);
            List<DataDict> list = ddService.listToEntity("select id, category from DataDict where category is not null and category != '' group by category");
            model.addAttribute("list", list);
        }
        return "edit";
    }

    /**
     * 编辑
     *
     * @param dataDict
     * @return
     */
    @RequestMapping(value = "/edit/do", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject item(DataDict dataDict) {
        try {
            if (StringKit.isEmpty(dataDict.getName()))
                return WebResult.error("请填写字典名称！");
            DataDict eqDataDict = ddService.getById(dataDict.getId());
            if (ObjectKit.isNull(eqDataDict))
                return WebResult.error("数据字典不存在!");
            //判断是否重复
            if (ddService.count(Condition.NEW().eq("name", dataDict.getName()).not(Restrictions.eq("id", dataDict.getId()))) > 0)
                return WebResult.error("'" + dataDict.getName() + "' 已存在!");

            eqDataDict.setCategory(dataDict.getCategory());
            eqDataDict.setName(dataDict.getName());
            eqDataDict.setRemarks(dataDict.getRemarks());
            ddService.merge(eqDataDict);

            logService.info("sys-datadict", StringKit.format("编辑字典[{0}]", dataDict.getId()));
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }

    /**
     * 新增
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String add(Model model) {
        List<DataDict> list = ddService.listToEntity("select id, category from DataDict where category is not null and category != '' group by category");
        model.addAttribute("list", list);
        return "add";
    }

    /**
     * 保存数据字典
     *
     * @param datadict
     * @return
     */
    @RequestMapping(value = "/add/do", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject doAdd(DataDict datadict) {
        try {
            if (StringKit.isEmpty(datadict.getName()))
                return WebResult.error("请填写字典名称！");
            //判断是否重复
            if (ddService.count(Condition.NEW().eq("name", datadict.getName())) > 0)
                return WebResult.error("'" + datadict.getName() + "' 已存在!");
            datadict.setCreateUser(getCurrentUser().getUserName());
            datadict.setCreateTime(getCurrentTime());
            ddService.save(datadict);

            logService.info("sys-datadict", StringKit.format("创建字典[{0}, {1}, {2}, {3}]", datadict.getId(), datadict.getCategory(), datadict.getName(), datadict.getRemarks()));
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }

    /**
     * 保存数据字典项
     *
     * @param id
     * @param key
     * @param value
     * @return
     */
    @RequestMapping(value = "/saveItem", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject saveItem(String id, @RequestParam(value = "key", required = false) String[] key, @RequestParam(value = "value", required = false) String[] value) {
        try {
            if (StringKit.isEmpty(id))
                return WebResult.error("非法操作！");
            DataDict dd = ddService.getById(id);
            if (ObjectKit.isNull(dd))
                return WebResult.error("数据字典不存在！");
            //更新数据字典Item
            ddiService.updateItem(id, key, value, dd);

            logService.info("sys-datadict", StringKit.format("编辑字典项[{0}]", id));
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }

    /**
     * 删除数据字典
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject delete(@PathVariable String id) {
        try {
            ddService.delete(id);

            logService.info("sys-datadict", StringKit.format("删除字典[{0}]", id));
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }

    /**
     * 删除分类下的所有数据字典
     *
     * @param catalog
     * @return
     */
    @RequestMapping(value = "/delete/catalog/{catalog}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject deleteCatalog(@PathVariable String catalog) {
        try {
            ddService.executeBatch("delete from DataDictItem where dataDict.id in (select id from DataDict where category = ?)", catalog);
            ddService.executeBatch("delete from DataDict where category = ?", catalog);

            logService.info("sys-datadict", StringKit.format("删除分类下所有字典[{0}]", catalog));
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }

    /**
     * 同步缓存
     *
     * @return
     */
    @RequestMapping(value = "/sync", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject sync() {
        try {
            boolean success = getDataDict().sync();//同步数据字典
            if (success)
                return WebResult.success();

            logService.info("sys-datadict", "刷新缓存");
            return WebResult.error("同步失败!");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }

    /**
     * 导出JSON
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/export/json", method = {RequestMethod.GET, RequestMethod.POST})
    public String export(Model model, String id) {
        if (!StringKit.isEmpty(id)) {
            JSONArray array = new JSONArray();
            List<DataDict> list = new ArrayList<>();
            if (id.length() < 20) {
                list = ddService.list_NoLazy(Restrictions.eq("category", id));
            } else {
                DataDict dd = ddService.getById_NoLazy(id);
                list.add(dd);
            }
            for (DataDict dd : list) {
                JSONObject dict = new JSONObject();
                //dict.element("id", dd.getId());
                dict.element("name", dd.getName());
                dict.element("category", dd.getCategory());
                dict.element("remarks", dd.getRemarks());

                JSONArray items = new JSONArray();
                for (DataDictItem item : dd.getItems()) {
                    JSONObject obj = new JSONObject();

                    //obj.element("id", item.getId());
                    obj.element("k", item.getK());
                    obj.element("v", item.getV());
                    obj.element("location", item.getLocation());

                    items.add(obj);
                }

                dict.element("items", items);
                array.add(dict);
            }
            model.addAttribute("json", array.toString());

            logService.info("sys-datadict", StringKit.format("导出JSON[{0}]", id));
        }
        return "export";
    }


    /**
     * 导入JSON页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/import/json", method = {RequestMethod.GET})
    public String importJson(Model model) {
        return "import";
    }

    /**
     * 执行导入JSON
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "/import/json/do", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject doImportJSON(String json) {
        try {
            JSONArray array = JSONArray.fromObject(json);
            ddService.saveJson(array);

            logService.info("sys-datadict", "导入JSON");
            return WebResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return WebResult.error(e);
        }
    }
}
