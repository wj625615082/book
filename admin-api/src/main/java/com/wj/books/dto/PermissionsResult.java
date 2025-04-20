package com.wj.books.dto;

import com.wj.books.vo.PermissionVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * 权限结果类
 *
 * @author wujun
 * @date 2025-04-19
 */
@Data
@ApiModel(description = "权限结果类")
public class PermissionsResult {
    @ApiModelProperty(value = "权限列表", required = true)
    private List<PermissionVo> permissions;

    @ApiModelProperty(value = "已分配的权限ID集合", required = true)
    private Set<Integer> assignedPermissions;
}
