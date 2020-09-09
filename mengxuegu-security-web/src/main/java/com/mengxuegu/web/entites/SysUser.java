package com.mengxuegu.web.entites;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/9 15:14
 */
@Data
@TableName(value = "sys_user")
public class SysUser implements UserDetails {

    @TableId(value = "id", type = IdType.AUTO)
    private long Id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 账号是否过期 1.已过期 0.未过期
     */
    private boolean isAccountNonExpired = true;

    /**
     * 账号是否被锁定 1.已锁定 0.未锁定
     */
    private boolean isAccountNonLocked = true;

    /**
     * 密码是否过期 1.已过期 2.未过期
     */
    private boolean isCredentialsNonExpired = true;

    /**
     * 账号是否可用 1.可用 0.不可用
     */
    private boolean isEnabled = true;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 注册手机号
     */
    private String mobile;

    /**
     * 注册邮箱
     */
    private String email;

    private Date createDate;
    private Date updateDate;

    /**
     * 权限集合
     */
    @TableField(exist = false)
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * 角色集合
     */
    @TableField(exist = false)
    private List<SysRole> sysRoles = new ArrayList<>();

    /**
     * 角色ID集合
     */
    @TableField(exist = false)
    private List<Long> roleIds = new ArrayList<>();

    /**
     * 权限集合
     */
    @TableField(exist = false)
    private List<SysPermission> permissions = new ArrayList<>();

    public List<Long> getRoleIds() {
        return sysRoles.stream().map(SysRole::getId).collect(Collectors.toList());
    }


    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return the password
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * Returns the username used to authenticate the user. Cannot return <code>null</code>.
     *
     * @return the username (never <code>null</code>)
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
