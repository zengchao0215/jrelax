package com.jrelax.web.doc.controller;


import com.jrelax.core.web.annotation.ViewPrefix;
import com.jrelax.core.web.transform.DataGridTransforms;
import com.jrelax.core.web.transform.TreeTransforms;
import com.jrelax.kit.DateKit;
import com.jrelax.kit.StringKit;
import com.jrelax.orm.query.PageBean;
import com.jrelax.web.support.BaseController;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/doc")
@ViewPrefix("/doc/")
public class DocController extends BaseController {

    /**
     * @param model
     * @param name
     * @return
     */
    @RequestMapping(value = "/{name}", method = {RequestMethod.GET})
    public String p(Model model, @PathVariable String name) {
        model.addAttribute("time", DateKit.now());
        model.addAttribute("id", getParameter("id").stringValue());
        name = name.replaceAll("-", "/");
        return name;
    }

    @RequestMapping("/tree-data")
    @ResponseBody
    public JSONArray tree(String pid) {
        List<String[]> list = new ArrayList<>();
        String prefix = "node-", idPrefix = "";
        if (StringKit.isNotEmpty(pid)) {
            prefix = "node-" + pid + "-";
        }
        for (int i = 1; i < 4; i++) {
            list.add(new String[]{i + "", prefix + i});
        }
        return TreeTransforms.JSTree.transform2(list, (s, treeNode) -> {
            treeNode.setId(s[0]);
            treeNode.setText(s[1]);
            treeNode.setLeaf(false);
        });
    }

    @RequestMapping("/grid-data")
    @ResponseBody
    public Map<String, Object> gridData(PageBean pageBean) {
        pageBean.setTotalCount(1500);
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String idx = (pageBean.getPage() + i) + "";
            Map<String, String> map = new HashMap<>();
            map.put("id", idx);
            map.put("name", "name-" + idx);

            list.add(map);
        }
        return DataGridTransforms.JQGRID.transform(list, pageBean);
    }
}
