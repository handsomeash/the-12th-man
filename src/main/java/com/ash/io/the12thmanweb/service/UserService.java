package com.ash.io.the12thmanweb.service;

import com.ash.io.the12thmanweb.entity.article.Article;
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
    boolean register(User user);

    /**
     * 根据账号查询用户
     *
     * @param username
     * @return
     */
    User getByUsername(String username);

    /**
     * 根据邮箱查询用户
     *
     * @param email
     * @return
     */
    User getByEmail(String email);

    /**
     * 根据手机查询用户
     *
     * @param phone
     * @return
     */
    User getByPhone(String phone);

    /**
     * 根据用户id查找用户明细
     *
     * @param userId
     * @return
     */
    UserDetail getDetailByUserId(Integer userId);

    /**
     * 收藏文章
     * @param userId
     * @param articleId
     * @return
     */
    boolean collectArticle(Integer userId, Integer articleId);
}
