package com.jrelax.kit;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 数字处理
 */
public class NumberKit {
    private final static String NUMERALS = "0123456789";
    private final static String LETTERS_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private final static String LETTERS_UPPERCASE = LETTERS_LOWERCASE.toUpperCase();
    private final static Map<Integer, String> KNOWN_ALPHABETS = new HashMap<>();

    static {
        for (int i = 2; i <= 10; i++) {
            KNOWN_ALPHABETS.put(i, NUMERALS.substring(0, i));
        }

        for (int i = 11; i <= 16; i++) {
            KNOWN_ALPHABETS.put(i, NUMERALS + LETTERS_LOWERCASE.substring(0, i - 10));
        }

        KNOWN_ALPHABETS.put(26, LETTERS_LOWERCASE);
        KNOWN_ALPHABETS.put(32, NUMERALS + LETTERS_UPPERCASE.replaceAll("[ILOU]", ""));
        KNOWN_ALPHABETS.put(36, NUMERALS + LETTERS_LOWERCASE);
        KNOWN_ALPHABETS.put(52, LETTERS_LOWERCASE + LETTERS_UPPERCASE);
        KNOWN_ALPHABETS.put(58, (NUMERALS + LETTERS_LOWERCASE + LETTERS_UPPERCASE).replaceAll("[0OlI]", ""));
        KNOWN_ALPHABETS.put(62, NUMERALS + LETTERS_LOWERCASE + LETTERS_UPPERCASE);
        KNOWN_ALPHABETS.put(64, LETTERS_UPPERCASE + LETTERS_LOWERCASE + NUMERALS + "+/");
    }

    private static String toBase(String str, int shift) {
        String alphabet = KNOWN_ALPHABETS.get(shift);
        int len = alphabet.length();
        Stack<Double> digits = new Stack<>();

        double num = Double.parseDouble(str);
        do {
            digits.add(num % len);
            num = Math.floor(num / len);
        } while (num > 0);

        StringBuilder sb = new StringBuilder();
        while (!digits.empty()) {
            sb.append(alphabet.charAt(digits.pop().intValue()));
        }
        return sb.toString();
    }

    private static String fromBase(String str, int shift) {
        String alphabet = KNOWN_ALPHABETS.get(shift);
        int len = alphabet.length();
        double pos = 0, num = 0;
        char c;
        boolean isUpper;
        while (str.length() > 0) {
            c = str.charAt(str.length() - 1);
            String s = String.valueOf(c);
            str = str.substring(0, str.length() - 1);
            isUpper = c >= 65 && c <= 90;
            if (alphabet.indexOf(c) == -1) {
                if (isUpper) {
                    s = s.toLowerCase();
                } else {
                    s = s.toUpperCase();
                }
            }
            num += Math.pow(len, pos) * alphabet.indexOf(s);
            pos++;
        }
        return num + "";
    }

    /**
     * 进制转换
     * 进制范围：1～64
     *
     * @param str     待转字符串
     * @param fromHex 原进制
     * @param toHex   目标进制
     * @return
     */
    public static String toHex(String str, int fromHex, int toHex) {
        return toBase(fromBase(str, fromHex), toHex);
    }

    /**
     * 十进制转二进制
     *
     * @param str
     * @return
     */
    public static String toHex2(String str) {
        return toHex(str, 10, 2);
    }

    /**
     * 十进制转8进制
     *
     * @param str
     * @return
     */
    public static String toHex8(String str) {
        return toHex(str, 10, 8);
    }

    /**
     * 十进制转16进制
     *
     * @param str
     * @return
     */
    public static String toHex16(String str) {
        return toHex(str, 10, 16);
    }

    /**
     * 十进制转32进制
     *
     * @param str
     * @return
     */
    public static String toHex32(String str) {
        return toHex(str, 10, 32);
    }

    /**
     * 十进制转36进制
     *
     * @param str
     * @return
     */
    public static String toHex36(String str) {
        return toHex(str, 10, 36);
    }

    /**
     * 十进制转58进制
     *
     * @param str
     * @return
     */
    public static String toHex58(String str) {
        return toHex(str, 10, 58);
    }

    /**
     * 十进制转62进制
     *
     * @param str
     * @return
     */
    public static String toHex62(String str) {
        return toHex(str, 10, 62);
    }

    /**
     * 十进制转64进制
     *
     * @param str
     * @return
     */
    public static String toHex64(String str) {
        return toHex(str, 10, 64);
    }
}
