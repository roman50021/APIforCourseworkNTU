package com.example.apiforcourseworkntu.models;

import com.example.apiforcourseworkntu.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToMany
//    @JoinTable(
//            name = "orders_menu",
//            joinColumns = @JoinColumn(name = "orders_id"),
//            inverseJoinColumns = @JoinColumn(name = "menu_id")
//    )
//    private List<Menu> menuItems;

}
