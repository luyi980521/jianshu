package com.lyman.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by luyi on 2021/5/18 17:15.
 * 描述：用户使用验证码登录注册BO
 */
@Data
public class UserLoginOrRegBO {

    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @NotBlank(message = "验证码不能为空")
    private String code;
}
