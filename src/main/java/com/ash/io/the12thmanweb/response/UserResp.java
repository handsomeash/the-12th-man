package com.ash.io.the12thmanweb.response;

import com.ash.io.the12thmanweb.enums.GenderEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 用于返回用户信息相关的response类
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-03-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResp implements Serializable {
    private static final long serialVersionUID = -3155152051105341506L;

    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String portraitUrl;
    private LocalDate registerDate;
    private GenderEnums gender;
    private LocalDate birthday;
    //用户收藏数
    private Integer collectionNum;
    //用户发表文章数
    private Integer articleNum;
}
