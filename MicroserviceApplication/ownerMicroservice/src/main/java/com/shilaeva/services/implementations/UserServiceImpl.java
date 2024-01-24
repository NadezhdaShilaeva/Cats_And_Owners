package com.shilaeva.services.implementations;

import com.shilaeva.dto.OwnerDto;
import com.shilaeva.dto.UserDto;
import com.shilaeva.exceptions.OwnerException;
import com.shilaeva.exceptions.UserException;
import com.shilaeva.models.Owner;
import com.shilaeva.models.Role;
import com.shilaeva.models.Status;
import com.shilaeva.models.User;
import com.shilaeva.repositories.OwnerRepository;
import com.shilaeva.repositories.UserRepository;
import com.shilaeva.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserDto getById(Long userId) {
        return UserDto.asDto(getUserById(userId));
    }

    public UserDto getByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw UserException.userNotFoundByUsername(username);
        }

        return UserDto.asDto(user.get());
    }

    public OwnerDto getOwnerByUserId(Long userId) {
        Owner owner = getUserById(userId).getOwner();
        if (owner == null) {
            throw UserException.userDoesNotHaveOwner(userId);
        }

        return OwnerDto.asDto(owner);
    }

    public List<UserDto> getActiveUsers() {
        List<User> users = userRepository.findByStatus(Status.ACTIVE);
        if (users.isEmpty()) {
            throw UserException.usersNotFound();
        }

        return users.stream().map(UserDto::asDto).toList();
    }

    public List<UserDto> getNotActiveUsers() {
        List<User> users = userRepository.findByStatus(Status.NOT_ACTIVE);
        if (users.isEmpty()) {
            throw UserException.usersNotFound();
        }

        return users.stream().map(UserDto::asDto).toList();
    }

    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw UserException.usersNotFound();
        }

        return users.stream().map(UserDto::asDto).toList();
    }

    public UserDto createAdmin(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw UserException.userAlreadyExists(username);
        }

        User admin = new User();
        admin.setUsername(username);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setStatus(Status.ACTIVE);
        admin.setRole(Role.ROLE_ADMIN);

        return UserDto.asDto(userRepository.save(admin));
    }

    public UserDto createUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw UserException.userAlreadyExists(username);
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setStatus(Status.NOT_ACTIVE);
        user.setRole(Role.ROLE_USER);

        return UserDto.asDto(userRepository.save(user));
    }

    public void setOwner(Long userId, Long ownerId) {
        User user = getUserById(userId);
        user.setOwner(getOwnerById(ownerId));
        user.setStatus(Status.ACTIVE);

        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    private User getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw UserException.userNotExists(userId);
        }

        return user.get();
    }

    private Owner getOwnerById(Long ownerId) {
        Optional<Owner> owner = ownerRepository.findById(ownerId);
        if (owner.isEmpty()) {
            throw OwnerException.ownerNotExists(ownerId);
        }

        return owner.get();
    }
}
