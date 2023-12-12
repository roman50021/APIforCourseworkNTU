package com.example.apiforcourseworkntu.services;
import com.example.apiforcourseworkntu.dto.*;
import com.example.apiforcourseworkntu.models.Menu;
import com.example.apiforcourseworkntu.models.Order;
import com.example.apiforcourseworkntu.models.OrderStatus;
import com.example.apiforcourseworkntu.repositories.MenuRepository;
import com.example.apiforcourseworkntu.repositories.OrderRepository;
import com.example.apiforcourseworkntu.repositories.UserRepository;
import com.example.apiforcourseworkntu.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrderSuccess() {
        // Mocking authentication
        when(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()).thenReturn(true);
        UserDetails userDetails = mock(UserDetails.class);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("user@example.com");

        // Mocking userRepository
        User user = new User();
        user.setEmail("user@example.com");
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        // Mocking menuRepository
        Menu menu = new Menu();
        menu.setId(1);
        menu.setName("Dish 1");
        menu.setPrice(10.0);
        when(menuRepository.findAllByIdIn(Collections.singletonList(1))).thenReturn(Collections.singletonList(menu));

        // Mocking orderRepository
        when(orderRepository.save(any())).thenReturn(new Order());

        // Test data
        CrateOrder crateOrder = new CrateOrder();
        crateOrder.setEmail("user@example.com");
        crateOrder.setMenuIds(Collections.singletonList(1));
        crateOrder.setPaid(true);
        crateOrder.setAddress("123 Main St");

        // Perform the test
        ResponseEntity<Message> responseEntity = orderService.create(crateOrder);

        // Verify the result
        assertEquals("Order created successfully", responseEntity.getBody().getMessage());
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void testCreateOrderMismatchedUserEmail() {
        // Mocking authentication
        when(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()).thenReturn(true);
        UserDetails userDetails = mock(UserDetails.class);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("user@example.com");

        // Test data with mismatched email
        CrateOrder crateOrder = new CrateOrder();
        crateOrder.setEmail("otheruser@example.com");
        crateOrder.setMenuIds(Collections.singletonList(1));
        crateOrder.setPaid(true);
        crateOrder.setAddress("123 Main St");

        // Perform the test
        ResponseEntity<Message> responseEntity = orderService.create(crateOrder);

        // Verify the result
        assertEquals("Mismatched user email", responseEntity.getBody().getMessage());
        assertEquals(400, responseEntity.getStatusCodeValue());
    }
    
    // Add more test cases for other methods as needed
    // ...

}