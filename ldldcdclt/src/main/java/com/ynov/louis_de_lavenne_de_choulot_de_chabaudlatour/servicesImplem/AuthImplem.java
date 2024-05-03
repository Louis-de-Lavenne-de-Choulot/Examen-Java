package com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.servicesImplem;

import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.Admin;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.Role;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.User;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.security.JwtService;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.services.AdminService;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.services.AuthService;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthImplem implements AuthService {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserService userService;
    @Autowired
    AdminService adminService;
    @Autowired
    JwtService jwtService;

    @Override
    public String login(User user, String password) {
        if(bCryptPasswordEncoder.matches(password, user.getPassword()))
            return jwtService.generateToken(user);
        return null;
    }

    @Override
    public User register(User entity, Role role) {
        String passwordEncoded = bCryptPasswordEncoder.encode(entity.getPassword());
        entity.setPassword(passwordEncoded);
        userService.addRoleToUser(entity, role);
        return (User) userService.createUser(entity);
    }

    @Override
    public String login(Admin user, String password) {
        if(bCryptPasswordEncoder.matches(password, user.getPassword()))
            return jwtService.generateToken(user);
        return null;
    }

    @Override
    public Admin register(Admin entity, Role admin) {
        String passwordEncoded = bCryptPasswordEncoder.encode(entity.getPassword());
        entity.setPassword(passwordEncoded);
        adminService.addRoleToAdmin(entity, admin);
        return (Admin) adminService.createAdmin(entity);
    }
}
