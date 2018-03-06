package com.jrelax.web.open.controller;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jrelax.kit.StringKit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.jrelax.third.qrcode.MatrixToImageWriter;
import com.jrelax.web.support.BaseController;

/**
 * 二维码生成
 * @author zengc
 *
 */
@Controller
@RequestMapping("/open/qrcode")
public class QRCodeController extends BaseController<Object>{
	
	/**
	 * 生成二维码
	 * @param response
	 * @param content 转换为二维码的字符
	 */
	@RequestMapping(method = { RequestMethod.GET })
	public void index(HttpServletResponse response, String content, String w, String h) {
		int width = 100, height = 100;
		if(StringKit.isNotEmpty(w)) {
			width = Integer.parseInt(w);
		}
		if(StringKit.isNotEmpty(h)){
			height = Integer.parseInt(h);
		}
		String format = "png";
		Hashtable<EncodeHintType, String> hints = new Hashtable<>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
			MatrixToImageWriter.writeToStream(bitMatrix, format, response.getOutputStream());
			response.getOutputStream().close();
		} catch (WriterException | IOException e) {
			e.printStackTrace();
		}
	}
}
