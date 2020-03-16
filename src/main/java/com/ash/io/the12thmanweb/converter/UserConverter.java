package com.ash.io.the12thmanweb.converter;

import com.ash.io.the12thmanweb.entity.user.User;
import com.ash.io.the12thmanweb.entity.user.UserDetail;
import com.ash.io.the12thmanweb.response.UserResp;

/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-03-16
 */
public class UserConverter {
    public static UserResp converter(User user, UserDetail userDetail){
        UserResp userResp = UserResp.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .birthday(user.getBirthday())
                .gender(user.getGender())
                .email(user.getEmail())
                .phone(user.getPhone())
                .portraitUrl(user.getPortraitUrl())
                .registerDate(user.getRegisterDate())
                .articleNum(userDetail.getArticleNum())
                .collectionNum(userDetail.getCollectionNum())
                .build();
        return userResp;
    }
}
