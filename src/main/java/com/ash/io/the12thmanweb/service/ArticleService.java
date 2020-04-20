package com.ash.io.the12thmanweb.service;

import com.ash.io.the12thmanweb.entity.article.Article;
import com.ash.io.the12thmanweb.enums.ArticleEnums;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-22
 */
public interface ArticleService extends IService<Article> {
    /**
     * 分页查询所有文章
     *
     * @param PageIndex
     * @param PageSize
     * @return
     */
    IPage<Article> getArticles(Integer PageIndex, Integer PageSize);

    /**
     * 按照分类分页查询所有文章
     *
     * @param PageIndex
     * @param PageSize
     * @param type
     * @return
     */
    IPage<Article> getArticlesByType(Integer PageIndex, Integer PageSize, ArticleEnums type);

    /**
     * 按照模糊查询分页查询所有文章
     *
     * @param PageIndex
     * @param PageSize
     * @param keywords
     * @return
     */
    IPage<Article> getArticlesByKeywords(Integer PageIndex, Integer PageSize, String keywords);

    /**
     * 分页查询用户收藏文章
     *
     * @param PageIndex
     * @param PageSize
     * @param userId
     * @return
     */
    IPage<Article> getCollectionArticles(Integer PageIndex, Integer PageSize, Integer userId);

    /**
     * 分页查询用户发表的文章
     *
     * @param PageIndex
     * @param PageSize
     * @param userId
     * @return
     */
    IPage<Article> getWriteArticles(Integer PageIndex, Integer PageSize, Integer userId);

    /**
     * 删除文章（逻辑删除）
     *
     * @param id
     * @return
     */
    boolean deleteArticleById(Integer id);
}
