package com.ch.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//Util为工具类
//本方法是前端密码加密的工具，将密码转换为MD5码存储在数据库中
public class MD5Util {
    public static  String code(String str){
        try{
            MessageDigest md=MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest=md.digest();
            int i;
            StringBuffer buf=new StringBuffer("");
            for (int offset=0;offset<byteDigest.length;offset++){
                i=byteDigest[offset];
                if(i<0)
                    i+=256;

                if (i<16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));

            }
//            32位加密
            return buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
