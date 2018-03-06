package com.jrelax.kit;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串的一些实用操作
 *
 * @author ZENGCHAO
 */
public class StringKit extends StringUtils {

    private static final String _BR = "<br/>";
    private static final String FOLDER_SEPARATOR = "/";

    private static final String WINDOWS_FOLDER_SEPARATOR = "\\";

    private static final String TOP_PATH = "..";

    private static final String CURRENT_PATH = ".";

    private static final char EXTENSION_SEPARATOR = '.';

    /**
     * 功能描述：分割字符串
     *
     * @param str       String 原始字符串
     * @param splitsign String 分隔符
     * @return String[] 分割后的字符串数组
     */
    public static String[] split(String str, String splitsign) {
        int index;
        if (str == null || splitsign == null) {
            return null;
        }
        ArrayList<String> al = new ArrayList<String>();
        while ((index = str.indexOf(splitsign)) != -1) {
            al.add(str.substring(0, index));
            str = str.substring(index + splitsign.length());
        }
        al.add(str);
        return al.toArray(new String[0]);
    }

    /**
     * 功能描述：替换字符串
     *
     * @param from   String 原始字符串
     * @param to     String 目标字符串
     * @param source String 母字符串
     * @return String 替换后的字符串
     */
    public static String replace(String from, String to, String source) {
        if (source == null || from == null || to == null)
            return null;
        StringBuffer str = new StringBuffer("");
        int index = -1;
        while ((index = source.indexOf(from)) != -1) {
            str.append(source.substring(0, index) + to);
            source = source.substring(index + from.length());
            index = source.indexOf(from);
        }
        str.append(source);
        return str.toString();
    }

    /**
     * 重复字符串
     *
     * @param str 重复字符串
     * @param count 重复次数
     * @return
     */
    public static String repeat(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * 替换字符串，能能够在HTML页面上直接显示(替换双引号和小于号)
     *
     * @param str String 原始字符串
     * @return String 替换后的字符串
     */
    public static String htmlencode(String str) {
        if (str == null) {
            return null;
        }
        return replace("\"", "&quot;", replace("<", "&lt;", str));
    }

    /**
     * 替换字符串，将被编码的转换成原始码（替换成双引号和小于号）
     *
     * @param str String
     * @return String
     */
    public static String htmldecode(String str) {
        if (str == null) {
            return null;
        }

        return replace("&quot;", "\"", replace("&lt;", "<", str));
    }

    /**
     * 功能描述：在页面上直接显示文本内容，替换小于号，空格，回车，TAB
     *
     * @param str String 原始字符串
     * @return String 替换后的字符串
     */
    public static String htmlshow(String str) {
        if (str == null) {
            return null;
        }

        str = replace("<", "&lt;", str);
        str = replace(" ", "&nbsp;", str);
        str = replace("\r\n", _BR, str);
        str = replace("\n", _BR, str);
        str = replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;", str);
        return str;
    }

    /**
     * html字符串转换成纯字符串，删除所有html标签
     *
     * @param str
     * @return
     */
    public static String htmlToText(String str) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
        String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(str);
        str = m_script.replaceAll(""); // 过滤script标签  

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(str);
        str = m_style.replaceAll(""); // 过滤style标签  

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(str);
        str = m_html.replaceAll(""); // 过滤html标签  

        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(str);
        str = m_space.replaceAll(""); // 过滤空格回车标签 
        return str.trim();
    }


