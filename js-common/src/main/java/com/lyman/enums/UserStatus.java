package com.lyman.enums;

import org.omg.PortableInterceptor.ACTIVE;
import org.omg.PortableInterceptor.INACTIVE;

/**
 * Created by luyi on 2021/5/17 19:21.
 * 描述：用户状态
 */
public enum UserStatus {
//    用户状态：
//    0：已激活，默认已激活
//    1：已冻结


    ACTIVE(0, "已激活"),
    FROZEN(1, "已冻结");

    public final Integer type;
    public final String value;

    UserStatus(Integer type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
     * 判断传入的用户状态是不是有效的值
     * @param tempStatus
     * @return
     */
    public static boolean isUserStatusValid(Integer tempStatus) {
        if (tempStatus != null) {
            if (tempStatus == ACTIVE.type || tempStatus == FROZEN.type) {
                return true;
            }
        }
        return false;
    }
}
