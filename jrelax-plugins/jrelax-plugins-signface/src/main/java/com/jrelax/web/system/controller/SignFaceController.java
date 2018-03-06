package com.jrelax.web.system.controller;

import com.jrelax.core.web.support.WebResult;
import com.jrelax.kit.ImageKit;
import com.jrelax.kit.Md5Kit;
import com.jrelax.kit.ObjectKit;
import com.jrelax.kit.StringKit;
import com.jrelax.orm.query.Condition;
import com.jrelax.plugins.facedetect.Detector;
import com.jrelax.web.support.BaseController;
import com.jrelax.web.system.entity.User;
import com.jrelax.web.system.service.LogService;
import com.jrelax.web.system.service.UserService;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.File;
import java.util.List;

@Controller
@RequestMapping("/")
public class SignFaceController extends BaseController<User>{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private LogService logService;
	@Autowired
	private UserService userService;
	/**
	 * 人脸识别登录
	 * @param request
	 * @param img
	 * @return
	 */
	@RequestMapping(value="/signin/face", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JSONObject index(HttpSession session, HttpServletRequest request, String img) {
		try {
			String dirPath = request.getServletContext().getRealPath("/resources/application");
			String fileName = dirPath + "/faces/temp/" + Md5Kit.encode(System.currentTimeMillis()+"") + ".png";
			Base64 decoder = new Base64();
			// Base64解码
			byte[] imgData = decoder.decode(img.substring(22));
			FileUtils.writeByteArrayToFile(new File(fileName), imgData);
			//图片压缩
			ImageKit.compressImage(fileName);
			// 人脸识别
			Detector detector = Detector.create(dirPath + "/faces/opencv/haarcascade_frontalface_default.xml");
			List<Rectangle> rec = detector.getFaces(fileName, 1.2f, 1.1f, .05f,2, true);
			
			if (rec.size() > 0) {
				Rectangle r = rec.get(0);
				//图片剪切
				ImageKit.cutImage(fileName, fileName, r.x, r.y, r.width, r.height);
				
				JSONObject result = WebResult.success().element("x", rec.get(0).x)
						.element("y", rec.get(0).y)
						.element("w", rec.get(0).width)
						.element("h", rec.get(0).height);
				//匹配用户
				User loginUser = matchImageWithLogin(request, fileName);
				if(ObjectKit.isNotNull(loginUser)){
					//匹配成功后，后台直接登录
					//doSignin(session, request, loginUser.getUserName(), loginUser.getPassword(), "");
					//根据用户名或密码查询
					if(userService.count(Condition.NEW().eq("userName", loginUser.getUserName()).eq("password", loginUser.getPassword()))>0){
						logger.debug(String.format("[UserName: %s] 人脸识别登录成功", loginUser.getUserName()));
						// 用户名密码正确, 初始化用户权限
						userService.executeLogin(loginUser.getUserName(), loginUser.getPassword(), "", session);
					}
					result.element("login", true);
				}
				//删除图片
				FileUtils.deleteDirectory(new File(dirPath + "/faces/temp/"));
				return result;
			}else{
				return WebResult.error("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return WebResult.error(e);
		}
	}
	
	/**
	 * 与系统中登记的用户头像进行对比
	 * @param request
	 * @param fileName
	 * @return
	 */
	public User matchImageWithLogin(HttpServletRequest request, String fileName){
		String dirPath = request.getServletContext().getRealPath("/resources/application/faces/");
		//先比对同IP的用户
		User eqHostUser = userService.get("select id,userName,password,headImage from User where lastLoginIp = '"+getRequestAddr()+"'");
		
		//获取系统配置识别阀值
		String strRate = getSystemConfig("faceRate");
		double rate = 0.9;
		if(!StringKit.isEmpty(strRate))
			rate = Double.parseDouble(strRate);
		
		if(ObjectKit.isNotNull(eqHostUser) && !StringKit.isEmpty(eqHostUser.getHeadImage())){
			double n = new ImageKit().matchImage(fileName, dirPath +"/"+ eqHostUser.getHeadImage());
			
			logger.debug("人脸识别相似度为："+n);
			if(n>=rate) return eqHostUser;
		}
		
		//遍历所有已经设置头像的用户
		String sql = "select userName,password,headImage from User where headImage IS NOT NULL";
		if(ObjectKit.isNotNull(eqHostUser))
			sql += " and id != '"+eqHostUser.getId()+"'";
		List<User> userList = userService.listToEntity(sql);
		for(User user : userList){
			if(!StringKit.isEmpty(user.getHeadImage())){
				double n = new ImageKit().matchImage(fileName, dirPath +"/"+ user.getHeadImage());
				
				logger.debug("人脸识别相似度为："+n);
				if(n>=rate) return user;
			}
		}
		return null;
	}

	/**
	 * 上传用户头像
	 * @param request
	 * @param img
	 * @return
	 */
	@RequestMapping(value="/uc/himage/do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JSONObject doHimage(HttpServletRequest request, String img) {
		try {
			String dirPath = request.getServletContext().getRealPath("/resources");
			String fileName =dirPath + "/faces/" + getCurrentUser().getId() + ".png";
			Base64 decoder = new Base64();
			// Base64解码
			byte[] imgData = decoder.decode(img.substring(22));
			FileUtils.writeByteArrayToFile(new File(fileName), imgData);
			//图片压缩
			ImageKit.compressImage(fileName);
			// 人脸识别
			Detector detector = Detector.create(dirPath + "/application/opencv/haarcascade_frontalface_default.xml");
			List<Rectangle> rec = detector.getFaces(fileName, 1.2f, 1.1f, .05f,2, true);

			if (rec.size() > 0) {
				Rectangle r = rec.get(0);
				//图片剪切
				ImageKit.cutImage(fileName, fileName, r.x, r.y, r.width, r.height);

				getCurrentUser().setHeadImage(getCurrentUser().getId()+".png");
				userService.merge(getCurrentUser());//更新到数据库中
				return WebResult.success();
			}else{
				return WebResult.error("识别失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return WebResult.error(e);
		}
	}
}
