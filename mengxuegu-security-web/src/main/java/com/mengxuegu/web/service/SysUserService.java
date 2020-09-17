package com.mengxuegu.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mengxuegu.web.entites.SysUser;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/9 17:22
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 通过用户名查询用户信息
     *
     * @param userName
     * @return
     */
    SysUser findByUserName(String userName);

    /**
     * 通过手机号查询用户信息
     *
     * @param mobile
     * @return
     */
    SysUser findByUserMobile(String mobile);

    /**
     * 获取用户列表
     *
     * @param sysUser
     * @return
     */
    IPage<SysUser> findPage(SysUser sysUser, Page<SysUser> page);
}
