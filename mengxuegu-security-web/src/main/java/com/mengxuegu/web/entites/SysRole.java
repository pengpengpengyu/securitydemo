package com.mengxuegu.web.entites;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/9 15:49
 * @desc 权限实体类
 */
@Data
@TableName(value = "sys_role")
public class SysRole implements Serializable {

    @TableId(type = IdType.AUTO)
    private long id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 备注
     */
    private String remark;
    private Date createDate;
    private Date updateDate;

    /**
     * 当前角色拥有的权限列表
     * 修改角色时使用
     */
    @TableField(exist = false)
    private List<SysPermission> permissions = new ArrayList<>();

    /**
     * 当前角色拥有的权限Id列表
     * 修改角色时使用
     */
    @TableField(exist = false)
    private List<Long> perIds = new ArrayList<>();

    public List<Long> getPerIds () {
        return permissions.stream().map(SysPermission::getId).collect(Collectors.toList());
    }

}
