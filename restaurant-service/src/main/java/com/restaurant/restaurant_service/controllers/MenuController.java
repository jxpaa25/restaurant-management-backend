package com.restaurant.restaurant_service.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.restaurant_service.dto.CreateCategoryRequest;
import com.restaurant.restaurant_service.dto.CreateMenuItemRequest;
import com.restaurant.restaurant_service.models.Category;
import com.restaurant.restaurant_service.models.MenuItem;
import com.restaurant.restaurant_service.services.MenuService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {
    
    private final MenuService menuService;

    // Public routes

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(menuService.getAllCategories());
    }

    @GetMapping("/items")
    public ResponseEntity<List<MenuItem>> getMenuItems() {
        return ResponseEntity.ok(menuService.getAllMenuItems());
    }

    @GetMapping("/items/category/{categoryId}")
    public ResponseEntity<List<MenuItem>> getItemsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(menuService.getItemsByCategory(categoryId));        
    }

    // Admin routes

    @PostMapping("/categories")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Category> createCategory(@RequestBody CreateCategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        return ResponseEntity.ok(menuService.addCategory(category));
    }

    @DeleteMapping("/categories/{categoryId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        menuService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/items/{categoryId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<MenuItem> createMenuItem(
        @PathVariable Long categoryId,
        @RequestBody CreateMenuItemRequest request
    ) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(request.getName());
        menuItem.setDescription(request.getDescription());
        menuItem.setPrice(request.getPrice());
        return ResponseEntity.ok(menuService.addMenuItem(categoryId, menuItem));
    }

    @PatchMapping("/items/{menuItemId}/availability")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<MenuItem> toggleAvailability(
        @PathVariable Long menuItemId, 
        @RequestParam boolean available
    ) {
        return ResponseEntity.ok(menuService.updateAvailability(menuItemId, available));
    }

    @DeleteMapping("/items/{menuItemId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long menuItemId) {
        menuService.deleteMenuItem(menuItemId);
        return ResponseEntity.noContent().build();
    }
}
