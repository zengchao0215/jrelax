package com.jrelax.kit.excel;

import com.jrelax.kit.excel.style.StyleParser;
import com.jrelax.kit.excel.style.StyleUtils;
import com.jrelax.kit.excel.style.parser.BGParser;
import com.jrelax.kit.excel.style.parser.BorderParser;
import com.jrelax.kit.excel.style.parser.SizeParser;
import com.jrelax.kit.excel.style.parser.TextParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

/**
 * HTML表格转换为Excel
 * 借鉴自：https://github.com/chyxion/table-to-xls
 */
public class TableToExcel {
    private static final List<StyleParser> STYLE_PARSERS = new LinkedList<>();

    // static init
    static {
        STYLE_PARSERS.add(new BGParser());
        STYLE_PARSERS.add(new SizeParser());
        STYLE_PARSERS.add(new BorderParser());
        STYLE_PARSERS.add(new TextParser());
    }

    private Workbook workBook = null;
    private Sheet sheet = null;
    private Map<String, Object> cellsOccupied = new HashMap<>();
    private Map<String, CellStyle> cellStyles = new HashMap<>();
    private CellStyle defaultCellStyle = null;
    private int maxRow = 0;

    /**
     * 解析
     *
     * @param html
     * @return
     */
    public Workbook parse(String html) {
        workBook = new HSSFWorkbook();
        sheet = workBook.createSheet();
        defaultCellStyle = StyleUtils.getDefaultCellStyle(workBook);

        Elements tables = Jsoup.parseBodyFragment(html).select("table");
        for (Element table : tables) {
            processTable(table);
        }
        return workBook;
    }

    private void processTable(Element table) {
        int rowIndex = 0;
        if (maxRow > 0) {
            // blank row
            maxRow += 2;
            rowIndex = maxRow;
        }
        for (Element row : table.select("tr")) {
            int colIndex = 0;
            for (Element td : row.select("td, th")) {
                // skip occupied cell
                while (cellsOccupied.get(rowIndex + "_" + colIndex) != null) {
                    ++colIndex;
                }
                int rowSpan = 0;
                String strRowSpan = td.attr("rowspan");
                if (StringUtils.isNotBlank(strRowSpan) && StringUtils.isNumeric(strRowSpan)) {
                    rowSpan = Integer.parseInt(strRowSpan);
                }
                int colSpan = 0;
                String strColSpan = td.attr("colspan");
                if (StringUtils.isNotBlank(strColSpan) &&
                        StringUtils.isNumeric(strColSpan)) {
                    colSpan = Integer.parseInt(strColSpan);
                }
                // col span & row span
                if (colSpan > 1 && rowSpan > 1) {
                    spanRowAndCol(td, rowIndex, colIndex, rowSpan, colSpan);
                    colIndex += colSpan;
                }
                // col span only
                else if (colSpan > 1) {
                    spanCol(td, rowIndex, colIndex, colSpan);
                    colIndex += colSpan;
                }
                // row span only
                else if (rowSpan > 1) {
                    spanRow(td, rowIndex, colIndex, rowSpan);
                    ++colIndex;
                }
                // no span
                else {
                    createCell(td, getOrCreateRow(rowIndex), colIndex).setCellValue(td.text());
                    ++colIndex;
                }
            }
            ++rowIndex;
        }
    }

    private void spanRow(Element td, int rowIndex, int colIndex, int rowSpan) {
        mergeRegion(rowIndex, rowIndex + rowSpan - 1, colIndex, colIndex);
        for (int i = 0; i < rowSpan; ++i) {
            Row row = getOrCreateRow(rowIndex + i);
            createCell(td, row, colIndex);
            cellsOccupied.put((rowIndex + i) + "_" + colIndex, true);
        }
        getOrCreateRow(rowIndex).getCell(colIndex).setCellValue(td.text());
    }

