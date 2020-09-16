package com.mengxuegu.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mengxuegu.web.entites.SysRole;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/9 17:22
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 分页查询角色信息
     *
     * @param sysRole
     * @return
     */
    IPage<SysRole> findPage(Page<SysRole> page, SysRole sysRole);

    /**
     * 根据角色ID查询角色信息(封装对应权限)
     *
     * @param roleId
     * @return
     */
    SysRole findById(Long roleId);

    /**
     * 删除角色(同时删除角色与用户关联数据)
     *
     * @param roleId
     * @return
     */
    boolean deleteById(Long roleId);


}
