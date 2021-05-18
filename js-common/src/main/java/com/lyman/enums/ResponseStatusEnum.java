package com.lyman.enums;

/**
 * Created by luyi on 2021/5/17 19:55.
 * 描述：响应结果枚举
 */
public enum ResponseStatusEnum {

    SUCCESS(200, true, "操作成功！"),
    FAILED(500, false, "操作失败！"),

    TICKET_INVALID(501,false,"会话失效，请重新登录！"),
    MOBILE_NOT_EXIST(502,false,"手机号不存在，请输入有效手机号"),
    USER_STATUS_INVALID(503,false,"用户不存在或已被冻结"),
    USER_NOT_EXIST(504,false,"用户不存在"),
    CODE_NOT_CORRECT(505,false,"验证码不正确"),
    PASSWORD_NOT_CORRECT(505,false,"验证码不正确"),

    ;

    // 响应业务状态
    private Integer status;
    // 调用是否成功
    private Boolean success;
    // 响应消息，可以为成功或者失败的消息
    private String msg;

    ResponseStatusEnum(Integer status, Boolean success, String msg) {
        this.status = status;
        this.success = success;
        this.msg = msg;
    }

    public Integer status() {
        return status;
    }
    public Boolean success() {
        return success;
    }
    public String msg() {
        return msg;
    }
}
