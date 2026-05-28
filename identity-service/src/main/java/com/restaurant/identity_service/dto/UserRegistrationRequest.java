package com.restaurant.identity_service.dto;

import java.util.Set;

import lombok.Data;

@Data
public class UserRegistrationRequest {
    private String username;
    private String password;
    private Set<String> roles;
}
