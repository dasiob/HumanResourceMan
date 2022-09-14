package com.vmo.service;

import com.vmo.models.entities.EmailConfirmToken;

import java.util.Optional;

public interface ConfirmTokenService {
    void saveConfirmationToken(EmailConfirmToken token);
    Optional<EmailConfirmToken> getToken(String token);
}
