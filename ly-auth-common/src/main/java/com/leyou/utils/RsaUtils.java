package com.leyou.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RsaUtils {
    /**
     * 从文件中读取公钥
     */
    public  static PublicKey getPublicKey(String fileName){
      byte[] bytes = readFile(fileName);
      return  getPublicKey(bytes);
    }
    /**
     * 从文件中读取密钥
     *
     * @param filename 私钥保存路径，相对于classpath
     * @return 私钥对象
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String filename)  {
        byte[] bytes = readFile(filename);


        return getPrivateKey(bytes) ;
    }

    /**
     * 读取文件字节
     * @return
     */
    public  static byte[] readFile(String filename){
        try {
            return Files.readAllBytes(new File(filename).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("读取文件失败返回空！");

    }
    /** 获取公钥 字节形式获取
     *
     */
    public  static PublicKey getPublicKey(byte[] bytes)  {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(bytes);
        KeyFactory rsa = null;
        PublicKey publicKey =null;
        try {
            rsa = KeyFactory.getInstance("RSA");
            publicKey = rsa.generatePublic(x509EncodedKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return publicKey;
    }

    /**
     *  获取私人钥 字节形式获取
     */
    public  static PrivateKey getPrivateKey(byte[] bytes){
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory rsa = null;
        PrivateKey privateKey = null;
        try{
         rsa = KeyFactory.getInstance("RSA");
         privateKey =  rsa.generatePrivate(spec);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  privateKey;
    }

    /**
     * 根据密文 生成rsa公私钥  并且写入 指定文件
     */
    public static  void  generateKey(String publicKeyFilename, String privateKeyFilename, String secret) throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator ras = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(secret.getBytes());
        ras.initialize(1024,secureRandom);
        KeyPair keyPair = ras.genKeyPair();
        // 获取公钥并写出
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        writeFile(publicKeyFilename, publicKeyBytes);
        // 获取私钥并写出
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        writeFile(privateKeyFilename, privateKeyBytes);
    }

    private static void writeFile(String destPath, byte[] bytes) throws IOException {
        File dest = new File(destPath);
        if (!dest.exists()) {
            dest.createNewFile();
        }
        Files.write(dest.toPath(), bytes);
    }
}
