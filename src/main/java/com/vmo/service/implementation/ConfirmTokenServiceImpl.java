package com.vmo.service.implementation;

import com.vmo.models.entities.EmailConfirmToken;
import com.vmo.repository.EmailConfirmTokenRepository;
import com.vmo.service.ConfirmTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfirmTokenServiceImpl implements ConfirmTokenService {

    @Autowired
    private EmailConfirmTokenRepository confirmTokenRepository;


    @Override
    public void saveConfirmationToken(EmailConfirmToken token) {
        confirmTokenRepository.save(token);
    }

    @Override
    public Optional<EmailConfirmToken> getToken(String token) {
        return confirmTokenRepository.findBytoken(token);
    }
}
