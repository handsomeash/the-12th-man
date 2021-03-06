package com.ash.io.the12thmanweb.controller;

import com.ash.io.the12thmanweb.converter.CommentConverter;
import com.ash.io.the12thmanweb.entity.comment.Comment;
import com.ash.io.the12thmanweb.entity.user.User;
import com.ash.io.the12thmanweb.enums.CalculationEnums;
import com.ash.io.the12thmanweb.enums.ResultCode;
import com.ash.io.the12thmanweb.response.CommentResp;
import com.ash.io.the12thmanweb.result.Result;
import com.ash.io.the12thmanweb.service.ArticleNumberService;
import com.ash.io.the12thmanweb.service.CommentService;
import com.ash.io.the12thmanweb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论controller
 *
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-03-14
 */
@Slf4j
@RestController
@RequestMapping("api")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleNumberService articleNumberService;

    /**
     * 用户发表评论
     *
     * @param comment
     * @return
     */
    @PostMapping("/comment")
    public Result comment(@RequestBody Comment comment) {
        log.info("用户发表评论" + comment.toString());
        commentService.save(comment);
        //更新文章的评论字段的数量
        articleNumberService.updateCommentNumByArticleId(comment.getArticleId(), CalculationEnums.ADD);
        String message = "发表评论成功";
        return new Result(ResultCode.SUCCESS.getCode(), message);

    }

    /**
     * 获取所有该文章下的评论以及评论者的信息
     *
     * @param id
     * @return
     */
    @GetMapping("/comment/{id}")
    public List<CommentResp> getArticle(@PathVariable("id") Integer id) {
        List<Comment> commentList = commentService.getByArticleId(id);
        List<CommentResp> commentRespList = new ArrayList<>();
        commentList.forEach(comment -> {
            User user = userService.getById(comment.getUserId());
            CommentResp commentResp = CommentConverter.converter(comment, user);
            commentRespList.add(commentResp);
        });

        return commentRespList;
    }
}
