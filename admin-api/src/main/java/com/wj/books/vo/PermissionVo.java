package com.wj.books.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 权限视图对象
 * @author wujun
 * @date 2025-04-19
 */
@Data
@Builder
public class PermissionVo {
    private Integer id;
    private String label;
    private String permission;
    private Integer type;
    private Integer parentId;
    private List<PermissionVo> children;
}