    private void spanCol(Element td, int rowIndex, int colIndex, int colSpan) {
        mergeRegion(rowIndex, rowIndex, colIndex, colIndex + colSpan - 1);
        Row row = getOrCreateRow(rowIndex);
        for (int i = 0; i < colSpan; ++i) {
            createCell(td, row, colIndex + i);
        }
        row.getCell(colIndex).setCellValue(td.text());
    }

    private void spanRowAndCol(Element td, int rowIndex, int colIndex,
                               int rowSpan, int colSpan) {
        mergeRegion(rowIndex, rowIndex + rowSpan - 1, colIndex, colIndex + colSpan - 1);
        for (int i = 0; i < rowSpan; ++i) {
            Row row = getOrCreateRow(rowIndex + i);
            for (int j = 0; j < colSpan; ++j) {
                createCell(td, row, colIndex + j);
                cellsOccupied.put((rowIndex + i) + "_" + (colIndex + j), true);
            }
        }
        getOrCreateRow(rowIndex).getCell(colIndex).setCellValue(td.text());
    }

    private Cell createCell(Element td, Row row, int colIndex) {
        Cell cell = row.getCell(colIndex);
        if (cell == null) {
            cell = row.createCell(colIndex);
        }
        return applyStyle(td, cell);
    }

    private Cell applyStyle(Element td, Cell cell) {
        String style = td.attr(StyleParser.STYLE);
        CellStyle cellStyle = null;
        if (StringUtils.isNotBlank(style)) {
            if (cellStyles.size() < 4000) {
                Map<String, String> mapStyle = parseStyle(style.trim());
                Map<String, String> mapStyleParsed = new HashMap<String, String>();
                for (StyleParser applier : STYLE_PARSERS) {
                    mapStyleParsed.putAll(applier.parse(mapStyle));
                }
                cellStyle = cellStyles.get(styleStr(mapStyleParsed));
                if (cellStyle == null) {
                    cellStyle = workBook.createCellStyle();
                    cellStyle.cloneStyleFrom(defaultCellStyle);
                    for (StyleParser applier : STYLE_PARSERS) {
                        applier.apply(cell, cellStyle, mapStyleParsed);
                    }
                    // cache style
                    cellStyles.put(styleStr(mapStyleParsed), cellStyle);
                }
            } else {
                cellStyle = defaultCellStyle;
            }
        } else {
            cellStyle = defaultCellStyle;
        }
        cell.setCellStyle(cellStyle);
        return cell;
    }

    private String styleStr(Map<String, String> style) {
        StringBuilder sbStyle = new StringBuilder();
        Object[] keys = style.keySet().toArray();
        Arrays.sort(keys);
        for (Object key : keys) {
            sbStyle.append(key)
                    .append(':')
                    .append(style.get(key))
                    .append(';');
        }
        return sbStyle.toString();
    }

    private Map<String, String> parseStyle(String style) {
        Map<String, String> mapStyle = new HashMap<String, String>();
        for (String s : style.split("\\s*;\\s*")) {
            if (StringUtils.isNotBlank(s)) {
                String[] ss = s.split("\\s*\\:\\s*");
                if (ss.length == 2 &&
                        StringUtils.isNotBlank(ss[0]) &&
                        StringUtils.isNotBlank(ss[1])) {
                    String attrName = ss[0].toLowerCase();
                    String attrValue = ss[1];
                    // do not change font name
                    if (!StyleParser.FONT.equals(attrName) &&
                            !StyleParser.FONT_FAMILY.equals(attrName)) {
                        attrValue = attrValue.toLowerCase();
                    }
                    mapStyle.put(attrName, attrValue);
                }
            }
        }
        return mapStyle;
    }


    private Row getOrCreateRow(int rowIndex) {
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
            if (rowIndex > maxRow) {
                maxRow = rowIndex;
            }
        }
        return row;
    }

    /**
     * 合并单元格
     *
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     */
    private void mergeRegion(int firstRow, int lastRow, int firstCol, int lastCol) {
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }
}
