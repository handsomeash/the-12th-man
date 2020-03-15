package com.ash.io.the12thmanweb.converter;

import com.ash.io.the12thmanweb.entity.comment.Comment;
import com.ash.io.the12thmanweb.entity.user.User;
import com.ash.io.the12thmanweb.response.CommentResp;

/**
 * comment转换类
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-03-15
 */
public class CommentConverter {
    public static CommentResp converter(Comment comment, User user){
        CommentResp commentResp = CommentResp.builder()
                .id(comment.getId())
                .articleId(comment.getArticleId())
                .content(comment.getContent())
                .createDate(comment.getCreateDate())
                .userId(comment.getUserId())
                .user(user)
                .build();
        return commentResp;
    }
}
