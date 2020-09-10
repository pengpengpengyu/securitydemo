package com.mengxuegu.security.service;

import com.mengxuegu.web.entites.SysUser;
import com.mengxuegu.web.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/6 20:17
 */
@Slf4j
@Service
public class MobileUserDetailsService extends AbstractUserDetailsService {


    @Autowired
    private SysUserMapper sysUserMapper;


    /**
     * 获取须认证的用户
     *
     * @param mobile
     * @return
     */
    @Override
    public SysUser findSysuser(String mobile) {
        log.info("请求认证的手机号是:{}", mobile);
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", mobile);
        SysUser sysUser = sysUserMapper.selectSysUserInfo(params);

        if (null == sysUser) {
            throw new UsernameNotFoundException(String.format("手机号%s尚未注册", mobile));
        }
        return sysUser;
    }


}
