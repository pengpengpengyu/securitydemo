package com.mengxuegu.security.authentication.mobile;

import org.apache.commons.lang.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/6 17:04
 * @desc 校验手机号过滤器
 */

public class MobileAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private String mobileParameter = "mobile";
    private boolean postOnly = true;

    public MobileAuthenticationFilter() {
        super(new AntPathRequestMatcher("/mobile/form", "POST"));
    }

    protected MobileAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException {

        if (postOnly && !httpServletRequest.getMethod().equalsIgnoreCase("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported:" + httpServletRequest.getMethod());
        }

        // 从请求中获取手机号码，来验证手机号是否有效
        String mobile = obtainMobile(httpServletRequest);

        mobile = StringUtils.isBlank(mobile) ? "" : mobile.trim();
        MobileAuthenticationToken token = new MobileAuthenticationToken(mobile);

        setDetails(httpServletRequest, token);
        return this.getAuthenticationManager().authenticate(token);
    }


    /**
     * 获取请求中的手机号码
     *
     * @param request
     * @return
     */
    @Nullable
    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }

    /**
     * 将请求中的sessionID和host主机ip放到MobileAuthenticationToken
     * @param request
     * @param authenticationToken
     */
    protected void setDetails(HttpServletRequest request, MobileAuthenticationToken authenticationToken) {
        authenticationToken.setDetails(authenticationDetailsSource.buildDetails(request));
    }


    public String getMobileParameter() {
        return mobileParameter;
    }

    public void setMobileParameter(String mobileParameter) {
        this.mobileParameter = mobileParameter;
    }

    public boolean isPostOnly() {
        return postOnly;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
}
