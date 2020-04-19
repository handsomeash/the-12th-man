package com.ash.io.the12thmanweb.response;

import com.ash.io.the12thmanweb.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 用于返回评论相关的response类
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-03-15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResp implements Serializable {
    private static final long serialVersionUID = -4365306914824549462L;

    private Integer id;
    private Integer articleId;
    private Integer userId;
    private String content;
    private LocalDate createDate;
    private String articleName;
    private User user;
}
