package com.ash.io.the12thmanweb.service;

import com.ash.io.the12thmanweb.entity.comment.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-03-14
 */
public interface CommentService extends IService<Comment> {
    List<Comment> getByArticleId(Integer articleId);
}
