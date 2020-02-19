package com.ash.io.the12thmanweb.service.serviceImpl;

import com.ash.io.the12thmanweb.entity.article.Article;
import com.ash.io.the12thmanweb.entity.article.ArticleDetail;
import com.ash.io.the12thmanweb.entity.user.User;
import com.ash.io.the12thmanweb.entity.user.UserCollection;
import com.ash.io.the12thmanweb.mapper.ArticleDetailMapper;
import com.ash.io.the12thmanweb.mapper.ArticleMapper;
import com.ash.io.the12thmanweb.service.ArticleService;
import com.ash.io.the12thmanweb.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-22
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ArticleDetailMapper articleDetailMapper;

    @Autowired
    UserService userService;

    @Override
    public IPage<Article> getArticles(Integer PageIndex, Integer PageSize) {
        //设置查询条件
        QueryWrapper<Article> wrapper = new QueryWrapper();
        //按时间倒序查询
        wrapper.orderByDesc("create_date");

        //pageIndex:查询第几页,pageSize:查询几条数据
        Page<Article> page = new Page<>(PageIndex, PageSize);
        return articleMapper.selectPage(page, wrapper);
    }

    @Override
    public boolean writeArticle(Integer userId, ArticleDetail articleDetail) {
        User user = userService.getById(userId);
        LocalDate now = LocalDate.now();
        articleDetail.setUserId(user.getId());
        articleDetail.setCreateDate(now);
        int insert = articleDetailMapper.insert(articleDetail);
        if (insert > 0) {
            Article article = new Article();
            article.setArticleDetailId(articleDetail.getId());
            article.setTitle(articleDetail.getTitle());
            article.setUserId(user.getId());
            article.setAuthor(user.getNickname());
            article.setCreateDate(now);
            article.setImgUrl(articleDetail.getImgUrl());
            articleMapper.insert(article);
        }

        return insert > 0;
    }

    @Override
    public ArticleDetail getArticleDetail(Integer id) {
        ArticleDetail article = articleDetailMapper.selectById(id);
        return article;
    }

    @Override
    public boolean collectArticle(Integer userId, Integer articleId) {
        boolean isCollect = userService.collectArticle(userId, articleId);

        if (isCollect == false) {
            return false;
        }

        //文章表收藏数字段+1
        Article article = articleMapper.selectById(articleId);
        article.setCollectionNum(article.getCollectionNum() + 1);
        UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", articleId);
        int update = articleMapper.update(article, updateWrapper);

//
//        ArticleDetail article = articleDetailMapper.selectById(articleId);
//        article.setCollectionNum(article.getCollectionNum() + 1);
//        UpdateWrapper<ArticleDetail> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.eq("id",articleId);
//        int update = articleDetailMapper.update(article, updateWrapper);

        return update > 0;
    }

    @Override
    public boolean cancelCollectArticle(Integer userId, Integer articleId) {
        userService.cancelCollectArticle(userId, articleId);

        //文章表收藏数字段-1
        Article article = articleMapper.selectById(articleId);
        article.setCollectionNum(article.getCollectionNum() - 1);
        UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", articleId);
        int update = articleMapper.update(article, updateWrapper);
        return update > 0;
    }

    @Override
    public IPage<Article> getCollectionArticles(Integer PageIndex, Integer PageSize, Integer userId) {

        List<Integer> articleId = userService.getArticleIdByUserId(userId);
        //如果用户没有收藏过文章
        if (articleId.size() == 0) {
            return null;
        }
        //设置查询条件
        QueryWrapper<Article> wrapper = new QueryWrapper();
        //按时间倒序查询，通过文章id批量查询用户收藏的文章
        wrapper.orderByDesc("create_date").in("id", articleId);

        //pageIndex:查询第几页,pageSize:查询几条数据
        Page<Article> page = new Page<>(PageIndex, PageSize);
        return articleMapper.selectPage(page, wrapper);
    }
}
