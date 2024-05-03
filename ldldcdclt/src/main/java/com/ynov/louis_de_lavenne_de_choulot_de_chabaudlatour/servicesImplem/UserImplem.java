package com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.servicesImplem;

import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.Role;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.User;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.repositories.UserRepo;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserImplem implements UserService {
    @Autowired
    UserRepo userRepo;

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public void addRoleToUser(User user, Role role){
        user.addRole(role);
        userRepo.save(user);
    }

    @Override
    public User createUser(User entity) {
        return userRepo.save(entity);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public User saveUser(User user) {
        return userRepo.save(user);
    }
}
