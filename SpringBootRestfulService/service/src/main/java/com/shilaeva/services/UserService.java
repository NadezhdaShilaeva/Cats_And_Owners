package com.shilaeva.services;

import com.shilaeva.dto.OwnerDto;
import com.shilaeva.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getById(Long userId);
    UserDto getByUsername(String username);
    OwnerDto getOwnerByUserId(Long userId);
    List<UserDto> getActiveUsers();
    List<UserDto> getNotActiveUsers();
    List<UserDto> getAll();
    UserDto createUser(String username, String password);
    UserDto createAdmin(String username, String password);
    void setOwner(Long userId, Long ownerId);
    void deleteUser(Long userId);
}