    /**
     * 功能描述：是否为空白
     * 包括null和空格
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 功能描述：是否不为空白
     * 包括null和空格
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        return str != null && str.trim().length() > 0;
    }

    /**
     * 功能描述：是否为空白
     * 不包括空格
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 功能描述：是否不为空白
     * 不包括空格
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }

    /**
     * 功能描述：人民币转成大写
     *
     * @param str 数字字符串
     * @return String 人民币转换成大写后的字符串
     */
    public static String hangeToBig(String str) {
        double value;
        try {
            value = Double.parseDouble(str.trim());
        } catch (Exception e) {
            return null;
        }
        char[] hunit = {'拾', '佰', '仟'}; // 段内位置表示
        char[] vunit = {'万', '亿'}; // 段名表示
        char[] digit = {'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'}; // 数字表示
        long midVal = (long) (value * 100); // 转化成整形
        String valStr = String.valueOf(midVal); // 转化成字符串

        String head = valStr.substring(0, valStr.length() - 2); // 取整数部分
        String rail = valStr.substring(valStr.length() - 2); // 取小数部分

        String prefix = ""; // 整数部分转化的结果
        String suffix = ""; // 小数部分转化的结果
        // 处理小数点后面的数
        if (rail.equals("00")) { // 如果小数部分为0
            suffix = "整";
        } else {
            suffix = digit[rail.charAt(0) - '0'] + "角"
                    + digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
        }
        // 处理小数点前面的数
        char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
        char zero = '0'; // 标志'0'表示出现过0
        byte zeroSerNum = 0; // 连续出现0的次数
        for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
            int idx = (chDig.length - i - 1) % 4; // 取段内位置
            int vidx = (chDig.length - i - 1) / 4; // 取段位置
            if (chDig[i] == '0') { // 如果当前字符是0
                zeroSerNum++; // 连续0次数递增
                if (zero == '0') { // 标志
                    zero = digit[0];
                } else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
                    prefix += vunit[vidx - 1];
                    zero = '0';
                }
                continue;
            }
            zeroSerNum = 0; // 连续0次数清零
            if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的
                prefix += zero;
                zero = '0';
            }
            prefix += digit[chDig[i] - '0']; // 转化该数字表示
            if (idx > 0)
                prefix += hunit[idx - 1];
            if (idx == 0 && vidx > 0) {
                prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
            }
        }

        if (prefix.length() > 0)
            prefix += '圆'; // 如果整数部分存在,则有圆的字样
        return prefix + suffix; // 返回正确表示
    }

    /**
     * 功能描述：去掉字符串中重复的子字符串
     *
     * @param str 原字符串，如果有子字符串则用空格隔开以表示子字符串
     * @return String 返回去掉重复子字符串后的字符串
     */
    public static String removeSameString(String str) {
        Set<String> mLinkedSet = new LinkedHashSet<String>();// set集合的特征：其子集不可以重复
        String[] strArray = str.split(" ");// 根据空格(正则表达式)分割字符串
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < strArray.length; i++) {
            if (!mLinkedSet.contains(strArray[i])) {
                mLinkedSet.add(strArray[i]);
                sb.append(strArray[i] + " ");
            }
        }
        return sb.toString();
    }

    /**
     * 功能描述：判断是不是合法的手机号码
     *
     * @param handset
     * @return boolean
     */
    public static boolean isHandset(String handset) {
        try {
            String regex = "^1[\\d]{10}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(handset);
            return matcher.matches();

        } catch (RuntimeException e) {
            return false;
        }
    }


    /**
     * 数组转换成String
     * 默认分隔符为,
     *
     * @param objects
     * @return
     */
    public static String toString(Object[] objects) {
        if (ObjectKit.isNull(objects))
            return null;
        if (objects.length == 0)
            return "";
        StringBuffer sb = new StringBuffer();
        for (Object obj : objects) {
            sb.append(',').append(ObjectKit.toString(obj));
        }
        return sb.substring(1);
    }

    /**
     * 数组转换成String
     *
     * @param objects  数组
     * @param splitStr 分隔符
     * @return
     */
    public static String toString(Object[] objects, String splitStr) {
        if (ObjectKit.isNull(objects))
            return null;
        if (objects.length == 0)
            return "";
        StringBuffer sb = new StringBuffer();
        for (Object obj : objects) {
            sb.append(splitStr).append(ObjectKit.toString(obj));
        }
        return sb.substring(1);
    }

    /**
     * 集合转换成String
     * 默认分隔符为,
     *
     * @param coll
     * @return
     */
    public static String toString(Collection<?> coll) {
        return StringKit.toString(coll, ",");
    }

    /**
     * 集合转换成String
     *
     * @param coll     集合
     * @param splitStr 分隔符
     * @return
     */
    public static String toString(Collection<?> coll, String splitStr) {
        return toString(coll, splitStr, "");
    }

    /**
     * 集合转换成String
     *
     * @param coll     集合
     * @param splitStr 分隔符
     * @param fixStr   前后包围的字符串
     * @return
     */
    public static String toString(Collection<?> coll, String splitStr, String fixStr) {
        if (ObjectKit.isNull(coll))
            return null;
        if (coll.size() == 0)
            return "";
        StringBuffer sb = new StringBuffer();
        for (Object obj : coll) {
            sb.append(splitStr).append(fixStr).append(ObjectKit.toString(obj)).append(fixStr);
        }
        return sb.substring(1);
    }

    /**
     * 是否包含某个字符串
     *
     * @param strs 字符串数组
     * @param str  包含的字符串
     * @return
     */
    public static boolean hasStr(String[] strs, String str) {
        if (ObjectKit.isNotNull(strs) && ObjectKit.isNotNull(str)) {
            for (String s : strs) {
                if (str.equals(s)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 字符串分离后，每个子元素左右两侧拼接单引号
     *
     * @param str
     * @return
     */
    public static String splitAndDpk(String str) {
        String[] array = str.split(",");
        String temp = "";
        for (int i = 0; i < array.length; i++) {
            String s = array[i];
            if (s.startsWith("'") || s.endsWith("'"))
                continue;
            s = "'" + s + "'";

            temp += "," + s;
        }
        if (temp.length() > 0)
            str = temp.substring(1);
        return str;
    }

    /**
     * null转空字符串
     *
     * @return
     */
    public static String null2String(Object str) {
        if (ObjectKit.isNull(str))
            return "";
        else
            return str.toString();
    }

    /**
     * 首字母变大写
     *
     * @param str
     */
    public static String firstUpperCase(String str) {
        if (ObjectKit.isNull(str) || StringKit.isEmpty(str))
            return "";
        if (str.length() == 1)
            return str.toUpperCase();
        else
            return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 首字母变小写
     *
     * @param str
     */
    public static String firstLowerCase(String str) {
        if (ObjectKit.isNull(str) || StringKit.isEmpty(str))
            return "";
        if (str.length() == 1)
            return str.toLowerCase();
        else
            return str.substring(0, 1).toLowerCase() + str.substring(1);
    }


    /**
     * 字符串拼接
     *
     * @param strings
     * @return
     */
    public static String append(String... strings) {
        StringBuffer sb = new StringBuffer();
        for (String s : strings) {
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * 获取指定位置的字符
     *
     * @param str 字符串
     * @param pos 位置，从0开始
     * @return
     */
    public static String at(String str, int pos) {
        if (pos >= str.length()) {
            return null;
        } else {
            return str.substring(pos, pos + 1);
        }
    }

    /**
     * 获取后缀名
     *
     * @param str
     * @return
     */
    public static String getSuffix(String str) {
        return FileKit.getSuffix(str);
    }

    /**
     * 格式化路径，统一Windows目录下和Linux目录下的路径格式
     *
     * @param path
     * @return
     */
    public static String cleanPath(String path) {
        return org.springframework.util.StringUtils.cleanPath(path);
    }

    /**
     * 路径整理， // 转换为 /
     *
     * @param path
     * @return
     */
    public static String normalizePath(String path) {
        // Normalize the slashes and add leading slash if necessary
        String normalized = path;
        if (normalized.indexOf('\\') >= 0) {
            normalized = normalized.replace('\\', '/');
        }

        if (!normalized.startsWith("/")) {
            normalized = "/" + normalized;
        }

        // Resolve occurrences of "//" in the normalized path
        while (true) {
            int index = normalized.indexOf("//");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) +
                    normalized.substring(index + 1);
        }

        // Resolve occurrences of "%20" in the normalized path
        while (true) {
            int index = normalized.indexOf("%20");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) + " " +
                    normalized.substring(index + 3);
        }

        // Resolve occurrences of "/./" in the normalized path
        while (true) {
            int index = normalized.indexOf("/./");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) +
                    normalized.substring(index + 2);
        }

        // Resolve occurrences of "/../" in the normalized path
        while (true) {
            int index = normalized.indexOf("/../");
            if (index < 0)
                break;
            if (index == 0)
                return (null);  // Trying to go outside our context
            int index2 = normalized.lastIndexOf('/', index - 1);
            normalized = normalized.substring(0, index2) +
                    normalized.substring(index + 3);
        }

        // Return the normalized path that we have completed
        return (normalized);
    }

    /**
     * 字符串格式化，
     * {0},{1},{n}作为占位符； 其中数字表示在参数列表中的位置
     *
     * @param source 原字符串
     * @param params 参数列表
     * @return
     */
    public static String format(String source, Object... params) {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                source = source.replaceAll("\\{" + i + "\\}", params[i].toString());
            }
        }
        return source;
    }
}
