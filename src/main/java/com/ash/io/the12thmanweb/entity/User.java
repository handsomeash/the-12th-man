package com.ash.io.the12thmanweb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

/**
 * 用户实体类
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-16
 */
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class User {
    //id使用自增，默认mybatis会生成long类型的长id，但是莫名会导致传到前端值有差异
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String portraitUrl;
    private LocalDate registerDate;

}
