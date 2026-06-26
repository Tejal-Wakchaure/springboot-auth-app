package com.example.auth.service;

import com.example.auth.dto.UserRequestDTO;
import com.example.auth.dto.UserResponseDTO;
import com.example.auth.entity.User;
import com.example.auth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    /*@Test
    void shouldCreateUser() {

        UserRequestDTO request = new UserRequestDTO(
                "abc",
                "abc@example.com",
                "your_pass",
                true
        );

        User savedUser = User.builder()
                .id(1)
                .name("abc")
                .email("abc@example.com")
                .password(your_pass)
                .verified(true)
                .build();
        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        UserResponseDTO response = userService.addUser(request);

        assertNotNull(response);
        assertEquals(1, response.id());
        assertEquals("abc", response.name());
        assertEquals("abc@example.com", response.email());
        assertTrue(response.verified());

        verify(userRepository, times(1))
                .save(any(User.class));
    }

    @Test
    void shouldGetUserById() {

        User user = User.builder()
                .id(1)
                .name("abc")
                .email("abc@example.com")
                .password("your_pass")
                .verified(true)
                .build();

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        UserResponseDTO response =
                userService.getUserById(1);

        assertEquals(1, response.id());
        assertEquals("abc", response.name());
        assertEquals("abc@example.com", response.email());
        verify(userRepository).findById(1);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findById(100))
                .thenReturn(Optional.empty());
        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> userService.getUserById(100));
        assertEquals(
                "User not found with id: 100",
                exception.getMessage()
        );

        verify(userRepository).findById(100);
    }

    @Test
    void shouldReturnAllUsers() {

        List<User> users = List.of(
                User.builder()
                        .id(1)
                        .name("User1")
                        .email("user1@test.com")
                        .verified(true)
                        .build(),

                User.builder()
                        .id(2)
                        .name("User2")
                        .email("user2@test.com")
                        .verified(false)
                        .build()
        );

        when(userRepository.findAll())
                .thenReturn(users);

        List<UserResponseDTO> response =
                userService.getAllUsers();

        assertEquals(2, response.size());

        verify(userRepository).findAll();
    }*/
}