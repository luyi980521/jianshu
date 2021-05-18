package com.lyman.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by luyi on 2021/5/17 20:25.
 * 描述：用户注册登录对象
 */
@Data
public class UserLoginWithPwdBO {

    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @NotBlank(message = "密码不能为空")
    private String password;
}
