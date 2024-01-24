package com.shilaeva.dto;

import com.shilaeva.models.User;

public record UserDto(Long id, String username, String status, String role) {
    public static UserDto asDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getStatus().toString(),
                user.getRole().toString());
    }
}
