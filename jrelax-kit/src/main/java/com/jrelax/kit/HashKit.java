package com.jrelax.kit;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 生成消息摘要
 *
 * @author zengchao
 */
public class HashKit {
    private static final char[] LETTERS = "0123456789ABCDEF".toCharArray();

    /**
     * 获取MD5
     * @param src
     * @return
     */
    public final static String md5(String src) {
        try {
            return hash(MessageDigest.getInstance("MD5"), src);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取sha-1
     * @param src
     * @return
     */
    public final static String sha1(String src) {
        try {
            return hash(MessageDigest.getInstance("SHA-1"), src);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取sha-265
     * @param src
     * @return
     */
    public final static String sha265(String src) {
        try {
            return hash(MessageDigest.getInstance("SHA-265"), src);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取sha-348
     * @param src
     * @return
     */
    public final static String sha384(String src) {
        try {
            return hash(MessageDigest.getInstance("SHA-384"), src);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取sha-521
     * @param src
     * @return
     */
    public final static String sha512(String src) {
        try {
            return hash(MessageDigest.getInstance("SHA-512"), src);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param digest 消息摘要类型
     * @param src 字符串
     * @return
     */
    public final static String hash(MessageDigest digest, String src) {
        try {
            String hex = toHexString(digest.digest(src.getBytes("UTF-8")));
            return hex;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    private final static String toHexString(byte[] bytes) {
        char[] values = new char[bytes.length * 2];
        int i = 0;
        for (byte b : bytes) {
            values[i++] = LETTERS[((b & 0xF0) >>> 4)];
            values[i++] = LETTERS[b & 0xF];
        }
        return String.valueOf(values);
    }

}
