package com.ash.io.the12thmanweb.service.serviceImpl;

import com.ash.io.the12thmanweb.entity.article.Article;
import com.ash.io.the12thmanweb.enums.ArticleEnums;
import com.ash.io.the12thmanweb.mapper.ArticleMapper;
import com.ash.io.the12thmanweb.service.ArticleService;
import com.ash.io.the12thmanweb.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-22
 */
@Slf4j
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserService userService;

    @Override
    @Cacheable(value = "articles", key = "#PageIndex")
    public IPage<Article> getArticles(Integer PageIndex, Integer PageSize) {
        log.info("通过数据库查询所有文章");
        //设置查询条件
        QueryWrapper<Article> wrapper = new QueryWrapper();
        //按时间倒序查询
        wrapper.orderByDesc("create_date");

        //pageIndex:查询第几页,pageSize:查询几条数据
        Page<Article> page = new Page<>(PageIndex, PageSize);
        return articleMapper.selectPage(page, wrapper);
    }

    @Override
    public IPage<Article> getArticlesByType(Integer PageIndex, Integer PageSize, ArticleEnums type) {
        //设置查询条件
        QueryWrapper<Article> wrapper = new QueryWrapper();

        //按时间倒序查询
        wrapper.orderByDesc("create_date");
        wrapper.eq("article_type", type);
        //pageIndex:查询第几页,pageSize:查询几条数据
        Page<Article> page = new Page<>(PageIndex, PageSize);
        return articleMapper.selectPage(page, wrapper);
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
