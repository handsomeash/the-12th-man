package com.ash.io.the12thmanweb.service;

import com.ash.io.the12thmanweb.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    /**
     * 注册用户
     * @param user
     * @return
     */
    public boolean register(User user);

    /**
     * 根据账号查询用户
     * @param username
     * @return
     */
    public User getByUsername(String username);

    /**
     * 根据邮箱查询用户
     * @return
     */
    public User getByEmail(String email);


}
