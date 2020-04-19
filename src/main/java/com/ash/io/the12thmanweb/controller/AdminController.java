package com.ash.io.the12thmanweb.controller;

import com.ash.io.the12thmanweb.converter.ArticleConverter;
import com.ash.io.the12thmanweb.converter.CommentConverter;
import com.ash.io.the12thmanweb.entity.admin.Admin;
import com.ash.io.the12thmanweb.entity.article.Article;
import com.ash.io.the12thmanweb.entity.article.ArticleNumber;
import com.ash.io.the12thmanweb.entity.comment.Comment;
import com.ash.io.the12thmanweb.entity.user.User;
import com.ash.io.the12thmanweb.enums.CalculationEnums;
import com.ash.io.the12thmanweb.enums.ResultCode;
import com.ash.io.the12thmanweb.response.ArticleResp;
import com.ash.io.the12thmanweb.response.CommentResp;
import com.ash.io.the12thmanweb.result.Result;
import com.ash.io.the12thmanweb.service.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-04-11
 */
@Slf4j
@RestController
@RequestMapping("api")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleNumberService articleNumberService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;

    /**
     * 管理员登陆
     *
     * @param admin
     * @return
     */
    @PostMapping("/adminLogin")
    public Result login(@RequestBody Admin admin) {
        Admin find = adminService.findAdmin(admin);
        String message;
        if (Objects.isNull(find)) {
            message = "账号或密码错误";
            return new Result(ResultCode.FAIL.getCode(), message);
        } else {
            message = "登陆成功";
            //将管理员信息传递给前端，保存到session中
            return new Result(ResultCode.SUCCESS.getCode(), message, find);
        }
    }

    /**
     * 查询所有用户信息
     *
     * @param map
     * @return
     */
    @PostMapping("/useradmin")
    public Map<String, Object> userAdmin(@RequestBody Map<String, Object> map) {
        Integer PageIndex = (Integer) map.get("current");
        Integer PageSize = (Integer) map.get("pagesize");
        log.info("查询页数：" + PageIndex + "查询数据条数：" + PageSize);
        IPage<User> users = userService.findAllUsersByPage(PageIndex, PageSize);
        long total = users.getTotal();
        List<User> userList = users.getRecords();
        Map<String, Object> result = new HashMap<>();
        result.put("users", userList);
        result.put("total", total);
        return result;
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @GetMapping("/deleteUser/{id}")
    public Result deleteUserById(@PathVariable("id") Integer id) {
        log.info("删除用户，id：" + id);
        boolean result = userService.deleteUserById(id);
        String message;
        if (result) {
            message = "删除成功";
            return new Result(ResultCode.SUCCESS.getCode(), message);
        } else {
            message = "删除失败，该用户不存在或已删除";
            return new Result(ResultCode.FAIL.getCode(), message);
        }
    }

    /**
     * 查询所有文章信息
     *
     * @param map
     * @return
     */
    @PostMapping("/articleadmin")
    public Map<String, Object> articleAdmin(@RequestBody Map<String, Object> map) {
        Integer PageIndex = (Integer) map.get("current");
        Integer PageSize = (Integer) map.get("pagesize");
        log.info("查询页数：" + PageIndex + "查询数据条数：" + PageSize);
        IPage<Article> articles = articleService.getArticles(PageIndex, PageSize);
        long total = articles.getTotal();
        List<Article> articleList = articles.getRecords();
        List<ArticleResp> respList = new ArrayList<>();
        //获取到文章评论收藏信息，作者信息，封装到response中
        articleList.forEach(r -> {

            ArticleNumber articleNumber = articleNumberService.getByArticleId(r.getId());
            User user = userService.getById(r.getUserId());
            ArticleResp articleResp = ArticleConverter.converter(r, articleNumber, user);
            respList.add(articleResp);

        });
        Map<String, Object> result = new HashMap<>();
        result.put("articles", respList);
        result.put("total", total);
        return result;
    }

    /**
     * 删除文章
     *
     * @param id
     * @return
     */
    @GetMapping("/deleteArticle/{id}")
    public Result deleteArticleById(@PathVariable("id") Integer id) {
        log.info("删除文章，id：" + id);
        boolean result = articleService.deleteArticleById(id);
        String message;
        if (result) {
            message = "删除成功";
            return new Result(ResultCode.SUCCESS.getCode(), message);
        } else {
            message = "删除失败，该文章不存在或已删除";
            return new Result(ResultCode.FAIL.getCode(), message);
        }
    }

    /**
     * 查询所有评论信息
     *
     * @param map
     * @return
     */
    @PostMapping("/commentadmin")
    public Map<String, Object> commentAdmin(@RequestBody Map<String, Object> map) {
        Integer PageIndex = (Integer) map.get("current");
        Integer PageSize = (Integer) map.get("pagesize");
        log.info("查询页数：" + PageIndex + "查询数据条数：" + PageSize);
        IPage<Comment> comments = commentService.findAllCommentsByPage(PageIndex, PageSize);
        long total = comments.getTotal();

        List<Comment> commentList = comments.getRecords();
        List<CommentResp> respList = new ArrayList<>();
        //获取到文章评论收藏信息，作者信息，封装到response中
        commentList.forEach(r -> {

            Article article = articleService.getById(r.getArticleId());
            User user = userService.getById(r.getUserId());
            CommentResp commentResp = CommentConverter.converter(r, user, article.getTitle());

            respList.add(commentResp);

        });
        Map<String, Object> result = new HashMap<>();
        result.put("comments", respList);
        result.put("total", total);
        return result;
    }

    /**
     * 删除评论
     *
     * @param id
     * @return
     */
    @GetMapping("/deleteComment/{id}")
    public Result deleteCommentById(@PathVariable("id") Integer id) {
        log.info("删除评论，id：" + id);
        //更新文章收藏和评论表，注意要先更新再删除
        Integer articleId = commentService.getById(id).getArticleId();
        articleNumberService.updateCommentNumByArticleId(articleId, CalculationEnums.DELETE);

        boolean result = commentService.deleteArticleById(id);
        String message;
        if (result) {
            message = "删除成功";
            return new Result(ResultCode.SUCCESS.getCode(), message);
        } else {
            message = "删除失败，该评论不存在或已删除";
            return new Result(ResultCode.FAIL.getCode(), message);
        }
    }

    @PutMapping("/passwordAdmin")
    public Result editAdminPassWord(@RequestBody Map<String, String> map) {
        String oldPassword = map.get("oldPassword");
        String newPassword = map.get("newPassword");
        String message;
        Admin admin = adminService.findByPassword(oldPassword);
        if (Objects.isNull(admin)) {
            message = "旧密码不正确，修改失败";
            log.info("editUserPassword:" + message);
            return new Result(ResultCode.FAIL.getCode(), message);
        } else {
            admin.setPassword(newPassword);
            adminService.saveOrUpdate(admin);
            message = "密码修改成功";
            log.info("editAdminPassword:" + message);
            return new Result(ResultCode.SUCCESS.getCode(), message);
        }
    }
}
