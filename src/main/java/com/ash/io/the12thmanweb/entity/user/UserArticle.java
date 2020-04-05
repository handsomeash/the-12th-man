package com.ash.io.the12thmanweb.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户发表文章表实体类
 *
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-04-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserArticle extends Model<UserArticle> {
    private static final long serialVersionUID = -6541224338225358307L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer articleId;
}
