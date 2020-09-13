package com.mengxuegu.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mengxuegu.web.entites.SysPermission;
import com.mengxuegu.web.mapper.SysPermissionMapper;
import com.mengxuegu.web.service.SysPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 权限服务实现类
 */
@Service
public class SysPermissionImpl extends ServiceImpl<SysPermissionMapper, SysPermission>
        implements SysPermissionService {


    /**
     * 通过用户ID获取用户权限
     *
     * @param userId 用户ID
     * @return
     */
    @Override
    public List<SysPermission> findByUserId(Long userId) {
        if (null == userId) {
            return null;
        }
        return baseMapper.selectSysPermissionsByUserId(userId);
    }

    /**
     * 根据ID删除权限及其子权限
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id)
                .or()
                .eq("parent_id", id);

        return baseMapper.delete(queryWrapper) > 0;
    }
}
