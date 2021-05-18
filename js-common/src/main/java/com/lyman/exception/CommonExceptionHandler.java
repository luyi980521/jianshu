package com.lyman.exception;

import com.lyman.result.JSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by luyi on 2021/5/18 18:32.
 * 描述：异常处理类
 *  1. 用于异常信息的统一处理
 *  2. 解耦controller和service
 */
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(MyCustomException.class)
    public JSONResult returnMyCustomException(MyCustomException e) {
        e.printStackTrace();
        return JSONResult.exception(e.getResponseStatusEnum());
    }
}
