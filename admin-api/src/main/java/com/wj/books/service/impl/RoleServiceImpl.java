package com.wj.books.service.impl;

import com.wj.books.domain.AdminRoleRelation;
import com.wj.books.domain.Permission;
import com.wj.books.domain.Role;
import com.wj.books.domain.RolePermissionRelation;
import com.wj.books.dto.CreateRoleParam;
import com.wj.books.dto.PermissionsResult;
import com.wj.books.dto.UpdateRoleParam;
import com.wj.books.repository.AdminRoleRelationRepository;
import com.wj.books.repository.PermissionRepository;
import com.wj.books.repository.RolePermissionRelationRepository;
import com.wj.books.repository.RoleRepository;
import com.wj.books.service.RoleService;
import com.wj.books.vo.PermissionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色服务类
 *
 * @author wujun
 * @date 2025-04-19
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRelationRepository rolePermissionRelationRepository;
    @Autowired
    private AdminRoleRelationRepository adminRoleRelationRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository,
                           PermissionRepository permissionRepository,
                           RolePermissionRelationRepository rolePermissionRelationRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.rolePermissionRelationRepository = rolePermissionRelationRepository;
    }

    /**
     * 根据角色名称和分页参数查询角色列表
     *
     * @param name 角色名称
     * @param page 当前页码
     * @param size 每页大小
     * @return 角色列表的分页对象
     */
    @Override
    public Page<Role> list(String name, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return roleRepository.findAll((Specification<Role>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();

            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));

            if (!StringUtils.isEmpty(name)) {
                list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + name + "%"));
            }
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);
    }

    /**
     * 判断adminId是否具备该权限
     *
     * @param adminId 用户ID
     * @param permission 权限字符串
     * @return 如果用户具备该权限则返回true，否则返回false
     */
    @Override
    public boolean havePermission(Integer adminId, String permission) {
        // 1. 查询 adminId 对应的角色 ID
        List<Integer> roleIds = adminRoleRelationRepository.findByAdminId(adminId).stream()
                .map(AdminRoleRelation::getRoleId)
                .collect(Collectors.toList());

        // 2. 根据角色 ID 查询对应的权限 ID
        List<Integer> permissionIds = new ArrayList<>();
        for (Integer roleId : roleIds) {
            List<Integer> rolePermissionIds = rolePermissionRelationRepository.findPermissionIdsByRoleId(roleId);
            permissionIds.addAll(rolePermissionIds);
        }

        // 3. 根据权限 ID 查询对应的 Permission 对象
        List<Permission> permissions = permissionRepository.findByIdIn(permissionIds);

        // 4. 判断这些权限中是否包含指定的 permission
        return permissions.stream()
                .anyMatch(p -> p.getPermission().equals(permission));
    }

    /**
     * 根据adminId查询角色列表
     *
     * @param adminId 用户ID
     * @param page 当前页码
     * @param size 每页大小
     * @return 角色列表的分页对象
     */
    @Override
    public Page<Role> listByAdmin(Integer adminId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        // 查询 AdminRoleRelation 表以获取该 adminId 用户的角色 ID
        List<Integer> roleIds = adminRoleRelationRepository.findByAdminId(adminId).stream()
                .map(AdminRoleRelation::getRoleId)
                .collect(Collectors.toList());

        // 根据 deleted 状态为 0 查询所有 Role 列表
        Page<Role> rolesPage = roleRepository.findAll((Specification<Role>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 添加 deleted 状态为 0 的条件
            predicates.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));

            Predicate[] p = new Predicate[predicates.size()];
            return criteriaBuilder.and(predicates.toArray(p));
        }, pageable);

        // 设置每个 Role 的 isRole 字段
        List<Role> roles = rolesPage.getContent();
        for (Role role : roles) {
            role.setRole(roleIds.contains(role.getId()));
        }

        // 返回修改后的 Role 列表
        return new PageImpl(roles, rolesPage.getPageable(), rolesPage.getTotalElements());
    }

    /**
     * 更新用户的角色
     *
     * @param adminId 用户ID
     * @param roleIds 角色ID列表
     */
    @Override
    public void updateRoleByAdmin(Integer adminId, List<Integer> roleIds) {
        // 清空 AdminRoleRelation 表中指定 adminId 的记录
        adminRoleRelationRepository.deleteByAdminId(adminId);

        // 插入新的 AdminRoleRelation 记录
        List<AdminRoleRelation> adminRoleRelations = new ArrayList<>();
        for (Integer roleId : roleIds) {
            AdminRoleRelation relation = new AdminRoleRelation();
            relation.setAdminId(adminId);
            relation.setRoleId(roleId);
            adminRoleRelations.add(relation);
        }
        adminRoleRelationRepository.saveAll(adminRoleRelations);
    }

    /**
     * 创建新角色
     *
     * @param createRoleParam 创建角色的参数对象
     * @return 创建后的角色对象
     */
    @Override
    public Role create(CreateRoleParam createRoleParam) {
        Role existing = roleRepository.findByName(createRoleParam.getName());
        Assert.isNull(existing, "角色已存在: " + createRoleParam.getName());

        Role role = new Role();
        role.preInsert();
        BeanUtils.copyProperties(createRoleParam, role);
        roleRepository.save(role);
        return role;
    }

    /**
     * 根据ID更新角色
     *
     * @param id 角色ID
     * @param updateRoleParam 更新角色的参数对象
     * @return 更新后的角色对象
     */
    @Override
    public Role updateById(Integer id, UpdateRoleParam updateRoleParam) {
        Role role = roleRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        // 如果修改角色名，需要检查修改后的角色名在数据库中是否存在
        if (!role.getName().equals(updateRoleParam.getName())) {
            Role existing = roleRepository.findByName(updateRoleParam.getName());
            Assert.isNull(existing, "角色已存在: " + updateRoleParam.getName());
        }
        role.preUpdate();
        BeanUtils.copyProperties(updateRoleParam, role);
        roleRepository.save(role);
        return role;
    }

    /**
     * 根据ID删除角色
     *
     * @param id 角色ID
     * @return 删除前的角色对象
     */
    @Override
    public Role deleteById(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        role.preDelete();
        roleRepository.save(role);
        return role;
    }

    /**
     * 获取角色的权限信息
     *
     * @param roleId 角色ID
     * @return 权限信息结果对象
     */
    @Override
    public PermissionsResult getPermissions(Integer roleId) {

        PermissionsResult result = new PermissionsResult();
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("角色不存在，roleId：" + roleId));

        Set<Integer> assignedPermissions = new HashSet<>();
        role.getPermissions().forEach(p -> assignedPermissions.add(p.getId()));
        result.setAssignedPermissions(assignedPermissions);

        Iterable<Permission> allPermission = permissionRepository.findAll();
        List<PermissionVo> permissionVoList = new ArrayList<>();

        allPermission.forEach(permission -> permissionVoList.add(
                PermissionVo.builder()
                        .id(permission.getId())
                        .label(permission.getName())
                        .permission(permission.getPermission())
                        .parentId(permission.getParentId())
                        .type(permission.getType())
                        .build()
        ));

        List<PermissionVo> permissions = new ArrayList<>();

        for (PermissionVo vo : permissionVoList) {
            if (vo.getParentId().equals(0)) {
                permissions.add(vo);
            }

            for (PermissionVo iv : permissionVoList) {
                if (iv.getParentId().equals(vo.getId())) {
                    if (vo.getChildren() == null) {
                        vo.setChildren(new ArrayList<>());
                    }
                    vo.getChildren().add(iv);
                }
            }
        }

        result.setPermissions(permissions);

        return result;
    }

    /**
     * 更新角色的权限
     *
     * @param roleId 角色ID
     * @param permissions 权限ID数组
     */
    @Override
    public void updatePermissions(Integer roleId, Integer[] permissions) {

        Iterable<RolePermissionRelation> iterable = rolePermissionRelationRepository.findByRoleId(roleId);
        rolePermissionRelationRepository.deleteAll(iterable);

        List<RolePermissionRelation> saves = new ArrayList<>();
        for (Integer pId : permissions) {
            RolePermissionRelation r = new RolePermissionRelation();
            r.setRoleId(roleId);
            r.setPermissionId(pId);
            saves.add(r);
        }
        rolePermissionRelationRepository.saveAll(saves);
    }
}
