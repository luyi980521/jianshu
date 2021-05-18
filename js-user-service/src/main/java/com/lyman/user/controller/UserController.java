package com.lyman.user.controller;

import com.lyman.BaseController;
import com.lyman.api.user.UserControllerApi;
import com.lyman.bo.UserLoginOrRegBO;
import com.lyman.bo.UserLoginWithPwdBO;
import com.lyman.enums.ResponseStatusEnum;
import com.lyman.enums.YesOrNo;
import com.lyman.pojo.TbUser;
import com.lyman.result.JSONResult;
import com.lyman.user.service.UserService;
import com.lyman.utils.IPUtil;
import com.lyman.utils.RedisOperator;
import com.lyman.utils.SMSUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by luyi on 2021/5/17 20:16.
 * 描述：用户接口访问控制器
 */
@Slf4j
@RestController
public class UserController extends BaseController implements UserControllerApi {

    @Autowired
    private UserService userService;

    @Autowired
    private SMSUtils smsUtils;

    @Autowired
    private RedisOperator redis;

    /**
     * 获取验证码
     *
     * @param mobile  手机号
     * @param request request对象
     * @return
     */
    @Override
    public JSONResult getSMSCode(String mobile,
                                 HttpServletRequest request) {
        log.info("mobile: {}", mobile);

        if (StringUtils.isBlank(mobile)) {
            return JSONResult.errorCustom(ResponseStatusEnum.MOBILE_NOT_EXIST);
        }

        Integer code = new Random().nextInt(899999) + 100000;
        smsUtils.sendSMS(mobile, code);

        String requestIp = IPUtil.getRequestIp(request);
        redis.setnx60s(SEND_VERIFY_CODE + ":" + requestIp, requestIp);
        redis.set(SEND_VERIFY_CODE + ":" + mobile,
                code.toString(), 10 * 60);

        log.info("send verify code to {}", mobile);

        return JSONResult.ok();
    }

    /**
     * 使用验证码登录，如果没有注册则直接注册用户
     *
     * @param userLoginOrRegBO 用户登录BO
     * @param bindingResult    参数校验结果
     * @param request          request对象
     * @param response         response对象
     * @return
     */
    @Override
    public JSONResult loginWithSMS(@Valid UserLoginOrRegBO userLoginOrRegBO,
                                   BindingResult bindingResult,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> errMap = getError(bindingResult);
            return JSONResult.errorMap(errMap);
        }

        log.info("{} request to {}, params: mobile: {}, code: {}",
                request.getMethod(), request.getRequestURI(),
                userLoginOrRegBO.getMobile(), userLoginOrRegBO.getCode());

        String mobile = userLoginOrRegBO.getMobile();
        String code = userLoginOrRegBO.getCode();

        // 校验验证码是否正确
        String codeStr = redis.get(SEND_VERIFY_CODE + ":" + mobile);
        if (StringUtils.isBlank(codeStr)
                || !codeStr.equalsIgnoreCase(code)) {
            log.info("验证码不正确，codeStr: {}, code: {}", codeStr, code);
            return JSONResult.errorCustom(ResponseStatusEnum.CODE_NOT_CORRECT);
        }

        // 判断用户是否注册
        TbUser user = userService.queryUserByMobile(mobile);
        if (user != null && user.getFreeze().equals(YesOrNo.YES.type)) {
            log.info("用户已被冻结，状态为：{}", user.getFreeze());
            return JSONResult.errorCustom(ResponseStatusEnum.USER_STATUS_INVALID);
        } else if (user == null) {
            // 直接注册用户
            user = userService.createUser(mobile);
        }

        // 保存用户的会话信息
        String uToken = UUID.randomUUID().toString();
        setCookie(request, response, "uid", user.getId(), COOKIE_MONTH);
        setCookie(request, response, "utoken", uToken, COOKIE_MONTH);

        // 删除验证码
        redis.del(SEND_VERIFY_CODE + ":" + mobile);
        return JSONResult.ok();
    }

    /**
     * 用户登录接口
     *
     * @param userLoginWithPwdBO 用户注册登录对象
     * @param bindingResult      参数校验结果
     * @param request            request对象
     * @param response           response对象
     * @return
     */
    @Override
    public JSONResult loginWithPwd(@Valid UserLoginWithPwdBO userLoginWithPwdBO,
                                   BindingResult bindingResult,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        log.info("{} request to {}, params: mobile: {}, password: {}",
                request.getMethod(), request.getRequestURI(),
                userLoginWithPwdBO.getMobile(), userLoginWithPwdBO.getPassword());

        if (bindingResult.hasErrors()) {
            Map<String, Object> errMap = getError(bindingResult);
            return JSONResult.errorMap(errMap);
        }

        String mobile = userLoginWithPwdBO.getMobile();
        String password = userLoginWithPwdBO.getPassword();

        // 查询用户是否注册
        TbUser tbUser = userService.queryUserByMobile(mobile);

        if (tbUser != null && tbUser.getFreeze().equals(YesOrNo.YES.type)) {
            log.info("用户信息不合规或已被冻结");
            return JSONResult.errorCustom(ResponseStatusEnum.USER_STATUS_INVALID);
        }

        if (tbUser == null) {
            log.info("用户不存在");
            return JSONResult.errorCustom(ResponseStatusEnum.USER_NOT_EXIST);
        }

        // 比较密码是否正确
        if (!password.equals(tbUser.getPassword())) {
            log.info("密码不正确，输入的密码: {}, 正确的密码: {}", password, tbUser.getPassword());
            return JSONResult.errorCustom(ResponseStatusEnum.PASSWORD_NOT_CORRECT);
        }

        // 保存用户信息到cookie中
        String uToken = UUID.randomUUID().toString();
        String uId = tbUser.getId().toString();
        setCookie(request, response, "uid", uId, COOKIE_MONTH);
        setCookie(request, response, "utoken", uToken, COOKIE_MONTH);

        return JSONResult.ok();
    }


    /**
     * 退出登录
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public JSONResult logout(String userId,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        log.info("userId: {}", userId);
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorCustom(ResponseStatusEnum.USER_NOT_EXIST);
        }
        setCookie(request, response, "uid", "", DELETE_COOKIE);
        setCookie(request, response, "utoken", "", DELETE_COOKIE);
        return JSONResult.ok();
    }
}
