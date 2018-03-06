package com.jrelax.kit.security;

import com.jrelax.kit.Base64Kit;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA工具类
 * 生成公钥、密钥、签名，验证签名
 * Created by zengchao on 2016-12-30.
 * @since 1.8
 */
public class RSAKit extends SecurityKit {
    private final int MAX_ENCRYPT_BLOCK = 117;
    private final int MAX_DECRYPT_BLOCK  = 128;
    private final int DEFAULT_LENGTH = 1024;
    private String privateKey = "";
    private String publicKey = "";

    /**
     * 实例化RSA工具类
     */
    public RSAKit() {
        init(DEFAULT_LENGTH);
    }

    /**
     * 实例化RSA工具类
     * @param length RSA加密长度， 必须是64的整数倍
     */
    public RSAKit(int length) {
        init(length);
    }

    private void init(int length){
        if(length % 512 > 0) throw new RuntimeException("length 长度必须是64的整数倍");
        super.setAlgorithm("RSA");
        super.setSignAlgorithm("MD5withRSA");
        //生成公钥+私钥
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(super.getAlgorithm());
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.setSeed(secureRandom.generateSeed(20));
            keyPairGenerator.initialize(length, secureRandom);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            this.publicKey = Base64Kit.encode2(rsaPublicKey.getEncoded());
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            this.privateKey = Base64Kit.encode2(rsaPrivateKey.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 生成公钥
     *
     * @return
     */
    public String getPublicKey() {
        return this.publicKey;
    }

    /**
     * 生成私钥
     *
     * @return
     */
    public String getPrivateKey() {
        return this.privateKey;
    }


    /**
     * 加密
     * @param data 待加密数据
     * @param privateKey 私钥
     * @return
     */
    public String encode(String data, String privateKey){
        String encodeStr = "";
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64Kit.decode2(privateKey));
            KeyFactory keyFactory = KeyFactory.getInstance(super.getAlgorithm());
            PrivateKey key = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            int inputLen = data.getBytes().length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data.getBytes(), offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data.getBytes(), offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();

            encodeStr = Base64Kit.encode2(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * 解密
     * @param data 加密数据
     * @param publicKey 公钥
     * @return
     */
    public String decode(String data, String publicKey){
        String decodeStr = "";
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64Kit.decode2(publicKey));
            KeyFactory keyFactory = KeyFactory.getInstance(super.getAlgorithm());
            PublicKey key = keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] bData = Base64Kit.decode2(data);
            int inputLen = bData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(bData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(bData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();

            decodeStr = new String(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decodeStr;
    }

//    public static void main(String[] args) {
//        RSAKit rsaKit = new RSAKit(512);
//        String str = "Hello, 中国";
//
//        //加密、解密
//        String e = rsaKit.encode(str, rsaKit.getPrivateKey());
//        String d = rsaKit.decode(e, rsaKit.getPublicKey());
//
//        System.out.println(e);
//        System.out.println(d);
//
//        //验证签名
//        String privateKey = rsaKit.getPrivateKey();
//        String publicKey = rsaKit.getPublicKey();
//        String sign = rsaKit.sign(str, privateKey);
//        System.out.println(sign);
//        System.out.println(rsaKit.verify(str, sign, publicKey));
//    }
}
