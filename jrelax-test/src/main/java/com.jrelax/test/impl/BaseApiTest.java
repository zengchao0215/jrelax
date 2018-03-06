package com.jrelax.test.impl;

import com.jrelax.test.IApiTest;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

import java.util.Map;

/**
 * 基础测试类
 * Created by zengc on 2016-05-20.
 */
public class BaseApiTest implements IApiTest {
    @Override
    public int request(RequestMethod method, String url, Map<String, String> params, Map<String, String> response) {
        try {
            Connection connection = Jsoup.connect(url);
            connection.ignoreContentType(true);
            connection.followRedirects(false);

            //设置参数
            connection.data(params);

            long a = System.currentTimeMillis();
            if(method == RequestMethod.GET){
                connection.method(Connection.Method.GET);
            }else if(method == RequestMethod.POST){
                connection.method(Connection.Method.POST);
            }
            Connection.Response resp = connection.execute();
            long b = System.currentTimeMillis();
            response.put("time", (b-a)+""); //精确到毫秒
            if(resp.statusCode() == 200){
                response.put("charset", resp.charset());
                response.put("contentType", resp.contentType());
                response.put("response", resp.body());
            }else{
                response.put("error", resp.statusMessage());
                return resp.statusCode();
            }

        }catch(IllegalArgumentException e){
            response.put("error", "请求地址无效");
            return 400;
        }catch(HttpStatusException e){
            response.put("error", e.getLocalizedMessage());
            return e.getStatusCode();
        }catch (Exception e) {
            response.put("error", e.getLocalizedMessage());
            return 500;
        }
        return 200;
    }
}
