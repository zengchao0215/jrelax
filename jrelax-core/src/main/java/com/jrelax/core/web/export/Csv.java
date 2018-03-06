package com.jrelax.core.web.export;

import com.jrelax.core.web.support.http.ContentType;
import com.jrelax.kit.ReflectKit;
import com.jrelax.kit.StringKit;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * CSV文件封装
 * Created by zengchao on 2017-03-15.
 */
public class Csv implements IExport {
    private String name = null; //文件名
    private String[] titles = null;//标题
    private List<Object[]> data = new ArrayList<>();//表格数据
    private StringBuilder content = new StringBuilder();

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSuffix() {
        return ".csv";
    }

    @Override
    public String getContentType() {
        return ContentType.CSV;
    }

    /**
     * 获取标题
     *
     * @return
     */
    public String[] getTitles() {
        return titles;
    }

    /**
     * 设置标题
     *
     * @param titles
     */
    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    /**
     * 设置
     *
     * @param titleList
     */
    public void setTitles(List<String> titleList) {
        this.titles = titleList.toArray(new String[]{});
    }

    /**
     * 获取数据行
     *
     * @return
     */
    public List<Object[]> getData() {
        return data;
    }

    /**
     * 设置数据行
     *
     * @param data
     */
    public void setData(List<Object[]> data) {
        this.data = data;
    }

    private void resetData(){
        content = new StringBuilder();
    }

    /**
     * 设置数据
     * @param mapList
     * @param keys
     */
    public void setDataByMap(List<Map<String, Object>> mapList, String[] keys) {
        this.resetData();
        for (Map<String, Object> map : mapList) {
            Object[] array = new Object[keys.length];
            for (int i = 0; i < keys.length; i++) {
                array[i] = map.get(keys[i]);
            }
            this.data.add(array);
        }
    }

    /**
     * 设置数据
     * @param dataList
     */
    public void setDataByList(List<List<Object>> dataList) {
        this.resetData();
        for (List<Object> list : dataList) {
            this.data.add(list.toArray(new Object[]{}));
        }
    }

    /**
     * 设置数据，JavaBean
     * @param beanList
     * @param fields
     */
    public void setDataByBean(List<?> beanList, String[] fields) {
        this.resetData();
        for (Object bean : beanList) {
            Object[] array = new Object[fields.length];
            for (int i = 0; i < fields.length; i++) {
                array[i] = ReflectKit.getFieldValue(bean, fields[i]);
            }
            this.data.add(array);
        }
    }


    @Override
    public void write(OutputStream outputStream) throws IOException {
        build();
        OutputStreamWriter out = new OutputStreamWriter(outputStream, "UTF-8");
        out.write(content.toString());
        out.flush();
        out.close();
    }

    private void build() {
        this.resetData();
        if (this.titles == null && this.data == null)
            throw new RuntimeException("Csv Data is Empty");

        if (this.titles != null) {
            content.append(StringKit.toString(this.titles, ","));
            content.append(System.lineSeparator());
        }

        if (this.data != null) {
            for (Object[] line : this.data) {
                content.append(StringKit.toString(line, ","));
                content.append(System.lineSeparator());
            }
        }
    }

    /**
     * 保存到磁盘
     *
     * @param path
     */
    public void store(String path) {
        build();
        FileOutputStream fos = null;
        OutputStreamWriter out = null;
        try {
            File file = new File(path);
            fos = new FileOutputStream(file);
            out = new OutputStreamWriter(fos, "UTF-8");
            out.write(content.toString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null)
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (out != null)
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }
}
