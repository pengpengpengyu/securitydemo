package com.mengxuegu.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mengxuegu.web.entites.SysPermission;
import com.mengxuegu.web.mapper.SysPermissionMapper;
import com.mengxuegu.web.service.SysPermissionService;
import org.springframework.stereotype.Service;

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
}
