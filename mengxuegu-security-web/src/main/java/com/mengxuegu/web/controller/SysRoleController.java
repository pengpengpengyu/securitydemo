package com.mengxuegu.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengxuegu.result.MengxueguResult;
import com.mengxuegu.web.entites.SysRole;
import com.mengxuegu.web.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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

    /**
     * 跳转新增修改页面
     *
     * @param id
     * @return
     */
    @GetMapping(value = {"/form", "/form/{id}"})
    @PreAuthorize("hasAnyAuthority('sys:role:add','sys:role:edit')")
    public ModelAndView form(@PathVariable(value = "id", required = false) Long id) {
        ModelAndView mv = new ModelAndView(HTML_PREFIX + "role-form");

        SysRole sysRole = sysRoleService.findById(id);

        mv.addObject("role", sysRole);

        return mv;
    }

    /**
     * 修改或新增角色
     *
     * @param sysRole
     * @return
     */
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    @PreAuthorize("hasAnyAuthority('sys:role:add', 'sys:role:edit')")
    public ModelAndView saveOrUpdate(SysRole sysRole) {
        ModelAndView mv = new ModelAndView(HTML_PREFIX + "role-list");

        sysRoleService.saveOrUpdate(sysRole);

        return mv;
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    public Object delete(@PathVariable("id") Long id) {
        sysRoleService.deleteById(id);
        return MengxueguResult.ok();
    }
}
