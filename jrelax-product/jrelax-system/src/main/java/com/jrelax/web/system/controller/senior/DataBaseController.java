package com.jrelax.web.system.controller.senior;

import java.util.*;
import java.util.Map.Entry;

import javax.persistence.Table;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.EntityPersister;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jrelax.kit.StringKit;
import com.jrelax.orm.datasource.DataSourceSwitcher;
import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.service.DataBaseService;

@Controller
@RequestMapping("/system/senior/database")
public class DataBaseController extends BaseController<Object> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String TPL = "system/senior/database/";

    @Autowired
    private DataBaseService dbService;
    @Autowired
    private DataSourceSwitcher dsSwitcher;

    @RequestMapping(method = {RequestMethod.GET})
    public String index(Model model) {
        List<String> dataSources = dsSwitcher.getAllDataSources();
        model.addAttribute("dataSources", dataSources);//所有数据源名称集合
        model.addAttribute("cds", dsSwitcher.getDataSourceName());//当前使用数据源名称
        logger.info("访问数据库管理");
        return TPL + "index";
    }

    /**
     * 切换数据源
     *
     * @param ds
     * @return
     */
    @RequestMapping(value = "/switch/{ds}")
    public String switchDS(@PathVariable String ds) {
        dsSwitcher.setDataSource(ds);
        return "redirect:/system/senior/database";
    }

    /**
     * 数据库表
     *
     * @return
     */
    @RequestMapping(value = "/table", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONArray table() {
        JSONArray data = new JSONArray();

        List<String> tableNames = dbService.getTables(null, null);
        //排序
        tableNames.sort(new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.toLowerCase().compareTo(o2.toLowerCase());
            }
        });

        //按照前缀分组
        List<String> groupList = new ArrayList<>();
        Map<String, List<String>> groupMap = new HashMap<>();
        for (String table : tableNames) {
            String prefix = table.substring(0, table.indexOf("_"));
            if (!groupList.contains(prefix))
                groupList.add(prefix);

            List<String> list = groupMap.get(prefix);
            if (list == null) list = new ArrayList<>();
            list.add(table);
            groupMap.put(prefix, list);
        }

        //组装数据
        for (int i = 0; i < groupList.size(); i++) {
            String prefix = groupList.get(i);
            JSONObject node = new JSONObject();

            node.element("id", prefix);
            node.element("text", prefix);

            JSONArray children = new JSONArray();
            List<String> list = groupMap.get(prefix);
            for (String table : list) {
                JSONObject cNode = new JSONObject();
                cNode.element("id", table);
                cNode.element("text", table);
                cNode.element("icon", "fa fa-table");

                children.add(cNode);
            }
            node.element("children", children);
            if(i == 0) node.element("state", "{opened: true}");

            data.add(node);
        }

        return data;
    }

    /**
     * 执行sql语句
     *
     * @param model
     * @param sql
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public String query(Model model, String sql) {
        if (StringKit.isEmpty(sql)) {
            model.addAttribute("error", "SQL语句错误！");
            return TPL + "query";
        } else if (sql.toLowerCase().startsWith("delete")) {
            model.addAttribute("error", "只支持SELECT/UPDATE/INSERT开头的SQL语句!");
            return TPL + "query";
        }
        try {
            if (!sql.toLowerCase().startsWith("select")) {
                int count = dbService.executeSqlBatch(sql);
                model.addAttribute("update", true);
                model.addAttribute("count", count);
                return TPL + "query";
            }
            List<?> list = dbService.executeQuery(sql);

            List<String> cols = new ArrayList<String>();
            if (list.size() > 0) {
                Map<String, Object> map = (Map<String, Object>) list.get(0);
                Iterator<String> keys = map.keySet().iterator();
                while (keys.hasNext())
                    cols.add(keys.next());

                Collections.sort(cols);//列排序
                if (cols.contains("name")) {
                    cols.remove("name");
                    cols.add(0, "name");
                }
                if (cols.contains("id")) {
                    cols.remove("id");
                    cols.add(0, "id");
                }
            }
            model.addAttribute("cols", cols);
            List<List<Object>> values = new ArrayList<List<Object>>();
            for (Object obj : list) {
                Map<String, Object> map = (Map<String, Object>) obj;
                List<Object> value = new ArrayList<Object>();
                Iterator<String> keys = cols.iterator();
                while (keys.hasNext())
                    value.add(map.get(keys.next()));

                values.add(value);
            }
            model.addAttribute("values", values);
        } catch (SQLGrammarException e) {
            logger.error(e.getSQLException().getMessage(), e);
            model.addAttribute("error", "SQL语句错误：" + e.getSQLException().getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            model.addAttribute("error", e.toString());
        }
        logger.info("执行SQL：" + sql);
        return TPL + "query";
    }
}
