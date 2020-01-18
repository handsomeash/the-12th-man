package com.ash.io.the12thmanweb.entity;

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
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String portraitUrl;
    private LocalDate registerDate;

}
