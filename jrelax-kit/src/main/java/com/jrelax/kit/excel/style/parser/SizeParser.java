package com.jrelax.kit.excel.style.parser;

import com.jrelax.kit.StringKit;
import com.jrelax.kit.excel.style.StyleParser;
import com.jrelax.kit.excel.style.StyleUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.Map;

public class SizeParser implements StyleParser {
    @Override
    public Map<String, String> parse(Map<String, String> style) {
        Map<String, String> mapRtn = new HashMap<String, String>();
        String height = style.get("height");
        if (StringKit.isNumeric(height)) {
            mapRtn.put("height", height);
        }
        String width = style.get("width");
        if (StringKit.isNumeric(width)) {
            mapRtn.put("width", width);
        }
        return mapRtn;
    }

    @Override
    public void apply(Cell cell, CellStyle cellStyle, Map<String, String> style) {
        //高度
        int height = Math.round(StyleUtils.getInt(style.get("height")) * 255 / 12.75F);
        Row row = cell.getRow();
        if (height > row.getHeight()) {
            row.setHeight((short) height);
        }

        //宽度
        int width = Math.round(StyleUtils.getInt(style.get(WIDTH)) * 2048 / 8.43F);
        Sheet sheet = cell.getSheet();
        int colIndex = cell.getColumnIndex();
        if (width > sheet.getColumnWidth(colIndex)) {
            if (width > 255 * 256) {
                width = 255 * 256;
            }
            sheet.setColumnWidth(colIndex, width);
        }
    }
}
