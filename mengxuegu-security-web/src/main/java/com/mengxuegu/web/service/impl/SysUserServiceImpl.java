package com.mengxuegu.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mengxuegu.web.entites.SysUser;
import com.mengxuegu.web.mapper.SysUserMapper;
import com.mengxuegu.web.service.SysUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/9 17:24
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    /**
     * 用户默认密码
     */
    public static final String PASSOWRD_DEFAULT =
            "$2a$10$rDkPvvAFV8kqwvKJzwlRv.i.q.wz1w1pz0SFsHn/55jNeZFQv/eCm";


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

    /**
     * 获取用户列表
     *
     * @param sysUser
     * @return
     */
    @Override
    public IPage<SysUser> findPage(SysUser sysUser, Page<SysUser> page) {
        sysUser = null == sysUser ? sysUser : new SysUser();
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(sysUser.getUsername()), "username", sysUser.getUsername())
                .eq(StringUtils.isNotBlank(sysUser.getMobile()), "mobile", sysUser.getMobile());
        page = baseMapper.selectPage(page, queryWrapper);
        return page;
    }

    @Override
    public boolean saveOrUpdate(SysUser sysUser) {
        if (null != sysUser) {
            sysUser.setPassword(PASSOWRD_DEFAULT);
        }

        sysUser.setUpdateDate(new Date());
        boolean flag = super.saveOrUpdate(sysUser);
        // 修改
        if (flag) {
            baseMapper.deleteUserRoleByUserId(sysUser.getId());
            if (CollectionUtils.isNotEmpty(sysUser.getRoleIds())) {
                baseMapper.insertUserRoles(sysUser.getRoleIds(), sysUser.getId());
            }
        }

        return flag;
    }
}
