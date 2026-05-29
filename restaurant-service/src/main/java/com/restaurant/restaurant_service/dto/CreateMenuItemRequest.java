package com.restaurant.restaurant_service.dto;

import lombok.Data;

@Data
public class CreateMenuItemRequest {
    private String name;
    private String description;
    private Double price;
}
