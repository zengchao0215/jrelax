package com.jrelax.kit.security;

import com.jrelax.kit.Base64Kit;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * DSA工具类
 * Created by zengchao on 2016-12-30.
 * @since 1.8
 */
public class DSAKit extends SecurityKit {
    private final int DEFAULT_LENGTH = 1024;
    private String privateKey = "";
    private String publicKey = "";

    /**
     * 实例化
     *
     * @param length 长度
     */
    public DSAKit(int length) {
        init(length);
    }

    public DSAKit() {
        init(DEFAULT_LENGTH);
    }

    private void init(int length) {
        if (length % 512 > 0) throw new RuntimeException("length 长度必须是64的整数倍");
        super.setAlgorithm("DSA");
        super.setSignAlgorithm("SHA1withDSA");
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(super.getAlgorithm());
            keyPairGenerator.initialize(length);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            DSAPublicKey dsaPublicKey = (DSAPublicKey) keyPair.getPublic();
            DSAPrivateKey dsaPrivateKey = (DSAPrivateKey) keyPair.getPrivate();
            this.publicKey = Base64Kit.encode2(dsaPublicKey.getEncoded());
            this.privateKey = Base64Kit.encode2(dsaPrivateKey.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取私钥
     *
     * @return
     */
    public String getPrivateKey() {
        return privateKey;
    }

    /**
     * 获取公钥
     *
     * @return
     */
    public String getPublicKey() {
        return publicKey;
    }


//    public static void main(String[] args) {
//        DSAKit dsaKit = new DSAKit();
//        String str = "Hello, 中国";
//
//        //验证签名
//        String privateKey = dsaKit.getPrivateKey();
//        String publicKey = dsaKit.getPublicKey();
//        String sign = dsaKit.sign(str, privateKey);
//        System.out.println(sign);
//        System.out.println(dsaKit.verify(str, sign, publicKey));
//    }
}
