package com.mengxuegu.security.authentication.listener;

import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wangpengyu
 * @version 1.0-SNAPSHOT
 * @date 2020/9/18 11:12
 * @desc 认证成功监听器
 */
public interface AuthenticationSuccessListener {
    /**
     * 认证成功后调用此方法
     *
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    void successListener(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException;
}
