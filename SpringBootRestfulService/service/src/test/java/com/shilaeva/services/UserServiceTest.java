package com.shilaeva.services;

import com.shilaeva.dto.OwnerDto;
import com.shilaeva.dto.UserDto;
import com.shilaeva.exceptions.UserException;
import com.shilaeva.models.Owner;
import com.shilaeva.models.Role;
import com.shilaeva.models.Status;
import com.shilaeva.models.User;
import com.shilaeva.repositories.OwnerRepository;
import com.shilaeva.repositories.UserRepository;
import com.shilaeva.services.implementations.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private OwnerRepository ownerRepository;
    @InjectMocks
    private UserServiceImpl userService;
    private MockitoSession mockitoSession;

    private User user;
    private UserDto userNadezhdaDto;
    private Owner owner;
    private OwnerDto ownerNadezhdaDto;

    @BeforeEach
    void setUp() {
        mockitoSession = Mockito.mockitoSession().initMocks(this).startMocking();

        user = new User("ShilaevaNadezhda", "123456", Status.ACTIVE);
        user.setId(1L);
        user.setRole(Role.ROLE_ADMIN);
        userNadezhdaDto = UserDto.asDto(user);

        Calendar ownerBirthday = new Calendar.Builder().setDate(2003, Calendar.MARCH, 20).build();
        owner = new Owner("Nadezhda", ownerBirthday.getTime());
        owner.setId(1L);
        ownerNadezhdaDto = OwnerDto.asDto(owner);

        user.setOwner(owner);
    }

    @AfterEach
    void tearDown() {
        mockitoSession.finishMocking();
    }

    @Test
    void getById_getUser() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDto userDto = userService.getById(1L);

        assertEquals(userNadezhdaDto, userDto);
    }

    @Test
    void getByUsername_getUser() {
        Mockito.when(userRepository.findByUsername("ShilaevaNadezhda")).thenReturn(Optional.of(user));

        UserDto userDto = userService.getByUsername("ShilaevaNadezhda");

        assertEquals(userNadezhdaDto, userDto);
    }

    @Test
    void getOwnerByUserId_getOwner() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        OwnerDto ownerDto = userService.getOwnerByUserId(1L);

        assertEquals(ownerNadezhdaDto, ownerDto);
    }

    @Test
    void getActiveUsers_getUser() {
        Mockito.when(userRepository.findByStatus(Status.ACTIVE)).thenReturn(List.of(user));

        List<UserDto> userDtoList = userService.getActiveUsers();

        assertEquals(List.of(userNadezhdaDto), userDtoList);
    }

    @Test
    void getNotActiveUsers_throwsException() {
        Mockito.when(userRepository.findByStatus(Status.NOT_ACTIVE)).thenReturn(List.of());

        assertThrows(UserException.class, () -> userService.getNotActiveUsers());
    }

    @Test
    void getAll_getUser() {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserDto> userDtoList = userService.getAll();

        assertEquals(List.of(userNadezhdaDto), userDtoList);
    }

    @Test
    void createUser_throwsException() {
        Mockito.when(userRepository.findByUsername("ShilaevaNadezhda")).thenReturn(Optional.of(user));

        assertThrows(UserException.class, () -> userService.createUser("ShilaevaNadezhda", "123"));
    }

    @Test
    void setOwner_userIsActiveAndWithOwner() {
        user.setOwner(null);
        user.setStatus(Status.NOT_ACTIVE);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));

        userService.setOwner(1L, 1L);

        assertEquals(owner, user.getOwner());
        assertEquals(Status.ACTIVE, user.getStatus());
    }

    @Test
    void deleteUser_methodWasInvoked() {
        Mockito.doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        Mockito.verify(userRepository).deleteById(1L);
    }
}