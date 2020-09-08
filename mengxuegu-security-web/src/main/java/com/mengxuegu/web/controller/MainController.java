package com.mengxuegu.web.controller;

import com.alibaba.fastjson.JSON;
import com.mengxuegu.security.common.utils.SpringSecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(value = "/main")
public class MainController {

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }
}
