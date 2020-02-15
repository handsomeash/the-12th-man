package com.ash.io.the12thmanweb.service;

import com.ash.io.the12thmanweb.entity.user.User;
import com.ash.io.the12thmanweb.entity.user.UserDetail;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    public boolean register(User user);

    /**
     * 根据账号查询用户
     *
     * @param username
     * @return
     */
    public User getByUsername(String username);

    /**
     * 根据邮箱查询用户
     *
     * @param email
     * @return
     */
    public User getByEmail(String email);

    /**
     * 根据手机查询用户
     *
      * @param phone
     * @return
     */
    public User getByPhone(String phone);

    /**
     * 根据用户id查找用户明细
     *
     * @param userId
     * @return
     */
    public UserDetail getByUserId(Integer userId);
}
