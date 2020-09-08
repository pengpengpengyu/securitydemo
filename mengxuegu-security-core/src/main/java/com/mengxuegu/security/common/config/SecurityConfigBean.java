package com.mengxuegu.security.common.config;

import com.mengxuegu.security.authentication.mobile.SmsCodeSender;
import com.mengxuegu.security.authentication.mobile.SmsSend;
import com.mengxuegu.security.authentication.session.CustomInvalidSessionStrategy;
import com.mengxuegu.security.authentication.session.CustomSessionInformationExpiredStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/6 13:06
 */
@Configuration
public class SecurityConfigBean {

    @Bean
    @ConditionalOnMissingBean(SmsSend.class)  // 默认使用SmsCodeSender，如果容器中有其他SmsSend实例，则当前配置失效
    public SmsSend smsSend() {
        return new SmsCodeSender();
    }

    /**
     * 注入session失效策略实例
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy InvalidSessionStrategy() {
        return new CustomInvalidSessionStrategy();
    }

    /**
     * 注入同一个用户多出登录踢出用户bean
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new CustomSessionInformationExpiredStrategy();
    }
}
