package com.ash.io.the12thmanweb.service;

import com.ash.io.the12thmanweb.entity.user.UserDetail;
import com.ash.io.the12thmanweb.enums.CalculationEnums;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-03-16
 */
public interface UserDetailService extends IService<UserDetail> {
    /**
     * 根据用户id查找用户明细
     *
     * @param userId
     * @return
     */
    UserDetail getDetailByUserId(Integer userId);

    /**
     * 用户明细表收藏或取消收藏文章
     *
     * @param userId
     * @return
     */
    UserDetail changeCollectionNum(Integer userId, CalculationEnums enums);

    /**
     * 用户明细表发表文章
     *
     * @param userId
     * @return
     */
    UserDetail writeArticle(Integer userId);
}
