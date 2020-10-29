package com.leyou.utils;


import com.leyou.entity.UserInfo;
import io.jsonwebtoken.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class RsaUtilsTest {

    private String publicKeyPath = "D://key/publickey.rsa";
    private String privateKeyPath = "D://key/privatekey.rsa";

    private PublicKey publicKey;
    private PrivateKey privateKey;

    private static String token;

    @Test
    public  void  test1(){
            //1.生成公私密钥
        try {
            RsaUtils.generateKey(publicKeyPath,privateKeyPath,"1234");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
     public  void   test2_1(){
        PublicKey publicKey = RsaUtils.getPublicKey(publicKeyPath);
        System.out.println(publicKey);
    }
    @Test
    public  void   test2_2(){
        PrivateKey privateKey = RsaUtils.getPrivateKey(privateKeyPath);
        System.out.println(privateKey);
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(publicKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(privateKeyPath);
    }
    //生成token   私钥加密
    @Test
    public  void  generateToken() throws Exception {
        String qlbToken = JwtUtils.generateToken(new UserInfo(1L, "qianlibo"), privateKey, 1000);
        token = qlbToken;
        System.out.println(qlbToken);

    }
    //私钥加密 -》公钥解密
    @Test
    public  void  parseToken() throws Exception {
        System.out.println(token);
        UserInfo infoFromToken = JwtUtils.getInfoFromToken("eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJxaWFubGlibyIsImV4cCI6MTYwMTQyMzcwNX0.lvCdY3jC2hFaN8kD8JiFu3ewpzv1t6TJFMrFOr_bHQSeH3oSLG71cgXh3diaEOIQ_1IJn91uEAnAcIYa1N9Z8i-IiKJa48Ys_KmeRaB2V8dA5ztYIlpFpDfBhbORCBCPKTC0NnXDE2ZghSsdF8ZRa4io4slp25COK3aJM7WUqjc", publicKey);
        System.out.println("  id    :  "+infoFromToken.getId());
        System.out.println("username:  "+infoFromToken.getUsername());

    }
    //私钥加密 ->私钥解密
    @Test
    public  void  parseTokenbyprivateKey() throws Exception {
        JwtParser jwtParser = Jwts.parser().setSigningKey(privateKey);
        Jws<Claims> token_ = jwtParser.parseClaimsJws("eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJxaWFubGlibyIsImV4cCI6MTYwMTQyMzcwNX0.lvCdY3jC2hFaN8kD8JiFu3ewpzv1t6TJFMrFOr_bHQSeH3oSLG71cgXh3diaEOIQ_1IJn91uEAnAcIYa1N9Z8i-IiKJa48Ys_KmeRaB2V8dA5ztYIlpFpDfBhbORCBCPKTC0NnXDE2ZghSsdF8ZRa4io4slp25COK3aJM7WUqjc");
        Claims body = token_.getBody();
        UserInfo userInfo = new UserInfo(ObjectUtils.toLong(body.get(JwtConstants.JWT_KEY_ID)),ObjectUtils.toString(JwtConstants.JWT_KEY_USER_NAME));

        System.out.println("  id    :  "+userInfo.getId());
        System.out.println("username:  "+userInfo.getUsername());

    }
    //公钥加密   jwt 不能使用rsa公钥加密
    //EXception  :   RSA signatures must be computed using an RSA PrivateKey
    @Test
    public  void generateTokenByPublicKey() throws Exception {
        String token = JwtUtils.generateTokenByPublicKey(new UserInfo(2L, "zs"), publicKeyPath, 1000);
        System.out.println("token: "+token);
    }



}