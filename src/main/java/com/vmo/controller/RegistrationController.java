package com.vmo.controller;

import com.vmo.models.request.UserDto;
import com.vmo.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(registrationService.register(userDto));
    }

    @GetMapping("/signup/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
