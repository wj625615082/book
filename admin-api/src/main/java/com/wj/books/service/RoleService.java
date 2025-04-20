package com.wj.books.service;

import com.wj.books.domain.Role;
import com.wj.books.dto.CreateRoleParam;
import com.wj.books.dto.PermissionsResult;
import com.wj.books.dto.UpdateRoleParam;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author wujun
 * @date 2025-04-19
 */
public interface RoleService {

    /**
     * 列表查询
     *
     * @param name 搜索条件：name
     * @param page 当前页码
     * @param size 分页条数
     * @return 分页列表
     */
    Page<Role> list(String name, Integer page, Integer size);

    boolean havePermission(Integer adminId, String permission);

    Page<Role> listByAdmin(Integer adminId, Integer page, Integer size);

    void updateRoleByAdmin(Integer adminId, List<Integer> roleIds);

    /**
     * 创建新角色
     *
     * @param createRoleParam 参数
     * @return 角色实体
     */
    Role create(CreateRoleParam createRoleParam);

    /**
     * 更新角色信息
     *
     * @param id              角色id
     * @param updateRoleParam 参数
     * @return 角色实体
     */
    Role updateById(Integer id, UpdateRoleParam updateRoleParam);

    /**
     * 逻辑删除角色信息
     *
     * @param id 角色id
     * @return 角色实体
     */
    Role deleteById(Integer id);

    /**
     * 获取系统权限和对应角色已分配的权限
     *
     * @param roleId 角色ID
     * @return 结果
     */
    PermissionsResult getPermissions(Integer roleId);

    /**
     * 更新角色权限
     *
     * @param roleId      角色ID
     * @param permissions 权限数组
     */
    void updatePermissions(Integer roleId, Integer[] permissions);
}
