package com.example.apiforcourseworkntu.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserBuyerController {
    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> testUserEndpoint() {
        return ResponseEntity.ok("Test successful");
    }
}
