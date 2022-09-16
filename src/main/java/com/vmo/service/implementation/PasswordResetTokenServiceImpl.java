package com.vmo.service.implementation;

import com.vmo.models.entities.PasswordResetToken;
import com.vmo.models.request.PasswordResetTokenRequest;
import com.vmo.service.PasswordResetTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
    @Override
    public PasswordResetToken getValidatedToken(PasswordResetTokenRequest tokenRequest) {
        return null;
    }

    @Override
    public void checkExpiration(PasswordResetToken token) {

    }

    @Override
    public void deleteToken(PasswordResetTokenRequest request) {

    }

    @Override
    public void matchEmail(PasswordResetToken token, String email) {
        if(!token.getUser().getEmail().equalsIgnoreCase(email)) {
            throw new
        }
    }
}
