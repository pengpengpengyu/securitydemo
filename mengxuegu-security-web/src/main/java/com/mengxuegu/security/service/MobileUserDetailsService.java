package com.mengxuegu.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/6 20:17
 */
@Slf4j
@Service public class MobileUserDetailsService implements UserDetailsService {
    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * 通过手机号查询用户信息
     *
     * @param mobile the mobile identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        log.info("请求的手机号是：{}", mobile);

        // 1. 通过手机号查询用户信息
        // 2.如果有此用户，则查询用户权限
        // 3.封装用户信息

        // 后期走数据库查询之后mobile参数应该放用户名，否则remember-me功能无效
        return new User("admin","",true,true,true,
                true, AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
    }
}
