package com.jrelax.kit.excel.style.parser;

import com.jrelax.kit.StringKit;
import com.jrelax.kit.excel.style.StyleParser;
import com.jrelax.kit.excel.style.StyleUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * supports: <br>
 * color: name | #rgb | #rrggbb | rgb(r, g, b) <br>
 * text-decoration: underline; <br>
 * font-style: italic | oblique; <br>
 * font-weight:  bold | bolder | 700 | 800 | 900; <br>
 * font-size: length; length unit will be ignored,
 * [xx-small|x-small|small|medium|large|x-large|xx-large] will be ignored. <br>
 * font：[[ font-style || font-variant || font-weight ]? font-size [/line-height]? font-family]
 * | caption | icon | menu | message-box | small-caption | status-bar;
 * [font-variant, line-height, caption, icon, menu, message-box, small-caption, status-bar] will be ignored.
 *
 * @author Shaun Chyxion <br>
 * chyxion@163.com <br>
 * Oct 24, 2014 5:21:30 PM
 * @version 0.0.1
 * @since 0.0.1
 */
public class TextParser implements StyleParser {
    private static final String TEXT_DECORATION = "text-decoration";
    private static final String UNDERLINE = "underline";
    private static final String TEXT_ALIGN = "text-align";
    private static final String VERTICAL_ALIGN = "vertical-align";

    /**
     * {@inheritDoc}
     */
    public Map<String, String> parse(Map<String, String> style) {
        Map<String, String> mapRtn = new HashMap<String, String>();
        // color
        String color = StyleUtils.processColor(style.get(COLOR));
        if (StringUtils.isNotBlank(color)) {
            mapRtn.put(COLOR, color);
        }
        // font
        parseFontAttr(style, mapRtn);
        // text text-decoration
        if (UNDERLINE.equals(style.get(TEXT_DECORATION))) {
            mapRtn.put(TEXT_DECORATION, UNDERLINE);
        }
        String align = style.get(TEXT_ALIGN);
        if (!ArrayUtils.contains(new String[] {LEFT, CENTER, RIGHT, JUSTIFY}, align)) {
            align = LEFT;
        }
        mapRtn.put(TEXT_ALIGN, align);
        align = style.get(VETICAL_ALIGN);
        if (!ArrayUtils.contains(new String[] {TOP, MIDDLE, BOTTOM}, align)) {
            align = MIDDLE;
        }
        mapRtn.put(VETICAL_ALIGN, align);
        return mapRtn;
    }

    /**
     * {@inheritDoc}
     */
    public void apply(Cell cell, CellStyle cellStyle, Map<String, String> style) {
        Workbook workBook = cell.getSheet().getWorkbook();
        Font font = null;
        if (ITALIC.equals(style.get(FONT_STYLE))) {
            font = getFont(cell, null);
            font.setItalic(true);
        }
        int fontSize = StyleUtils.getInt(style.get(FONT_SIZE));
        if (fontSize > 0) {
            font = getFont(cell, font);
            font.setFontHeightInPoints((short) fontSize);
        }
        if (BOLD.equals(style.get(FONT_WEIGHT))) {
            font = getFont(cell, font);
            font.setBold(true);
        }
        String fontFamily = style.get(FONT_FAMILY);
        if (StringUtils.isNotBlank(fontFamily)) {
            font = getFont(cell, font);
            font.setFontName(fontFamily);
        }
        HSSFColor color = StyleUtils.parseColor((HSSFWorkbook) workBook, style.get(COLOR));
        if (color != null) {
            if (color.getIndex() != HSSFColor.HSSFColorPredefined.BLACK.getIndex()) {
                font = getFont(cell, font);
                font.setColor(color.getIndex());
            } else {
                style.remove(COLOR);
            }
        }
        // text-decoration
        String textDecoration = style.get(TEXT_DECORATION);
        if (UNDERLINE.equals(textDecoration)) {
            font = getFont(cell, font);
            font.setUnderline(Font.U_SINGLE);
        }

        if (font != null) {
            cellStyle.setFont(font);
        }

        // text align
        String align = style.get(TEXT_ALIGN);
        HorizontalAlignment sAlign = HorizontalAlignment.LEFT;
        if (RIGHT.equals(align)) {
            sAlign = HorizontalAlignment.RIGHT;
        }
        else if (CENTER.equals(align)) {
            sAlign = HorizontalAlignment.CENTER;
        }
        else if (JUSTIFY.equals(align)) {
            sAlign = HorizontalAlignment.JUSTIFY;
        }
        cellStyle.setAlignment(sAlign);
        // vertical align
        align = style.get(VETICAL_ALIGN);
        VerticalAlignment vAlign = VerticalAlignment.CENTER;
        if (TOP.equals(align)) {
            vAlign = VerticalAlignment.TOP;
        }
        else if (BOTTOM.equals(align)) {
            vAlign = VerticalAlignment.BOTTOM;
        }
        else if (JUSTIFY.equals(align)) {
            vAlign = VerticalAlignment.JUSTIFY;
        }
        cellStyle.setVerticalAlignment(vAlign);
    }

