package com.wj.books.repository;

import com.wj.books.domain.Permission;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author wujun
 * @date 2025-04-19
 */
public interface PermissionRepository extends CrudRepository<Permission, Integer> {

    List<Permission> findByIdIn(List<Integer> ids);
}
