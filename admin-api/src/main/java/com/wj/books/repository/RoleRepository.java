package com.wj.books.repository;

import com.wj.books.domain.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author wujun
 * @date 2025-04-19
 */
public interface RoleRepository extends PagingAndSortingRepository<Role, Integer>, JpaSpecificationExecutor<Role> {

    /**
     * 根据名称查找角色信息
     *
     * @param name 角色名
     * @return 角色实体
     */
    Role findByName(String name);
}
