package com.mengxuegu.security.config;

import com.mengxuegu.security.authentication.mobile.SmsCodeSender;
import com.mengxuegu.security.authentication.mobile.SmsSend;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
