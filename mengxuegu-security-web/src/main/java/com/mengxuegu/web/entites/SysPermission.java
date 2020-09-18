package com.mengxuegu.web.entites;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/9 16:50
 * @desc 权限类
 */
@Data
@TableName(value = "sys_permission")
public class SysPermission implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 父级Id
     */
    private Long parentId;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限标识符
     */
    private String code;

    /**
     * 权限路径（资源路径）
     */
    private String url;

    /**
     * 资源类型（1菜单 2按钮）
     */
    private long type;

    /**
     * 图标
     */
    private String icon;

    /**
     * 备注
     */
    private String remark;
    private Date createDate;
    private Date updateDate;

    /**
     * 用于新增和修改页面上默认的根菜单名称
     */
    @TableField(exist = false)
    private String parentName = "根菜单";

    /**
     * 所有子权限对象集合
     * 左侧菜单渲染时要用
     */
    @TableField(exist = false)
    private List<SysPermission> children;

    /**
     * 所有子权限 URL 集合
     * 左侧菜单渲染时要用
     */
    @TableField(exist = false)
    private List<String> childrenUrl;
}
