package com.restaurant.restaurant_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.restaurant_service.models.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
}
