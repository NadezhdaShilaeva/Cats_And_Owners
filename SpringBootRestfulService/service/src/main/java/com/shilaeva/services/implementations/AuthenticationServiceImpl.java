package com.shilaeva.services.implementations;

import com.shilaeva.dto.UserDto;
import com.shilaeva.services.AuthenticationService;
import com.shilaeva.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public UserDto authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        return userService.getByUsername(username);
    }
}
