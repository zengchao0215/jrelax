package com.jrelax.kit.security;

import com.jrelax.kit.Base64Kit;

import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

/**
 * ECDSA工具类
 * Created by zengchao on 2016-12-30.
 * @since 1.8
 */
public class ECDSAKit extends SecurityKit {
    private final String ALGORITHM = "EC";
    private final String SIGN_ALGORITHM = "SHA1withECDSA";
    private final int DEFAULT_LENGTH = 256;
    private String privateKey = "";
    private String publicKey = "";

    public ECDSAKit(){
        init(DEFAULT_LENGTH);
    }

    public ECDSAKit(int length){
        init(length);
    }

    private void init(int length){
        if(length % 256 > 0) throw new RuntimeException("length 长度必须是64的整数倍");
        super.setAlgorithm("EC");
        super.setSignAlgorithm("SHA1withECDSA");
        //生成公钥+私钥
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(super.getAlgorithm());
            keyPairGenerator.initialize(length);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            ECPublicKey ecPublicKey = (ECPublicKey) keyPair.getPublic();
            ECPrivateKey ecPrivateKey = (ECPrivateKey) keyPair.getPrivate();
            this.publicKey = Base64Kit.encode2(ecPublicKey.getEncoded());
            this.privateKey = Base64Kit.encode2(ecPrivateKey.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取私钥
     * @return
     */
    public String getPrivateKey() {
        return privateKey;
    }

    /**
     * 获取公钥
     * @return
     */
    public String getPublicKey() {
        return publicKey;
    }

//    public static void main(String[] args) {
//        ECDSAKit ecdsaKit = new ECDSAKit();
//        String str = "Hello, 中国";
//
//        //验证签名
//        String privateKey = ecdsaKit.getPrivateKey();
//        String publicKey = ecdsaKit.getPublicKey();
//        String sign = ecdsaKit.sign(str, privateKey);
//        System.out.println(sign);
//        System.out.println(ecdsaKit.verify(str, sign, publicKey));
//    }
}
