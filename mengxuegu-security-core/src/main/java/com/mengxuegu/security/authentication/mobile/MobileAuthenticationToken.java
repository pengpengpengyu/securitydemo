package com.mengxuegu.security.authentication.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/6 17:28
 * @desc 手机登录认证Token
 */
public class MobileAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    // 认证之前放手机号码，认证之后放用户信息
    private final Object principal;

    /**
     * 开始认证时，创建一个MobileAuthenticationToken实例，接受的是手机号码，并且表示未认证
     *
     * @param principal
     */
    public MobileAuthenticationToken(Object principal) {
        super(null);
        this.principal = principal;  // 手机号
        setAuthenticated(false);
    }


    /**
     * 当通过认证后，会创建一个新的MobileAuthenticationToken ，来标识认证已通过
     *
     * @param principal
     * @param authorities
     */
    public MobileAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;  // 用户信息
        super.setAuthenticated(true); // 标识已经认证通过
    }

    /**
     * Checks the {@code credentials}, {@code principal} and {@code details} objects,
     * invoking the {@code eraseCredentials} method on any which implement
     * {@link }.
     */
    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

    /**
     * 在弗雷中是一个抽象方法，所以要实现，但是它是密码，而当前不需要，所以直接返回null
     *
     * @return the credentials that prove the identity of the <code>Principal</code>
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * 手机号码
     * The identity of the principal being authenticated. In the case of an authentication
     * request with username and password, this would be the username. Callers are
     * expected to populate the principal for an authentication request.
     * <p>
     * The <tt>AuthenticationManager</tt> implementation will often return an
     * <tt>Authentication</tt> containing richer information as the principal for use by
     * the application. Many of the authentication providers will create a
     * {@code UserDetails} object as the principal.
     *
     * @return the <code>Principal</code> being authenticated or the authenticated
     * principal after authentication.
     */
    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticationed) throws IllegalArgumentException {
        if (isAuthenticationed) {
            throw new IllegalArgumentException("Cannot set this token to trused-use constructor which takes a GrantedAuthority list instad");
        }
        super.setAuthenticated(false);
    }
}
