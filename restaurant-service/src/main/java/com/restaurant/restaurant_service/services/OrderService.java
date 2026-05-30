package com.restaurant.restaurant_service.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.restaurant.restaurant_service.dto.OrderItemRequest;
import com.restaurant.restaurant_service.dto.OrderRequest;
import com.restaurant.restaurant_service.models.MenuItem;
import com.restaurant.restaurant_service.models.Order;
import com.restaurant.restaurant_service.models.OrderItem;
import com.restaurant.restaurant_service.models.RestaurantTable;
import com.restaurant.restaurant_service.repositories.MenuItemRepository;
import com.restaurant.restaurant_service.repositories.OrderRepository;
import com.restaurant.restaurant_service.repositories.RestaurantTableRepository;
import com.restaurant.restaurant_service.types.OrderStatus;
import com.restaurant.restaurant_service.types.TableStatus;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantTableRepository restaurantTableRepository;

    @Transactional
    public Order createOrder(OrderRequest request) {
        RestaurantTable restaurantTable = restaurantTableRepository
                                            .findByTableNumber(request.getTableNumber())
                                            .orElseThrow(() -> new RuntimeException("Table is not found."));

        String waiterUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        Order order = new Order();
        order.setTable(restaurantTable);
        order.setWaiterUsername(waiterUsername);
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = new ArrayList<>();
        double totalPrice = 0;

        for (OrderItemRequest itemReq : request.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemReq.getMenuItemId())
                                    .orElseThrow(() -> new RuntimeException("Item is not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemReq.getQuantity());

            orderItems.add(orderItem);
            totalPrice += menuItem.getPrice() * itemReq.getQuantity();
        }

        order.setItems(orderItems);
        order.setTotalPrice(totalPrice);

        restaurantTable.setStatus(TableStatus.OCCUPIED);
        restaurantTableRepository.save(restaurantTable);

        return orderRepository.save(order);
    }

    public List<Order> getPendingOrders() {
        return orderRepository.findByStatus(OrderStatus.PENDING);
    }

    @Transactional
    public Order completeOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                        .orElseThrow(() -> new RuntimeException("Order is not found."));

        order.setStatus(OrderStatus.COMPLETED);

        RestaurantTable restaurantTable = order.getTable();
        if (restaurantTable != null) {
            restaurantTable.setStatus(TableStatus.FREE);
            restaurantTableRepository.save(restaurantTable);
        }

        return orderRepository.save(order);
    }

    @Transactional
    public Order cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                        .orElseThrow(() -> new RuntimeException("Order is not found"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("You can only cancel orders with status of PENDING");
        }

        order.setStatus(OrderStatus.CANCELLED);

        RestaurantTable restaurantTable = order.getTable();
        if (restaurantTable != null) {
            restaurantTable.setStatus(TableStatus.FREE);
            restaurantTableRepository.save(restaurantTable);
        }

        return orderRepository.save(order);
    }
}
