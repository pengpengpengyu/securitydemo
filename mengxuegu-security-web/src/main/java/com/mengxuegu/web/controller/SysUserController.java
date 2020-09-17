package com.mengxuegu.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengxuegu.result.MengxueguResult;
import com.mengxuegu.web.entites.SysRole;
import com.mengxuegu.web.entites.SysUser;
import com.mengxuegu.web.mapper.SysUserMapper;
import com.mengxuegu.web.service.SysRoleService;
import com.mengxuegu.web.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/9 13:32
 * @desc 用户控制器
 */
@RestController
@RequestMapping(value = "/user")
@Slf4j
public class SysUserController {

    /**
     * 网页前缀
     */
    private static final String HTML_PREFIX = "system/user/";

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 用户列表页面
     *
     * @return
     */
    @GetMapping(value = {"/", ""})
    @PreAuthorize("hasAuthority('sys:user')")
    public ModelAndView userPage() {
        ModelAndView mv = new ModelAndView(HTML_PREFIX + "user-list");
        return mv;
    }


    /**
     * 获取用户列表
     *
     * @param sysUser
     * @return
     */
    @PostMapping(value = "/page")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public Object page(SysUser sysUser, Page<SysUser> page) {
        return MengxueguResult.ok(sysUserService.findPage(sysUser, page));
    }

    /**
     * 跳转新增或修改用户页面
     *
     * @param id
     * @return
     */
    @GetMapping(value = {"/form", "/form/{id}"})
    @PreAuthorize("hasAnyAuthority('sys:user:edit', 'sys:user:add')")
    public ModelAndView form(@PathVariable(value = "id", required = false) Long id) {
        ModelAndView mv = new ModelAndView(HTML_PREFIX + "user-form");
        List<SysRole> roles = sysRoleService.list();
        Map<String, Object> params = new HashMap<>();
        params.put("userId", id);
        SysUser sysUser = sysUserMapper.selectSysUserInfo(params);
        mv.addObject("user", sysUser);
        mv.addObject("roleList", roles);
        return mv;
    }

    /**
     * 修改或新增用户信息
     *
     * @param sysUser
     * @return
     */
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    @PreAuthorize("hasAnyAuthority('sys:user:edit', 'sys:user:add')")
    public ModelAndView saveOrUpdate(SysUser sysUser) {
        ModelAndView modelAndView = new ModelAndView(HTML_PREFIX + "user-list");
        sysUserService.saveOrUpdate(sysUser);
        return modelAndView;
    }

    /**
     * 删除用户信息(逻辑删除)
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public Object delete(@PathVariable("id") Long id) {
        SysUser sysUser = sysUserService.getById(id);
        sysUser.setEnabled(false);
        sysUserService.updateById(sysUser);
        return MengxueguResult.ok();
    }
}
