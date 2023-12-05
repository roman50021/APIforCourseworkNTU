package com.example.apiforcourseworkntu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeOrder {
    private String email;
    private Integer id;
    private String address;
    private List<Integer> menuIds;
    private boolean paid;
}
