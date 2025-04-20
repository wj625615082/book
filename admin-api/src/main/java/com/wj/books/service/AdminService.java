package com.wj.books.service;

import com.wj.books.domain.Admin;
import com.wj.books.dto.AdminReadResult;
import com.wj.books.dto.CreateAdminParam;
import com.wj.books.dto.UpdateAdminParam;
import org.springframework.data.domain.Page;

/**
 * @author wujun
 * @date 2025-04-19
 */
public interface AdminService {

    /**
     * 管理员列表
     *
     * @param username 检索条件：用户名
     * @param page     当前页码
     * @param size     分页条数
     * @return 分页列表
     */
    Page<Admin> list(String username, Integer page, Integer size);

    /**
     * 管理员信息
     *
     * @param id 管理员id
     * @return 管理员信息
     */
    AdminReadResult info(Integer id);

    /**
     * 创建管理员
     *
     * @param createAdminParam 创建参数
     * @return 管理员实体
     */
    Admin create(CreateAdminParam createAdminParam);

    /**
     * 根据ID更新管理员信息
     *
     * @param id               id
     * @param updateAdminParam 更新参数
     * @return 管理员实体
     */
    Admin updateById(Integer id, UpdateAdminParam updateAdminParam);

    /**
     * 根据ID删除管理员
     *
     * @param id id
     */
    void deleteById(Integer id);
}
