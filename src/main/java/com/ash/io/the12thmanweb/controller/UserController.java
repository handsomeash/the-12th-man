package com.ash.io.the12thmanweb.controller;

import com.ash.io.the12thmanweb.entity.User;
import com.ash.io.the12thmanweb.enums.ResultCode;
import com.ash.io.the12thmanweb.result.Result;
import com.ash.io.the12thmanweb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
     * @param user
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "api/login")
    public Result login(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        User find = userService.findByUsernameAndPassword(username, password);
        String message;
        if (find == null) {
            message = "账号密码错误";
            log.info(message);
            return new Result(ResultCode.FAIL.getCode(), message);
        } else {
            message = "登陆成功";
            log.info(message);
            return new Result(ResultCode.SUCCESS.getCode(), message);
        }

    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "api/register")
    public Result register(@RequestBody User user){
        //判断账号是否被注册
        String username = user.getUsername();
        User find = userService.findByUsernameAndPassword(username,null);
        String message;
        if(find != null){
            message = "用户已存在";
            log.info(message);
            return new Result(ResultCode.FAIL.getCode(), message);
        }
        //判断邮箱是否被注册
        String email = user.getEmail();
        find = userService.findByEmail(email);
        if(find != null){
            message = "邮箱已被注册";
            log.info(message);
            return new Result(ResultCode.FAIL.getCode(), message);
        }
        //注册成功
        userService.save(user);
        message = "注册成功";
        log.info(message);
        return new Result(ResultCode.SUCCESS.getCode(), message);
    }

}
