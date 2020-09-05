package com.mengxuegu.security.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

@Slf4j
@Controller
public class CustomLoginController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    /**
     * 跳转登录页面
     *
     * @return
     */
    @RequestMapping(value = "/login/page")
    public String loginPage() {
        return "login";
    }

    /**
     * 获取验证码
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/code/image")
    public void imageCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 获取验证码字符串
        String code = defaultKaptcha.createText();
        log.info("生成的验证码是：{}", code);
        // 验证码字符串存放到session中
        request.getSession().setAttribute(SESSION_KEY, code);

        // 获取验证码图片
        BufferedImage bufferedImage = defaultKaptcha.createImage(code);

        // 将验证码图片写出去
        ServletOutputStream outputStream = response.getOutputStream();

        ImageIO.write(bufferedImage, "jpg", outputStream);
    }

}
