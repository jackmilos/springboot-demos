package com.demo.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5加密辅助类
 */
public class MD5Util {
    public static String md5(String plainText){
        byte[] secreBytes = null;
        try{
            secreBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("no md5 algorithm found");
        }
        String md5code = new BigInteger(1, secreBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }
}
