package com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.services;

import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.Role;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserById(Long id);
    void addRoleToUser(User user, Role role);
    User saveUser(User user);
    User createUser(User entity);
    void deleteUser(Long id);
}
