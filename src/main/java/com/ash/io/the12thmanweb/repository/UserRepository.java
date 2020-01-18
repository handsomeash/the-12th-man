package com.ash.io.the12thmanweb.repository;

import com.ash.io.the12thmanweb.entity.User;
import com.ash.io.the12thmanweb.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-16
 */
@Repository
public class UserRepository {
    @Autowired
    UserMapper userMapper;

    public int save(User user){
        LocalDate now = LocalDate.now();
        user.setRegisterDate(now);
        return userMapper.insert(user);
    }
    
    /**
     * 根据账号，密码查询用户
     * @param username
     * @param password
     * @return
     */
    public User selectByUsernameAndPassword(String username,String password){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        //如果密码不为空，则加入密码查询条件
        if(password != null){
            wrapper.eq("password",password);
        }
        User user = userMapper.selectOne(wrapper);
        return user;
    }

    /**
     * 根据邮箱查询用户
     * @param email
     * @return
     */
    public User selectByEmail(String email){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        User user = userMapper.selectOne(wrapper);
        return user;
    }
}
