package com.wj.books.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管理员读取结果类
 *
 * @author wujun
 * @date 2025-04-19
 */
@Data
@ApiModel(description = "管理员读取结果类")
public class AdminReadResult {
    @ApiModelProperty(value = "管理员ID", required = true)
    private Integer id;

    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @ApiModelProperty(value = "头像URL")
    private String avatar;

    @ApiModelProperty(value = "备注")
    private String remark;
}
