package com.mengxuegu.security.service;

import com.mengxuegu.web.entites.SysPermission;
import com.mengxuegu.web.entites.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * userDetailsService抽象类,用于简化代码
 */
public abstract class AbstractUserDetailsService implements UserDetailsService {

    /**
     * 获取须认证的用户
     *
     * @param userNameOrMobile
     * @return
     */
    public abstract SysUser findSysuser(String userNameOrMobile);

    @Override
    public UserDetails loadUserByUsername(String userNameOrMobile) throws UsernameNotFoundException {
        // 获取用户
        SysUser sysUser = findSysuser(userNameOrMobile);

        // 查询权限
        List<SysPermission> permissions = sysUser.getPermissions();
        if (CollectionUtils.isEmpty(permissions)) {
            return sysUser;
        }

        // 封装权限
        // sysUser.setPermissions(permissions);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities = permissions.stream().map(p -> new SimpleGrantedAuthority(p.getCode()))
                .collect(Collectors.toList());
        // 封装角色，角色需要加前缀ROLE_,使用的时候不用加
        authorities.addAll(sysUser.getSysRoles().stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r.getCode())).collect(Collectors.toList()));
        sysUser.setAuthorities(authorities);
        return sysUser;
    }
}
