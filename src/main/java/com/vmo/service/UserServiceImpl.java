package com.vmo.service;

import com.vmo.common.enums.DepartmentNames;
import com.vmo.models.entities.Department;
import com.vmo.models.entities.User;
import com.vmo.models.response.DepartmentDto;
import com.vmo.models.response.UserDto;
import com.vmo.repository.DepartmentRepository;
import com.vmo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ModelMapper mapper;

    private UserDto convertToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto = mapper.map(user, UserDto.class);
        return userDto;
    }

    private User convertToUser(UserDto userDto) {
        User user = new User();
        user = mapper.map(userDto, User.class);
        return user;
    }

    private DepartmentDto convertToDepartmentDto(Department department) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto = mapper.map(department, DepartmentDto.class);
        return departmentDto;
    }

    //for admin to get all info bout users
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(UserDto userDto) {
        Department department = departmentRepository.findByName(DepartmentNames.NOT_SET);
        DepartmentDto departmentDto = convertToDepartmentDto(department);
        List<DepartmentDto> departmentDtoList = new ArrayList<>();
        departmentDtoList.add(departmentDto);
        userDto.setDepartmentDtos(departmentDtoList);
        User user = convertToUser(userDto);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(int userId, UserDto userDto) {
        User user = convertToUser(userDto);
        user = userRepository.findById(userId).orElse(null);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
