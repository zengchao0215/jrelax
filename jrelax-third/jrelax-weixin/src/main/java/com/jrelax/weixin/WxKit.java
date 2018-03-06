package com.jrelax.weixin;

import com.jrelax.kit.HashKit;
import com.jrelax.kit.HttpKit;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class WxKit {
    /**
     * 微信服务器校验
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param token
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce, String token) {
        List<String> list = new ArrayList<>();
        list.add(timestamp);
        list.add(nonce);
        list.add(token);

        Collections.sort(list);

        String sign = HashKit.sha1(list.get(0) + list.get(1) + list.get(2));

        return sign.toLowerCase().equals(signature.toLowerCase());
    }

    public static JSONObject request(WxConfig wxConfig, String url, Map<String, String> paramMap) {
        if (paramMap == null) paramMap = new HashMap<>();
        paramMap.put("appid", wxConfig.getAppId());
        paramMap.put("secret", wxConfig.getAppSecret());
        paramMap.put("grant_type", "client_credential");
        return request(url, paramMap);
    }

    public static JSONObject request(WxToken wxToken, String url, Map<String, String> paramMap) {
        if (paramMap == null) paramMap = new HashMap<>();
        paramMap.put("access_token", wxToken.getAccessToken());
        return request(url, paramMap);
    }

    public static JSONObject request(String url, Map<String, String> paramMap) {
        WxLog.info("请求：" + url);
        if (paramMap == null) paramMap = new HashMap<>();
        WxLog.info("发送数据：" + paramMap.toString());
        String data = HttpKit.sendGet(url, paramMap);
        WxLog.info("响应：" + data);
        return JSONObject.fromObject(data);
    }

    public static JSONObject post(WxToken wxToken, String strUrl, String json) {
        try {
            WxLog.info("请求：" + strUrl);
            WxLog.info("发送数据：" + json);
            strUrl = strUrl + "?access_token=" + wxToken.getAccessToken();
            URL url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");

            OutputStream out = connection.getOutputStream();

            out.write(json.getBytes("UTF-8"));
            out.flush();
            out.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String data = reader.readLine();
            WxLog.info("响应：" + data);
            if (data != null) {
                JSONObject result = JSONObject.fromObject(data);
                if (!isSuccess(result))
                    throw WxException.fromJson(result);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new WxException(-1, "本地程序错误");
        }
        throw new WxException(-1, "本地程序错误");
    }

    public static JSONObject post(String strUrl, File file) {
        return post(strUrl, file, null, null);
    }
    public static JSONObject post(String strUrl, File file, String formKey, String formData) {
        if (file == null || !file.exists()) {
            throw new WxException(-1, "文件不存在");
        }
        try {
            WxLog.info("请求：" + strUrl);
            WxLog.info("发送文件：" + file.getName());
            URL url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            //上传文件
            String boundary = "----------" + System.currentTimeMillis();
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            StringBuilder sb = new StringBuilder();
            sb.append("--"); // 必须多两道线
            sb.append(boundary);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;");
            sb.append("name=\"media\";");
            sb.append(String.format("filename=\"%s\";", file.getName()));
            sb.append(String.format("filelength=%s;", file.length()));
            sb.append("\r\n");

            String contentType = "text/plain";
            try {
                contentType = Files.probeContentType(Paths.get(file.getAbsolutePath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            sb.append("Content-Type: ").append(contentType).append("\r\n\r\n");

            byte[] head = sb.toString().getBytes("utf-8");


            // 获得输出流
            OutputStream out = new DataOutputStream(connection.getOutputStream());
            // 输出表头
            out.write(head);

            // 文件正文部分
            // 把文件已流文件的方式 推入到url中
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            in.close();

            // 结尾部分
            byte[] foot = ("\r\n--" + boundary + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线

            out.write(foot);

            if (formKey != null && formData != null) {
                out.write(("--" + boundary + "\r\n").getBytes());
                out.write(("Content-Disposition: form-data; name=\"" + formKey + "\";\r\n\r\n").getBytes("UTF-8"));
                out.write(formData.getBytes("UTF-8"));
                out.write(("\r\n--" + boundary + "--\r\n\r\n").getBytes("UTF-8"));
            }

            out.flush();
            out.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String data = reader.readLine();
            WxLog.info("响应：" + data);
            if (data != null) {
                JSONObject result = JSONObject.fromObject(data);
                if (!isSuccess(result))
                    throw WxException.fromJson(result);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new WxException(-1, "本地程序错误");
        }
        throw new WxException(-1, "本地程序错误");
    }

    public static void download(WxToken wxToken, String strUrl, String json, String savePath) {
        try {
            WxLog.info("请求：" + strUrl);
            WxLog.info("发送数据：" + json);
            strUrl = strUrl + "?access_token=" + wxToken.getAccessToken();
            URL url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");

            OutputStream out = connection.getOutputStream();

            out.write(json.getBytes("UTF-8"));
            out.flush();
            out.close();

            WxLog.info("下载文件");
            InputStream in = connection.getInputStream();
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(savePath);
                byte[] buffer = new byte[1024];

                int byteRead;
                while ((byteRead = in.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteRead);
                }
            } finally {
                if (fos != null) {
                    fos.close();
                }

                WxLog.info("文件保存在：" + savePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new WxException(-1, "本地程序错误");
        }
    }

    /**
     * 判断请求是否成功
     *
     * @param data
     * @return
     */
    public static boolean isSuccess(JSONObject data) {
        if (data.has("errcode")) {
            return "0".equals(data.getString("errcode"));
        }
        return true;
    }
}
