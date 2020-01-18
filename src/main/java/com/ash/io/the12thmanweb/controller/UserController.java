package com.ash.io.the12thmanweb.controller;

import com.ash.io.the12thmanweb.entity.User;
import com.ash.io.the12thmanweb.enums.ResultCode;
import com.ash.io.the12thmanweb.result.Result;
import com.ash.io.the12thmanweb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-16
 */
@Slf4j
@RestController
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 用户登陆
     *
     * @param user
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "api/login")
    public Result login(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String message;
        // 使用shiro编写认证操作
        // 1.获取subject
        Subject subject = SecurityUtils.getSubject();
        // 2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 3.执行登陆认证
        try {
            // 执行subject.login(token);跳到UserRealm里
            subject.login(token);
            message = "登陆成功";
            log.info(message);
            return new Result(ResultCode.SUCCESS.getCode(), message);

        } catch (UnknownAccountException e) {
            message = "用户不存在";
            log.info(message);
            return new Result(ResultCode.FAIL.getCode(), message);

        } catch (IncorrectCredentialsException e) {
            message = "密码错误";
            log.info(message);
            return new Result(ResultCode.FAIL.getCode(), message);
        }
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "api/register")
    public Result register(@RequestBody User user) {
        //判断账号是否被注册
        String username = user.getUsername();
        String password = user.getPassword();
        User find = userService.findByUsername(username);
        String message;
        if (find != null) {
            message = "用户已存在";
            log.info(message);
            return new Result(ResultCode.FAIL.getCode(), message);
        }
        //判断邮箱是否被注册
        String email = user.getEmail();
        find = userService.findByEmail(email);
        if (find != null) {
            message = "邮箱已被注册";
            log.info(message);
            return new Result(ResultCode.FAIL.getCode(), message);
        }

        //成功注册
        // 设置 hash 算法迭代次数
        int hashTimes = 1024;
        //用账号生成盐值，因为账号是唯一的，所以盐值也是唯一的
        ByteSource salt = ByteSource.Util.bytes(user.getUsername());
        //加密后的密码
        String encodedPassword = new SimpleHash("md5", password, salt, hashTimes).toString();
        //设置加密后的密码
        user.setPassword(encodedPassword);
        userService.save(user);
        message = "注册成功";
        log.info(message);
        return new Result(ResultCode.SUCCESS.getCode(), message);
    }

    /**
     * 用户退出登录
     *
     * @return
     */
    @GetMapping("api/logout")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        String message = "退出登录";
        return new Result(ResultCode.SUCCESS.getCode(),message);
    }

}
