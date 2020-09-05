package com.mengxuegu.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Security认证参数配置类
 */
@Component
@ConfigurationProperties(prefix = "mengxuegu.security")
@Data
public class SecurityProperties {

    private AuthenticationPropertis authentication;
}
