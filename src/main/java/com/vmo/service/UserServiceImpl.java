package com.vmo.service;

import com.vmo.common.config.MapperUtil;
import com.vmo.common.enums.DepartmentNames;
import com.vmo.models.entities.Department;
import com.vmo.models.entities.User;
import com.vmo.models.response.DepartmentDto;
import com.vmo.models.response.UserDto;
import com.vmo.repository.DepartmentRepository;
import com.vmo.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    private MapperUtil mapper;

    private UserDto convertToUserDto(User user) {
        UserDto userDto = mapper.map(user, UserDto.class);
        List<DepartmentDto> departmentDtoList = new ArrayList<>();
        departmentDtoList.addAll(user.getDepartments().stream().map(s -> convertToDepartmentDto(s)).collect(Collectors.toList()));
        userDto.setDepartmentDtos(departmentDtoList);
        return userDto;
    }

    private User convertToUser(UserDto userDto) {
        User user = mapper.map(userDto, User.class);
        List<Department> departmentList = new ArrayList<>();
        departmentList.addAll(userDto.getDepartmentDtos().stream().map(s -> convertToDepartment(s)).collect(Collectors.toList()));
        user.setDepartments(departmentList);
        return user;
    }

    private DepartmentDto convertToDepartmentDto(Department department) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto = mapper.map(department, DepartmentDto.class);
        return departmentDto;
    }

    private Department convertToDepartment(DepartmentDto departmentDto) {
        Department department = new Department();
        department = mapper.map(departmentDto, Department.class);
        return department;
    }

    //for admin to get all info bout users
    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtoList = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        userList.addAll(userRepository.findAll());
        userDtoList.addAll(userList.stream().map(s -> convertToUserDto(s)).collect(Collectors.toList()));
        return userDtoList;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        Department department = departmentRepository.findBydepartmentName(DepartmentNames.NOT_SET);
        List<Department> departmentList = new ArrayList<>();
        departmentList.add(department);
        userDto.setDepartmentDtos(new ArrayList<DepartmentDto>());
        User user = convertToUser(userDto);
        user.setDepartments(departmentList );
        userRepository.save(user);
        return convertToUserDto(user);
    }

    @Override
    public UserDto updateUser(int userId, UserDto userDto) {
        Department department = departmentRepository.findBydepartmentName(DepartmentNames.NOT_SET);
        List<Department> departmentList = new ArrayList<>();
        departmentList.add(department);
        userDto.setDepartmentDtos(new ArrayList<DepartmentDto>());
        User user = convertToUser(userDto);
        user.setDepartments(departmentList );
        userRepository.save(user);
        return convertToUserDto(user);
    }

    @Override
    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserDto getUserById(int userId) {
        User user = userRepository.findById(userId).orElse(null);
        return convertToUserDto(user);
    }
}
