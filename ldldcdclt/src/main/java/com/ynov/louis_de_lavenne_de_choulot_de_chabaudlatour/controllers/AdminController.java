package com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.controllers;

import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.Role;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.User;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.Admin;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.repositories.RoleRepo;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.services.AuthService;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("admin")
@Tag(name = "Administration", description = "CRUD for admin and CR for user")
public class AdminController {
    @Autowired
    AdminService adminService;
    @Autowired
    UserService userService;
    @Autowired
    AuthService authService;
    @Autowired
    RoleRepo roleRepo;

    private ResponseEntity<?> adminExisteResponse (Admin entity){
        Optional<Admin> admin = adminService.getAdminByEmail(entity.getEmail());
        if(admin.isPresent())
            return new ResponseEntity<>(
                    "email existe déjà",
                    HttpStatus.CONFLICT
            );
        return null;
    }

    private ResponseEntity<?> userExisteResponse (User entity){
        Optional<User> user = userService.getUserByEmail(entity.getEmail());
        if(user.isPresent())
            return new ResponseEntity<>(
                    "email existe déjà",
                    HttpStatus.CONFLICT
            );
        return null;
    }

    @PostMapping("signup")
    public ResponseEntity<?> adminRegister(@Valid @RequestBody Admin entity){
        ResponseEntity<?> res = adminExisteResponse(entity);
        if (res != null)
            return res;
        Optional<Role> role = roleRepo.findByRoleName(Role.RoleEnum.ADMIN.name());
        if(role.isEmpty())
            return new ResponseEntity<>(
                    "Une erreur est servenue !",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        return new ResponseEntity<>(
                authService.register(entity, role.get()),
                HttpStatus.CREATED
        );
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request){
        String email = request.get("email");
        String password = request.get("password");
        Optional<Admin> admin = adminService.getAdminByEmail(email);
        if (admin.isEmpty())
            return new ResponseEntity(
                    "Admin n'existe pas",
                    HttpStatus.NOT_FOUND
            );
        String jwt = authService.login(admin.get(), password);
        if (jwt == null)
            return new ResponseEntity<>(
                    "Mot de passe incorrect",
                    HttpStatus.FORBIDDEN
            );
        return new ResponseEntity<>(
                jwt,
                HttpStatus.OK
        );
    }

    @PostMapping("users")
    public ResponseEntity<?> userRegister(@Valid @RequestBody User entity){
        ResponseEntity<?> res = userExisteResponse(entity);
        if (res != null)
            return res;
        Optional<Role> role = roleRepo.findByRoleName(Role.RoleEnum.USER.name());
        if(role.isEmpty())
            return new ResponseEntity<>(
                    "Une erreur est servenue !",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        //?  ------------------
        //?  ------------------
        //?  ------------------
        //?   SEND EMAIL WITH VALIDATION CODE HERE
        //?  ------------------
        //?  ------------------
        //?  ------------------
        entity.setValid(false);
        return new ResponseEntity<>(
                authService.register(entity, role.get()),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(
                    "User not found",
                    HttpStatus.NOT_FOUND
            );
        }
        userService.deleteUser(id);
        return new ResponseEntity<>(
                "User deleted successfully",
                HttpStatus.OK
        );
    }

    @PutMapping("users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Map<String, String> request) {
        Optional<User> user = userService.getUserById(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(
                    "User not found",
                    HttpStatus.NOT_FOUND
            );
        }
        String newEmail = request.get("email");
        String newPassword = request.get("password");
        if (newEmail != null) {
            user.get().setEmail(newEmail);
        }
        if (newPassword != null) {
            user.get().setPassword(newPassword);
        }
        userService.saveUser(user.get());
        return new ResponseEntity<>(
                "User updated successfully",
                HttpStatus.OK
        );
    }
}
