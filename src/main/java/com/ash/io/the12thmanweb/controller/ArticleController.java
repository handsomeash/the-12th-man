package com.ash.io.the12thmanweb.controller;

import com.ash.io.the12thmanweb.Utils.MyUtil;
import com.ash.io.the12thmanweb.converter.ArticleConverter;
import com.ash.io.the12thmanweb.entity.article.Article;
import com.ash.io.the12thmanweb.entity.article.ArticleDetail;
import com.ash.io.the12thmanweb.entity.article.ArticleNumber;
import com.ash.io.the12thmanweb.entity.user.User;
import com.ash.io.the12thmanweb.enums.ArticleEnums;
import com.ash.io.the12thmanweb.enums.CalculationEnums;
import com.ash.io.the12thmanweb.enums.ResultCode;
import com.ash.io.the12thmanweb.response.ArticleDetailResp;
import com.ash.io.the12thmanweb.response.ArticleResp;
import com.ash.io.the12thmanweb.result.Result;
import com.ash.io.the12thmanweb.service.ArticleDetailService;
import com.ash.io.the12thmanweb.service.ArticleNumberService;
import com.ash.io.the12thmanweb.service.ArticleService;
import com.ash.io.the12thmanweb.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    private ArticleService articleService;
    @Autowired
    private ArticleDetailService articleDetailService;
    @Autowired
    private ArticleNumberService articleNumberService;
    @Autowired
    private UserService userService;

    /**
     * 分页获取所有文章信息
     *
     * @return
     */
    @PostMapping("/article")
    public Map<String, Object> listArticles(@RequestBody Map<String, Object> map) {
        Integer PageIndex = (Integer) map.get("current");
        Integer PageSize = (Integer) map.get("pagesize");
        String type = (String) map.get("type");

        IPage<Article> articles;
        switch (type) {
            case "FOOTBALL":
                articles = articleService.getArticlesByType(PageIndex, PageSize, ArticleEnums.FOOTBALL);
                break;
            case "BASKETBALL":
                articles = articleService.getArticlesByType(PageIndex, PageSize, ArticleEnums.BASKETBALL);
                break;
            default:
                //搜索框模糊查询
                articles = articleService.getArticlesByKeywords(PageIndex, PageSize, type);
        }
        log.info("查询页数：" + PageIndex + "查询数据条数：" + PageSize);
        //获取总条数
        long total = articles.getTotal();
        List<Article> records = articles.getRecords();
        List<ArticleResp> respList = new ArrayList<>();
        //获取到文章评论收藏信息，作者信息，封装到response中
        records.forEach(r -> {

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
     * 前往单篇文章页面，获取文章信息
     *
     * @param id
     * @return
     */
    @GetMapping("/article/{id}")
    public ArticleDetailResp getArticle(@PathVariable("id") Integer id) {
        log.info("前往文章页面，文章id：" + id);

        ArticleDetail article = articleDetailService.getArticleDetail(id);
        User author = userService.getById(article.getUserId());
        ArticleDetailResp resp = ArticleConverter.converter(article, author);
        return resp;
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
        String type = map.get("type");
        ArticleDetail articleDetail = new ArticleDetail();
        articleDetail.setTitle(title);
        articleDetail.setContent(content);
        articleDetail.setImgUrl(imgURL);
        articleDetail.setArticleType(ArticleEnums.valueOf(type));
        articleDetailService.writeArticle(userId, articleDetail);
        String message = "发表成功";
        return new Result(ResultCode.SUCCESS.getCode(), message);
    }

    @PostMapping("/editArticle")
    public Result editArticle(@RequestBody Map<String, String> map) {
        log.info(map.toString());
        int id = Integer.parseInt(map.get("articleId"));
        String content = map.get("content");
        String title = map.get("title");
        String type = map.get("type");
        ArticleDetail articleDetail = ArticleDetail.builder()
                .id(id)
                .title(title)
                .content(content)
                .articleType(ArticleEnums.valueOf(type))
                .build();
        articleDetailService.editArticle(id, articleDetail);
        String message = "编辑成功";
        return new Result(ResultCode.SUCCESS.getCode(), message);
    }

    /**
     * 用户收藏文章
     *
     * @param map
     * @return
     */
    @PutMapping("/article/collect")
    public Result collectArticle(@RequestBody Map<String, Integer> map) {
        Integer userId = map.get("userId");
        //传过来的id其实是articleDetail的id
        Integer articleId = map.get("articleId");
        log.info("userId:" + userId);
        log.info("articleId:" + articleId);
        boolean success = articleNumberService.collectArticle(userId, articleId, CalculationEnums.ADD);
        String message;
        if (success) {
            message = "收藏成功";
            return new Result(ResultCode.SUCCESS.getCode(), message);
        } else {
            message = "已经收藏";
            return new Result(ResultCode.FAIL.getCode(), message);
        }
    }

    /**
     * 用户取消收藏
     *
     * @param map
     * @return
     */
    @PostMapping("/article/cancelCollect")
    public Result cancelCollectArticle(@RequestBody Map<String, Integer> map) {
        Integer userId = map.get("userId");
        //传过来的id其实是articleDetail的id
        Integer articleId = map.get("articleId");
        log.info("userId:" + userId);
        log.info("articleId:" + articleId);
        boolean success = articleNumberService.collectArticle(userId, articleId, CalculationEnums.DELETE);
        String message;
        if (success) {
            message = "取消收藏成功";
            return new Result(ResultCode.SUCCESS.getCode(), message);
        } else {
            message = "取消收藏失败";
            return new Result(ResultCode.FAIL.getCode(), message);
        }
    }

    /**
     * 分页获取收藏的文章信息
     *
     * @return
     */
    @PostMapping("/article/collection")
    public Map<String, Object> listCollectionArticles(@RequestBody Map<String, Integer> map) {
        //分页信息
        Integer PageIndex = map.get("current");
        Integer PageSize = map.get("pagesize");
        //用户信息
        Integer userId = map.get("userId");
        log.info("查询页数：" + PageIndex + "查询数据条数：" + PageSize);
        IPage<Article> articles = articleService.getCollectionArticles(PageIndex, PageSize, userId);
        //如果用户没有收藏过文章
        if (articles == null) {
            return null;
        }
        //获取总条数
        long total = articles.getTotal();
        List<Article> records = articles.getRecords();
        Map<String, Object> result = new HashMap<>();
        result.put("records", records);
        result.put("total", total);
        return result;
    }

    /**
     * 分页获取发表的文章信息
     *
     * @return
     */
    @PostMapping("/article/write")
    public Map<String, Object> listWriteArticles(@RequestBody Map<String, Integer> map) {
        //分页信息
        Integer PageIndex = map.get("current");
        Integer PageSize = map.get("pagesize");
        //用户信息
        Integer userId = map.get("userId");
        log.info("查询页数：" + PageIndex + "查询数据条数：" + PageSize);
        IPage<Article> articles = articleService.getWriteArticles(PageIndex, PageSize, userId);
        //如果用户没有发表过文章
        if (articles == null) {
            return null;
        }
        //获取总条数
        long total = articles.getTotal();
        List<Article> records = articles.getRecords();
        Map<String, Object> result = new HashMap<>();
        result.put("records", records);
        result.put("total", total);
        return result;
    }

    @GetMapping("/getEditArticle/{id}")
    public ArticleDetail getEditArticleDetail(@PathVariable("id") Integer id) {

        ArticleDetail articleDetail = articleDetailService.getArticleDetail(id);
        return articleDetail;
    }

    /**
     * 上传封面
     *
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
