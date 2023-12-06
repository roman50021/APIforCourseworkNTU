package com.example.apiforcourseworkntu.services;

import com.example.apiforcourseworkntu.dto.*;
import com.example.apiforcourseworkntu.models.Menu;
import com.example.apiforcourseworkntu.models.Order;
import com.example.apiforcourseworkntu.models.OrderStatus;
import com.example.apiforcourseworkntu.repositories.MenuRepository;
import com.example.apiforcourseworkntu.repositories.OrderRepository;
import com.example.apiforcourseworkntu.repositories.UserRepository;
import com.example.apiforcourseworkntu.user.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    public ResponseEntity<Message> create(CrateOrder request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = getUserDetails(authentication);

        if (userDetails != null) {
            String userEmail = userDetails.getUsername();
            if (!userEmail.equals(request.getEmail())) {
                return ResponseEntity.badRequest().body(new Message("Mismatched user email"));
            }

            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("Authenticated user not found in the database"));

            List<Menu> menuList = menuRepository.findAllByIdIn(request.getMenuIds());

            if (menuList.size() != request.getMenuIds().size()) {
                return ResponseEntity.badRequest().body(new Message("Invalid menuId found"));
            }

            List<String> menuNames = menuList.stream()
                    .map(Menu::getName)
                    .collect(Collectors.toList());

            double totalPrice = calculateTotalPrice(menuList);

            Order order = Order.builder()
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
            return ResponseEntity.badRequest().body(new Message("User not authenticated"));
        }
    }

    private UserDetails getUserDetails(Authentication authentication) {
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }

    private double calculateTotalPrice(List<Menu> menuList) {
        return menuList.stream()
                .mapToDouble(Menu::getPrice)
                .sum();
    }

    public ResponseEntity<Message> cancel(CancelOrder request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = getUserDetails(authentication);

        if (userDetails != null) {
            String userEmail = userDetails.getUsername();

            if (!userEmail.equals(request.getEmail())) {
                return ResponseEntity.badRequest().body(new Message("Mismatched user email"));
            }

            // Используем метод map для преобразования Optional<User> в Optional<Order>
            Optional<Order> existingOrder = userRepository.findByEmail(userEmail)
                    .map(User::getId) // Получаем id пользователя
                    .flatMap(userId -> orderRepository.findByIdAndUserId(request.getId(), userId));

            if (existingOrder.isPresent()) {
                orderRepository.deleteById(existingOrder.get().getId());
                return ResponseEntity.ok().body(Message.builder().message("You have successfully deleted your order!").build());
            } else {
                return ResponseEntity.badRequest().body(Message.builder().message("We do not have such an order with this ID!").build());
            }
        } else {
            return ResponseEntity.badRequest().body(new Message("User not authenticated"));
        }
    }

    public ResponseEntity<OrdersMessage> getMyOrders(AllOrdersForUser request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = getUserDetails(authentication);

        if (userDetails != null) {
            String userEmail = userDetails.getUsername();

            if (!userEmail.equals(request.getEmail())) {
                return ResponseEntity.badRequest().body(new OrdersMessage("Mismatched user email"));
            }

            // Получаем пользователя из репозитория по email
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("Authenticated user not found in the database"));

            // Получаем все заказы пользователя по его идентификатору
            List<Order> userOrders = orderRepository.findAllByUserId(user.getId());

            // Возвращаем список заказов пользователя, обернутый в OrdersMessage
            return ResponseEntity.ok().body(new OrdersMessage("User orders retrieved successfully", userOrders));
        } else {
            return ResponseEntity.badRequest().body(new OrdersMessage("User not authenticated"));
        }
    }

    public ResponseEntity<Message> changeMyOrder(ChangeOrder request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = getUserDetails(authentication);

        if (userDetails != null) {
            String userEmail = userDetails.getUsername();
            if (!userEmail.equals(request.getEmail())) {
                return ResponseEntity.badRequest().body(new Message("Mismatched user email"));
            }

            // Поиск заказа в базе данных по его ID
            Optional<Order> optionalOrder = orderRepository.findById(request.getId());

            if (optionalOrder.isPresent()) {
                Order existingOrder = optionalOrder.get();

                // Проверка, что заказ принадлежит текущему пользователю
                if (!existingOrder.getUser().getEmail().equals(userEmail)) {
                    return ResponseEntity.badRequest().body(new Message("Order does not belong to the authenticated user"));
                }

                // Проверка, что все menuIds существуют в базе данных
                List<Menu> menuList = menuRepository.findAllByIdIn(request.getMenuIds());
                if (menuList.size() != request.getMenuIds().size()) {
                    return ResponseEntity.badRequest().body(new Message("Invalid menuId found"));
                }

                // Обновление полей заказа
                existingOrder.setAddress(request.getAddress());
                existingOrder.setMenuIds(request.getMenuIds());
                existingOrder.setPaid(request.isPaid());

                // Обновление цены заказа
                double totalPrice = calculateTotalPrice(menuList);
                existingOrder.setTotalPrice(totalPrice);

                // Обновление связанных таблиц
                List<String> menuNames = menuList.stream().map(Menu::getName).collect(Collectors.toList());
                existingOrder.setMenuNames(menuNames);

                // Дополнительные обновления, если необходимо

                // Сохранение обновленного заказа
                orderRepository.save(existingOrder);

                return ResponseEntity.ok(new Message("Order updated successfully"));
            } else {
                return ResponseEntity.badRequest().body(new Message("Order not found"));
            }
        } else {
            return ResponseEntity.badRequest().body(new Message("User not authenticated"));
        }
    }

    @Transactional
    public ResponseEntity<Message> deleteAccount(EmailRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = getUserDetails(authentication);

        if (userDetails != null) {
            String userEmail = userDetails.getUsername();
            if (!userEmail.equals(request.getEmail())) {
                return ResponseEntity.badRequest().body(new Message("Mismatched user email"));
            }

            Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

            if (optionalUser.isPresent()) {
                User userToDelete = optionalUser.get();

                if (!userEmail.equals(userToDelete.getEmail())) {
                    return ResponseEntity.badRequest().body(new Message("User does not match authenticated user"));
                }

                // Delete associated orders
                orderRepository.deleteByUserId(userToDelete.getId());

                // Delete the user
                userRepository.delete(userToDelete);

                return ResponseEntity.ok(new Message("Account deleted successfully"));
            } else {
                return ResponseEntity.badRequest().body(new Message("User not found"));
            }
        } else {
            return ResponseEntity.badRequest().body(new Message("User not authenticated"));
        }
    }
}
