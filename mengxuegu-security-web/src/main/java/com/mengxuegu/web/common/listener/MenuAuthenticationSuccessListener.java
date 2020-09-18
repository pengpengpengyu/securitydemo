package com.mengxuegu.web.common.listener;

import com.mengxuegu.security.authentication.listener.AuthenticationSuccessListener;
import com.mengxuegu.web.entites.SysPermission;
import com.mengxuegu.web.entites.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangpengyu
 * @version 1.0-SNAPSHOT
 * @date 2020/9/18 11:18
 * @desc 认证成功后触发
 */
@Slf4j
@Component
public class MenuAuthenticationSuccessListener implements AuthenticationSuccessListener {
    /**
     * 认证成功后调用此方法
     *
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void successListener(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        if (null != principal && principal instanceof SysUser) {
            SysUser sysUser = (SysUser) principal;
            this.loadMenuTree(sysUser);
        }

        Object newPrincaipal = authentication.getPrincipal();
        log.info("newPrincaipa+++++++++++++++++==", newPrincaipal);
    }

    /**
     * 重组父子菜单
     *
     * @param sysUser
     */
    private void loadMenuTree(SysUser sysUser) {

        List<SysPermission> permissions = sysUser.getPermissions();
        permissions = permissions.stream().sorted(Comparator.comparing(SysPermission::getId)).collect(Collectors.toList());
        // 无权限
        if (CollectionUtils.isEmpty(permissions)) {
            return;
        }

        List<SysPermission> menuList = permissions.stream().filter(t -> 1 == t.getType()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(menuList)) {
            return;
        }
        // 设置子菜单
        this.buildChilder(menuList);
        sysUser.setPermissions(menuList.stream().filter(t -> t.getParentId().equals(0L)).collect(Collectors.toList()));
    }

    /**
     * 获取子菜单
     *
     * @param list
     */
    private void buildChilder(List<SysPermission> list) {

        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        list.forEach(t -> {
            List<String> childUrl = new ArrayList<>();
            List<SysPermission> childMenu = new ArrayList<>();
            list.forEach(h -> {
                if (h.getParentId().equals(t.getId())) {
                    childMenu.add(h);
                    childUrl.add(h.getUrl());
                }
            });
            t.setChildren(childMenu);
            t.setChildrenUrl(childUrl);
        });
    }
}
