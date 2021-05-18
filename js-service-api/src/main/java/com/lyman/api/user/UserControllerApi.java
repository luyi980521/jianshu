package com.lyman.api.user;

import com.lyman.bo.UserLoginOrRegBO;
import com.lyman.bo.UserLoginWithPwdBO;
import com.lyman.result.JSONResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by luyi on 2021/5/17 19:49.
 * 描述：用户相关api接口
 */
@RequestMapping("/user")
public interface UserControllerApi {

    /**
     * 获取验证码
     *
     * @param mobile  手机号
     * @param request request对象
     * @return
     */
    @PostMapping("/getSMSCode")
    public JSONResult getSMSCode(String mobile, HttpServletRequest request);

    /**
     * 用户密码登录
     *
     * @param userLoginWithPwdBO 用户注册登录对象
     * @param bindingResult      参数校验结果
     * @param request            request对象
     * @param response           response对象
     * @return
     */
    @PostMapping("/loginWithPwd")
    public JSONResult loginWithPwd(UserLoginWithPwdBO userLoginWithPwdBO,
                                   BindingResult bindingResult,
                                   HttpServletRequest request,
                                   HttpServletResponse response);

    /**
     * 使用验证码登录，如果没有注册则直接注册用户
     *
     * @param userLoginOrRegBO 用户登录BO
     * @param bindingResult    参数校验结果
     * @param request          request对象
     * @param response         response对象
     * @return
     */
    @PostMapping("/loginWithSMS")
    public JSONResult loginWithSMS(UserLoginOrRegBO userLoginOrRegBO,
                                   BindingResult bindingResult,
                                   HttpServletRequest request,
                                   HttpServletResponse response);

    /**
     * 退出登录
     *
     * @param userId   用户id
     * @param request  request对象
     * @param response response对象
     * @return
     */
    @PostMapping("/logout")
    public JSONResult logout(String userId,
                             HttpServletRequest request,
                             HttpServletResponse response);


}
