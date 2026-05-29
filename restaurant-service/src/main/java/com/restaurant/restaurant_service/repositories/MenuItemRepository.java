package com.restaurant.restaurant_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.restaurant_service.models.MenuItem;
import java.util.List;


public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByCategoryId(Long categoryId);
    List<MenuItem> findByAvailable(Boolean available);
}
