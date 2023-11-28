package com.example.apiforcourseworkntu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InfoResponse {
    private String firstname;
    private String lastname;
    private String message;
}
