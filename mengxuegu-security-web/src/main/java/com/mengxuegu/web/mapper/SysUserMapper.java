package com.mengxuegu.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mengxuegu.web.entites.SysUser;

import java.util.Map;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/9 17:19
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 获取用户信息
     *
     * @param params id，username, mobile
     * @return
     */
    SysUser selectSysUserInfo(Map<String, Object> params);
}
