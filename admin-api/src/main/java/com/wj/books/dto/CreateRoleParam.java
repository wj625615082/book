package com.wj.books.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 创建角色参数类
 *
 * @author wujun
 * @date 2025-04-19
 */
@Data
@ApiModel(description = "创建角色参数类")
public class CreateRoleParam {
    @ApiModelProperty(value = "角色名称", required = true)
    private String name;

    @ApiModelProperty(value = "角色描述")
    private String description;
}
