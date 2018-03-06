package com.jrelax.core.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

/**
 * 字符串转换为BigDecimal
 * Created by zengc on 2016-10-02.
 */
public class StringToBigDecimalConverter implements Converter<String, BigDecimal> {
    @Override
    public BigDecimal convert(String text) {
        if(!StringUtils.hasText(text)) return null;
        text = text.replaceAll(",","").replaceAll("￥", "").replaceAll("$", "");
        return new BigDecimal(text);
    }
}
