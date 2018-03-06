package com.jrelax.web.doc.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.jrelax.core.web.support.WebResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.dom.DOMSource;

@Controller
@RequestMapping("/open/doc")
public class OpenDocController {
	
	private final String TPL = "open/doc/";
	
	@RequestMapping(method={RequestMethod.GET})
	public String index(){
		return TPL + "index";
	}
	
	
	@RequestMapping(value = "/p/{name}", method = { RequestMethod.GET })
	public String p(Model model, @PathVariable String name) {
		name = name.replaceAll("-", "/");
		return TPL + name;
	}
	
	//自动补全数据
	@RequestMapping(value="/autocomplete/data", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JSONArray autocomplete_data(String query){
		JSONArray data = new JSONArray();
		try {
			Document root = Jsoup.connect("http://suggestion.baidu.com/su?wd="+query+"&json=1").timeout(10000).get();
			String text = root.text();
			text = text.substring(text.indexOf("(")+1);
			text = text.substring(0,text.length()-2);
			JSONArray array = JSONObject.fromObject(text).getJSONArray("s");
			for(int i = 0;i<array.size();i++){
				JSONObject json = new JSONObject();
				
				json.element("value", array.getString(i));
				
				data.add(json);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
}
