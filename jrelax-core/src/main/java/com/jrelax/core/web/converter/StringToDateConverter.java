package com.jrelax.core.web.converter;

import com.jrelax.kit.StringKit;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换
 * 需要日期格式严格按照 yyyy-MM-dd HH:mm:ss sss
 * Created by zengchao on 2017-04-14.
 */
public class StringToDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String str) {
        Date date = null;

        if(StringKit.isNotEmpty(str)){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            switch (str.length()){
                case 4:
                    simpleDateFormat = new SimpleDateFormat("yyyy");
                    break;
                case 6:
                    simpleDateFormat = new SimpleDateFormat("yyyy-MM");
                    break;
                case 10:
                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    break;
                case 13:
                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
                    break;
                case 16:
                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    break;
                case 19:
                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    break;
                case 21:
                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss sss");
                    break;
            }
            try {
                date = simpleDateFormat.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return date;
    }
}
