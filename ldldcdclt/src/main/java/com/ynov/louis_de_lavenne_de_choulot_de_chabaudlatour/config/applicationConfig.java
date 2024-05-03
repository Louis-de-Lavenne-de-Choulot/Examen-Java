package com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.config;

import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.Admin;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.User;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.repositories.AdminRepo;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.repositories.UserRepo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class applicationConfig {
    @Autowired
    UserRepo repository;
    @Autowired
    AdminRepo adminRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            Optional<User> user = repository.findByEmail(email);
            if (user.isPresent()) {
                return user.get();
            }
            else {
                Optional<Admin> admin = adminRepository.findByEmail(email);
                if (admin.isPresent()) {
                    return admin.get();
                }
                else {
                    throw new UsernameNotFoundException("User not found with email: " + email);
                }
            }
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
