package com.ash.io.the12thmanweb.entity.comment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-03-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends Model<Comment> {
    private static final long serialVersionUID = 897097969510961569L;

    //id使用自增
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer articleId;
    private Integer userId;
    private String content;
    private LocalDate createDate;
}
