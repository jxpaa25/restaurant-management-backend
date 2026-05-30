package com.restaurant.identity_service.config;

import com.restaurant.common.services.JwtService;
import com.restaurant.identity_service.models.CustomUserDetails;
import com.restaurant.identity_service.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfig {

    @Bean
    public JwtService jwtService() {
        return new JwtService();
    }
    
    @Bean
    public UserDetailsService userDetailsService(UserRepository repository) {
        return username -> repository.findByUsername(username)
                .map(user -> new CustomUserDetails(user)) // Ovo ćemo napraviti u sledećem koraku
                .orElseThrow(() -> new UsernameNotFoundException("Korisnik nije pronađen: " + username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
