package com.vmo.controller;

import com.vmo.models.entities.User;
import com.vmo.models.response.UserDto;
import com.vmo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/listUsers")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/addUser")
    public UserDto createUser(@RequestBody UserDto userDto) {
        return  userService.createUser(userDto);
    }

    @PutMapping("/updateUser/{userId}")
    public UserDto updateUser(@PathVariable int userId, @RequestBody UserDto userDto) {
        return userService.updateUser(userId, userDto);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public void deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/user/{userId}")
    public UserDto getUserById(@PathVariable int userId) {
        return userService.getUserById(userId);
    }
}
