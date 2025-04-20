package com.wj.books.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author wujun
 * @date 2025-04-19
 */
@Entity
@Table(name = "admin")
@Data
@ApiModel(description = "登录用户表")
public class Auth implements UserDetails {

    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "用户ID", required = true, example = "1")
    private Integer id;

    @Column(name = "username")
    @ApiModelProperty(value = "用户名", required = true, example = "admin")
    private String username;

    @Column(name = "password")
    @ApiModelProperty(value = "密码", required = true, example = "password123")
    private String password;

    @Column(name = "avatar")
    @ApiModelProperty(value = "头像URL", example = "http://example.com/avatar.jpg")
    private String avatar;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "admin_role_relation",
            joinColumns = {@JoinColumn(name = "admin_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @ApiModelProperty(value = "角色列表", required = true)
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        List<Role> roles = this.getRoles();
        for (Role role : roles) {
            for (Permission permission : role.getPermissions()) {
                auths.add(new SimpleGrantedAuthority(permission.getPermission()));
            }
        }
        return auths;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
