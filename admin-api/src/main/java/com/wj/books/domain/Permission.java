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
@ApiModel(description = "权限表")
public class Permission extends BaseEntity {

    @Column(name = "name")
    @ApiModelProperty(value = "权限名称", required = true, example = "图书管理")
    private String name;

    @Column(name = "permission")
    @ApiModelProperty(value = "权限标识", required = true, example = "book:manage")
    private String permission;

    @Column(name = "parent_id")
    @ApiModelProperty(value = "父权限ID", example = "0")
    private Integer parentId;

    @Column(name = "type")
    @ApiModelProperty(value = "权限类型", required = true, allowableValues = "1, 2", example = "1")
    private Integer type;
}
