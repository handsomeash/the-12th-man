package com.ash.io.the12thmanweb.controller;

import com.ash.io.the12thmanweb.Utils.MyUtil;
import com.ash.io.the12thmanweb.entity.article.Article;
import com.ash.io.the12thmanweb.entity.article.ArticleDetail;
import com.ash.io.the12thmanweb.entity.user.User;
import com.ash.io.the12thmanweb.enums.ResultCode;
import com.ash.io.the12thmanweb.result.Result;
import com.ash.io.the12thmanweb.service.ArticleService;
import com.ash.io.the12thmanweb.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-22
 */
@Slf4j
@RestController
@RequestMapping("api")
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;

    /**
     * 分页获取所有文章信息
     *
     * @return
     */
    @PostMapping("/article")
    public Map<String, Object> listArticle(@RequestBody Map<String, Integer> map) {
        Integer PageIndex = map.get("current");
        Integer PageSize = map.get("pagesize");
        log.info("查询页数：" + PageIndex + "查询数据条数：" + PageSize);
        IPage<Article> articles = articleService.getArticles(PageIndex, PageSize);
        //获取总条数
        long total = articles.getTotal();
        List<Article> records = articles.getRecords();
        Map<String, Object> result = new HashMap<>();
        result.put("records", records);
        result.put("total", total);
        return result;
    }

    /**
     * 前往单篇文章页面，获取文章信息
     *
     * @param id
     * @return
     */
    @GetMapping("/article/{id}")
    public Map<String, Object> getArticle(@PathVariable("id") Integer id) {
        log.info("前往文章页面，文章id：" + id);

        Map<String, Object> map = new HashMap<>();
        ArticleDetail article = articleService.getArticleDetail(id);
        User author = userService.getById(article.getUserId());
        map.put("author",author);
        map.put("article",article);
        return map;
    }

    /**
     * 用户发表文章
     *
     * @param map
     * @return
     */
    @PostMapping("/write")
    public Result writeArticle(@RequestBody Map<String, String> map) {
        log.info(map.toString());
        int userId = Integer.parseInt(map.get("userid"));
        String content = map.get("content");
        String title = map.get("title");
        String imgURL = map.get("imgurl");
        ArticleDetail articleDetail = new ArticleDetail();
        articleDetail.setTitle(title);
        articleDetail.setContent(content);
        articleDetail.setImgUrl(imgURL);
        articleService.writeArticle(userId, articleDetail);
        String message;
        message = "发表成功";
        return new Result(ResultCode.SUCCESS.getCode(), message);
    }

    /**
     * 上传封面
     * @param file
     * @return
     */
    @PostMapping("/cover")
    public String edit(MultipartFile file) {
        String folder = "D:/workspace/img";
        File imageFolder = new File(folder);
        File f = new File(imageFolder, MyUtil.getRandomString(5)
                + file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4));
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try {
            file.transferTo(f);
            String imgURL = "http://localhost:8443/api/file/" + f.getName();
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
