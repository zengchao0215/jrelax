package com.jrelax.core.web.export;

import com.jrelax.kit.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Excel封装
 * 不支持合并
 * Created by zengchao on 2017/3/12.
 * Updated by zengchao on 2017/3/13. 增加接口定义
 */
public class Excel implements IExport {
    public static final int XLS = 1;
    public static final int XLSX = 2;
    private int type = XLSX;//类型
    private boolean fromFile = false;//从文件读取
    private Workbook book = null;
    private String name = null; //文件名
    private List<String[]> titles = new ArrayList<>();//标题
    private List<List<Object[]>> data = new ArrayList<>();//表格数据

    /**
     * 获取标题
     *
     * @return
     */
    public String[] getTitles() {
        return getTitles(0);
    }

    /**
     * 获取标题
     *
     * @param sheetAt 标签索引
     * @return
     */
    public String[] getTitles(int sheetAt) {
        return this.titles.get(sheetAt);
    }

    /**
     * 设置标题
     *
     * @param titles
     */
    public void setTitles(String[] titles) {
        setTitles(titles, 0);
    }

    /**
     * 设置标题
     *
     * @param titles
     * @param sheetAt 标签索引
     */
    public void setTitles(String[] titles, int sheetAt) {
        this.titles.add(sheetAt, titles);
    }

    /**
     * 设置标题
     *
     * @param list
     */
    public void setTitles(List<String> list) {
        setTitles(list, 0);
    }

    /**
     * 设置标题
     *
     * @param list
     * @param sheetAt 标签索引
     */
    public void setTitles(List<String> list, int sheetAt) {
        setTitles(list.toArray(new String[]{}), sheetAt);
    }

    public int getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 1：XLS 2：XLSX
     */
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String getSuffix() {
        return this.type == Excel.XLS ? ".xls" : ".xlsx";
    }

    @Override
    public String getContentType() {
        return "application/msexcel";
    }

    public List<Object[]> getData() {
        return getData(0);
    }

    public List<Object[]> getData(int sheetAt) {
        return data.get(sheetAt);
    }

    public void setData(List<Object[]> data) {
        setData(data, 0);
    }

    public void setData(List<Object[]> data, int sheetAt) {
        this.resetData(sheetAt);
        this.data.add(sheetAt, data);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void resetData(int sheetAt) {
        fromFile = false;
        this.data.add(sheetAt, new ArrayList<>());
    }

    /**
     * 设置数据，需先设置titles
     *
     * @param mapList
     */
    public void setDataByMap(List<Map<String, Object>> mapList) {
        setDataByMap(mapList, titles.get(0), 0);
    }

    /**
     * 设置数据，需先设置titles
     *
     * @param mapList
     */
    public void setDataByMap(List<Map<String, Object>> mapList, int sheetAt) {
        setDataByMap(mapList, titles.get(0), sheetAt);
    }

    /**
     * 设置数据
     *
     * @param mapList
     * @param keys    map中key及顺序
     */
    public void setDataByMap(List<Map<String, Object>> mapList, String[] keys) {
        setDataByMap(mapList, keys, 0);
    }

    public void setDataByMap(List<Map<String, Object>> mapList, String[] keys, int sheetAt) {
        this.resetData(sheetAt);
        List<Object[]> list = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            Object[] array = new Object[keys.length];
            for (int i = 0; i < keys.length; i++) {
                array[i] = map.get(keys[i]);
            }
            list.add(array);
        }
        this.data.add(sheetAt, list);
    }

    /**
     * 设置数据
     *
     * @param dataList
     */
    public void setDataByList(List<List<Object>> dataList) {
        setDataByList(dataList, 0);
    }

    /**
     * 设置数据
     *
     * @param dataList
     */
    public void setDataByList(List<List<Object>> dataList, int sheetAt) {
        this.resetData(sheetAt);
        List<Object[]> list2 = new ArrayList<>();
        for (List<Object> list : dataList) {
            list2.add(list.toArray(new Object[]{}));
        }
        this.data.add(sheetAt, list2);
    }

    /**
     * 设置数据，JavaBean
     *
     * @param beanList
     * @param fields   JavaBean中的字段名
     */
    public void setDataByBean(List<?> beanList, String[] fields) {
        setDataByBean(beanList, fields, 0);
    }

