package com.wj.books.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 创建管理员参数类
 *
 * @author wujun
 * @date 2025-04-19
 */
@Data
@ApiModel(description = "创建管理员参数类")
public class CreateAdminParam {
    @NotBlank
    @Length(min = 6, max = 20)
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @NotBlank
    @Length(min = 6, max = 40)
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @Length(max = 255)
    @ApiModelProperty(value = "头像URL")
    private String avatar;

    @Length(max = 255)
    @ApiModelProperty(value = "备注")
    private String remark;
}
