package com.jrelax.web.common.controller;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jrelax.core.web.support.WebResult;

@Controller
@RequestMapping(value="/common/location")
public class LocationController {
	/**
	 * 根据坐标获取地理位置
	 * @param location
	 * @return
	 */
	@RequestMapping(value="/baidu/geocoder", method={RequestMethod.POST})
	@ResponseBody
	public JSONObject geocoder(String location){
		String ak = "2d0c81a0ce6d06eabaaa82a23c010c40";
		JSONObject json = new JSONObject();
		try {
			//先转换为百度地图坐标
			String[] array = location.split(",");
			location = array[1]+","+array[0];
			Document convert = Jsoup.connect("http://api.map.baidu.com/geoconv/v1/?coords="+location+"&ak="+ak+"&from=1&to=5&output=json").timeout(10000).get();
			JSONObject newLocation = JSONObject.fromObject(convert.text()).getJSONArray("result").getJSONObject(0);
			location = newLocation.getString("y")+","+newLocation.getString("x");
			//再获取实际位置
			Document root = Jsoup.connect("http://api.map.baidu.com/geocoder/v2/?ak="+ak+"&output=json&pois=0&location="+location).timeout(10000).get();
			JSONObject result = JSONObject.fromObject(root.text()).getJSONObject("result");
			json.element("success", true);
			json.element("address", result.getString("formatted_address"));
			json.element("business", result.getString("business"));
			json.element("city", result.getJSONObject("addressComponent").getString("city"));
			json.element("district", result.getJSONObject("addressComponent").getString("district"));
			json.element("province", result.getJSONObject("addressComponent").getString("province"));
			json.element("street", result.getJSONObject("addressComponent").getString("street"));
			
			
		} catch (IOException e) {
			e.printStackTrace();
			return WebResult.error("获取位置失败！");
		}
		return json;
	}
}
