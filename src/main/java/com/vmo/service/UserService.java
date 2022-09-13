package com.vmo.service;

import com.vmo.models.entities.User;
import com.vmo.models.request.UserDto;
import com.vmo.models.response.Message;
import com.vmo.models.response.UserPagingResponse;
import com.vmo.models.response.UserResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface UserService {

    UserPagingResponse getAllUsers(int pageNo, int pageSize);
    UserResponse createUser(UserDto userDto, MultipartFile file);
    UserResponse updateUser(int userId, UserDto userDto, MultipartFile file);
    Message deleteUser(int userId);
    UserResponse getUserById(int userId);
    User getUserByName(String userName);
    String uploadImage(String path, MultipartFile file) throws IOException;
    InputStream getResource(String path, String fileName) throws FileNotFoundException;
}
