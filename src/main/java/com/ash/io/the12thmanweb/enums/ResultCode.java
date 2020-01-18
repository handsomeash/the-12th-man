package com.ash.io.the12thmanweb.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应码枚举
 *
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-17
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(200, "成功"),
    FAIL(400, "失败"),
    NOT_FOUND(404, "接口不存在");

    private int code;
    private String desc;

}
