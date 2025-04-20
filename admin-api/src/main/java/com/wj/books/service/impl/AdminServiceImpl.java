package com.wj.books.service.impl;

import com.wj.books.domain.Admin;
import com.wj.books.domain.AdminRoleRelation;
import com.wj.books.dto.AdminReadResult;
import com.wj.books.dto.CreateAdminParam;
import com.wj.books.dto.UpdateAdminParam;
import com.wj.books.repository.AdminRepository;
import com.wj.books.repository.AdminRoleRelationRepository;
import com.wj.books.service.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 管理员服务实现类
 *
 * @author wujun
 * @date 2025-04-19
 */
@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    @Autowired
    private AdminRoleRelationRepository adminRoleRelationRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /**
     * 列出管理员列表
     *
     * @param username 搜索用户名
     * @param page     页码
     * @param size     每页大小
     * @return 管理员列表
     */
    @Override
    public Page<Admin> list(String username, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return adminRepository.findAll((Specification<Admin>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();

            // 确保只列出未删除的管理员
            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));

            // 如果提供了用户名，则添加模糊搜索条件
            if (!StringUtils.isEmpty(username)) {
                list.add(criteriaBuilder.like(root.get("username").as(String.class), "%" + username + "%"));
            }
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);
    }

    /**
     * 获取管理员详细信息
     *
     * @param id 管理员ID
     * @return 管理员详细信息
     */
    @Override
    public AdminReadResult info(Integer id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        AdminReadResult result = new AdminReadResult();
        BeanUtils.copyProperties(admin, result);
        return result;
    }

    /**
     * 创建新管理员
     *
     * @param createAdminParam 创建管理员的参数
     * @return 新创建的管理员
     */
    @Override
    public Admin create(CreateAdminParam createAdminParam) {

        // 检查管理员是否已存在
        Admin existing = adminRepository.findByUsername(createAdminParam.getUsername());
        Assert.isNull(existing, "管理员已存在: " + createAdminParam.getUsername());

        // 加密密码
        String rawPassword = createAdminParam.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(rawPassword);

        Admin admin = new Admin();
        BeanUtils.copyProperties(createAdminParam, admin);
        admin.preInsert();
        admin.setPassword(encodedPassword);

        return adminRepository.save(admin);
    }

    /**
     * 更新管理员信息
     *
     * @param id                管理员ID
     * @param updateAdminParam 更新管理员的参数
     * @return 更新后的管理员
     */
    @Override
    public Admin updateById(Integer id, UpdateAdminParam updateAdminParam) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        admin.preUpdate();
        BeanUtils.copyProperties(updateAdminParam, admin);
        adminRepository.save(admin);
        return admin;
    }

    /**
     * 删除管理员
     *
     * @param id 管理员ID
     */
    @Override
    public void deleteById(Integer id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        admin.preDelete();
        adminRepository.save(admin);
    }

    /*
    @Override
    public boolean createOrUpdateOrDeleteRole(AdminRoleRelation adminRoleRelation) {

        Optional<AdminRoleRelation> optionalAdminRoleRelation = adminRoleRelationRepository.findByAdminIdAndRoleId(adminRoleRelation.getAdminId(), adminRoleRelation.getRoleId());
        AdminRoleRelation adminRoleRelation1 = optionalAdminRoleRelation.orElse(adminRoleRelation);
        if(adminRoleRelation1==null){
            adminRoleRelationRepository.save(adminRoleRelation);
        }


        return true;
    }*/
}
