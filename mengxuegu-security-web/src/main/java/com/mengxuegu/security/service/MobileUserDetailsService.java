package com.mengxuegu.security.service;

import com.mengxuegu.web.entites.SysUser;
import com.mengxuegu.web.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/6 20:17
 */
@Slf4j
@Service
public class MobileUserDetailsService extends AbstractUserDetailsService {

    @Autowired
    private SysUserService sysUserService;



    /**
     * 获取须认证的用户
     *
     * @param mobile
     * @return
     */
    @Override
    public SysUser findSysuser(String mobile) {
        log.info("请求认证的手机号是:{}", mobile);
        return sysUserService.findByUserMobile(mobile);
    }


}
