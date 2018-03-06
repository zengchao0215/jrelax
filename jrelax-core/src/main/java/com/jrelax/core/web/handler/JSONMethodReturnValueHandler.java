package com.jrelax.core.web.handler;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jrelax.core.web.annotation.ResponseJson;
import com.jrelax.core.web.converter.JSONObjectMapper;
import com.jrelax.core.web.support.http.ContentType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.procedure.NoSuchParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 响应JSON配置
 * Created by zengchao on 2017-02-14.
 */
public class JSONMethodReturnValueHandler implements HandlerMethodReturnValueHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return methodParameter.getMethodAnnotation(ResponseJson.class) != null;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {
        try {
            modelAndViewContainer.setRequestHandled(true);

            HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
            response.setContentType(ContentType.JSON + ";charset=UTF-8");
            ObjectMapper objectMapper = new JSONObjectMapper();
            Object value = returnValue;

            ResponseJson responseJson = methodParameter.getMethodAnnotation(ResponseJson.class);
            if (responseJson.includes().length > 0 || responseJson.excludes().length > 0) {
                if (value instanceof Collection) {
                    JSONArray jsonArray = JSONArray.fromObject(objectMapper.writeValueAsString(returnValue));
                    for (Object object : jsonArray)
                        filter(object, responseJson.includes(), responseJson.excludes());
                    value = jsonArray;
                } else if (value instanceof Map) {
                    value = JSONObject.fromObject(objectMapper.writeValueAsString(returnValue));
                    filter(value, responseJson.includes(), responseJson.excludes());
                }
            }

            JsonGenerator generator = objectMapper.getFactory().createGenerator(response.getOutputStream(), JsonEncoding.UTF8);
            //支持JSONP
            if(responseJson.jsonp()){
                String function = nativeWebRequest.getParameter(responseJson.jsoupFunction());
                if(function == null)
                    throw new NoSuchParameterException("JSONP请求异常，请求中未找到参数："+responseJson.jsoupFunction());

                generator.writeRaw(function + "(");
            }
            //输出JSON
            ObjectWriter objectWriter = objectMapper.writer();
            objectWriter.writeValue(generator, value);

            if(responseJson.jsonp()){
                generator.writeRaw(");");
            }
            generator.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 过滤
     *
     * @param object
     * @param includes 包含
     * @param excludes 排除
     */
    private void filter(Object object, String[] includes, String[] excludes) {
        if (object instanceof JSONObject) {
            JSONObject json = (JSONObject) object;
            if (includes.length > 0) {
                Map<String, Object> map = new HashMap<>();
                for (String key : includes) {
                    map.put(key, json.get(key));
                }
                json.clear();
                json.putAll(map);
            } else if (excludes.length > 0) {
                for (String key : excludes) {
                    json.remove(key);
                }
            }
        }
    }
}
