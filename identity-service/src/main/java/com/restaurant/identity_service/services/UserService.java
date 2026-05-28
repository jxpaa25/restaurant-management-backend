package com.restaurant.identity_service.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.restaurant.identity_service.dto.UserRegistrationRequest;
import com.restaurant.identity_service.models.Role;
import com.restaurant.identity_service.models.User;
import com.restaurant.identity_service.repositories.RoleRepository;
import com.restaurant.identity_service.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public String registerUser(UserRegistrationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("User with this username already exists.");
        }

        User user = new User();
        user.setUsername(request.getUsername());

        // Password hashing
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstLogin(true);

        Set<Role> roles = new HashSet<>();
        request.getRoles().forEach(roleName -> {
            Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Role is not found: " + roleName));
            roles.add(role);
        });

        user.setRoles(roles);
        userRepository.save(user);

        return "User successfully created!";
    }
}
