package com.example.apiforcourseworkntu.dto;

import com.example.apiforcourseworkntu.models.OrderStatus;
import com.example.apiforcourseworkntu.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrateOrder {
    private String email;
    private String address;
    private List<Integer> menuIds;
    private boolean paid;
}
