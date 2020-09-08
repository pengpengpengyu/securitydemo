package com.mengxuegu.security.common.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/8 15:05
 * @desc SpringSecurity工具类
 */
public class SpringSecurityUtil {

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    public static Authentication getUserDetails() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    public static Object getPrincipal() {
        return getUserDetails().getPrincipal();
    }
}
