package com.mengxuegu.security.authentication;

import com.mengxuegu.result.MengxueguResult;
import com.mengxuegu.security.authentication.listener.AuthenticationSuccessListener;
import com.mengxuegu.security.properties.LoginResponseType;
import com.mengxuegu.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证成功处理器
 */
@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {


    @Autowired
    private SecurityProperties securityProperties;

    // 有则注入，无则不注入
    @Autowired(required = false)
    private AuthenticationSuccessListener authenticationSuccessListener;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

    }

    /**
     * 认证成功后处理逻辑
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param authentication      封装了用户信息UserDetails 访问ip等信息
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        // 认证成功后，加载此用户拥有的权限
        if (null != authenticationSuccessListener) {
            authenticationSuccessListener.successListener(httpServletRequest, httpServletResponse, authentication);
        }

        if (LoginResponseType.JSON.equals(securityProperties.getAuthentication().getLoginType())) {

            MengxueguResult result = new MengxueguResult("请求成功");
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(result.toJsonString());
        } else {
            super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
        }
    }
}
