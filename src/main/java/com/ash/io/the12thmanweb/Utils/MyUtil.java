package com.ash.io.the12thmanweb.Utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * 工具类
 *
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-21
 */
public class MyUtil {
    /**
     * 封装生成加密密码方法
     * @param username
     * @param password
     * @return
     */
    public static String GeneratePassword(String username, String password) {
        // 设置 hash 算法迭代次数
        int hashTimes = 1024;
        //用账号生成盐值，因为账号是唯一的，所以盐值也是唯一的
        ByteSource salt = ByteSource.Util.bytes(username);
        //加密后的密码
        String encodedPassword = new SimpleHash("md5", password, salt, hashTimes).toString();
        return encodedPassword;
    }

    /**
     * 生成指定长度随机字符串，用于上传图片时的命名
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        LocalDateTime time = LocalDateTime.now();
        String localTime = df.format(time);
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        sb.append(localTime);
        return sb.toString();
    }

}
