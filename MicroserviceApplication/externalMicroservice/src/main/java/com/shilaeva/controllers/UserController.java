package com.shilaeva.controllers;

import com.shilaeva.dto.RegisterUserDto;
import com.shilaeva.dto.SetUserOwnerDto;
import com.shilaeva.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private KafkaProducer kafkaProducer;

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasUserId(principal, #id)")
    @GetMapping("/get-by-id")
    public ResponseEntity<?> getUserById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(kafkaProducer
                    .sendAndReceive("get-user-by-id", "reply-user-by-id", id));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.name == #username")
    @GetMapping("/get-by-username")
    public ResponseEntity<?> getUserByUsername(@RequestParam String username) {
        try {
            return ResponseEntity.ok(kafkaProducer
                    .sendAndReceive("get-user-by-username", "reply-user-by-username", username));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasUserId(principal, #userId)")
    @GetMapping("/get-owner/{userId}")
    public ResponseEntity<?> getOwnerByUserId(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(kafkaProducer
                    .sendAndReceive("get-owner-by-userId", "reply-owner-by-userId", userId));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-active-users")
    public ResponseEntity<?> getNotActiveUsers() {
        try {
            return ResponseEntity.ok(kafkaProducer
                    .sendAndReceive("get-active-users", "reply-active-users", null));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-not-active-users")
    public ResponseEntity<?> getActiveUsers() {
        try {
            return ResponseEntity.ok(kafkaProducer
                    .sendAndReceive("get-not-active-users", "reply-not-active-users", null));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.ok(kafkaProducer
                    .sendAndReceive("get-all-users", "reply-all-users", null));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterUserDto userModel) {
        try {
            return ResponseEntity.ok(kafkaProducer
                    .sendAndReceive("register-admin", "reply-register-admin", userModel));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDto userModel) {
        try {
            return ResponseEntity.ok(kafkaProducer
                    .sendAndReceive("register-user", "reply-register-user", userModel));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/set-owner/{userId}")
    public ResponseEntity<?> setOwner(@PathVariable Long userId, Long ownerId) {
        try {
            return ResponseEntity.ok(kafkaProducer.sendAndReceive("set-owner-to-user",
                    "reply-set-owner-to-user", new SetUserOwnerDto(userId, ownerId)));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            return ResponseEntity
                    .ok(kafkaProducer.sendAndReceive("delete-user", "reply-delete-user", userId));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
