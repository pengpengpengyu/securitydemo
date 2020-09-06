package com.mengxuegu.security.authentication.mobile;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/6 19:57
 * @desc 提供给底层ProviderManager使用
 */
public class MobileAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    /**
     * Performs authentication with the same contract as
     * {@link AuthenticationManager#authenticate(Authentication)}
     * .
     * 处理认证：
     * 1.通过手机号去数据库查询用户信息（UserDetailService）
     * 2.再重新构建认证信息
     *
     * @param authentication the authentication request object.
     * @return a fully authenticated object including credentials. May return
     * <code>null</code> if the <code>AuthenticationProvider</code> is unable to support
     * authentication of the passed <code>Authentication</code> object. In such a case,
     * the next <code>AuthenticationProvider</code> that supports the presented
     * <code>Authentication</code> class will be tried.
     * @throws AuthenticationException if authentication fails.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        MobileAuthenticationToken mobileAuthenticationToken = (MobileAuthenticationToken) authentication;

        // 获取用户手机号
        String mobile = (String) mobileAuthenticationToken.getPrincipal();

        // 查询数据库
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);

        if (null == userDetails) {
            throw new AuthenticationServiceException("该手机未注册");
        }

        // 查询到用户信息，则认证通过。重新构建MobileAuthenticationToken实例
        MobileAuthenticationToken authenticationToken
                = new MobileAuthenticationToken(userDetails, userDetails.getAuthorities());

        authenticationToken.setDetails(mobileAuthenticationToken.getDetails());

        return authentication;
    }

    /**
     * Returns <code>true</code> if this <Code>AuthenticationProvider</code> supports the
     * indicated <Code>Authentication</code> object.
     * <p>
     * Returning <code>true</code> does not guarantee an
     * <code>AuthenticationProvider</code> will be able to authenticate the presented
     * instance of the <code>Authentication</code> class. It simply indicates it can
     * support closer evaluation of it. An <code>AuthenticationProvider</code> can still
     * return <code>null</code> from the {@link #authenticate(Authentication)} method to
     * indicate another <code>AuthenticationProvider</code> should be tried.
     * </p>
     * <p>
     * Selection of an <code>AuthenticationProvider</code> capable of performing
     * authentication is conducted at runtime the <code>ProviderManager</code>.
     * </p>
     * 通过此方法来判断采用哪一个AuthenticationProvider
     *
     * @param authentication
     * @return <code>true</code> if the implementation can more closely evaluate the
     * <code>Authentication</code> class presented
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }


    /**
     * 注入MobileUserDetailService
     * @param userDetailsService
     */
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
   }
}
