package com.jrelax.kit.excel.style.parser;

import com.jrelax.kit.excel.style.StyleParser;
import com.jrelax.kit.excel.style.StyleUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shaun Chyxion <br>
 * chyxion@163.com <br>
 * Oct 24, 2014 5:03:32 PM
 * @version 0.0.1
 * @since 0.0.1
 */
public class BGParser implements StyleParser {

    /**
     * {@inheritDoc}
     */
    public Map<String, String> parse(Map<String, String> style) {
        Map<String, String> mapRtn = new HashMap<String, String>();
        String bg = style.get(BACKGROUND);
        String bgColor = null;
        if (StringUtils.isNotBlank(bg)) {
            for (String bgAttr : bg.split("(?<=\\)|\\w|%)\\s+(?=\\w)")) {
                if ((bgColor = StyleUtils.processColor(bgAttr)) != null) {
                    mapRtn.put(BACKGROUND_COLOR, bgColor);
                    break;
                }
            }
        }
        bg = style.get(BACKGROUND_COLOR);
        if (StringUtils.isNotBlank(bg) &&
                (bgColor = StyleUtils.processColor(bg)) != null) {
            mapRtn.put(BACKGROUND_COLOR, bgColor);

        }
        if (bgColor != null) {
            bgColor = mapRtn.get(BACKGROUND_COLOR);
            if ("#ffffff".equals(bgColor)) {
                mapRtn.remove(BACKGROUND_COLOR);
            }
        }
        return mapRtn;
    }

    /**
     * {@inheritDoc}
     */
    public void apply(Cell cell, CellStyle cellStyle, Map<String, String> style) {
        String bgColor = style.get(BACKGROUND_COLOR);
        if (StringUtils.isNotBlank(bgColor)) {
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setFillForegroundColor(
                    StyleUtils.parseColor((HSSFWorkbook) cell.getSheet().getWorkbook(),
                            bgColor).getIndex());
        }
    }
}
