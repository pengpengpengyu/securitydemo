package com.mengxuegu.web.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mengxuegu.web.entites.SysRole;
import com.mengxuegu.web.mapper.SysRoleMapper;
import com.mengxuegu.web.service.SysRoleService;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {


    /**
     * 分页查询角色信息
     *
     * @param page
     * @param sysRole
     * @return
     */
    @Override
    public IPage<SysRole> findPage(Page<SysRole> page, SysRole sysRole) {
        IPage<SysRole> iPage = baseMapper.selectPage(page, sysRole);
        return iPage;
    }


}
