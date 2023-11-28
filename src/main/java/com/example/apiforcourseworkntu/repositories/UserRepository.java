package com.example.apiforcourseworkntu.repositories;

import com.example.apiforcourseworkntu.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    List<User> findAll();
    Optional<User> deleteByEmail(String email);

}
