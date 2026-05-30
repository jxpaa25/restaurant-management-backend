package com.restaurant.identity_service.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.restaurant.common.services.JwtService;
import com.restaurant.identity_service.dto.AuthResponse;
import com.restaurant.identity_service.dto.LoginRequest;
import com.restaurant.identity_service.dto.UserRegistrationRequest;
import com.restaurant.identity_service.models.CustomUserDetails;
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
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

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

    public AuthResponse login(LoginRequest request) {
        Authentication authenticate = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        if (authenticate.isAuthenticated()) {
            CustomUserDetails userDetails = (CustomUserDetails) authenticate.getPrincipal();

            String token = jwtService.generateToken(request.getUsername(), userDetails.getAuthorities());

            User user = userRepository.findByUsername(userDetails.getUsername())
                            .orElseThrow(() -> new RuntimeException("User is not found"));
            
            return new AuthResponse(token, user.isFirstLogin());
        } else {
            throw new RuntimeException("Invalid credentials.");
        }
    }

    public String changePassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new RuntimeException("User is not found."));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setFirstLogin(false);
        userRepository.save(user);

        return "Password changed successfully.";
    }
}
