package com.lyman.exception;

import com.lyman.enums.ResponseStatusEnum;

/**
 * Created by luyi on 2021/5/18 18:27.
 * 描述：异常工具
 */
public class CommonException {

    public static void display(ResponseStatusEnum responseStatusEnum) {
        throw new MyCustomException(responseStatusEnum);
    }
}
