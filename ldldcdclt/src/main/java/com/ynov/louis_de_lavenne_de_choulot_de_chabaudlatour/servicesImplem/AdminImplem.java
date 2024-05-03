package com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.servicesImplem;

import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.Role;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.Admin;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.repositories.AdminRepo;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AdminImplem implements AdminService {
    @Autowired
    AdminRepo adminRepo;

    @Override
    public Optional<Admin> getAdminByEmail(String email) {
        return adminRepo.findByEmail(email);
    }

    @Override
    public void addRoleToAdmin(Admin admin, Role role){
        admin.addRole(role);
    }

    @Override
    public Admin createAdmin(Admin entity) {
        return adminRepo.save(entity);
    }

    @Override
    public Optional<Admin> getAdminById(Long id) {
        return adminRepo.findById(id);
    }

    @Override
    public void deleteAdmin(Long id) {
        adminRepo.deleteById(id);
    }

    @Override
    public Admin saveAdmin(Admin admin) {
        return adminRepo.save(admin);
    }
}
