package com.example.apiforcourseworkntu.dto;

import com.example.apiforcourseworkntu.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUser {
    private String firstname;
    private String lastname;
    private String email;
    private String message;
    private Role role;
}
