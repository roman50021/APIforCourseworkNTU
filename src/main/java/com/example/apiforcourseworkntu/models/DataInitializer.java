package com.example.apiforcourseworkntu.models;

import com.example.apiforcourseworkntu.auth.AuthenticationService;
import com.example.apiforcourseworkntu.auth.RegisterRequest;
import com.example.apiforcourseworkntu.auth.dtos.AuthenticationResponse;
import com.example.apiforcourseworkntu.config.JwtService;
import com.example.apiforcourseworkntu.dto.Admin;
import com.example.apiforcourseworkntu.dto.UpdateUser;
import com.example.apiforcourseworkntu.repositories.UserRepository;
import com.example.apiforcourseworkntu.user.Role;
import com.example.apiforcourseworkntu.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Создаем пользователя-администратора при старте приложения
        String adminEmail = "admin@example.com";
        String adminPassword = "admin"; // Помните, что пароль следует хешировать перед сохранением
        String firstname = "Admin";
        String lastname = "Admin";

        // Вызываем метод регистрации администратора
        RegisterRequest adminRequest = new RegisterRequest();
        adminRequest.setEmail(adminEmail);
        adminRequest.setPassword(adminPassword);
        adminRequest.setFirstname(firstname);
        adminRequest.setLastname(lastname);

        // Регистрируем администратора
        ResponseEntity<AuthenticationResponse> responseEntity = register(adminRequest);

        // Обработка результата, если это необходимо
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            // Регистрация прошла успешно
            AuthenticationResponse authenticationResponse = responseEntity.getBody();
            // Дополнительная обработка, если нужно
        } else {
            // Обработка ошибки регистрации, если нужно
        }

    }

    public ResponseEntity<AuthenticationResponse> register(RegisterRequest request) {
        // Используем ваш существующий метод, но заменяем Role.USER на Role.ADMIN
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
                .role(Role.ADMIN) // Заменяем на Role.ADMIN
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return ResponseEntity.ok()
                .body(AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build());
    }
}