package com.ash.io.the12thmanweb.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户收藏表实体类
 *
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCollection extends Model<UserCollection> {
    private static final long serialVersionUID = 4977250634611813822L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer articleId;
}
