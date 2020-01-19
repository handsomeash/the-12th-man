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
     * 根据账号查询用户
     * @param username
     * @return
     */
    public User findByUsername(String username);

    /**
     * 根据邮箱查询用户
     * @return
     */
    public User findByEmail(String email);

    /**
     * 根据id查询用户
     * @return
     */
    public User findById(Integer id);
}
