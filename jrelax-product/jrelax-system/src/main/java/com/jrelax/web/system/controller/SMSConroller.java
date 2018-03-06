package com.jrelax.web.system.controller;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jrelax.kit.StringKit;
import com.jrelax.core.web.support.WebResult;
import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.entity.SMS;
import com.jrelax.web.system.service.ConfigService;

@Controller
@RequestMapping("/system/sms")
public class SMSConroller extends BaseController<SMS>{
	public final String TPL = "/system/sms/";
	
	@Autowired
	private ConfigService configService;
	
	@RequestMapping( method = { RequestMethod.GET })
	public String index(Model model) {
		Map<String, String> configMap = configService.getMapByGroup("sms.");
		
		String url = configMap.get("sms.url");
		String method = configMap.get("sms.method").toUpperCase();
		String content = configMap.get("sms.field.content");
		String mobile = configMap.get("sms.field.mobile");
		String encoding = configMap.get("sms.encoding");
		
		int idx = url.indexOf("?");
		if(idx >=0){
			String param = url.substring(idx+1);
			url = url.substring(0, idx);
			
			String[] params = param.split("&");
			
			Map<String, String> pMap = new LinkedHashMap<String, String>();
			for(String p : params){
				if(StringKit.isNotEmpty(p)){
					String[] ps = p.split("=");
					if(ps.length == 1)
						pMap.put(ps[0], "");
					else
						pMap.put(ps[0], ps[1]);
				}
			}
			model.addAttribute("params", pMap);
		}
		
		model.addAttribute("url", url);
		model.addAttribute("method", method);
		model.addAttribute("content", content);
		model.addAttribute("mobile", mobile);
		model.addAttribute("encoding", encoding);
		return TPL + "index";
	}
	
	
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public JSONObject save(String url, String content,String mobile, String method,String encoding, String[] paramName, String[] paramValue, boolean isTest, String mobile2) {
		try {
			String params = "";
			for(int i=0;i<paramName.length;i++){
				String name = paramName[i];
				if(StringKit.isNotEmpty(name)){
					params += name+"="+paramValue[i]+"&";
				}
			}
			if(!isTest){
				if(!url.contains("?"))
					url += "?" + params;
				else
					url += "&" + params;
				
				configService.executeBatch("delete from Config where k like 'sms.%'");
				configService.save("sms.url", url);
				configService.save("sms.method", method);
				configService.save("sms.field.content", content);
				configService.save("sms.field.mobile", mobile);
				configService.save("sms.encoding", encoding);
				/*Properties props = new Properties();
				FileInputStream fis = new FileInputStream(JRelaxCoreConfigHelper.getFile());
				props.load(fis);
				FileOutputStream fos = new FileOutputStream(JRelaxCoreConfigHelper.getFile());
				
				props.setProperty("sms.url", url);
				props.setProperty("sms.method", method);
				props.setProperty("sms.field.content", content);
				props.setProperty("sms.field.mobile", mobile);
				props.setProperty("sms.encoding", encoding);
				
				props.store(fos, "短信中心配置");
				fis.close();
				fos.close();*/
			}else{
				params += mobile+"="+mobile2+"&"+content+"=这是系统发送的短信，请忽略。";
				try {
					URL u = new URL(url);
					HttpURLConnection conn = (HttpURLConnection) u.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod(method);
					conn.setUseCaches(false);
					conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
					conn.setRequestProperty("Connection", "Close");
					conn.setRequestProperty("Content-length", String.valueOf(params.length()));
					conn.setDoInput(true);
					conn.connect();
					OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), encoding);
					out.write(params.toString());
					out.flush();
					out.close();
					InputStream in = conn.getInputStream();
					InputStreamReader r = new InputStreamReader(in);
					LineNumberReader din = new LineNumberReader(r);
					String line = null;
					StringBuffer sb = new StringBuffer();
					while((line=din.readLine())!=null)
					{
						sb.append(line+"\n");
					}
					return WebResult.success().element("isTest", true).element("response", sb.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return WebResult.success();
		} catch (Exception e) {
			return WebResult.error(e);
		}
	}
}
