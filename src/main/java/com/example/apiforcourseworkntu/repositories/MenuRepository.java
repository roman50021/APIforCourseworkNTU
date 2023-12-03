package com.example.apiforcourseworkntu.repositories;

import com.example.apiforcourseworkntu.models.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    Optional<Menu> findByName(String name);
    Optional<Menu> findById(Integer id);

    List<Menu> findAll();

    List<Menu> findAllByIdIn(List<Integer> menuIds);
}
