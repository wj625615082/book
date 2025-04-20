package com.wj.books.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录参数类
 *
 * @author wujun
 * @date 2025-04-19
 */
@Data
@ApiModel(description = "登录参数类")
public class LoginParam {
    @NotBlank
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @NotBlank
    @ApiModelProperty(value = "密码", required = true)
    private String password;
}
