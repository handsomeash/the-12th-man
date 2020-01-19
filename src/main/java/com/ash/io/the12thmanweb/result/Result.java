package com.ash.io.the12thmanweb.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    //响应码
    private int code;
    //响应提示信息
    private String message;
    //响应结果对象
    private Object data;

    //构造带code和message的构造器，@AllArgsConstructor是构造带全参的构造器
    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
