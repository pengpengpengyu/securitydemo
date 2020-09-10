package com.mengxuegu.security.service;

import com.mengxuegu.web.entites.SysUser;
import com.mengxuegu.web.mapper.SysUserMapper;
import com.mengxuegu.web.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
public class CustomUserDetailsService extends AbstractUserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 获取须认证的用户
     *
     * @param username
     * @return
     */
    @Override
    public SysUser findSysuser(String username) {
        log.info("请求认证的用户是:{}", username);
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        SysUser sysUser = sysUserMapper.selectSysUserInfo(params);

        if (null == sysUser) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return sysUser;
    }


}
