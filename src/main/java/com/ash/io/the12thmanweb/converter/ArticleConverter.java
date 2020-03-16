package com.ash.io.the12thmanweb.converter;

import com.ash.io.the12thmanweb.entity.article.ArticleDetail;
import com.ash.io.the12thmanweb.entity.user.User;
import com.ash.io.the12thmanweb.response.ArticleDetailResp;

/**
 * comment转换类
 *
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-03-16
 */
public class ArticleConverter {
    public static ArticleDetailResp converter(ArticleDetail articleDetail, User author){
        ArticleDetailResp articleDetailResp = ArticleDetailResp.builder()
                .id(articleDetail.getId())
                .userId(articleDetail.getUserId())
                .title(articleDetail.getTitle())
                .imgUrl(articleDetail.getImgUrl())
                .createDate(articleDetail.getCreateDate())
                .content(articleDetail.getContent())
                .collectionNum(articleDetail.getCollectionNum())
                .commentNum(articleDetail.getCommentNum())
                .author(author)
                .build();
        return articleDetailResp;
    }
}
