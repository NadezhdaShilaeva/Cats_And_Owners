package com.shilaeva.consumer;

import com.shilaeva.dto.RegisterUserDto;
import com.shilaeva.dto.SetUserOwnerDto;
import com.shilaeva.dto.UserDto;
import com.shilaeva.exceptions.UserException;
import com.shilaeva.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
public class UserKafkaConsumer {
    @Autowired
    UserService userService;

    @KafkaListener(topics = "get-user-by-id")
    @SendTo
    public Object getUserById(Long userId) {
        try {
            return userService.getById(userId);
        } catch (UserException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "get-user-by-username")
    @SendTo
    public Object getByUsername(String username) {
        try {
            return userService.getByUsername(username);
        } catch (UserException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "get-owner-by-userId")
    @SendTo
    public Object getOwnerByUserId(Long userId) {
        try {
            return userService.getOwnerByUserId(userId);
        } catch (UserException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "get-active-users")
    @SendTo
    public Object getActiveUsers() {
        try {
            return userService.getActiveUsers().toArray(new UserDto[0]);
        } catch (UserException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "get-not-active-users")
    @SendTo
    public Object getNotActiveUsers() {
        try {
            return userService.getNotActiveUsers().toArray(new UserDto[0]);
        } catch (UserException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "get-all-users")
    @SendTo
    public Object getAll() {
        try {
            return userService.getAll().toArray(new UserDto[0]);
        } catch (UserException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "register-user")
    @SendTo
    public Object createUser(RegisterUserDto registerUserDto) {
        try {
            return userService.createUser(registerUserDto.username(), registerUserDto.password());
        } catch (UserException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "register-admin")
    @SendTo
    public Object createAdmin(RegisterUserDto registerUserDto) {
        try {
            return userService.createAdmin(registerUserDto.username(), registerUserDto.password());
        } catch (UserException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "set-owner-to-user")
    @SendTo
    public Object setOwner(SetUserOwnerDto setUserOwnerDto) {
        try {
            userService.setOwner(setUserOwnerDto.userId(), setUserOwnerDto.ownerId());

            return "Owner was set to hte user.";
        } catch (UserException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "delete-user")
    @SendTo
    public Object deleteUser(Long userId) {
        try {
            userService.deleteUser(userId);

            return "User was deleted.";
        } catch (UserException e) {
            return e.getMessage();
        }
    }
}
