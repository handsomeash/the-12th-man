package com.ash.io.the12thmanweb.service.serviceImpl;

import com.ash.io.the12thmanweb.entity.article.Article;
import com.ash.io.the12thmanweb.entity.article.ArticleNumber;
import com.ash.io.the12thmanweb.enums.CalculationEnums;
import com.ash.io.the12thmanweb.mapper.ArticleNumberMapper;
import com.ash.io.the12thmanweb.service.ArticleNumberService;
import com.ash.io.the12thmanweb.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-03-17
 */
@Slf4j
@Service
public class ArticleNumberServiceImpl extends ServiceImpl<ArticleNumberMapper, ArticleNumber> implements ArticleNumberService {

    @Autowired
    private ArticleNumberMapper articleNumberMapper;
    @Autowired
    private UserService userService;

    @Override
    public ArticleNumber getByArticleId(Integer articleId) {
        QueryWrapper<ArticleNumber> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId);
        return articleNumberMapper.selectOne(wrapper);
    }

    @Override
    public boolean updateCommentNumByArticleId(Integer articleId, CalculationEnums enums) {
        ArticleNumber articleNumber = getByArticleId(articleId);
        if (enums.equals(CalculationEnums.ADD)) {
            articleNumber.setCommentNum(articleNumber.getCommentNum() + 1);
        }else{
            articleNumber.setCommentNum(articleNumber.getCommentNum() - 1);
        }

        int update = articleNumberMapper.updateById(articleNumber);
        return update > 0;
    }

    @Override
    public boolean collectArticle(Integer userId, Integer articleId, CalculationEnums enums) {
        ArticleNumber articleNumber = getByArticleId(articleId);;
        if (enums.equals(CalculationEnums.ADD)) {
            boolean isCollect = userService.collectArticle(userId, articleId);
            //如果用户已经收藏了，返回false
            if (!isCollect) {
                return false;
            }
            articleNumber.setCollectionNum(articleNumber.getCollectionNum() + 1);
        } else {
            userService.cancelCollectArticle(userId, articleId);
            articleNumber.setCollectionNum(articleNumber.getCollectionNum() - 1);
        }
        Integer update = articleNumberMapper.updateById(articleNumber);
        return update > 0;
    }

}
