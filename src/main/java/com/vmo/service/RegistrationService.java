package com.vmo.service;

import com.vmo.models.entities.User;
import com.vmo.models.request.UserDto;
import com.vmo.models.response.Message;

public interface RegistrationService {
    void saveConfirmationToken(User user, String token);
    Message register(UserDto userDto);
    String confirmToken(String token);
    String buildEmail(String name, String link);
}
