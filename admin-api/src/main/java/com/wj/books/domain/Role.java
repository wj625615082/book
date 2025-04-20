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
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "角色表")
public class Role extends BaseDataEntity {

    @Column(name = "name")
    @ApiModelProperty(value = "角色名称", required = true, example = "管理员")
    private String name;

    @Column(name = "description")
    @ApiModelProperty(value = "角色描述", example = "系统管理员角色")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission_relation",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")})
    @ApiModelProperty(value = "权限列表", required = true)
    private List<Permission> permissions;

    @Transient
    @ApiModelProperty(value = "是否具备角色", required = false)
    private boolean isRole;
}
