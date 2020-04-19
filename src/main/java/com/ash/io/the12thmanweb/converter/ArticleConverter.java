package com.ash.io.the12thmanweb.converter;

import com.ash.io.the12thmanweb.entity.article.Article;
import com.ash.io.the12thmanweb.entity.article.ArticleDetail;
import com.ash.io.the12thmanweb.entity.article.ArticleNumber;
import com.ash.io.the12thmanweb.entity.user.User;
import com.ash.io.the12thmanweb.response.ArticleDetailResp;
import com.ash.io.the12thmanweb.response.ArticleResp;

/**
 * comment转换类
 *
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-03-16
 */
public class ArticleConverter {
    public static ArticleDetailResp converter(ArticleDetail articleDetail, User author) {
        ArticleDetailResp articleDetailResp = ArticleDetailResp.builder()
                .id(articleDetail.getId())
                .userId(articleDetail.getUserId())
                .title(articleDetail.getTitle())
                .imgUrl(articleDetail.getImgUrl())
                .createDate(articleDetail.getCreateDate())
                .content(articleDetail.getContent())
                .author(author)
                .build();
        return articleDetailResp;
    }

    public static ArticleResp converter(Article article, ArticleNumber number, User author) {
        ArticleResp articleResp = ArticleResp.builder()
                .id(article.getId())
                .articleDetailId(article.getArticleDetailId())
                .imgUrl(article.getImgUrl())
                .title(article.getTitle())
                .createDate(article.getCreateDate())
                .userId(article.getUserId())
                .articleType(article.getArticleType())
                .author(author)
                .collectionNum(number.getCollectionNum())
                .commentNum(number.getCommentNum())
                .build();
        return articleResp;
    }
}
