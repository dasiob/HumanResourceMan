package com.vmo.controller;

import com.vmo.models.request.LeavePlanningDto;
import com.vmo.models.request.UserDto;
import com.vmo.models.response.UserPagingResponse;
import com.vmo.service.LeavePlanningService;
import com.vmo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private LeavePlanningService leavePlanningService;

    @Value("${project.image}")
    private String path;

    @GetMapping("/listPlans")
    public ResponseEntity<?> getLeavePlanningListAdmin() {
        return ResponseEntity.ok(leavePlanningService.getLeavePlanningListAdmin());
    }

    @PostMapping("/addPlan/{userId}")
    public ResponseEntity<?> addLeavePlan(@RequestBody LeavePlanningDto leavePlanningDto, @PathVariable int userId) {
        return ResponseEntity.ok(leavePlanningService.addLeavePlan(leavePlanningDto, userId));
    }

    @PutMapping("/updatePlan/{planId}")
    public ResponseEntity<?> updateLeavePlanByAdmin(@RequestBody LeavePlanningDto leavePlanningDto, @PathVariable int planId) {
        return ResponseEntity.ok(leavePlanningService.updateLeavePlanByAdmin(leavePlanningDto, planId));
    }

    @DeleteMapping("/deletePlan/{planId}")
    public ResponseEntity<?> deleteLeavePlan(@PathVariable int planId) {
        return ResponseEntity.ok(leavePlanningService.deleteLeavePlan(planId));
    }

    @GetMapping("/getDaysOfAbsences")
    public ResponseEntity<?> getTotalLeaveDaysOfAUser(@RequestParam int userId, @RequestParam int year) {
        return ResponseEntity.ok(leavePlanningService.getTotalLeaveDaysOfAUser(userId, year));
    }

    @GetMapping("/listUsers")
    public UserPagingResponse getAllUsers(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                          @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return userService.getAllUsers(pageNo, pageSize);
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> createUser(@RequestPart UserDto userDto, @RequestPart("image") MultipartFile image) {
        return ResponseEntity.ok(userService.createUser(userDto, image));
    }

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
