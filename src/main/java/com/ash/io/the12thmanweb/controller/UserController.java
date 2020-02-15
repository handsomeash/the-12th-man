package com.ash.io.the12thmanweb.controller;

import com.ash.io.the12thmanweb.Utils.MyUtil;
import com.ash.io.the12thmanweb.entity.user.User;
import com.ash.io.the12thmanweb.enums.ResultCode;
import com.ash.io.the12thmanweb.result.Result;
import com.ash.io.the12thmanweb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-16
 */
@Slf4j
@RestController
@RequestMapping("api")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 用户登陆
     *
     * @param map
     * @return
     */
    @PostMapping(value = "/login")
//    public Result login(@RequestBody User user) {
    public Result login(@RequestBody Map<String, Object> map) {

        String username = (String) map.get("username");
        String password = (String) map.get("password");
        boolean rememberMe = (boolean) map.get("rememberme");
        String message;
        // 使用shiro编写认证操作
        // 1.获取subject
        Subject subject = SecurityUtils.getSubject();
        // 2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        if (rememberMe) {
            token.setRememberMe(true);
        }

        // 3.执行登陆认证
        try {
            // 执行subject.login(token);跳到UserRealm里
            subject.login(token);
            message = "登陆成功";
            log.info("login:" + message);
            //返回给前端,存储用户id，用户头像url
            log.info(subject.getPrincipal().toString());
            User data = new User();
            data.setId(((User) subject.getPrincipal()).getId());
            data.setPortraitUrl(((User) subject.getPrincipal()).getPortraitUrl());
            return new Result(ResultCode.SUCCESS.getCode(), message, data);

        } catch (UnknownAccountException e) {
            message = "用户不存在";
            log.info("login:" + message);
            return new Result(ResultCode.FAIL.getCode(), message);

        } catch (IncorrectCredentialsException e) {
            message = "密码错误";
            log.info("login:" + message);
            return new Result(ResultCode.FAIL.getCode(), message);
        }
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @PostMapping(value = "/register")
    public Result register(@RequestBody User user) {
        //判断账号是否被注册
        String username = user.getUsername();
        String password = user.getPassword();
        User find = userService.getByUsername(username);
        String message;
        if (find != null) {
            message = "用户已存在";
            log.info("register:" + message);
            return new Result(ResultCode.FAIL.getCode(), message);
        }
        //判断邮箱是否被注册
        String email = user.getEmail();
        find = userService.getByEmail(email);
        if (find != null) {
            message = "邮箱已被注册";
            log.info("register:" + message);
            return new Result(ResultCode.FAIL.getCode(), message);
        }
        //成功注册，生成加密密码
        String encodedPassword = MyUtil.GeneratePassword(username, password);
        //设置加密后的密码
        user.setPassword(encodedPassword);
        userService.register(user);
        message = "注册成功";
        log.info("register:" + message);
        return new Result(ResultCode.SUCCESS.getCode(), message);
    }

    /**
     * 用户退出登录
     *
     * @return
     */
    @GetMapping("/logout")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        String message = "退出登录";
        log.info("logout:" + message);
        return new Result(ResultCode.SUCCESS.getCode(), message);
    }

    /**
     * 获取用户个人空间信息（包括用户关系表中的数据）
     *
     * @param userId
     * @return
     */
    @GetMapping("/user/{id}")
    public Map<String, Object> getUserHomeInfo(@PathVariable("id") Integer userId) {
        log.info("前往个人空间页面，用户id：" + userId);

        Map<String, Object> map = new HashMap<>();
        User user = userService.getById(userId);
        if (user != null) {
            map.put("user", user);
        }
        return map;
    }

    /**
     * 编辑页面获取用户个人信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/user/edit/{id}")
    public User getUserInfo(@PathVariable("id") Integer userId) {
        log.info("前往编辑页面，用户id：" + userId);
        User user = userService.getById(userId);
        return user;
    }

    /**
     * 用户修改个人信息
     *
     * @param user
     * @return
     */
    @PutMapping("/user/edit")
    public Result editUserInfo(@RequestBody User user) {
        String message;

        User find = userService.getByEmail(user.getEmail());
        //如果新的邮箱在数据库中存在，并且id不是该用户
        if (find != null && find.getId() != user.getId()) {
            message = "邮箱已被注册，修改失败";
            log.info("editUserInfo:" + message);
            return new Result(ResultCode.FAIL.getCode(), message);
        }
        String phone = user.getPhone();
        //先判断传过来的手机号如果是空，则不进行校验
        if (phone != null) {
            User find2 = userService.getByPhone(phone);
            if (find2 != null && find.getId() != user.getId()) {
                message = "手机号已被注册，修改失败";
                log.info("editUserInfo:" + message);
                return new Result(ResultCode.FAIL.getCode(), message);
            }
        }

        Subject subject = SecurityUtils.getSubject();
        log.info("用户修改个人信息的subject：" + subject.getPrincipal().toString());

        //考虑到用户修改头像后，localStorage中的头像未改变，因此要传递其的路径
        String portraitUrl = ((User) subject.getPrincipal()).getPortraitUrl();

        message = "个人信息修改成功";
        log.info("editUserInfo:" + message);
        userService.saveOrUpdate(user);

        if (!user.getPortraitUrl().equals(portraitUrl)) {
            log.info("修改头像，url：" + user.getPortraitUrl());
            User data = new User();
            data.setId(user.getId());
            data.setPortraitUrl(user.getPortraitUrl());
            return new Result(ResultCode.SUCCESS.getCode(), message, data);
        }
        return new Result(ResultCode.SUCCESS.getCode(), message);

    }

    /**
     * 用户修改密码
     *
     * @param map
     * @return
     */
    @PutMapping("/user/edit/password")
    public Result editUserPassword(@RequestBody Map<String, String> map) {
        int userId = Integer.parseInt(map.get("userId"));
        String oldPassword = map.get("oldPassword");
        String newPassword = map.get("newPassword");
        User user = userService.getById(userId);
        String username = user.getUsername();
        String message;
        //如果传递过来的旧密码和数据库的密码不一致
        if (!MyUtil.GeneratePassword(username, oldPassword).equals(user.getPassword())) {
            message = "旧密码不正确，修改失败";
            log.info("editUserPassword:" + message);
            return new Result(ResultCode.FAIL.getCode(), message);
        }
        //如果新密码与旧密码相同
        else if (oldPassword.equals(newPassword)) {
            message = "新密码与旧密码一致，修改失败";
            log.info("editUserPassword:" + message);
            return new Result(ResultCode.FAIL.getCode(), message);
        } else {
            String encodedPassword = MyUtil.GeneratePassword(username, newPassword);
            user.setPassword(encodedPassword);
            userService.saveOrUpdate(user);
            message = "密码修改成功";
            log.info("editUserPassword:" + message);
            return new Result(ResultCode.SUCCESS.getCode(), message);
        }
    }

    /**
     * 用户上传头像
     *
     * @param file
     * @return
     */
    @PostMapping("/user/editPortrait")
    public String editPortrait(MultipartFile file) {
        String folder = "D:/workspace/img";
        File imageFolder = new File(folder);
        File f = new File(imageFolder, MyUtil.getRandomString(5)
                + file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4));
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try {
            file.transferTo(f);
            String imgURL = "http://localhost:8443/api/file/" + f.getName();
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 判断用户是否是通过记住我登陆的，如果是，则将用户的信息发送给前端，便于存储在sessionStorage中
     */
    @GetMapping("/isRememberMe")
    public Result isRememberMe(){
        Subject subject = SecurityUtils.getSubject();
        log.info("isRememberMe：" + subject.isRemembered());
        String message = "通过记住我登陆";
        if(subject.isRemembered()){
            User data = new User();
            data.setId(((User) subject.getPrincipal()).getId());
            data.setPortraitUrl(((User) subject.getPrincipal()).getPortraitUrl());
            return new Result(ResultCode.SUCCESS.getCode(), message, data);
        }
        return null;
    }

//    @CrossOrigin
//    @GetMapping("/authentication")
//    public Result authentication() {
//        Subject subject = SecurityUtils.getSubject();
//        String message;
//        if (subject.getPrincipal() == null) {
//            message = "身份认证失败";
//            log.info(message);
//            return new Result(ResultCode.FAIL.getCode(), message);
//        } else {
//            message = "身份认证成功";
//            log.info(message);
//            return new Result(ResultCode.SUCCESS.getCode(), message);
//        }
//    }
}
