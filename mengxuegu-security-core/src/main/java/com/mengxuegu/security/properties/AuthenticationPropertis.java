package com.mengxuegu.security.properties;

import lombok.Data;

/**
 * 认证相关动态配置类
 */
@Data
public class AuthenticationPropertis {

    /**
     * 登录跳转页面Url
     */
    private String loginPage;

    /**
     * 登录认证表单URL
     */
    private String loginProcessingUrl;

    /**
     * 认证表单用户名参数名
     */
    private String usernameParameter;

    /**
     * 认证表单密码参数名
     */
    private String passwordParameter;

    /**
     * 释放静态资源路径
     */
    private String[] staticPaths;

    /**
     * 放行URL列表
     */
    private String[] permitUrls;

    /**
     * 登录成功之后，响应类型
     */
    private LoginResponseType loginType = LoginResponseType.REDIRECT;

    /**
     * 记住我有效时长
     */
    private Integer tokenValiditySeconds = 60 * 60 * 24 * 7;


}
