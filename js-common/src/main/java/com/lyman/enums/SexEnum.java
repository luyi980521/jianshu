package com.lyman.enums;

/**
 * Created by luyi on 2021/5/17 19:19.
 * 描述：性别 枚举
 */
public enum SexEnum {
    man(0, "男"),
    woman(1, "女"),
    secret(2, "保密");

    public final Integer type;
    public final String value;

    SexEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
