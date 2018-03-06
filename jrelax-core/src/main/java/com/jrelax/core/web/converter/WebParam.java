package com.jrelax.core.web.converter;

import com.jrelax.kit.StringKit;

/**
 * 参数结构化
 * Created by Administrator on 2016/6/29.
 */
public class WebParam {
    private String param = null;

    public WebParam(Object param) {
        if (param != null)
            this.param = param.toString();
    }

    public String stringValue() {
        return this.param;
    }

    public boolean boolValue() {
        if (param.equals("true")) {
            return true;
        }
        return false;
    }

    public int intValue() {
        if (StringKit.isEmpty(param))
            return 0;
        else
            return Integer.parseInt(param);
    }

    public double doubleValue() {
        if (StringKit.isEmpty(param))
            return 0.0;
        else
            return Double.parseDouble(param);
    }

    public float floatValue() {
        if (StringKit.isEmpty(param))
            return 0;
        else
            return Float.parseFloat(param);
    }

    public long longValue() {
        if (StringKit.isEmpty(param))
            return 0;
        else
            return Long.parseLong(param);
    }

    public boolean isNull(){
        return this.param == null;
    }

    public boolean isNotNull(){
        return !isNull();
    }

    public boolean isEmpty(){
        return StringKit.isEmpty(this.param);
    }

    public boolean isNotEmpty(){
        return !isEmpty();
    }

    @Override
    public String toString() {
        return this.param;
    }
}
