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

    /**
     * 根据角色ID查询对应权限
     *
     * @param roleId
     * @return
     */
    List<SysPermission> selectSysPermissionsByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据角色Id删除对应权限
     *
     * @param roleId
     * @return
     */
    int deleteRolePermissionByRoleId(@Param("roleId") Long roleId);

    /**
     * 新增角色对应的权限
     *
     * @param roleId
     * @param perIds
     * @return
     */
    int saveRolePermission(@Param("roleId") Long roleId, @Param("perIds") List<Long> perIds);
}
