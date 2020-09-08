package com.mengxuegu.security.config;

import com.mengxuegu.security.authentication.code.ImageCodeValidateFilter;
import com.mengxuegu.security.authentication.mobile.MobileAuthenticationConfig;
import com.mengxuegu.security.authentication.mobile.MobileValidateFilter;
import com.mengxuegu.security.properties.AuthenticationPropertis;
import com.mengxuegu.security.properties.SecurityProperties;
import com.mengxuegu.security.authentication.CustomAuthenticationFailureHandler;
import com.mengxuegu.security.authentication.CustomAuthenticationSuccessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;

/**
 * 安全控制中心
 */
@Configuration
@EnableWebSecurity  //启用SpringSecurity过滤链功能
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private ImageCodeValidateFilter imageCodeValidateFilter;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MobileValidateFilter mobileValidateFilter;

    @Autowired
    private MobileAuthenticationConfig mobileAuthenticationConfig;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 设置默认的加密方式
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证管理器：
     * 1、认证信息提供方式（用户名、密码、当前用户的资源权限）
     * 2、可采用内存存储方式，也可能采用数据库方式等
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*// 密码加密处理
        String password = passwordEncoder().encode("password");
        logger.info("加密后的密码={}", password);
        // 用户信息存在内存中
        auth.inMemoryAuthentication().withUser("admin").password(password)
                .authorities("ADMIN");*/
        auth.userDetailsService(customUserDetailsService);
    }


    /**
     * 资源权限配置（过滤器链）:
     * 1、被拦截的资源
     * 2、资源所对应的角色权限
     * 3、定义认证方式：httpBasic 、httpForm
     * 4、定制登录页面、登录请求地址、错误处理方式
     * 5、自定义 spring security 过滤器
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http.formLogin()
                .loginPage("/login/page")  // 登陆页面交给/login/page
                .loginProcessingUrl("/login/form")  // 登录表单url，默认/login
                .usernameParameter("name")  // 登录表单用户名参数，默认username
                .passwordParameter("pwd")  // 登录表单密码参数, 默认password
                .and()
                .authorizeRequests()// 认证请求
                .antMatchers("/login/page").permitAll()
                .anyRequest().authenticated()  // 所有进入应用的HTTP请求都要经过认证
        .and()
        .csrf().disable()  // 禁用csrf
        ;*/
        AuthenticationPropertis authenticationPropertis = securityProperties.getAuthentication();
        http.addFilterBefore(mobileValidateFilter, UsernamePasswordAuthenticationFilter.class)  // 在验证用户名密码之前先执行手机号验证
                .addFilterBefore(imageCodeValidateFilter, UsernamePasswordAuthenticationFilter.class)  // 在验证用户名密码之前先执行验证码过滤器
                .formLogin()
                .loginPage(authenticationPropertis.getLoginPage())  // 登录页面url
                .loginProcessingUrl(authenticationPropertis.getLoginProcessingUrl())  // 认证页面url
                .usernameParameter(authenticationPropertis.getUsernameParameter())  // 登录请求userName参数
                .passwordParameter(authenticationPropertis.getPasswordParameter())  // 登录请求password参数
                .successHandler(customAuthenticationSuccessHandler)  // 请求成功处理handler
                .failureHandler(customAuthenticationFailureHandler)  // 请求失败处理handler
                .and()
                .authorizeRequests()
                .antMatchers(authenticationPropertis.getPermitUrls()).permitAll()  // 开放登录请求页面等，不需要认证
                .anyRequest().authenticated()
                .and()
                .csrf().disable()  // 禁用csrf
                .rememberMe()
                .tokenRepository(jdbcTokenRepository())  // 保存登录信息
                .tokenValiditySeconds(60 * 60 * 24 * 7);  // 记住我时长7天

        // 将手机号相关的配置绑定过滤器链上
        http.apply(mobileAuthenticationConfig);
    }

    /**
     * 释放静态资源
     *
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(securityProperties.getAuthentication().getStaticPaths());
    }

    /**
     * 用于remeber me
     *
     * @return
     */
    @Bean
    public JdbcTokenRepositoryImpl jdbcTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // jdbcTokenRepository.setCreateTableOnStartup(true);  // 启动时创建记录rember me的数据表
        return jdbcTokenRepository;
    }
}
