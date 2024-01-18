package com.example.demo.common.jwt;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;

public class PasswordGenerateUtil {
    public static String getPassword(String username, String password){
        // 与在ShiroConfig设置的一样
        ByteSource salt = ByteSource.Util.bytes(username);
        int hashTimes = 1024;
        Md5Hash md5Hash = new Md5Hash(password, salt, hashTimes);
        return md5Hash.toString();
    }
}
