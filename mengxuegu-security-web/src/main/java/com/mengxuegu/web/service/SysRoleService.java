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
}
