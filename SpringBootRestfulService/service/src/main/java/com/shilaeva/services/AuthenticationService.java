package com.shilaeva.services;

import com.shilaeva.dto.UserDto;

public interface AuthenticationService {
    UserDto authenticate(String username, String password);
}
