package com.jrelax.test.kit;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试用HashMap工具类
 * Created by zengchao on 2017-01-20.
 */
public class MapKit {
    /**
     * 表格形式打印
     *
     * @param mapList
     */
    public void printMap(List<Map<String, String>> mapList) {
        //先找出所有的key
        List<String> keyList = new ArrayList<>();
        for (Map<String, String> map : mapList) {
            for (String key : map.keySet()) {
                if (!keyList.contains(key))
                    keyList.add(key);
            }
        }

        //打印key
        for (String key : keyList) {
            System.out.print(key + "\t");
        }
        System.out.println();

        //打印值
        for (Map<String, String> map : mapList) {
            for (String key : keyList) {
                System.out.print(map.get(key) + "\t");
            }
            System.out.println();
        }
        System.out.println();

    }

    /**
     * 字符串转换为Map
     * 字符串格式： key:value|key1:value1|key2:value2
     * 每一组键值对以`|`隔开，键值用`:`隔开
     *
     * @param str
     * @return
     */
    private Map<String, String> toMap(String str) {
        Map<String, String> map = new LinkedHashMap<>();
        String[] array = str.split("\\|");
        for (String s : array) {
            String[] s1 = s.split("\\:");
            map.put(s1[0].trim(), s1[1].trim());
        }
        return map;
    }
}
