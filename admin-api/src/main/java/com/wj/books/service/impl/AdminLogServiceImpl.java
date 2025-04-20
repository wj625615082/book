package com.wj.books.service.impl;

import com.wj.books.domain.AdminLog;
import com.wj.books.repository.AdminLogRepository;
import com.wj.books.service.AdminLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * 管理员日志服务实现类
 * 负责处理管理员日志的相关操作，如查询和保存日志
 *
 * @author wujun
 * @date 2025-04-19
 */
@Service
public class AdminLogServiceImpl implements AdminLogService {

    // 管理员日志仓库，用于执行日志的CRUD操作
    private final AdminLogRepository adminLogRepository;

    /**
     * 构造函数注入管理员日志仓库
     *
     * @param adminLogRepository 管理员日志仓库
     */
    @Autowired
    public AdminLogServiceImpl(AdminLogRepository adminLogRepository) {
        this.adminLogRepository = adminLogRepository;
    }

    /**
     * 分页查询管理员日志
     *
     * @param page 页码
     * @param size 每页大小
     * @return 分页的管理员日志
     */
    @Override
    public Page<AdminLog> list(Integer page, Integer size) {
        // 按创建时间降序排序
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        // 创建分页请求
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        // 查询所有日志并分页返回
        return adminLogRepository.findAll(pageable);
    }

    /**
     * 保存管理员日志
     *
     * @param adminLog 要保存的管理员日志
     */
    @Override
    public void save(AdminLog adminLog) {
        // 保存日志到数据库
        adminLogRepository.save(adminLog);
    }
}

