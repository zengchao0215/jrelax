package com.jrelax.kit;

import com.jrelax.kit.excel.TableToExcel;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel工具类
 * Created by zengchao on 2017-01-14.
 */
public class ExcelKit {
    private static Logger logger = Logger.getLogger(ExcelKit.class);

    /**
     * 读取XLS格式文件
     *
     * @param file          Excel文件
     * @param startSheetNum 起始标签号 从0开始
     * @param startRowNum   起始行号
     * @return
     */
    private static List<List<String>> readRows(File file, int startSheetNum, int startRowNum) {
        List<List<String>> list = new ArrayList<List<String>>();
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            Workbook workbook = createWorkbook(is, FileKit.getSuffix(file));
            Sheet sheet = workbook.getSheetAt(startSheetNum);
            if (sheet != null) {
                for (int j = startRowNum; j <= sheet.getLastRowNum(); j++) {
                    Row row = sheet.getRow(j);
                    if (isEmptyRow(row)) {
                        break;
                    }
                    list.add(createList(row));
                }
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 创建工作簿
     *
     * @param in
     * @param suffix
     * @return
     * @throws IOException
     */
    private static Workbook createWorkbook(InputStream in, String suffix) throws IOException {
        Workbook workbook = null;
        if (suffix.equals(".xls")) {
            workbook = new HSSFWorkbook(in);
        } else if (suffix.equals(".xlsx")) {
            workbook = new XSSFWorkbook(in);
        } else {
            throw new RuntimeException("文件类型错误：预期类型：xls/xlsx，传入类型：" + suffix);
        }
        return workbook;
    }

    /**
     * 判断是否是空行
     * 只有一行中 所有的单元格均为空值时才认为此行为空
     *
     * @param row
     * @return
     */
    private static boolean isEmptyRow(Row row) {
        if (row == null) return true;
        boolean empty = true;
        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && !StringKit.isEmpty(getCellValue(cell))) {
                empty = false;
                break;
            }
        }
        return empty;
    }

    /**
     * 创建List集合，存放每一行数据
     *
     * @param row
     * @return
     */
    private static List<String> createList(Row row) {
        List<String> list = new ArrayList<String>();
        try {
            int lastCellNum = row.getLastCellNum();
            for (int i = 0; i < lastCellNum; i++) {
                list.add(getCellValue(row.getCell(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取单元格值
     *
     * @param cell
     * @return
     */
    private static String getCellValue(Cell cell) {
        if (cell.getCellTypeEnum() == CellType.NUMERIC || cell.getCellTypeEnum() == CellType.FORMULA) {
            DecimalFormat df;
            if (cell.getCellStyle().getDataFormatString().contains(".")) {
                df = new DecimalFormat("0.00");
                double temp = cell.getNumericCellValue();
                return String.valueOf(df.format(temp));
            } else {
                df = new DecimalFormat("0");
                double temp = cell.getNumericCellValue();
                return String.valueOf(df.format(temp));
            }
        } else if (cell.getCellTypeEnum() == CellType.STRING) {
            return String.valueOf(cell.getStringCellValue());
        } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
            return cell.getBooleanCellValue() ? "是" : "否";
        } else if (cell.getCellTypeEnum() == CellType.BLANK) {
            return "";
        } else {
            return null;
        }
    }

    /**
     * 是否是Excel文件
     * 根据后缀名判断
     *
     * @param file
     * @return
     */
    public static boolean isExcelFile(File file) {
        if (file == null || !file.exists() || !file.isFile()) return false;
        String suffix = FileKit.getSuffix(file).toLowerCase();
        return suffix.equals(".xls") || suffix.equals(".xlsx");
    }

    /**
     * 读取Excel文件
     *
     * @param path          文件路径，绝对路径
     * @param startSheetNum 起始标签号 从0开始
     * @param startRowNum   起始行号 从0开始
     * @return
     */
    public static List<List<String>> getRows(String path, int startSheetNum, int startRowNum) {
        try {
            File file = new File(path);
            if (StringKit.isEmpty(path) || !file.isFile() || !file.exists()) {
                return null;
            }
            return getRows(file, startSheetNum, startRowNum);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取Excel
     *
     * @param file          Excel文件
     * @param startSheetNum 起始标签号 从0开始
     * @param startRowNum   起始行号 从0开始
     * @return
     */
    public static List<List<String>> getRows(File file, int startSheetNum, int startRowNum) {
        try {
            logger.debug("ExcelKit: 读取Excel文件到List");
            if (!isExcelFile(file)) return null;
            return readRows(file, startSheetNum, startRowNum);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取特定行数据
     *
     * @param sheetNum 选项卡编号，起始为0
     * @param rowNum   行号，起始为0
     * @return
     */
    public static List<String> getRowAt(File file, int sheetNum, int rowNum) {
        if (!isExcelFile(file)) return null;
        List<String> rowList = null;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            Workbook workbook = createWorkbook(is, FileKit.getSuffix(file));
            Sheet sheet = workbook.getSheetAt(sheetNum);
            if (sheet != null && sheet.getLastRowNum() < rowNum) {
                Row row = sheet.getRow(rowNum);
                if (isEmptyRow(row)) {
                    return null;
                }
                rowList = createList(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return rowList;
    }

    /**
     * 获取所有的选项卡，仅名称
     *
     * @param file
     * @return
     */
    public static List<String> getSheets(File file) {
        if (!isExcelFile(file)) return null;
        List<String> sheetList = new ArrayList<>();
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            Workbook workbook = createWorkbook(is, FileKit.getSuffix(file));
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                sheetList.add(workbook.getSheetName(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sheetList;
    }

    /**
     * 从HTML解析为Excel
     *
     * @param html
     * @return
     */
    public static Workbook fromHtml(String html) {
        return new TableToExcel().parse(html);
    }
}
