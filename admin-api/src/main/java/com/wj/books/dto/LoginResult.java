package com.wj.books.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 登录结果类
 *
 * @author wujun
 * @date 2025-04-19
 */
@Data
@AllArgsConstructor
@ApiModel(description = "登录结果类")
public class LoginResult {
    @ApiModelProperty(value = "JWT令牌", required = true)
    private String token;
}
