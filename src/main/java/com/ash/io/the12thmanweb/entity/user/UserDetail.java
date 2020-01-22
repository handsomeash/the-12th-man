package com.ash.io.the12thmanweb.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息明细实体类
 *
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail extends Model<UserDetail> {
    private static final long serialVersionUID = -6096620995189799684L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer roleId;
    private Integer collectionNum;
    private Integer articleNum;

}
