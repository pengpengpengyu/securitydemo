package com.mengxuegu.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengxuegu.web.entites.SysRole;
import org.apache.ibatis.annotations.Param;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/9 17:20
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 分页查询角色信息
     *
     * @param page 分页对象
     * @param sysRole
     * @return
     */
    IPage<SysRole> selectPage(@Param("page") Page<SysRole> page, @Param("p") SysRole sysRole);
}
