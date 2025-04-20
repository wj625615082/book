package com.wj.books.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 认证信息结果类
 *
 * @author wujun
 * @date 2025-04-19
 */
@Data
@ApiModel(description = "认证信息结果类")
public class AuthInfoResult implements Serializable {
    private static final long serialVersionUID = 3881879290777080792L;

    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @ApiModelProperty(value = "权限列表", required = true)
    private String[] perms;

    @ApiModelProperty(value = "头像URL")
    private String avatar;

    @ApiModelProperty(value = "角色列表", required = true)
    private String[] roles;
}
