package com.example.apiforcourseworkntu.services;

import com.example.apiforcourseworkntu.auth.AuthenticationService;
import com.example.apiforcourseworkntu.auth.RegisterRequest;
import com.example.apiforcourseworkntu.auth.dtos.AuthenticationResponse;
import com.example.apiforcourseworkntu.dto.EmailRequest;
import com.example.apiforcourseworkntu.dto.InfoResponse;
import com.example.apiforcourseworkntu.dto.Message;
import com.example.apiforcourseworkntu.dto.UpdateUser;
import com.example.apiforcourseworkntu.repositories.OrderRepository;
import com.example.apiforcourseworkntu.user.User;
import com.example.apiforcourseworkntu.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private AuthenticationService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testCreateUser() {
        // Mock data
        RegisterRequest registerRequest = new RegisterRequest();
        AuthenticationResponse expectedResponse = new AuthenticationResponse();

        // Mock behavior
        when(authService.register(registerRequest)).thenReturn(ResponseEntity.ok(expectedResponse));

        // Test
        ResponseEntity<AuthenticationResponse> response = userService.createUser(registerRequest);

        // Verify
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void testLoadUserByEmailExistingUser() {
        // Mock data
        EmailRequest emailRequest = new EmailRequest();
        User existingUser = new User();
        existingUser.setEmail("test@example.com");

        // Mock behavior
        when(userRepository.findByEmail(emailRequest.getEmail())).thenReturn(Optional.of(existingUser));

        // Test
        ResponseEntity<InfoResponse> response = userService.loadUserByEmail(emailRequest);

        // Verify
        assertEquals("We have this user in database", response.getBody().getMessage());
    }

    @Test
    void testLoadUserByEmailNonExistingUser() {
        // Mock data
        EmailRequest emailRequest = new EmailRequest();

        // Mock behavior
        when(userRepository.findByEmail(emailRequest.getEmail())).thenReturn(Optional.empty());

        // Test
        ResponseEntity<InfoResponse> response = userService.loadUserByEmail(emailRequest);

        // Verify
        assertEquals("We don't have such a user!", response.getBody().getMessage());
    }

    // Similar tests can be written for other methods in UserService

    // ...

    @Test
    void testDeleteUserByEmailExistingUser() {
        // Mock data
        EmailRequest emailRequest = new EmailRequest();
        User existingUser = new User();
        existingUser.setEmail("test@example.com");

        // Mock behavior
        when(userRepository.findByEmail(emailRequest.getEmail())).thenReturn(Optional.of(existingUser));
        doNothing().when(userRepository).deleteByEmail(existingUser.getEmail());
        doNothing().when(orderRepository).deleteByUserId(existingUser.getId());

        // Test
        ResponseEntity<Message> response = userService.deleteUserByEmail(emailRequest);

        // Verify
        assertEquals("You have successfully deleted this user!", response.getBody().getMessage());
    }

    @Test
    void testDeleteUserByEmailNonExistingUser() {
        // Mock data
        EmailRequest emailRequest = new EmailRequest();

        // Mock behavior
        when(userRepository.findByEmail(emailRequest.getEmail())).thenReturn(Optional.empty());

        // Test
        ResponseEntity<Message> response = userService.deleteUserByEmail(emailRequest);

        // Verify
        assertEquals("We don't have such a user!", response.getBody().getMessage());
    }

    @Test
    void testFindAllUsers() {
        // Mock data
        User user1 = new User();
        User user2 = new User();
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Test
        ResponseEntity<List<User>> response = userService.findAllUsers();

        // Verify
        assertEquals(2, response.getBody().size());
    }

    // Additional tests for other methods can be added similarly

    // ...

}