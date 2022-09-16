package com.vmo.service;

import com.vmo.models.entities.PasswordResetToken;
import com.vmo.models.request.PasswordResetTokenRequest;

public interface PasswordResetTokenService {
    PasswordResetToken getValidatedToken(PasswordResetTokenRequest tokenRequest);
    void checkExpiration(PasswordResetToken token);
    void deleteToken(PasswordResetTokenRequest request);
    void matchEmail(PasswordResetToken token, String email);
}
