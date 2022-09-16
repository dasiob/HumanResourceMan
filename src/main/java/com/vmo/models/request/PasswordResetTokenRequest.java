package com.vmo.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetTokenRequest {
    private String email;
    private String password;
    private String confirmPassword;
    private String token;
}
