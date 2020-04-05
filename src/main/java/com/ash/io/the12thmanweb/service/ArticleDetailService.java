package com.ash.io.the12thmanweb.service;

import com.ash.io.the12thmanweb.entity.article.ArticleDetail;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-03-16
 */
public interface ArticleDetailService extends IService<ArticleDetail> {
    /**
     * 根据id获取文章明细
     *
     * @param id
     * @return
     */
    ArticleDetail getArticleDetail(Integer id);

    /**
     * 写文章
     *
     * @param userId
     * @param articleDetail
     * @return
     */
    boolean writeArticle(Integer userId, ArticleDetail articleDetail);

    /**
     * 编辑文章
     *
     * @param id
     * @param articleDetail
     * @return
     */
    ArticleDetail editArticle(Integer id, ArticleDetail articleDetail);
}
