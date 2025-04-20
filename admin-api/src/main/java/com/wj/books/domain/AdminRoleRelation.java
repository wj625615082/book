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
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "用户权限关联表")
public class AdminRoleRelation extends BaseEntity {

    @Column(name = "admin_id")
    @ApiModelProperty(value = "用户ID", required = true, example = "1")
    private Integer adminId;

    @Column(name = "role_id")
    @ApiModelProperty(value = "角色ID", required = true, example = "1")
    private Integer roleId;
}
