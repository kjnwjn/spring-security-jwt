package net.quanpham.security.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.quanpham.security.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
    @Query(value = " SELECT * FROM roles r " +
            " WHERE r.role_id IN (SELECT ur.role_id FROM user_roles ur WHERE ur.user_id = :userId) ", nativeQuery = true)
    List<Role> findByUserId(@Param("userId") UUID userId);
}
