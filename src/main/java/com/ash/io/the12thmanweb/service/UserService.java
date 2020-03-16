package com.ash.io.the12thmanweb.service;

import com.ash.io.the12thmanweb.entity.user.User;
import com.ash.io.the12thmanweb.entity.user.UserDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserService extends IService<User> {
    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    User register(User user);

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
     * 收藏文章
     * @param userId
     * @param articleId
     * @return
     */
    boolean collectArticle(Integer userId, Integer articleId);

    /**
     * 取消收藏
     * @param userId
     * @param articleId
     * @return
     */
    boolean cancelCollectArticle(Integer userId, Integer articleId);

    /**
     * 通过用户id查询收藏的文章id
     * @param userId
     * @return
     */
    List<Integer> getArticleIdByUserId(Integer userId);


}
