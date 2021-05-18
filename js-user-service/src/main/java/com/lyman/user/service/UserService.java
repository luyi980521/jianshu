package com.lyman.user.service;

import com.lyman.pojo.TbUser;

/**
 * Created by luyi on 2021/5/17 20:48.
 * 描述：用户业务接口
 */
public interface UserService {

    /**
     * 通过手机号查询用户信息
     *
     * @param mobile 手机号
     * @return
     */
    public TbUser queryUserByMobile(String mobile);

    /**
     * 创建用户
     *
     * @param mobile 手机号
     * @return
     */
    public TbUser createUser(String mobile);
}
