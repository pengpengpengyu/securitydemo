package com.mengxuegu.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mengxuegu.web.entites.SysUser;
import com.mengxuegu.web.mapper.SysUserMapper;
import com.mengxuegu.web.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/9 17:24
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    /**
     * 通过用户名查询用户信息
     *
     * @param userName
     * @return
     */
    @Override
    public SysUser findByUserName(String userName) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userName);
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 通过手机号查询用户信息
     *
     * @param mobile
     * @return
     */
    @Override
    public SysUser findByUserMobile(String mobile) {

        QueryWrapper<SysUser> queryWrapper = new QueryWrapper();
        queryWrapper.eq("mobile", mobile);

        return baseMapper.selectOne(queryWrapper);
    }
}
