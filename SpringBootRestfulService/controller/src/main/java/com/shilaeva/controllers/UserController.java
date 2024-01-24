package com.shilaeva.controllers;

import com.shilaeva.models.RegisterUserModel;
import com.shilaeva.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasUserId(principal, #id)")
    @GetMapping("/get-by-id")
    public ResponseEntity<?> getUserById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(userService.getById(id));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.name == #username")
    @GetMapping("/get-by-username")
    public ResponseEntity<?> getUserByUsername(@RequestParam String username) {
        try {
            return ResponseEntity.ok(userService.getByUsername(username));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasUserId(principal, #userId)")
    @GetMapping("/get-owner/{userId}")
    public ResponseEntity<?> getOwnerByUserId(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(userService.getOwnerByUserId(userId));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-active-users")
    public ResponseEntity<?> getNotActiveUsers() {
        try {
            return ResponseEntity.ok(userService.getActiveUsers());
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-not-active-users")
    public ResponseEntity<?> getActiveUsers() {
        try {
            return ResponseEntity.ok(userService.getNotActiveUsers());
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.ok(userService.getAll());
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterUserModel userModel) {
        try {
            return ResponseEntity.ok(userService.createAdmin(userModel.username(), userModel.password()));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserModel userModel) {
        try {
            return ResponseEntity.ok(userService.createUser(userModel.username(), userModel.password()));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/set-owner/{userId}")
    public ResponseEntity<?> setOwner(@PathVariable Long userId, Long ownerId) {
        try {
            userService.setOwner(userId, ownerId);

            return ResponseEntity.ok("User with id " + userId + " was matched with owner with id " + ownerId + ".");
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);

            return ResponseEntity.ok("User with id " + userId + " was deleted.");
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
