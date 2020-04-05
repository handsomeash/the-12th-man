package com.ash.io.the12thmanweb.service;

import com.ash.io.the12thmanweb.entity.user.User;
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
     *
     * @param userId
     * @param articleId
     * @return
     */
    boolean collectArticle(Integer userId, Integer articleId);

    /**
     * 取消收藏
     *
     * @param userId
     * @param articleId
     * @return
     */
    boolean cancelCollectArticle(Integer userId, Integer articleId);

    /**
     * 通过用户id查询收藏的文章id
     *
     * @param userId
     * @return
     */
    List<Integer> getCollectionArticleIdByUserId(Integer userId);

    /**
     * 通过用户id查询发表的文章id
     *
     * @param userId
     * @return
     */
    List<Integer> getWriteArticleIdByUserId(Integer userId);

    /**
     * 用户发表文章时，针对用户相关的操作
     * （创建一条用户发表文章表记录，用户明细表中，发表文章数+1）
     *
     * @param userId
     * @param articleId
     */
    void writeArticle(Integer userId,Integer articleId);
}
