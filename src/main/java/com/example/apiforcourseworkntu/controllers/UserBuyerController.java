package com.example.apiforcourseworkntu.controllers;

import com.example.apiforcourseworkntu.dto.*;
import com.example.apiforcourseworkntu.services.OrderService;
import com.example.apiforcourseworkntu.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserBuyerController {
    private final UserService userService;
    private final OrderService orderService;
    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> testUserEndpoint() {
        return ResponseEntity.ok("Test successful");
    }

    @PostMapping ("/create")
    public ResponseEntity<Message> createOrder(@RequestBody CrateOrder request) {
        return orderService.create(request);
    }

    @PostMapping ("/cancel")
    public ResponseEntity<Message> cancelOrder(@RequestBody CancelOrder request){
        return orderService.cancel(request);
    }

    @GetMapping  ("/orders")
    public ResponseEntity<OrdersMessage> getOrders(@RequestBody AllOrdersForUser request){
        return orderService.getMyOrders(request);
    }
    @PostMapping("/change")
    public ResponseEntity<Message> changeOrder(@RequestBody ChangeOrder request){
        return orderService.changeMyOrder(request);
    }

    @DeleteMapping("/delete")
    @Transactional
    public ResponseEntity<Message> deleteAccount(@RequestBody EmailRequest request){
        return orderService.deleteAccount(request);
    }



}
