package com.ash.io.the12thmanweb.entity.article;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 文章具体内容类
 *
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetail extends Model<ArticleDetail> {
    private static final long serialVersionUID = -901510063572446676L;

    //id使用自增
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String title;
    //用户表id
    private Integer userId;
    private String imgUrl;
    private LocalDate createDate;
    //内容
    private String content;
    //收藏数
    private Integer collectionNum;
    //评论数
    private Integer commentNum;
}
