package com.ash.io.the12thmanweb.service;

import com.ash.io.the12thmanweb.entity.article.ArticleNumber;
import com.ash.io.the12thmanweb.enums.CalculationEnums;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-03-17
 */
public interface ArticleNumberService extends IService<ArticleNumber> {
    /**
     * 根据文章id查询文章评论数和收藏数
     *
     * @param articleId
     * @return
     */
    ArticleNumber getByArticleId(Integer articleId);

    /**
     * 根据文章id更新评论数量
     *
     * @param articleId
     * @return
     */
    boolean updateCommentNumByArticleId(Integer articleId);

    /**
     * 用户收藏文章或取消收藏文章
     *
     * @param userId
     * @param articleId
     * @param enums
     * @return
     */
    boolean collectArticle(Integer userId, Integer articleId, CalculationEnums enums);

}
