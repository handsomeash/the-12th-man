package com.ash.io.the12thmanweb.response;

import com.ash.io.the12thmanweb.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 用于返回文章相关的response类
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-03-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDetailResp implements Serializable {
    private static final long serialVersionUID = -7701763123433012614L;

    private Integer id;
    private String title;
    //用户表id
    private Integer userId;
    private String imgUrl;
    private LocalDate createDate;
    //内容
    private String content;
    //收藏数
    private Integer collectionNum;
    //评论数
    private Integer commentNum;
    //作者信息
    private User author;
}
