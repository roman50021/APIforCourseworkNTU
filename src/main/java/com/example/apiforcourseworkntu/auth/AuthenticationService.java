package com.example.apiforcourseworkntu.auth;

import com.example.apiforcourseworkntu.auth.dtos.AuthenticationRequest;
import com.example.apiforcourseworkntu.auth.dtos.AuthenticationResponse;
import com.example.apiforcourseworkntu.config.JwtService;
import com.example.apiforcourseworkntu.user.Role;
import com.example.apiforcourseworkntu.user.User;
import com.example.apiforcourseworkntu.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public ResponseEntity<AuthenticationResponse> register(RegisterRequest request){
        Optional<User> existingUser = repository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(AuthenticationResponse.builder()
                            .errorMessage("A user with this email already exists")
                            .build());
        }

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return ResponseEntity.ok()
                .body(AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build());

    }

    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        try {
            System.out.println("Authentication attempt for: " + request.getEmail());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var user = repository.findByEmail(request.getEmail())
                    .orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            return ResponseEntity.ok(AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build());
        } catch (AuthenticationException e) {
            // Authentication error handling
            System.out.println("Authentication failed for: " + request.getEmail());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(AuthenticationResponse.builder()
                            .errorMessage("Invalid email or password")
                            .build());
        }
    }

    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String jwtToken = authorizationHeader.substring(7);

            // TODO:

            return ResponseEntity.ok("Logout successful");
        }

        return ResponseEntity.badRequest().body("No valid Authorization header found");
    }
}
