package com.example.apiforcourseworkntu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InfoMenu {
    private Integer id;
    private String name;
    private String description;
    private double servingWeight;
    private double price;
    private String message;
}
