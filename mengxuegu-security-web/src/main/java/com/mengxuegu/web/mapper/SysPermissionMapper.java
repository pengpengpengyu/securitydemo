package com.mengxuegu.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mengxuegu.web.entites.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/9 17:22
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 根据用户ID查询用户拥有权限
     *
     * @param userId 用户ID
     * @return
     */
    List<SysPermission> selectSysPermissionsByUserId(@Param("userId") Long userId);
}
