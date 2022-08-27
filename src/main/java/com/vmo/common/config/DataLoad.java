package com.vmo.common.config;

import com.vmo.common.enums.DepartmentNames;
import com.vmo.models.entities.Department;
import com.vmo.repository.DepartmentRepository;
import com.vmo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;

@Component
public class DataLoad implements ApplicationListener<ContextRefreshedEvent> {
    private boolean alreadySetup = false;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) return;

        EnumSet<DepartmentNames> departmentNames = EnumSet.allOf(DepartmentNames.class);
        for (DepartmentNames name : departmentNames) {
            Department department = createIfNotFound(name);
        }

        alreadySetup = true;
    }

    private Department createIfNotFound(DepartmentNames departmentNames) {
        Department department = departmentRepository.findBydepartmentName(departmentNames);
        if (department == null) department = new Department(departmentNames);
        department = departmentRepository.save(department);
        return department;
    }
}
