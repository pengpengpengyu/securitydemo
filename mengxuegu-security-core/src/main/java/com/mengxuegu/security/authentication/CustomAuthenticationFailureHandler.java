package com.mengxuegu.security.authentication;

import com.mengxuegu.result.MengxueguResult;
import com.mengxuegu.security.properties.LoginResponseType;
import com.mengxuegu.security.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理器
 */
@Component
@Slf4j
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        if (LoginResponseType.JSON.equals(securityProperties.getAuthentication().getLoginType())) {

            MengxueguResult result = MengxueguResult.build(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(result.toJsonString());
        } else {
            // 重定向到认证错误页面
            // 获取上一次请求路径
            String referer = httpServletRequest.getHeader("Referer");
            log.info("referer:{}", referer);
            String lastRul = StringUtils.substringBefore(referer, "?");
            log.info("上次请求路径：{}", lastRul);
            super.setDefaultFailureUrl(lastRul + "?error");
            super.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
        }
    }
}
