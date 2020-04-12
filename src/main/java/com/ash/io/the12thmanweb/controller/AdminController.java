package com.ash.io.the12thmanweb.controller;

import com.ash.io.the12thmanweb.entity.admin.Admin;
import com.ash.io.the12thmanweb.entity.article.Article;
import com.ash.io.the12thmanweb.entity.user.User;
import com.ash.io.the12thmanweb.enums.ResultCode;
import com.ash.io.the12thmanweb.result.Result;
import com.ash.io.the12thmanweb.service.AdminService;
import com.ash.io.the12thmanweb.service.ArticleService;
import com.ash.io.the12thmanweb.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private ArticleService articleService;

    /**
     * 管理员登陆
     * @param admin
     * @return
     */
    @PostMapping("/adminLogin")
    public Result login(@RequestBody Admin admin){
        Admin find = adminService.findAdmin(admin);
        String message;
        if(Objects.isNull(find)){
            message = "账号或密码错误";
            return new Result(ResultCode.FAIL.getCode(), message);
        }else{
            message = "登陆成功";
            return new Result(ResultCode.SUCCESS.getCode(), message);
        }
    }

    @PostMapping("/useradmin")
    public Map<String, Object> userAdmin(@RequestBody Map<String, Object> map){
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

    @PostMapping("/articleadmin")
    public Map<String, Object> articleAdmin(@RequestBody Map<String, Object> map){
        Integer PageIndex = (Integer) map.get("current");
        Integer PageSize = (Integer) map.get("pagesize");
        log.info("查询页数：" + PageIndex + "查询数据条数：" + PageSize);
        IPage<Article> articles = articleService.getArticles(PageIndex, PageSize);
        long total = articles.getTotal();
        List<Article> articleList = articles.getRecords();
        Map<String, Object> result = new HashMap<>();
        result.put("articles", articleList);
        result.put("total", total);
        return result;
    }
}
