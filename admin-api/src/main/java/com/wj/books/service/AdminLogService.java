package com.wj.books.service;

import com.wj.books.domain.AdminLog;
import org.springframework.data.domain.Page;

/**
 * @author wujun
 * @date 2025-04-19
 */
public interface AdminLogService {
    /**
     * 后台日志列表
     *
     * @param page 当前页码
     * @param size 分页条数
     * @return 分页列表
     */
    Page<AdminLog> list(Integer page, Integer size);

    /**
     * 保存日志
     *
     * @param adminLog 日志对象
     */
    void save(AdminLog adminLog);

}
