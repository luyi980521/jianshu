package com.lyman.user.service.impl;

import com.lyman.enums.ResponseStatusEnum;
import com.lyman.enums.Sex;
import com.lyman.enums.YesOrNo;
import com.lyman.exception.CommonException;
import com.lyman.idworker.Sid;
import com.lyman.pojo.TbUser;
import com.lyman.user.mapper.TbUserMapper;
import com.lyman.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * Created by luyi on 2021/5/17 20:49.
 * 描述：用户业务接口实现类
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private Sid sid;

    /**
     * 通过手机号查询用户信息
     *
     * @param mobile 手机号
     * @return
     */
    @Override
    public TbUser queryUserByMobile(String mobile) {
        Example example = new Example(TbUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("mobile", mobile);
        return userMapper.selectOneByExample(example);
    }

    /**
     * 创建用户
     *
     * @param mobile 手机号
     * @return
     */
    @Override
    @Transactional
    public TbUser createUser(String mobile) {
        log.info("mobile: {}", mobile);
        if (StringUtils.isBlank(mobile)) {
            CommonException.display(ResponseStatusEnum.MOBILE_NOT_EXIST);
        }
        TbUser user = new TbUser();
        String id = sid.nextShort();
        user.setId(id);
        user.setHeadPhoto("");
        user.setNickname("用户" + mobile);
        user.setSex(Sex.secret.type);
        user.setMobile(mobile);
        user.setFreeze(YesOrNo.NO.type);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userMapper.insert(user);
        return user;
    }
}
