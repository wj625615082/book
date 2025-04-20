package com.wj.books.repository;

import com.wj.books.domain.Auth;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wujun
 * @date 2025-04-19
 */
@Repository
public interface UserDetailsRepository extends CrudRepository<Auth, Integer> {

    /**
     * 根据用户名查询系统管理员
     *
     * @param username 用户名
     * @return 系统管理员
     */
    Auth findByUsername(String username);
}
