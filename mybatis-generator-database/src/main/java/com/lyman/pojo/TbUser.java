package com.lyman.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_user")
public class TbUser {
    @Id
    private Integer id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    @Column(name = "head_photo")
    private String headPhoto;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private Integer mobile;

    /**
     * 性别 0-男 1-女 2-保密
     */
    private Integer sex;

    /**
     * 密码
     */
    private String password;

    /**
     * 个人网站
     */
    private String website;

    /**
     * 冻结 0-否 1-是
     */
    private Integer freeze;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取昵称
     *
     * @return nickname - 昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置昵称
     *
     * @param nickname 昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取头像
     *
     * @return head_photo - 头像
     */
    public String getHeadPhoto() {
        return headPhoto;
    }

    /**
     * 设置头像
     *
     * @param headPhoto 头像
     */
    public void setHeadPhoto(String headPhoto) {
        this.headPhoto = headPhoto;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取手机号
     *
     * @return mobile - 手机号
     */
    public Integer getMobile() {
        return mobile;
    }

    /**
     * 设置手机号
     *
     * @param mobile 手机号
     */
    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取性别 0-男 1-女 2-保密
     *
     * @return sex - 性别 0-男 1-女 2-保密
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置性别 0-男 1-女 2-保密
     *
     * @param sex 性别 0-男 1-女 2-保密
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取个人网站
     *
     * @return website - 个人网站
     */
    public String getWebsite() {
        return website;
    }

    /**
     * 设置个人网站
     *
     * @param website 个人网站
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * 获取冻结 0-否 1-是
     *
     * @return freeze - 冻结 0-否 1-是
     */
    public Integer getFreeze() {
        return freeze;
    }

    /**
     * 设置冻结 0-否 1-是
     *
     * @param freeze 冻结 0-否 1-是
     */
    public void setFreeze(Integer freeze) {
        this.freeze = freeze;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}