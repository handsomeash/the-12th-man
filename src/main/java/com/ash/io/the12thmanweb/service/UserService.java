package com.ash.io.the12thmanweb.service;

import com.ash.io.the12thmanweb.entity.User;

public interface UserService {
    /**
     * 注册用户
     * @param user
     * @return
     */
    public int save(User user);

    /**
     * 根据账号密码查询用户
     * @param username
     * @param password
     * @return
     */
    public User findByUsernameAndPassword(String username,String password);

    /**
     * 根据邮箱查询用户
     * @return
     */
    public User findByEmail(String email);
}
