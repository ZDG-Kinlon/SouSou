package com.sousou.client;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 字符串加密算法
 */
public class Hash {
    /**
     * @param str
     * @param lx, 算法（如 DSA、RSA、MD5 或 SHA-1）。
     * @return
     * @throws UnsupportedEncodingException
     * @desc 计算字符串的md5 or sha1
     */
    public static String sha1(String str) {
        try {
            return object(str, "sha1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "-1";
        }
    }

    private static String object(String str, String lx)
            throws UnsupportedEncodingException {
        try {
            MessageDigest md = MessageDigest.getInstance(lx);
            byte[] inputByteArr = str.getBytes("UTF-8");
            md.update(inputByteArr);
            byte[] rsByteArr = md.digest();
            return byteArrayToHex(rsByteArr);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param arr
     * @return
     * @desc 将生成的字节数组转化为十六进制字符串
     */
    private static String byteArrayToHex(byte[] arr) {
        String hexStr = "0123456789ABCDEF";
        StringBuilder rslt = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            rslt.append(String.valueOf(hexStr.charAt((arr[i] & 0xf0) >> 4)));
            rslt.append(String.valueOf(hexStr.charAt(arr[i] & 0x0f)));
        }
        return rslt.toString();
    }


}
