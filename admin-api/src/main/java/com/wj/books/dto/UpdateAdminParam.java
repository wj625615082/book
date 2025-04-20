package com.wj.books.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 更新管理员参数类
 *
 * @author wujun
 * @date 2025-04-19
 */
@Data
@ApiModel(description = "更新管理员参数类")
public class UpdateAdminParam {
    @ApiModelProperty(value = "头像URL")
    private String avatar;

    @ApiModelProperty(value = "备注")
    private String remark;
}
