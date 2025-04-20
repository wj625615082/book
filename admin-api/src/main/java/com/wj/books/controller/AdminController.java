package com.wj.books.controller;

import com.wj.books.commons.CommonResult;
import com.wj.books.domain.Admin;
import com.wj.books.dto.CreateAdminParam;
import com.wj.books.dto.UpdateAdminParam;
import com.wj.books.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理控制类
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "系统管理-管理员")
/*@PreAuthorize("hasPermission('/admin', 'sys:admin:access')")*/
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * 获取管理员列表
     *
     * @param username 用户名，用于搜索过滤
     * @param page      页码
     * @param size      每页大小
     * @return 包含管理员列表的CommonResult对象
     */
    @ApiOperation("获取管理员列表")
    @GetMapping
    public CommonResult list(@RequestParam(value = "username", required = false) String username,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return new CommonResult().okPage(adminService.list(username, page, size));
    }

    /**
     * 获取指定管理员信息
     *
     * @param id 管理员ID
     * @return 包含管理员信息的CommonResult对象
     */
    @ApiOperation("获取指定管理员信息")
    @GetMapping("/{id}")
    public CommonResult read(@PathVariable("id") Integer id) {
        return new CommonResult().ok(adminService.info(id));
    }

    /**
     * 创建管理员信息
     *
     * @param createAdminParam 包含管理员创建参数的对象
     * @return 包含新创建管理员信息的CommonResult对象
     */
    @ApiOperation("创建管理员信息")
    @PostMapping
    public CommonResult create(@RequestBody CreateAdminParam createAdminParam) {
        Admin admin = adminService.create(createAdminParam);
        return new CommonResult().ok(admin);
    }

    /**
     * 更新指定管理员信息
     *
     * @param id               管理员ID
     * @param updateAdminParam 包含管理员更新参数的对象
     * @return 包含更新后管理员信息的CommonResult对象
     */
    @ApiOperation("更新指定管理员信息")
    @PutMapping("/{id}")
    public CommonResult update(@PathVariable("id") Integer id,
                               @RequestBody UpdateAdminParam updateAdminParam) {
        Admin admin = adminService.updateById(id, updateAdminParam);
        return new CommonResult().ok(admin);
    }

    /**
     * 删除指定管理员
     *
     * @param id 管理员ID
     * @return 表示操作结果的CommonResult对象
     */
    @ApiOperation("指定指定管理员")
    @DeleteMapping("/{id}")
    public CommonResult delete(@PathVariable("id") Integer id) {
        adminService.deleteById(id);
        return new CommonResult().ok();
    }

}
