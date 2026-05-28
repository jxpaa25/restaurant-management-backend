package com.restaurant.identity_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordRequest {
    private String username;
    private String newPassword;
}
