package com.example.myblog.util;

public class MD5Utils {
    // 生成MD5加密后的字符串 (直接拿来用即可)  --实现数据库密码密文保存
    public static String code(String str){
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest = md.digest();
            int i;
            StringBuffer stringBuffer = new StringBuffer("");
            for (int offset = 0; offset < byteDigest.length; offset++){
                i = byteDigest[offset];
                if (i < 0){
                    i += 256;
                }
                if (i < 16){
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(i));
            }
            // 32位加密
            return stringBuffer.toString();
            // 16位加密
            // return stringBuffer.toString().substring(8,24);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
