package com.vmo.repository;

import com.vmo.common.enums.DepartmentNames;
import com.vmo.models.entities.Department;
import com.vmo.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Department findBydepartmentName(DepartmentNames departmentName);
}
