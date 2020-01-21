package com.ash.io.the12thmanweb.Utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 工具类，封装生成加密密码方法
 *
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-21
 */
public class GeneratePasswordUtil {

    public static String GeneratePassword(String username, String password) {
        // 设置 hash 算法迭代次数
        int hashTimes = 1024;
        //用账号生成盐值，因为账号是唯一的，所以盐值也是唯一的
        ByteSource salt = ByteSource.Util.bytes(username);
        //加密后的密码
        String encodedPassword = new SimpleHash("md5", password, salt, hashTimes).toString();
        return encodedPassword;
    }
}
