package com.ash.io.the12thmanweb.service.serviceImpl;

import com.ash.io.the12thmanweb.entity.comment.Comment;
import com.ash.io.the12thmanweb.mapper.CommentMapper;
import com.ash.io.the12thmanweb.service.CommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-03-14
 */
@Slf4j
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Override
    public List<Comment> getByArticleId(Integer articleId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId);
        List<Comment> commentList = commentMapper.selectList(wrapper);
        return commentList;
    }

    @Override
    public IPage<Comment> findAllCommentsByPage(Integer PageIndex, Integer PageSize) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0).orderByAsc("article_id");
        Page<Comment> page = new Page<>(PageIndex, PageSize);
        return commentMapper.selectPage(page, wrapper);
    }

    @Override
    public boolean deleteArticleById(Integer id) {
        int i = commentMapper.deleteById(id);
        return i > 0;
    }

    @Override
    public Integer getArticleIdById(Integer id) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        Comment comment = commentMapper.selectOne(wrapper);
        return comment.getArticleId();
    }
}
