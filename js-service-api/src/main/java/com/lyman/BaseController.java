package com.lyman;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luyi on 2021/5/17 20:33.
 * 描述：为controller提供功能扩展
 */
public class BaseController {

    /** 设置cookie的有效期为1个月 */
    public static final Integer COOKIE_MONTH = 60 * 60 * 24 * 30;

    /** 删除cookie，将有效时间设置为0 */
    public static final Integer DELETE_COOKIE = 0;

    public static final String SEND_VERIFY_CODE = "send:verify:code";

    /**
     * 获取参数校验结果
     *
     * @param bindingResult 校验结果集
     * @return
     */
    public static Map<String, Object> getError(BindingResult bindingResult) {
        Map<String, Object> errMap = new HashMap<>();
        List<FieldError> errorList = bindingResult.getFieldErrors();
        errorList.forEach(d -> {
            errMap.put(d.getField(), d.getDefaultMessage());
        });
        return errMap;
    }

    /**
     * 对cookieValue进行encode
     *
     * @param request request对象
     * @param response response对象
     * @param cookieName cookie的key
     * @param cookieValue cookie的value
     * @param maxAge cookie的有效时间
     */
    public static void setCookie(HttpServletRequest request,
                                 HttpServletResponse response,
                                 String cookieName,
                                 String cookieValue,
                                 Integer maxAge) {
        try {
            cookieValue = URLEncoder.encode(cookieValue, "UTF-8");
            setCookieValue(request, response, cookieName, cookieValue, maxAge);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    /**
     * 对cookieValue进行encode
     *
     * @param request request对象
     * @param response response对象
     * @param cookieName cookie的key
     * @param cookieValue cookie的value
     * @param maxAge cookie的有效时间
     */
    private static void setCookieValue(HttpServletRequest request,
                                       HttpServletResponse response,
                                       String cookieName,
                                       String cookieValue,
                                       Integer maxAge) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        // 将cookie添加到response中
        response.addCookie(cookie);
    }

    /**
     * 删除cookie信息
     *
     * @param request request对象
     * @param response response对象
     * @param cookieName cookie的key
     */
    public static void delCookie(HttpServletRequest request,
                                 HttpServletResponse response,
                                 String cookieName) {
        try {
            String delCookieValue = URLEncoder.encode("", "UTF-8");
            setCookieValue(request, response, cookieName, delCookieValue, DELETE_COOKIE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
