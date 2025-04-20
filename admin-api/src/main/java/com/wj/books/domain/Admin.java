package com.wj.books.domain;

import com.wj.books.commons.BaseDataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

/**
 * @author wujun
 * @date 2025-04-19
 *
 * 用户表
 */
@Entity
@Table(name = "admin")
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "用户表")
public class Admin extends BaseDataEntity {

    /**
     * 用户名
     */
    @Column(name = "username")
    @ApiModelProperty(value = "用户名", required = true, example = "admin")
    private String username;

    /**
     * 密码
     */
    @Column(name = "password")
    @ApiModelProperty(value = "密码", required = true, example = "password123")
    private String password;

    /**
     * 头像
     */
    @Column(name = "avatar")
    @ApiModelProperty(value = "头像URL", example = "http://example.com/avatar.jpg")
    private String avatar;

    /**
     * 备注信息
     */
    @Column(name = "remark")
    @ApiModelProperty(value = "备注信息", example = "系统管理员")
    private String remark;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "admin_role_relation",
            joinColumns = {@JoinColumn(name = "admin_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @ApiModelProperty(value = "角色列表", required = true)
    private List<Role> roles;
}
