package com.mengxuegu.security.authentication.mobile;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/6 13:02
 */
@Slf4j
public class SmsCodeSender implements SmsSend {

    @Override
    public boolean send(String mobile, String content) {

        String smsContent = String.format("用户您好，验证码：%s, 请勿泄露他人", content);
        log.info("向手机号{}发送的短信为：{}", mobile, smsContent);
        return true;
    }
}
