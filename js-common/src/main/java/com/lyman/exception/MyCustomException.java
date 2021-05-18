package com.lyman.exception;

import com.lyman.enums.ResponseStatusEnum;

/**
 * Created by luyi on 2021/5/18 18:30.
 * 描述：自定义异常
 */
public class MyCustomException extends RuntimeException {

    private ResponseStatusEnum responseStatusEnum;

    public MyCustomException(ResponseStatusEnum responseStatusEnum) {
        super("异常代码为：" + responseStatusEnum.status()
                + "，异常信息：" + responseStatusEnum.msg());
        this.responseStatusEnum = responseStatusEnum;
    }

    public ResponseStatusEnum getResponseStatusEnum() {
        return responseStatusEnum;
    }

    public void setResponseStatusEnum(ResponseStatusEnum responseStatusEnum) {
        this.responseStatusEnum = responseStatusEnum;
    }
}
