package com.ash.io.the12thmanweb.service;

import com.ash.io.the12thmanweb.entity.comment.Comment;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-03-14
 */
public interface CommentService extends IService<Comment> {
    /**
     * 根据文章id查找评论
     *
     * @param articleId
     * @return
     */
    List<Comment> getByArticleId(Integer articleId);

    /**
     * 分页查询所有评论
     *
     * @param PageIndex
     * @param PageSize
     * @return
     */
    IPage<Comment> findAllCommentsByPage(Integer PageIndex, Integer PageSize);

    /**
     * 删除评论
     *
     * @param id
     * @return
     */
    boolean deleteArticleById(Integer id);

    /**
     * 根据评论id查找文章id
     *
     * @param id
     * @return
     */
    Integer getArticleIdById(Integer id);
}
