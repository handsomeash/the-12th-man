package com.ash.io.the12thmanweb.response;

import com.ash.io.the12thmanweb.entity.article.Article;
import com.ash.io.the12thmanweb.entity.user.User;
import com.ash.io.the12thmanweb.enums.ArticleEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 用于返回文章相关的response类
 *
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-03-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleResp extends Article implements Serializable {
    private static final long serialVersionUID = -8356219651964695627L;

    private Integer id;
    private String title;
    //用户表id
    private Integer userId;
    private String imgUrl;
    private LocalDate createDate;
    private Integer articleDetailId;
    private User author;
    private ArticleEnums articleType;
    //收藏数
    private Integer collectionNum;
    //评论数
    private Integer commentNum;
}
