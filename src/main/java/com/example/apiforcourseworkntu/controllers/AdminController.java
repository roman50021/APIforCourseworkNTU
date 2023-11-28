package com.example.apiforcourseworkntu.controllers;

import com.example.apiforcourseworkntu.auth.AuthenticationService;
import com.example.apiforcourseworkntu.auth.RegisterRequest;
import com.example.apiforcourseworkntu.auth.dtos.AuthenticationResponse;
import com.example.apiforcourseworkntu.dto.*;
import com.example.apiforcourseworkntu.services.UserService;
import com.example.apiforcourseworkntu.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> testAdminEndpoint() {
        return ResponseEntity.ok("Test successful");
    }
    @PostMapping("/create")
    public ResponseEntity<AuthenticationResponse> createUser(@RequestBody RegisterRequest request){
        return userService.createUser(request);
    }
    @PostMapping("/get")
    public ResponseEntity<InfoResponse> infoByEmailUser(@RequestBody EmailRequest request){
        return userService.loadUserByEmail(request);
    }
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping("update")
    public ResponseEntity<Message> updateUser(@RequestBody UpdateUser request){
        return userService.update(request);
    }
    @PostMapping("/delete")
    public ResponseEntity<Message> deleteByEmailUser(@RequestBody EmailRequest request){
        return userService.deleteUserByEmail(request);
    }

}
