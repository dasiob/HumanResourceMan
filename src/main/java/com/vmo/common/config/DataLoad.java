package com.vmo.common.config;

import com.vmo.common.enums.DefaultDepartmentsNames;
import com.vmo.common.enums.RoleNames;
import com.vmo.models.entities.Department;
import com.vmo.models.entities.Role;
import com.vmo.models.entities.User;
import com.vmo.repository.DepartmentRepository;
import com.vmo.repository.RoleRepository;
import com.vmo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Component
public class DataLoad implements ApplicationListener<ContextRefreshedEvent> {
    private boolean alreadySetup = false;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) return;

        EnumSet<DefaultDepartmentsNames> defaultDepartmentsNames = EnumSet.allOf(DefaultDepartmentsNames.class);
        for (DefaultDepartmentsNames name : defaultDepartmentsNames) {
            createIfNotFound(name.toString());
        }

        EnumSet<RoleNames> roleNames = EnumSet.allOf(RoleNames.class);
        for (RoleNames name : roleNames) {
            createIfNotFound(name);
        }

        User userAdmin = new User();
        userAdmin.setFirstName("Bui");
        userAdmin.setLastName("Duc Luong");
        userAdmin.setUserName("admin");
        userAdmin.setEmail("luongbui263@gmail.com");
        System.out.println(passwordEncoder);
        userAdmin.setPassword(passwordEncoder.encode("123456789"));
        userAdmin.setPhone("0934573936");
        List<Role> adminRole = new ArrayList<>();
        adminRole.add(roleRepository.findByroleName(RoleNames.ROLE_ADMIN_USER));
        userAdmin.setRoles(adminRole);
        createIfNotFound(userAdmin);

        User userDepartmentAdmin = new User();
        userDepartmentAdmin.setFirstName("Vu");
        userDepartmentAdmin.setLastName("Linh Chi");
        userDepartmentAdmin.setUserName("department_admin");
        userDepartmentAdmin.setEmail("linhchew@gmail.com");
        userDepartmentAdmin.setPassword(passwordEncoder.encode("123456789"));
        userDepartmentAdmin.setPhone("0136941199");
        List<Role> adminDepartmentRole = new ArrayList<>();
        adminDepartmentRole.add(roleRepository.findByroleName(RoleNames.ROLE_USER));
        adminDepartmentRole.add(roleRepository.findByroleName(RoleNames.ROLE_ADMIN_DEPARTMENT));
        userDepartmentAdmin.setRoles(adminDepartmentRole);
        createIfNotFound(userDepartmentAdmin);

        alreadySetup = true;
    }

    private Department createIfNotFound(String departmentNames) {
        Department department = departmentRepository.findBydepartmentName(departmentNames);
        if (department == null) department = new Department(departmentNames);
        department = departmentRepository.save(department);
        return department;
    }

    private Role createIfNotFound(RoleNames roleNames) {
        Role role = roleRepository.findByroleName(roleNames);
        if (role == null) role = new Role(roleNames);
        role = roleRepository.save(role);
        return role;
    }

    private User createIfNotFound(User user) {
        User userAdmin = userRepository.findByuserName(user.getUserName());
        if (userAdmin == null) userAdmin = user;
        userAdmin = userRepository.save(userAdmin);
        return userAdmin;
    }
}
