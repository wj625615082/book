package com.wj.books.controller;

import com.wj.books.commons.CommonResult;
import com.wj.books.component.AdminLogHelper;
import com.wj.books.dto.LoginParam;
import com.wj.books.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author wujun
 * @date 2025-04-19
 *
 * 后台授权控制类
 */
@RestController
@RequestMapping("/auth")
@Validated
@Api(tags = "后台授权")
public class AuthController {

    private final AuthService authService;
    private final AdminLogHelper adminLogHelper;

    @Autowired
    public AuthController(AuthService authService,
                          AdminLogHelper adminLogHelper) {
        this.authService = authService;
        this.adminLogHelper = adminLogHelper;
    }

    /**
     * 管理员登录接口
     *
     * @param loginParam 登录参数，包含用户名和密码
     * @return 登录结果，包含token等信息
     */
    @ApiOperation(value = "管理员登陆")
    @PostMapping("/login")
    public CommonResult login(@RequestBody LoginParam loginParam) {
        return new CommonResult().ok(authService.login(loginParam));
    }

    /**
     * 管理员登出接口
     *
     * 该接口用于清除管理员的登录状态
     * @return 登出结果
     */
    @ApiOperation(value = "管理员登出")
    @PostMapping("/logout")
    public CommonResult logout() {
        adminLogHelper.logoutSucceed();
        return new CommonResult().ok(null);
    }

    /**
     * 获取当前登录用户信息接口
     *
     * 该接口用于获取当前登录用户的详细信息
     * @param principal 当前登录用户
     * @return 用户信息
     */
    @ApiOperation(value = "获取登陆用户信息")
    @GetMapping("/info")
    @ResponseBody
    public CommonResult getAdminInfo(Principal principal) {
        return new CommonResult().ok(authService.getAdminInfo(principal.getName()));
    }
}
