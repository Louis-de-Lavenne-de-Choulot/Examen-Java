package com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.services;

import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.Admin;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.Role;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.User;

public interface AuthService {
    String login(User user, String password);
    String login(Admin user, String password);
    User register(User entity, Role prof);
    Admin register(Admin entity, Role prof);

}
