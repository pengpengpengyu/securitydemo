package com.mengxuegu.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mengxuegu.web.entites.SysPermission;
import com.mengxuegu.web.entites.SysUser;

import java.util.List;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/9 17:22
 */
public interface SysPermissionService extends IService<SysPermission> {

    /**
     * 通过用户ID获取用户权限
     *
     * @param userId 用户ID
     * @return
     */
    List<SysPermission> findByUserId(Long userId);

    /**
     * 根据ID删除权限及其子权限
     *
     * @param Id
     * @return
     */
    boolean deleteById(Long Id);
}
