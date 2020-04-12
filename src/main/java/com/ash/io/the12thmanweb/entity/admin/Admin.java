package com.ash.io.the12thmanweb.entity.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理员实体类
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-04-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends Model<Admin> {
    private static final long serialVersionUID = 4147879187982547073L;
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
}
