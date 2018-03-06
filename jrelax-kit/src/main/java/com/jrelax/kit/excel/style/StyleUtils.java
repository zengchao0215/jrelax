package com.jrelax.kit.excel.style;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StyleUtils {
    // matches #rgb
    private static final String COLOR_PATTERN_VALUE_SHORT = "^(#(?:[a-f]|\\d){3})$";
    // matches #rrggbb
    private static final String COLOR_PATTERN_VALUE_LONG =
            "^(#(?:[a-f]|\\d{2}){3})$";
    // matches #rgb(r, g, b)
    private static final String COLOR_PATTERN_RGB =
            "^(rgb\\s*\\(\\s*(.+)\\s*,\\s*(.+)\\s*,\\s*(.+)\\s*\\))$";
    // color name -> POI Color
    private static Map<String, HSSFColor> colors = new HashMap<String, HSSFColor>();

    // static init
    static {
        for (Map.Entry<Integer, HSSFColor> color : HSSFColor.getIndexHash().entrySet()) {
            colors.put(colorName(color.getValue().getClass()), color.getValue());
        }
        // light gray
        HSSFColor color = colors.get(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.name());
        colors.put("lightgray", color);
        colors.put("lightgrey", color);
        // silver
        colors.put("silver", colors.get(HSSFColor.HSSFColorPredefined.GREY_40_PERCENT.name()));
        // darkgray
        color = colors.get(HSSFColor.HSSFColorPredefined.GREY_50_PERCENT.name());
        colors.put("darkgray", color);
        colors.put("darkgrey", color);
        // gray
        color = colors.get(HSSFColor.HSSFColorPredefined.GREY_80_PERCENT.name());
        colors.put("gray", color);
        colors.put("grey", color);
    }
    /**
     * 默认单元格样式
     *
     * @param workbook
     * @return
     */
    public static CellStyle getDefaultCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();

        cellStyle.setWrapText(true);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // border
        short black = HSSFColor.HSSFColorPredefined.BLACK.getIndex();
        // top
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor(black);
        // right
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(black);
        // bottom
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(black);
        // left
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(black);

        return cellStyle;
    }

    /**
     * get color name
     *
     * @param color HSSFColor
     * @return color name
     */
    private static String colorName(Class<? extends HSSFColor> color) {
        return color.getSimpleName().replace("_", "").toLowerCase();
    }

    /**
     * get int value of string
     *
     * @param strValue string value
     * @return int value
     */
    public static int getInt(String strValue) {
        int value = 0;
        if (StringUtils.isNotBlank(strValue)) {
            Matcher m = Pattern.compile("^(\\d+)(?:\\w+|%)?$").matcher(strValue);
            if (m.find()) {
                value = Integer.parseInt(m.group(1));
            }
        }
        return value;
    }

    /**
     * process color
     *
     * @param color color to process
     * @return color after process
     */
    public static String processColor(String color) {
        String colorRtn = null;
        if (StringUtils.isNotBlank(color)) {
            HSSFColor poiColor = null;
            // #rgb -> #rrggbb
            if (color.matches(COLOR_PATTERN_VALUE_SHORT)) {
                StringBuffer sbColor = new StringBuffer();
                Matcher m = Pattern.compile("([a-f]|\\d)").matcher(color);
                while (m.find()) {
                    m.appendReplacement(sbColor, "$1$1");
                }
                colorRtn = sbColor.toString();
            }
            // #rrggbb
            else if (color.matches(COLOR_PATTERN_VALUE_LONG)) {
                colorRtn = color;
            }
            // rgb(r, g, b)
            else if (color.matches(COLOR_PATTERN_RGB)) {
                Matcher m = Pattern.compile(COLOR_PATTERN_RGB).matcher(color);
                if (m.matches()) {
                    colorRtn = convertColor(calcColorValue(m.group(2)),
                            calcColorValue(m.group(3)),
                            calcColorValue(m.group(4)));
                }
            }
            // color name, red, green, ...
            else if ((poiColor = getColor(color)) != null) {
                short[] t = poiColor.getTriplet();
                colorRtn = convertColor(t[0], t[1], t[2]);
            }
        }
        return colorRtn;
    }

    /**
     * parse color
     *
     * @param workBook work book
     * @param color    string color
     * @return HSSFColor
     */
    public static HSSFColor parseColor(HSSFWorkbook workBook, String color) {
        HSSFColor poiColor = null;
        if (StringUtils.isNotBlank(color)) {
            Color awtColor = Color.decode(color);
            int r = awtColor.getRed();
            int g = awtColor.getGreen();
            int b = awtColor.getBlue();
            HSSFPalette palette = workBook.getCustomPalette();
            poiColor = palette.findColor((byte) r, (byte) g, (byte) b);
            if (poiColor == null) {
                poiColor = palette.findSimilarColor(r, g, b);
            }
        }
        return poiColor;
    }

    // --
    // private methods

    private static HSSFColor getColor(String color) {
        return colors.get(color.replace("_", ""));
    }

    private static String convertColor(int r, int g, int b) {
        return String.format("#%02x%02x%02x", r, g, b);
    }

    private static int calcColorValue(String color) {
        int rtn = 0;
        // matches 64 or 64%
        Matcher m = Pattern.compile("^(\\d*\\.?\\d+)\\s*(%)?$").matcher(color);
        if (m.matches()) {
            // % not found
            if (m.group(2) == null) {
                rtn = Math.round(Float.parseFloat(m.group(1))) % 256;
            } else {
                rtn = Math.round(Float.parseFloat(m.group(1)) * 255 / 100) % 256;
            }
        }
        return rtn;
    }
}
