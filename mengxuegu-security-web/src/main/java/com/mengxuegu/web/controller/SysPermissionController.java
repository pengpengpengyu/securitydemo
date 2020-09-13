package com.mengxuegu.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mengxuegu.result.MengxueguResult;
import com.mengxuegu.web.entites.SysPermission;
import com.mengxuegu.web.service.SysPermissionService;
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
@RequestMapping(value = "/permission")
@Slf4j
public class SysPermissionController {

    /**
     * 网页前缀
     */
    private static final String HTML_PREFIX = "system/permission/";

    @Autowired
    private SysPermissionService sysPermissionService;

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

    /**
     * 获取所有资源权限列表
     *
     * @return
     */
    @PostMapping(value = "/list")
    @PreAuthorize("hasAnyAuthority('sys:permission:list')")
    public MengxueguResult list() {
        return MengxueguResult.ok(sysPermissionService.list());
    }

    /**
     * 跳转权限新增或修改页面
     *
     * @param id
     * @return
     */
    @GetMapping(value = {"/form", "/form/{id}"})
    @PreAuthorize("hasAnyAuthority('sys:permission:add','sys:permission:edit')")
    public ModelAndView form(@PathVariable(value = "id", required = false) Long id) {
        ModelAndView mv = new ModelAndView(HTML_PREFIX + "permission-form");
        SysPermission sysPermission = new SysPermission();
        if (null != id) {
            sysPermission = sysPermissionService.getById(id);
        }
        mv.addObject("permission", sysPermission);

        return mv;
    }


    /**
     * 新增或修改权限
     *
     * @param sysPermission
     * @return
     */
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.POST})
    @PreAuthorize("hasAnyAuthority('sys:permission:add', 'sys:permisssion:edit')")
    public ModelAndView saveOrUpdate(SysPermission sysPermission) {
        sysPermissionService.saveOrUpdate(sysPermission);
        ModelAndView mv = new ModelAndView(HTML_PREFIX + "permission-list");
        return mv;
    }

    /**
     * 删除资源及其子资源
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('sys:permission:delete')")
    public Object delete(@PathVariable("id") Long id) {
        sysPermissionService.deleteById(id);
        return MengxueguResult.ok();
    }
}
