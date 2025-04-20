package com.wj.books.controller;

import com.wj.books.annotation.Loggable;
import com.wj.books.commons.CommonResult;
import com.wj.books.domain.AdminLog;
import com.wj.books.domain.Book;
import com.wj.books.dto.BookDto;
import com.wj.books.repository.BookRepository;
import com.wj.books.service.AdminLogService;
import com.wj.books.service.BookService;
import com.wj.books.util.IpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

/**
 * @author wujun
 * @date 2025-04-19
 *
 * 图书管理控制类
 */
@RestController
@RequestMapping("/book")
@Validated
@Api(tags = "图书管理")
@PreAuthorize("hasPermission('/book', 'book.manage')")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AdminLogService adminLogService;

    /**
     * 查询图书列表
     *
     * @param bookDto 查询dto参数
     * @return CommonResult图书列表
     * */
    @ApiOperation("获取图书列表")
    @PostMapping("/list")
    public CommonResult list(@RequestBody BookDto bookDto) {
        return new CommonResult().okPage(bookService.list(bookDto));
    }

    /**
     * 新增图书
     * @param book 图书实体类 ，书号和书名必填
     * @return CommonResult图书保存实体
     * Loggable 注解形式日志记录
     * */
    @ApiOperation("新增图书")
    @PostMapping("/save")
    @Loggable
    public CommonResult save(@RequestBody @Validated Book book) {
        Book savedBook = bookService.save(book);
        return new CommonResult().ok(savedBook);
    }

    /**
     * 新增图书
     * @param book 图书实体类 ，书号和书名必填
     * @return CommonResult图书保存实体
     * Loggable 注解形式日志记录
     * */
    @ApiOperation("修改图书")
    @PostMapping("/update")
    @Loggable
    public CommonResult update(@RequestBody @Validated Book book) {
        Book savedBook = bookService.update(book);
        return new CommonResult().ok(savedBook);
    }

    /**
     * 根据ID获取图书
     * @param id 图书id
     * @return CommonResult图书实体
     * Loggable 注解形式日志记录
     * */
    @ApiOperation("根据ID获取图书")
    @GetMapping("/getById")
    @Loggable   //注解形式日志记录
    public CommonResult getById(@RequestParam Integer id) {
        if (id == null) {
            return new CommonResult().failed(1,"ID不能为空");
        }
        return new CommonResult().ok(bookService.getById(id));
    }

    /**
     * 根据图书id删除图书
     *
     * @param params 图书删除参数
     * @return CommonResult是否成功
     * */
    @ApiOperation("删除图书")
    @PostMapping("/deleteById")
    public CommonResult deleteById(@RequestBody Map<String, Integer> params, HttpServletRequest request){
        Integer id = params.get("id");
        bookService.deleteById(id);

        // 记录日志
        adminLogService.save(AdminLog.builder().admin("admin")
                .path("/book/deleteById")
                .action("删除图书")
                .type(1)
                .result("成功")
                .createTime(new java.util.Date())
                .ip(IpUtil.getIpAddress(request))
                .build());

        return new CommonResult().ok();
    }

}
