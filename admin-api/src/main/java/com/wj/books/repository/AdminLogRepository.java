package com.wj.books.repository;

import com.wj.books.domain.AdminLog;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author wujun
 * @date 2025-04-19
 */
public interface AdminLogRepository extends PagingAndSortingRepository<AdminLog, Integer> {
}
