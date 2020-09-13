package com.mengxuegu.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengxuegu.result.MengxueguResult;
import com.mengxuegu.web.entites.SysRole;
import com.mengxuegu.web.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping(value = "/role")
@Slf4j
public class SysRoleController {

    /**
     * 网页前缀
     */
    private static final String HTML_PREFIX = "system/role/";

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 角色列表页面
     *
     * @return
     */
    @GetMapping(value = {"/", ""})
    @PreAuthorize("hasAnyAuthority('sys:role')")
    public ModelAndView userPage() {
        ModelAndView mv = new ModelAndView(HTML_PREFIX + "role-list");
        return mv;
    }

    /**
     * 分页获取角色列表
     *
     * @param page
     * @param sysRole
     * @return
     */
    @PostMapping(value = "/page")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public Object page(Page<SysRole> page, SysRole sysRole) {
        return MengxueguResult.ok(sysRoleService.findPage(page, sysRole));
    }

}
