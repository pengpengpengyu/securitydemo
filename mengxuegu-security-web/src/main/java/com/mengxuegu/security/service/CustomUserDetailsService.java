package com.mengxuegu.security.service;

import com.mengxuegu.web.entites.SysUser;
import com.mengxuegu.web.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CustomUserDetailsService extends AbstractUserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取须认证的用户
     *
     * @param username
     * @return
     */
    @Override
    public SysUser findSysuser(String username) {
        log.info("请求认证的用户是:{}", username);
        return sysUserService.findByUserName(username);
    }


}
