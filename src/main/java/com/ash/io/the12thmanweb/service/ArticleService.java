package com.ash.io.the12thmanweb.service;

import com.ash.io.the12thmanweb.entity.article.Article;
import com.ash.io.the12thmanweb.entity.article.ArticleDetail;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-22
 */
public interface ArticleService {
    /**
     * 分页查询所有文章
     *
     * @param PageIndex
     * @param PageSize
     * @return
     */
    IPage<Article> getArticles(Integer PageIndex, Integer PageSize);

    /**
     * 写文章
     *
     * @param userId
     * @param articleDetail
     * @return
     */
    boolean writeArticle(Integer userId, ArticleDetail articleDetail);

    /**
     * 获取文章信息
     *
     * @param id
     * @return
     */
    ArticleDetail getArticleDetail(Integer id);

    /**
     * 收藏文章
     *
     * @param userId
     * @param articleId
     * @return
     */
    boolean collectArticle(Integer userId, Integer articleId);
}
