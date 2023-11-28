package com.example.apiforcourseworkntu.services;

import com.example.apiforcourseworkntu.auth.AuthenticationService;
import com.example.apiforcourseworkntu.auth.RegisterRequest;
import com.example.apiforcourseworkntu.auth.dtos.AuthenticationResponse;
import com.example.apiforcourseworkntu.dto.EmailRequest;
import com.example.apiforcourseworkntu.dto.InfoResponse;
import com.example.apiforcourseworkntu.dto.Message;
import com.example.apiforcourseworkntu.dto.UpdateUser;
import com.example.apiforcourseworkntu.user.User;
import com.example.apiforcourseworkntu.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final AuthenticationService service;
    private final UserRepository repository;

    public ResponseEntity<AuthenticationResponse> createUser(RegisterRequest request) {
        return service.register(request);
    }

    public ResponseEntity<InfoResponse> loadUserByEmail(EmailRequest request) {
        Optional<User> existingUser = repository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.ok()
                    .body(InfoResponse.builder()
                            .message("We have this user in database")
                            .firstname(existingUser.get().getFirstname())
                            .lastname(existingUser.get().getLastname())
                            .build());
        } else {
            return ResponseEntity.badRequest()
                    .body(InfoResponse.builder()
                            .message("We don't have such a user!")
                            .build());
        }
    }

    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = repository.findAll();
        return ResponseEntity.ok(users);
    }

    @Transactional
    public ResponseEntity<Message> deleteUserByEmail(EmailRequest request) {
        Optional<User> existingUser = repository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            repository.deleteByEmail(existingUser.get().getEmail());
            return ResponseEntity.ok()
                    .body(Message.builder()
                            .message("You have successfully deleted this user!")
                            .build());
        } else {
            return ResponseEntity.badRequest()
                    .body(Message.builder()
                            .message("We don't have such a user!")
                            .build());
        }
    }

    public ResponseEntity<Message> updateUser(UpdateUser request) {
        Optional<User> existingUser = repository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            User userToUpdate = existingUser.get();
            userToUpdate.setFirstname(request.getFirstname());
            userToUpdate.setLastname(request.getLastname());
            userToUpdate.setRole(request.getRole());
            repository.save(userToUpdate);
            return ResponseEntity.ok()
                    .body(Message.builder()
                            .message("This user was updated!")
                            .build());
        } else {
            return ResponseEntity.badRequest()
                    .body(Message.builder()
                            .message("We don't have such a user!")
                            .build());
        }

    }
}
