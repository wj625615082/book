package com.wj.books.repository;

import com.wj.books.domain.Permission;
import com.wj.books.domain.RolePermissionRelation;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wujun
 * @date 2025-04-19
 */
@Repository
public interface RolePermissionRelationRepository extends CrudRepository<RolePermissionRelation, Integer> {

    /**
     * 根据角色id查询
     *
     * @param roleId 角色id
     * @return 结果
     */
    Iterable<RolePermissionRelation> findByRoleId(Integer roleId);

    @Query("SELECT rpr.permissionId FROM RolePermissionRelation rpr WHERE rpr.roleId = :roleId")
    List<Integer> findPermissionIdsByRoleId(@Param("roleId") Integer roleId);
}
