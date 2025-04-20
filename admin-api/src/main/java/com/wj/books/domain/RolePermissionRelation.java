package com.wj.books.domain;

import com.wj.books.commons.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author wujun
 * @date 2025-04-19
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "角色权限关联表")
public class RolePermissionRelation extends BaseEntity {

    @Column(name = "role_id")
    @ApiModelProperty(value = "角色ID", required = true, example = "1")
    private Integer roleId;

    @Column(name = "permission_id")
    @ApiModelProperty(value = "权限ID", required = true, example = "101")
    private Integer permissionId;
}
