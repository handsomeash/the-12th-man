package com.ash.io.the12thmanweb.service.serviceImpl;

import com.ash.io.the12thmanweb.entity.article.Article;
import com.ash.io.the12thmanweb.mapper.ArticleMapper;
import com.ash.io.the12thmanweb.service.ArticleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-22
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;

    @Override
    public IPage<Article> getArticles(Integer PageIndex, Integer PageSize) {
        //设置查询条件
        QueryWrapper<Article> wrapper = new QueryWrapper();
        //按时间倒序查询
        wrapper.orderByDesc("create_date");

        //pageIndex:查询第几页,pageSize:查询几条数据
        Page<Article> page = new Page<>(PageIndex, PageSize);
        return articleMapper.selectPage(page,wrapper);
    }
}
