package com.example.apiforcourseworkntu.repositories;

import com.example.apiforcourseworkntu.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findById(Integer id);

    @Query("SELECT o FROM Order o WHERE o.id = :orderId AND o.user.id = :userId")
    Optional<Order> findByIdAndUserId(Integer orderId, Integer userId);

    List<Order> findAllByUserId(Integer userId);

    void deleteByUserId(Integer userId);
}
