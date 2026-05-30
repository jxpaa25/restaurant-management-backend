package com.restaurant.restaurant_service.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.restaurant_service.dto.OrderRequest;
import com.restaurant.restaurant_service.models.Order;
import com.restaurant.restaurant_service.services.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }    

    @GetMapping("/pending")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Order>> getPendingOrders() {
        return ResponseEntity.ok(orderService.getPendingOrders());
    }

    @PatchMapping("/{id}/complete")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Order> completeOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.completeOrder(id));
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }
}
