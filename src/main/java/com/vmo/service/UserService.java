package com.vmo.service;

import com.vmo.models.entities.User;
import com.vmo.models.response.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto createUser(UserDto userDto);
    UserDto updateUser(int userId, UserDto userDto);
    void deleteUser(int userId);
    UserDto getUserById(int userId);
}
