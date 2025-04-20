package com.wj.books.controller;

import com.wj.books.commons.CommonResult;
import com.wj.books.dto.CreateRoleParam;
import com.wj.books.dto.UpdateRoleParam;
import com.wj.books.dto.UpdateRoleByAdminParam;
import com.wj.books.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色控制类
 * 负责处理与角色相关的HTTP请求，提供角色列表、创建角色、更新角色、删除角色等功能
 * @author wujun
 * @date 2025-04-19
 *
 */
@RestController
@RequestMapping("/role")
@Api(tags = "系统管理-角色管理")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * 获取角色列表
     * 根据角色名称、页码和每页大小获取角色列表
     *
     * @param name 角色名称，可选参数，用于搜索
     * @param page 页码，必选参数，默认值为1
     * @param size 每页大小，必选参数，默认值为5
     * @return 返回角色列表的CommonResult对象
     */
    @ApiOperation("获取角色列表")
    @GetMapping
    public CommonResult list(@RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return new CommonResult().okPage(roleService.list(name, page, size));
    }

    /**
     * 根据用户id获取角色列表
     * 根据用户id、页码和每页大小获取角色列表
     *
     * @param adminId 用户id，必选参数
     * @param page 页码，必选参数，默认值为1
     * @param size 每页大小，必选参数，默认值为5
     * @return 返回角色列表的CommonResult对象
     */
    @ApiOperation("根据用户id获取角色列表")
    @GetMapping("/listByAdmin")
    public CommonResult listByAdmin(@RequestParam(value = "adminId") Integer adminId,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "5") Integer size
                                    ) {
        return new CommonResult().okPage(roleService.listByAdmin(adminId, page, size));
    }

    /**
     * 修改用户id的角色
     * 根据用户id和新的角色列表更新用户的角色
     *
     * @param updateRoleByAdminParam 包含用户id和新角色列表的参数对象
     * @return 返回操作结果的CommonResult对象
     */
    @ApiOperation("修改用户id的角色")
    @PostMapping("/updateRoleByAdmin")
    public CommonResult updateRoleByAdmin(@RequestBody UpdateRoleByAdminParam updateRoleByAdminParam) {
        roleService.updateRoleByAdmin(updateRoleByAdminParam.getAdminId(), updateRoleByAdminParam.getRoles());
        return new CommonResult().ok();
    }

    /**
     * 创建角色
     * 根据创建角色的参数对象创建新角色
     *
     * @param createRoleParam 包含角色信息的参数对象
     * @return 返回新创建角色的CommonResult对象
     */
    @ApiOperation("创建角色")
    @PostMapping
    public CommonResult create(@RequestBody CreateRoleParam createRoleParam) {
        return new CommonResult().ok(roleService.create(createRoleParam));
    }

    /**
     * 更新角色信息
     * 根据角色id和更新角色的参数对象更新角色信息
     *
     * @param id 角色id
     * @param updateRoleParam 包含角色信息的参数对象
     * @return 返回更新后角色的CommonResult对象
     */
    @ApiOperation("更新角色信息")
    @PutMapping("/{id}")
    public CommonResult update(@PathVariable(value = "id") Integer id,
                               @RequestBody UpdateRoleParam updateRoleParam) {
        return new CommonResult().ok(roleService.updateById(id, updateRoleParam));
    }

    /**
     * 删除角色信息
     * 根据角色id删除角色信息
     *
     * @param id 角色id
     * @return 返回操作结果的CommonResult对象
     */
    @ApiOperation("删除角色信息")
    @DeleteMapping("/{id}")
    public CommonResult delete(@PathVariable(value = "id") Integer id) {
        return new CommonResult().ok(roleService.deleteById(id));
    }

    /**
     * 获取权限和当前角色分配的权限
     * 根据角色id获取所有权限和当前角色分配的权限
     *
     * @param id 角色id
     * @return 返回权限信息的CommonResult对象
     */
    @ApiOperation("获取所有权限和当前角色分配的权限")
    @GetMapping("/{id}/permissions")
    public CommonResult permissions(@PathVariable(value = "id") Integer id) {
        return new CommonResult().ok(roleService.getPermissions(id));
    }

    /**
     * 更新角色权限
     * 根据角色id和新的权限列表更新角色的权限
     *
     * @param id 角色id
     * @param permissions 新的权限列表
     * @return 返回操作结果的CommonResult对象
     */
    @ApiOperation("更新角色权限")
    @PutMapping("/{id}/permissions")
    public CommonResult updatePermissions(@PathVariable(value = "id") Integer id,
                                          @RequestBody Integer[] permissions) {
        roleService.updatePermissions(id, permissions);
        return new CommonResult().ok();
    }
}
