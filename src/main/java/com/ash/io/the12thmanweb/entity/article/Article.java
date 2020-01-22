package com.ash.io.the12thmanweb.entity.article;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章实体类
 *
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article extends Model<Article> {

    private static final long serialVersionUID = -854357082178215487L;

    //id使用自增
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String title;
    //用户表id
    private Integer userId;
    private String author;
    private String imgUrl;
    private String createDate;
    private String content;
    //收藏数
    private Integer collectionNum;
    //评论数
    private Integer commentNum;


}
