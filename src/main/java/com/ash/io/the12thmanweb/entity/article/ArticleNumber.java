package com.ash.io.the12thmanweb.entity.article;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章的评论数和收藏数
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-03-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleNumber extends Model<ArticleNumber> {
    private static final long serialVersionUID = -6617240776516464860L;
    //id使用自增
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer articleId;
    //收藏数
    private Integer collectionNum;
    //评论数
    private Integer commentNum;
}
