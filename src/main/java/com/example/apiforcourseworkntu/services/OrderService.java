package com.example.apiforcourseworkntu.services;

import com.example.apiforcourseworkntu.dto.AddMenu;
import com.example.apiforcourseworkntu.dto.CrateOrder;
import com.example.apiforcourseworkntu.dto.Message;
import com.example.apiforcourseworkntu.models.Menu;
import com.example.apiforcourseworkntu.models.Order;
import com.example.apiforcourseworkntu.models.OrderStatus;
import com.example.apiforcourseworkntu.repositories.MenuRepository;
import com.example.apiforcourseworkntu.repositories.OrderRepository;
import com.example.apiforcourseworkntu.repositories.UserRepository;
import com.example.apiforcourseworkntu.user.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    public ResponseEntity<Message> create(CrateOrder request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            // Fetch Menu entities from the database based on menuIds
            List<Menu> menuList = menuRepository.findAllByIdIn(request.getMenuIds());

            // Assuming menuIds are unique, create a map for efficient lookups
            Map<Integer, Menu> menuMap = menuList.stream()
                    .collect(Collectors.toMap(Menu::getId, Function.identity()));

            // Check if all requested menuIds are valid
            if (request.getMenuIds().stream().anyMatch(id -> !menuMap.containsKey(id))) {
                return ResponseEntity.badRequest().body(new Message("Invalid menuId found"));
            }

            // Map menuIds to menuNames
            List<String> menuNames = request.getMenuIds().stream()
                    .map(menuMap::get)
                    .map(Menu::getName)
                    .collect(Collectors.toList());

            // Calculate total price using the fetched Menu entities
            double totalPrice = counterPrice(menuList);
            var order = Order.builder()
                    .user(user)
                    .email(user.getEmail())
                    .address(request.getAddress())
                    .menuIds(request.getMenuIds())
                    .menuNames(menuNames)
                    .paid(request.isPaid())
                    .status(OrderStatus.PROCESSING)
                    .totalPrice(totalPrice)
                    .build();

            orderRepository.save(order);
            return ResponseEntity.ok(new Message("Order created successfully"));
        } else {
            return ResponseEntity.badRequest().body(new Message("User not found"));
        }
    }

    public double counterPrice(List<Menu> menuList){
        double sum = 0;
        for (Menu menuItem : menuList) {
            sum += menuItem.getPrice();
        }
        return sum;
    }



}
