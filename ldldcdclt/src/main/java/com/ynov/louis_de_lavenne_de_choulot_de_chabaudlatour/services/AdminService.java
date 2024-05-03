package com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.services;

import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.Role;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.Admin;

import java.util.Optional;

public interface AdminService {
    Optional<Admin> getAdminByEmail(String email);
    Optional<Admin> getAdminById(Long id);
    void addRoleToAdmin(Admin admin, Role role);
    Admin saveAdmin(Admin admin);
    Admin createAdmin(Admin entity);
    void deleteAdmin(Long id);
}
