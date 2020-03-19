package com.ash.io.the12thmanweb.service;

import com.ash.io.the12thmanweb.entity.article.Article;
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
     * 分页查询用户收藏文章
     *
     * @param PageIndex
     * @param PageSize
     * @param userId
     * @return
     */
    IPage<Article> getCollectionArticles(Integer PageIndex, Integer PageSize, Integer userId);

}
