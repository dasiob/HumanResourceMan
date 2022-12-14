package com.vmo.repository;

import com.vmo.common.enums.RoleNames;
import com.vmo.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByroleName(RoleNames roleNames);
}
