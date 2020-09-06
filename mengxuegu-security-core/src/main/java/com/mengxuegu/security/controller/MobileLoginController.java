package com.mengxuegu.security.controller;

import com.mengxuegu.result.MengxueguResult;
import com.mengxuegu.security.authentication.mobile.SmsSend;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/6 13:23
 * @desc 手机验证码登录控制层
 */
@Controller
public class MobileLoginController {

    /**
     * 手机验证码sessionKey
     */
    public static final String SESSION_KEY = "SESSION_KEY_MOBILE_CODE";

    @Autowired
    private SmsSend smsSend;


    /**
     * 跳转手机短信登录页面
     *
     * @return
     */
    @GetMapping(value = "/mobile/page")
    public String toMobilePage() {

        return "login-mobile";
    }

    /**
     * 发送验证码
     *
     * @param request
     * @param mobile
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/code/mobile")
    public MengxueguResult smsCode(HttpServletRequest request, String mobile) {

        String code = RandomStringUtils.randomNumeric(4);
        request.getSession().setAttribute(SESSION_KEY, code);
        smsSend.send(mobile, code);
        return MengxueguResult.ok();
    }
}
