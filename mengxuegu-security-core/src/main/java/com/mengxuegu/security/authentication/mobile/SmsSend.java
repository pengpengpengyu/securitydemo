package com.mengxuegu.security.authentication.mobile;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/6 13:01
 */
public interface SmsSend {

    /**
     * 发送消息
     *
     * @param mobile
     * @param content
     * @return
     */
    boolean send(String mobile, String content);
}
