package com.mengxuegu.security.service;

import com.mengxuegu.web.entites.SysPermission;
import com.mengxuegu.web.entites.SysUser;
import com.mengxuegu.web.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * userDetailsService抽象类,用于简化代码
 */
public abstract class AbstractUserDetailsService implements UserDetailsService {

    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 获取须认证的用户
     * @param userNameOrMobile
     * @return
     */
    public abstract SysUser findSysuser(String userNameOrMobile);

    @Override
    public UserDetails loadUserByUsername(String userNameOrMobile) throws UsernameNotFoundException {
        // 获取用户
        SysUser sysUser = findSysuser(userNameOrMobile);
        if (null == sysUser) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        // 查询权限
        List<SysPermission> permissions = sysPermissionService.findByUserId(sysUser.getId());
        if (CollectionUtils.isEmpty(permissions)) {
            return sysUser;
        }

        // 封装权限
        sysUser.setPermissions(permissions);
        sysUser.setAuthorities(permissions.stream().map(p -> new SimpleGrantedAuthority(p.getCode()))
        .collect(Collectors.toList()));
        return sysUser;
    }
}
