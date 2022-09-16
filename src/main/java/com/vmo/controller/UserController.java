package com.vmo.controller;

import com.vmo.models.request.LeavePlanningDto;
import com.vmo.models.request.UserDto;
import com.vmo.service.LeavePlanningService;
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
    @Autowired
    private LeavePlanningService leavePlanningService;

    @Value("${project.image}")
    private String path;

    @GetMapping("/listPlans/{userId}")
    public ResponseEntity<?> getLeavePlanningListUser(@PathVariable int userId) {
        return ResponseEntity.ok(leavePlanningService.getLeavePlanningListUser(userId));
    }

    @PostMapping("addPlan/{userId}")
    public ResponseEntity<?> addLeavePlan(@RequestBody LeavePlanningDto leavePlanningDto, @PathVariable int userId) {
        return ResponseEntity.ok(leavePlanningService.addLeavePlan(leavePlanningDto, userId));
    }

    @PutMapping("/updatePlan")
    public ResponseEntity<?> updateLeavePlanByUser(@RequestBody LeavePlanningDto leavePlanningDto, @RequestParam int planId, @RequestParam int userId) {
        return ResponseEntity.ok(leavePlanningService.updateLeavePlanByUser(leavePlanningDto, planId, userId));
    }

    @DeleteMapping("/deletePlan/{planId}")
    public ResponseEntity<?> deleteLeavePlan(@PathVariable int planId) {
        return ResponseEntity.ok(leavePlanningService.deleteLeavePlan(planId));
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
