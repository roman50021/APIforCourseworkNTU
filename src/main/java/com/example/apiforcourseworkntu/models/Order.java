package com.example.apiforcourseworkntu.models;

import com.example.apiforcourseworkntu.repositories.OrderRepository;
import com.example.apiforcourseworkntu.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String email;
    private String address;

    @ElementCollection
    @CollectionTable(name = "order_menu_ids", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "menu_id")
    private List<Integer> menuIds;

    @ElementCollection
    @CollectionTable(name = "order_menu_names", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "menu_name")
    private List<String> menuNames;


    boolean paid;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private double totalPrice;
}