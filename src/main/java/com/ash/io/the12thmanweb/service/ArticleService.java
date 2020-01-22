package com.ash.io.the12thmanweb.service;

import com.ash.io.the12thmanweb.entity.article.Article;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-22
 */
public interface ArticleService {
    IPage<Article> getArticles(Integer PageIndex,Integer PageSize);
}
