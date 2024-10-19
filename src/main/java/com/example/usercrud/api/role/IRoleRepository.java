package com.example.usercrud.api.role;

import com.example.usercrud.api.company.CompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<RoleModel, Long> {
    boolean existsByRoleCode(String roleCode);
}
