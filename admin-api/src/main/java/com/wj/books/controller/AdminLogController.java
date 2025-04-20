package com.wj.books.controller;

import com.wj.books.commons.CommonResult;
import com.wj.books.service.AdminLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志控制类
 *
 * 负责处理与日志相关的操作，如查看日志列表等
 *
 * @author wujun
 * @date 2025-04-19
 *
 */
@RestController
@RequestMapping("/log")
@Api(tags = "系统管理-日志管理")
public class AdminLogController {

    // 注入日志服务类，用于处理日志业务逻辑
    private final AdminLogService adminLogService;

    // 构造器注入
    @Autowired
    public AdminLogController(AdminLogService adminLogService) {
        this.adminLogService = adminLogService;
    }

    /**
     * 获取日志列表
     *
     * 根据页码和大小参数，分页获取日志信息
     *
     * @param page  页码，从1开始计数
     * @param size 每页的大小，即每页包含的日志数量
     * @return 返回封装了日志列表的CommonResult对象
     */
    @ApiOperation("获取日志列表")
    @GetMapping
    public CommonResult list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return new CommonResult().okPage(adminLogService.list(page, size));
    }
}

