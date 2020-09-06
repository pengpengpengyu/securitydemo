package com.mengxuegu.security;

import com.mengxuegu.security.authentication.mobile.SmsSend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/6 13:17
 */
@Slf4j
@Component
public class MobilSmsCodeSender implements SmsSend {
    /**
     * 发送消息
     *
     * @param mobile
     * @param content
     * @return
     */
    @Override
    public boolean send(String mobile, String content) {

        log.info("Web应用新的短信验证码接口---向手机号{}发送了验证码为：{}", mobile, content);
        return false;
    }
}
