package com.jrelax.kit.security;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import org.apache.commons.codec.binary.Base64;

public class DecKit {

    /**
     * 使用DES加密与解密,可对byte[],String类型进行加密与解密 密文可使用String,byte[]存储.
     * <p>
     * 方法: void getKey(String strKey)从strKey的字条生成一个Key
     * <p>
     * String getEncString(String strMing)对strMing进行加密,返回String密文 String
     * getDesString(String strMi)对strMin进行解密,返回String明文
     * <p>
     * byte[] getEncCode(byte[] byteS)byte[]型的加密 byte[] getDesCode(byte[]
     * byteD)byte[]型的解密
     */

    Key key;

    /**
     * 根据参数生成KEY
     *
     * @param strKey
     */
    public void genKey(String strKey) {
        try {
            KeyGenerator _generator = KeyGenerator.getInstance("DES");
            SecureRandom secure = SecureRandom.getInstance("SHA1PRNG");
            secure.setSeed(strKey.getBytes("UTF-8"));
            _generator.init(secure);
            this.key = _generator.generateKey();
            _generator = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密String明文输入,String密文输出
     *
     * @param strMing
     * @return
     */
    public String getEncString(String strMing) {
        byte[] byteMi = null;
        byte[] byteMing = null;
        String strMi = "";
        Base64 base64 = new Base64();
        try {
            byteMing = strMing.getBytes("UTF8");
            byteMi = this.getEncCode(byteMing);
            strMi = base64.encodeAsString(byteMi);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            base64 = null;
            byteMing = null;
            byteMi = null;
        }
        return strMi;
    }

    /**
     * 解密 以String密文输入,String明文输出
     *
     * @param strMi
     * @return
     */
    public String getDesString(String strMi) {
        Base64 base64 = new Base64();
        byte[] byteMing = null;
        byte[] byteMi = null;
        String strMing = "";
        try {
            byteMi = base64.decode(strMi);
            byteMing = this.getDesCode(byteMi);
            strMing = new String(byteMing, "UTF8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            base64 = null;
            byteMing = null;
            byteMi = null;
        }
        return strMing;
    }

    /**
     * 加密以byte[]明文输入,byte[]密文输出
     *
     * @param byteS
     * @return
     */
    private byte[] getEncCode(byte[] byteS) {
        byte[] byteFina = null;
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byteFina = cipher.doFinal(byteS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    /**
     * 解密以byte[]密文输入,以byte[]明文输出
     *
     * @param byteD
     * @return
     */
    private byte[] getDesCode(byte[] byteD) {
        Cipher cipher;
        byte[] byteFina = null;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byteFina = cipher.doFinal(byteD);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;

    }
}
