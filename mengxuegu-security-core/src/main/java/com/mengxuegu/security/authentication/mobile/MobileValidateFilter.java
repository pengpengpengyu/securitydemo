package com.mengxuegu.security.authentication.mobile;

import com.mengxuegu.security.authentication.CustomAuthenticationFailureHandler;
import com.mengxuegu.security.authentication.exception.ValidateCodeException;
import com.mengxuegu.security.controller.MobileLoginController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/6 16:49
 */
@Component
public class MobileValidateFilter extends OncePerRequestFilter {

    private static final String MOBILE_CODE_PARMETER = "code";

    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if ("/mobile/form".equalsIgnoreCase(request.getRequestURI())
                && "post".equalsIgnoreCase(request.getMethod())) {
            try {
                validateMobileCode(request);
            } catch (AuthenticationException e) {
                customAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }

        // 非手机验证码登录直接放行
        filterChain.doFilter(request, response);
    }


    /**
     * 校验短信验证码
     *
     * @param request
     * @return
     */
    private boolean validateMobileCode(HttpServletRequest request) {
        // session中存放的验证码
        String sessionCode = (String) request.getSession().getAttribute(MobileLoginController.SESSION_KEY);
        // 用户输入的手机验证码
        String inputCode = (String) request.getParameter(MOBILE_CODE_PARMETER);

        if (StringUtils.isBlank(inputCode)) {
            throw new ValidateCodeException("短信验证码不能为空");
        }

        if (!inputCode.equalsIgnoreCase(sessionCode)) {
            throw new ValidateCodeException("短信验证码输入错误");
        }

        request.getSession().removeAttribute(MobileLoginController.SESSION_KEY);

        return true;
    }
}
