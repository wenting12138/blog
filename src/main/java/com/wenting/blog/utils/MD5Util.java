package com.wenting.blog.utils;

import java.security.MessageDigest;

public class MD5Util {

    public static synchronized String code(String string) {

        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(string.getBytes());
            byte[] digest = md.digest();
            int i;
            StringBuffer buffer = new StringBuffer("");
            for (int offset = 0; offset < digest.length; offset ++) {
                i = digest[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16){
                    buffer.append(0);
                }
                buffer.append(Integer.toHexString(i));
            }
            // 32位加密
            return buffer.toString();
            // 16位加密
//            return buffer.toString().substring(8, 24);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }



}
