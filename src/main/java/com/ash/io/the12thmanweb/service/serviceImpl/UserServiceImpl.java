package com.ash.io.the12thmanweb.service.serviceImpl;

import com.ash.io.the12thmanweb.entity.user.User;
import com.ash.io.the12thmanweb.entity.user.UserDetail;
import com.ash.io.the12thmanweb.mapper.UserDetailMapper;
import com.ash.io.the12thmanweb.mapper.UserMapper;
import com.ash.io.the12thmanweb.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-16
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserDetailMapper userDetailMapper;

    @Override
    public boolean register(User user) {
        LocalDate now = LocalDate.now();
        //设置创建时间
        user.setRegisterDate(now);
        //设置默认头像路径
        user.setPortraitUrl("../../../static/img/touxiang.png");
        //插入用户数据
        int result = userMapper.insert(user);
        if (result > 0) {
            //创建用户明细表数据
            UserDetail userDetail = new UserDetail();
            //设置用户id
            userDetail.setUserId(user.getId());
            userDetailMapper.insert(userDetail);
        }
        return result > 0;
    }

    @Override
    public User getByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = userMapper.selectOne(wrapper);
        return user;
    }

    @Override
    public User getByEmail(String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        User user = userMapper.selectOne(wrapper);
        return user;
    }

    @Override
    public User getByPhone(String phone) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        User user = userMapper.selectOne(wrapper);
        return user;
    }


}
