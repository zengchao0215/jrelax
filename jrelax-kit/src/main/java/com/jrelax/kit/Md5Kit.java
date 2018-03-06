package com.jrelax.kit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MD5加密类
 * @author ZENGCHAO
 *
 */
public class Md5Kit extends DigestUtils{
	private final static Logger logger = LoggerFactory.getLogger(Md5Kit.class.getClass());
	/**
	 * MD5加密
	 * @param s
	 * @return
	 */
	public final static String encode(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes("UTF-8");
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取文件的MD5值
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	public static String file(String filePath) throws IOException{
		File file = new File(filePath);
		return file(file);
	}
	/**
	 * 获取文件的MD5值
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public static String file(File file) throws IOException {
		String md5 = null;
		if(file.exists()){
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				MappedByteBuffer byteBuffer = fis.getChannel().map(MapMode.READ_ONLY, 0, file.length());
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(byteBuffer);
				BigInteger bi = new BigInteger(1, md.digest());
				md5 = bi.toString(16);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} finally{
				if(ObjectKit.isNotNull(fis)){
					fis.getChannel().close();
					fis.close();
				}
			}
		}else{
			logger.error("文件不存在！");
		}
		return md5;
	}
	
	/***
	 * 生成文件的MD5值，8位
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static String fileTo8(String filePath) throws IOException{
		String md5 = file(filePath);
		
		if(!StringKit.isBlank(md5) && md5.length()>30){
			return md5.substring(8,16);
		}
		return null;
	}
	
	/***
	 * 生成文件的MD5值，8位
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String fileTo8(File file) throws IOException{
		String md5 = file(file);
		
		if(!StringKit.isBlank(md5) && md5.length()>30){
			return md5.substring(8,16);
		}
		return null;
	}
}
