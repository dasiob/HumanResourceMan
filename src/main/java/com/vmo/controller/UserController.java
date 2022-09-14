package com.vmo.controller;

import com.vmo.models.request.UserDto;
import com.vmo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${project.image}")
    private String path;

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable int userId, @RequestPart UserDto userDto, @RequestPart("image") MultipartFile image) {
        return ResponseEntity.ok(userService.updateUser(userId, userDto, image));
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable int userId) {
        return ResponseEntity.ok(userService.deleteUser(userId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable int userId) {
            return ResponseEntity.ok(userService.getUserById(userId));
        }
}
