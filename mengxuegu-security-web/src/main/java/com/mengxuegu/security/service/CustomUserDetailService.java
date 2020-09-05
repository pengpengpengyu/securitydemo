package com.mengxuegu.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("输入的用户名是:{}", username);
        String password = passwordEncoder.encode("password");

        // 1.根据用户名查询用户信息
        if (!"admin".equals(username)) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        // 2.查询用户权限

        // 3.封装用户信息，账号密码/资源权限等
        // SpringSecurity底层校验身份合法性


        return new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
    }
}
