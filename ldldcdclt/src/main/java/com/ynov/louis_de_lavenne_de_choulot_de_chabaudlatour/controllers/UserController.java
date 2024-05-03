package com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.controllers;

import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.Role;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.User;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.repositories.RoleRepo;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.security.JwtAuthenticationFilter;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.security.JwtService;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.services.AuthService;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.services.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("users")
@Tag(name = "User", description = "User's control over himself")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AuthService authService;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    JwtService jwtService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request){
        String email = request.get("email");
        String password = request.get("password");
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isEmpty())
            return new ResponseEntity<>(
                    "User n'existe pas",
                    HttpStatus.NOT_FOUND
            );
        String jwt = authService.login(user.get(), password);
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

    //validate user with email, password, validationcode
    @PostMapping("validate")
    public ResponseEntity<?> validateUser(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        String validationCode = request.get("validationCode");

        Optional<User> user = userService.getUserByEmail(email);
        if (user.isEmpty()) {
            return new ResponseEntity<>(
                    "User n'existe pas",
                    HttpStatus.NOT_FOUND
            );
        }
        
        // // ! VALIDATION CODE
        // if (!user.get().getValidationCode().equals(validationCode)) {
        //     return new ResponseEntity<>(
        //             "Code de validation incorrect",
        //             HttpStatus.FORBIDDEN
        //     );
        // }

        String jwt = authService.login(user.get(), password);
        if (jwt == null) {
            return new ResponseEntity<>(
                    "Mot de passe incorrect",
                    HttpStatus.FORBIDDEN
            );
        }
        user.get().setValid(true);
        //save user
        userService.saveUser(user.get());
        return new ResponseEntity<>(
                jwt,
                HttpStatus.OK
        );
    }  
    
    //update user info password mail with args User entity and token
    @PostMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody User entity, @RequestHeader("Authorization") String token, BindingResult bindingResult) {
        var errs = bindingResult.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(
                    errs,
                    HttpStatus.BAD_REQUEST
            );
        }

        //check if actual user is the same as the one to update
        Optional<User> user = userService.getUserByEmail(entity.getEmail());
        if (user.isEmpty()) {
            return new ResponseEntity<>(
                    "User n'existe pas",
                    HttpStatus.NOT_FOUND
            );
        };
        
        String mail = jwtService.extractUsername(token);
        if (!mail.equals(entity.getEmail())) {
            return new ResponseEntity<>(
                    "Vous n'êtes pas autorisé à modifier ce compte",
                    HttpStatus.FORBIDDEN
            );
        }
        return new ResponseEntity<>(
                userService.saveUser(entity),
                HttpStatus.OK
        );
    }
}
