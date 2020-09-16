package com.mengxuegu.web.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mengxuegu.web.common.exception.ServiceException;
import com.mengxuegu.web.entites.SysPermission;
import com.mengxuegu.web.entites.SysRole;
import com.mengxuegu.web.mapper.SysPermissionMapper;
import com.mengxuegu.web.mapper.SysRoleMapper;
import com.mengxuegu.web.mapper.SysUserMapper;
import com.mengxuegu.web.service.SysRoleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {


    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysUserMapper sysUserMapper;


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

    /**
     * 根据角色ID查询角色信息(封装对应权限)
     *
     * @param roleId
     * @return
     */
    @Override
    public SysRole findById(Long roleId) {
        if (null == roleId) {
            return new SysRole();
        }

        List<SysPermission> permissions = sysPermissionMapper.selectSysPermissionsByRoleId(roleId);
        SysRole sysRole = baseMapper.selectById(roleId);
        sysRole.setPermissions(permissions);

        return sysRole;
    }


    /**
     * 修改或更新角色
     *
     * @param sysRole
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdate(SysRole sysRole) {
        sysRole.setUpdateDate(new Date());
        boolean flag = super.saveOrUpdate(sysRole);
        if (flag) {
            sysPermissionMapper.deleteRolePermissionByRoleId(sysRole.getId());
            if (CollectionUtils.isNotEmpty(sysRole.getPerIds())) {
                sysPermissionMapper.saveRolePermission(sysRole.getId(), sysRole.getPerIds());
            }
        }
        return flag;
    }


    /**
     * 删除角色(同时删除角色与用户关联数据)
     *
     * @param roleId
     * @return
     */
    @Override
    public boolean deleteById(Long roleId) {
        if (sysUserMapper.selectUserCountByRoleId(roleId) > 0) {
            throw new ServiceException("该角色存在有效关联用户,无法删除");
        }

        baseMapper.deleteById(roleId);
        baseMapper.deleteUserRoleByRoleId(roleId);
        sysPermissionMapper.deleteRolePermissionByRoleId(roleId);
        return true;
    }

}
