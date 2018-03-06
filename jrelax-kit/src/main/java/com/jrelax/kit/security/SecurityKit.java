package com.jrelax.kit.security;

import com.jrelax.kit.Base64Kit;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 安全相关工具类
 * Created by zengchao on 2016-12-30.
 * @since 1.8
 */
public class SecurityKit {
    private String algorithm = "";
    private String signAlgorithm = "";
    protected SecurityKit(){}

    protected void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    protected void setSignAlgorithm(String signAlgorithm) {
        this.signAlgorithm = signAlgorithm;
    }

    protected String getAlgorithm() {
        return algorithm;
    }

    protected String getSignAlgorithm() {
        return signAlgorithm;
    }

    /**
     * 签名
     * @param str 待签名字符串
     * @param privateKey 私钥
     * @return
     */
    public String sign(String str, String privateKey){
        String sign = "";
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64Kit.decode2(privateKey));
            KeyFactory keyFactory = KeyFactory.getInstance(this.algorithm);
            PrivateKey key = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Signature signature = Signature.getInstance(this.signAlgorithm);
            signature.initSign(key);
            signature.update(str.getBytes());

            sign = Base64Kit.encode2(signature.sign());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sign;
    }

    /**
     * 验证签名
     * @param str 字符串
     * @param sign 签名字符串
     * @param publicKey 公钥
     * @return
     */
    public boolean verify(String str, String sign, String publicKey){
        boolean isVerify = false;
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64Kit.decode2(publicKey));
            KeyFactory keyFactory = KeyFactory.getInstance(this.algorithm);
            PublicKey key = keyFactory.generatePublic(x509EncodedKeySpec);
            Signature signature = Signature.getInstance(this.signAlgorithm);
            signature.initVerify(key);
            signature.update(str.getBytes());
            isVerify = signature.verify(Base64Kit.decode2(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isVerify;
    }
}
