package com.ash.io.the12thmanweb.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别枚举类
 *
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-21
 */
@AllArgsConstructor
public enum GenderEnums implements IEnum<Integer> {
    MALE(1),
    FEMALE(2);

    @EnumValue
    private int value;

    @Override
    public Integer getValue() {
        return value;
    }
}