    /**
     * 设置数据，JavaBean
     *
     * @param beanList
     * @param fields   JavaBean中的字段名
     * @param sheetAt  标签索引
     */
    public void setDataByBean(List<?> beanList, String[] fields, int sheetAt) {
        this.resetData(sheetAt);
        List<Object[]> list = new ArrayList<>();
        for (Object bean : beanList) {
            Object[] array = new Object[fields.length];
            for (int i = 0; i < fields.length; i++) {
                array[i] = ReflectKit.getFieldValue(bean, fields[i]);
            }
            list.add(array);
        }
        this.data.add(sheetAt, list);
    }

    /**
     * 构建Excel
     * 基于Apache POI
     */
    private void build() {
        if (this.type == XLS) book = new HSSFWorkbook();
        else if (this.type == XLSX) book = new XSSFWorkbook();
        if (this.titles.isEmpty()) throw new RuntimeException("titles is empty!");
//        if (this.data.isEmpty()) throw new RuntimeException("data is empty!");

        if (book != null) {
            for (int i = 0; i < this.titles.size(); i++) {
                Sheet sheet = book.createSheet();
                //写入标题
                writeRow(sheet, titles.get(i), 0);

                if(this.data.size() > 0){
                    List<Object[]> rows = this.data.get(i);
                    for (int j = 0; j < rows.size(); j++) {
                        writeRow(sheet, rows.get(j), j + 1);
                    }
                }
            }
        } else {
            throw new RuntimeException("类型设置错误，XLS=1，XLSX=2");
        }
    }

    /**
     * 写行
     * 第一行自动加粗
     *
     * @param sheet 标签
     * @param cols  列数据
     * @param rowAt 行号
     */
    private void writeRow(Sheet sheet, Object[] cols, int rowAt) {
        Row row = sheet.createRow(rowAt);
        for (int k = 0; k < cols.length; k++) {
            Cell cell = row.createCell(k);
            writeCell(cell, cols[k]);

            //第一行默认为标题
            if (rowAt == 0) setCellBold(cell);
        }
    }

    private void writeCell(Cell cell, Object value) {
        String v = StringKit.null2String(value);
        if (RegexKit.isNumber(v)) {
            cell.setCellFormula(v);
        } else {
            cell.setCellValue(v);
        }
    }

    /**
     * 加粗
     *
     * @param cell
     */
    private void setCellBold(Cell cell) {
        Font font = book.createFont();
        font.setFontName("微软雅黑");
        font.setBold(true);
        CellStyle cellStyle = book.createCellStyle();
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
    }

    /**
     * 保存到本地
     *
     * @param path 保存路径
     */
    public void store(String path) {
        //如果来自文件，则不从数据创建
        if (!fromFile) build();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);

            book.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null)
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 写文件流
     *
     * @param outputStream
     * @throws IOException
     */
    public void write(OutputStream outputStream) throws IOException {
        if (!fromFile) build();
        book.write(outputStream);
    }

    /**
     * 从文件读取
     *
     * @param file
     */
    public static Excel fromFile(File file) {
        return fromFile(file, null);
    }

    /**
     * 从文件读取
     * @param file 文件
     * @param name 导出的文件名
     * @return
     */
    public static Excel fromFile(File file, String name) {
        Excel excel = new Excel();
        excel.setName(name);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            String suffix = FileKit.getSuffix(file);
            if (".xls".equals(suffix.toLowerCase())) {
                excel.setType(XLS);
                excel.book = new HSSFWorkbook(fis);
            } else if (".xlsx".equals(suffix.toLowerCase())) {
                excel.setType(XLSX);
                excel.book = new XSSFWorkbook(fis);
            } else {
                throw new RuntimeException("文件类型错误");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        excel.fromFile = true;

        return excel;
    }

    /**
     * 解析HTML，将HTML转换为Excel
     * @param html
     * @param name
     * @return
     */
    public static Excel fromHtml(String html, String name){
        Excel excel = new Excel();
        excel.setName(name);
        excel.setType(Excel.XLS);
        excel.book = ExcelKit.fromHtml(html);
        return excel;
    }
}
