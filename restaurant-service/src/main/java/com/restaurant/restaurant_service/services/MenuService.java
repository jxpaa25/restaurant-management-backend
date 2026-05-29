package com.restaurant.restaurant_service.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.restaurant.restaurant_service.models.Category;
import com.restaurant.restaurant_service.models.MenuItem;
import com.restaurant.restaurant_service.repositories.CategoryRepository;
import com.restaurant.restaurant_service.repositories.MenuItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuService {
    
    private final CategoryRepository categoryRepository;
    private final MenuItemRepository menuItemRepository;

    // Categories

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    // Menu items

    public MenuItem addMenuItem(Long categoryId, MenuItem menuItem) {
        Category category = categoryRepository.findById(categoryId)
                                .orElseThrow(() -> new RuntimeException("Category is not find."));
        
        menuItem.setCategory(category);
        return menuItemRepository.save(menuItem);
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    public List<MenuItem> getItemsByCategory(Long categoryId) {
        return menuItemRepository.findByCategoryId(categoryId);
    }

    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }

    public MenuItem updateAvailability(Long id, boolean available) {
        MenuItem menuItem = menuItemRepository
                                .findById(id)
                                .orElseThrow(() -> new RuntimeException("Menu item is not found."));

        menuItem.setAvailable(available);
        return menuItemRepository.save(menuItem);
    }
}
