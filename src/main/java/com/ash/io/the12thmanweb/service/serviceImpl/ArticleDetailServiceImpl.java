package com.ash.io.the12thmanweb.service.serviceImpl;

import com.ash.io.the12thmanweb.entity.article.Article;
import com.ash.io.the12thmanweb.entity.article.ArticleDetail;
import com.ash.io.the12thmanweb.entity.article.ArticleNumber;
import com.ash.io.the12thmanweb.entity.user.User;
import com.ash.io.the12thmanweb.mapper.ArticleDetailMapper;
import com.ash.io.the12thmanweb.service.ArticleDetailService;
import com.ash.io.the12thmanweb.service.ArticleNumberService;
import com.ash.io.the12thmanweb.service.ArticleService;
import com.ash.io.the12thmanweb.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-03-16
 */
@Slf4j
@Service
public class ArticleDetailServiceImpl extends ServiceImpl<ArticleDetailMapper, ArticleDetail> implements ArticleDetailService {
    @Autowired
    private ArticleDetailMapper articleDetailMapper;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleNumberService articleNumberService;
    @Autowired
    private UserService userService;

    @Override
    @Cacheable(value = "articleDetail", key = "#id")
    public ArticleDetail getArticleDetail(Integer id) {
        log.info("通过数据库查询id为：" + id + "的文章");
        ArticleDetail article = articleDetailMapper.selectById(id);
        return article;
    }

    @Override
    public boolean writeArticle(Integer userId, ArticleDetail articleDetail) {
        User user = userService.getById(userId);
        LocalDate now = LocalDate.now();
        articleDetail.setUserId(userId);
        articleDetail.setCreateDate(now);
        int insert = articleDetailMapper.insert(articleDetail);

        if (insert > 0) {
            Article article = new Article();
            article.setArticleDetailId(articleDetail.getId());
            article.setTitle(articleDetail.getTitle());
            article.setUserId(user.getId());
            article.setCreateDate(now);
            article.setImgUrl(articleDetail.getImgUrl());
            //创建文章表信息
            articleService.save(article);
            ArticleNumber articleNumber = new ArticleNumber();
            articleNumber.setArticleId(article.getId());
            //创建文章评论数和收藏数表信息
            articleNumberService.save(articleNumber);
        }

        return insert > 0;
    }

}
