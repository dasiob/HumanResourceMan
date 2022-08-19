package com.vmo.service;

import com.vmo.models.entities.User;
import com.vmo.models.response.UserDto;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User createUser(UserDto userDto);
    User updateUser(int userId, UserDto userDto);
    void deleteUser(int userId);
    User getUserById(int userId);
}