    // --
    // private methods

    private Map<String, String> parseFontAttr(Map<String, String> style, Map<String, String> mapRtn) {
        // font
        String font = style.get(FONT);
        if (StringUtils.isNotBlank(font) &&
                !ArrayUtils.contains(new String[]{
                        "small-caps", "caption",
                        "icon", "menu", "message-box",
                        "small-caption", "status-bar"
                }, font)) {
            String[] ignoreStyles = new String[]{
                    "normal",
                    // font weight normal
                    "[1-3]00"
            };
            StringBuffer sbFont = new StringBuffer(
                    font.replaceAll("^|\\s*" + StringUtils.join(ignoreStyles, "|") + "\\s+|$", " "));
            // style
            Matcher m = Pattern.compile("(?:^|\\s+)(italic|oblique)(?:\\s+|$)")
                    .matcher(sbFont.toString());
            if (m.find()) {
                sbFont.setLength(0);
                mapRtn.put(FONT_STYLE, ITALIC);
                m.appendReplacement(sbFont, " ");
                m.appendTail(sbFont);
            }
            // weight
            m = Pattern.compile("(?:^|\\s+)(bold(?:er)?|[7-9]00)(?:\\s+|$)")
                    .matcher(sbFont.toString());
            if (m.find()) {
                sbFont.setLength(0);
                mapRtn.put(FONT_WEIGHT, BOLD);
                m.appendReplacement(sbFont, " ");
                m.appendTail(sbFont);
            }
            // size xx-small | x-small | small | medium | large | x-large | xx-large | 18px [/2]
            m = Pattern.compile(
                    // before blank or start
                    new StringBuilder("(?:^|\\s+)")
                            // font size
                            .append("(xx-small|x-small|small|medium|large|x-large|xx-large|")
                            .append("(?:")
                            .append(PATTERN_LENGTH)
                            .append("))")
                            // line height
                            .append("(?:\\s*\\/\\s*(")
                            .append(PATTERN_LENGTH)
                            .append("))?")
                            // after blank or end
                            .append("(?:\\s+|$)")
                            .toString())
                    .matcher(sbFont.toString());
            if (m.find()) {
                sbFont.setLength(0);
                String fontSize = m.group(1);
                if (StringUtils.isNotBlank(fontSize)) {
                    fontSize = StringUtils.deleteWhitespace(fontSize);
                    if (fontSize.matches(PATTERN_LENGTH)) {
                        mapRtn.put(FONT_SIZE, fontSize);
                    }
                }
                m.appendReplacement(sbFont, " ");
                m.appendTail(sbFont);
            }
            // font family
            if (sbFont.length() > 0) {
                // trim & remove '"
                String fontFamily = sbFont.toString()
                        .split("\\s*,\\s*")[0].trim().replaceAll("'|\"", "");
                mapRtn.put(FONT_FAMILY, fontFamily);
            }
        }
        font = style.get(FONT_STYLE);
        if (ArrayUtils.contains(new String[]{ITALIC, "oblique"}, font)) {
            mapRtn.put(FONT_STYLE, ITALIC);
        }
        font = style.get(FONT_WEIGHT);
        if (StringUtils.isNotBlank(font) &&
                Pattern.matches("^bold(?:er)?|[7-9]00$", font)) {
            mapRtn.put(FONT_WEIGHT, BOLD);
        }
        font = style.get(FONT_SIZE);
        if (StringKit.isNumeric(font)) {
            mapRtn.put(FONT_SIZE, font);
        }
        font = style.get(FONT_FAMILY);
        if (StringUtils.isNotBlank(font)) {
            mapRtn.put(FONT_FAMILY, font);
        }
        return mapRtn;
    }

    private Font getFont(Cell cell, Font font) {
        if (font == null) {
            font = cell.getSheet().getWorkbook().createFont();
        }
        return font;
    }
}
