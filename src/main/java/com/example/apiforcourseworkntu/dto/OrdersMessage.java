package com.example.apiforcourseworkntu.dto;

import com.example.apiforcourseworkntu.models.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdersMessage {
    private String message;
    private List<Order> orders;

    public OrdersMessage(String message) {
        this.message = message;
    }
}
