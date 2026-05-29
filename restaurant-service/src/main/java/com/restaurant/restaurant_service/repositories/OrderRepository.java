package com.restaurant.restaurant_service.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.restaurant_service.models.Order;
import com.restaurant.restaurant_service.types.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByWaiterUsername(String waiterUsername);
    List<Order> findByStatus(OrderStatus status);
}
