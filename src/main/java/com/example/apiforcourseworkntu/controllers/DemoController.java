package com.example.apiforcourseworkntu.controllers;


import com.example.apiforcourseworkntu.auth.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo-controller")
@RequiredArgsConstructor
public class DemoController {
    private final AuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity<String> sayHello(HttpServletRequest request) {
        // Логирование для отладки
        System.out.println("Request to /api/v1/demo received");
        return ResponseEntity.ok("Hello from secured endpoint!");
    }
}