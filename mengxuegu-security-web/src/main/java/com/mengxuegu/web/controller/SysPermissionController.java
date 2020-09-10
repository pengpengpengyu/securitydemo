package com.mengxuegu.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/9 13:32
 * @desc 角色控制器
 */
@RestController
@RequestMapping(value = "/permission")
@Slf4j
public class SysPermissionController {

    /**
     * 网页前缀
     */
    private static final String HTML_PREFIX = "system/permission/";

    /**
     * 角色列表页面
     *
     * @return
     */
    @GetMapping(value = {"/", ""})
    @PreAuthorize("hasAnyAuthority('sys:permission')")
    public ModelAndView userPage() {
        ModelAndView mv = new ModelAndView(HTML_PREFIX + "permission-list");
        return mv;
    }
}
