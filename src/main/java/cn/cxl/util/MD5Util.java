package cn.cxl.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 对密码进行MD5加密
 */
public class MD5Util {

    public static String getPwd(String pwd){
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(pwd.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
