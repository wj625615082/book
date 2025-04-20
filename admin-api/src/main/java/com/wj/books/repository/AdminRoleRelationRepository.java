package com.wj.books.repository;

import com.wj.books.domain.AdminRoleRelation;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRoleRelationRepository extends JpaRepository<AdminRoleRelation, Long> {
    Optional<AdminRoleRelation> findByAdminIdAndRoleId(Integer adminId, Integer roleId);
    List<AdminRoleRelation> findByAdminId(Integer adminId);

    @Modifying
    @Transactional
    @Query("DELETE FROM AdminRoleRelation WHERE adminId = :adminId")
    void deleteByAdminId(Integer adminId);


}
   