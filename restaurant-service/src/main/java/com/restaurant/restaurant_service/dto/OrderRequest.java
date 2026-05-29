package com.restaurant.restaurant_service.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderRequest {
    private Integer tableNumber;
    private List<OrderItemRequest> items;
}
