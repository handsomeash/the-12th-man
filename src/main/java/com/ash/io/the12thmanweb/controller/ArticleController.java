package com.ash.io.the12thmanweb.controller;

import com.ash.io.the12thmanweb.entity.article.Article;
import com.ash.io.the12thmanweb.service.ArticleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 分页获取所有文章信息
     *
     * @return
     */
    @CrossOrigin
    @PostMapping("/article")
    public Map<String,Object> listArticle(@RequestBody Map<String, Integer> map) {
        Integer PageIndex = map.get("current");
        Integer PageSize = map.get("pagesize");
        log.info("查询页数：" + PageIndex +"查询数据条数：" + PageSize);
        IPage<Article> articles = articleService.getArticles(PageIndex, PageSize);
        //获取总条数
        long total = articles.getTotal();
        List<Article> records = articles.getRecords();
        Map<String,Object> result = new HashMap<>();
        result.put("records",records);
        result.put("total",total);
        return result;
    }

    /**
     * 前往单篇文章页面，获取文章信息
     *
     * @param id
     * @return
     */
    @CrossOrigin
    @GetMapping("/article/{id}")
    public Article getArticle(@PathVariable("id") Integer id) {
        return null;
    }
}
