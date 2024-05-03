package com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.config;

import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.Role;
import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.repositories.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements CommandLineRunner {
    @Autowired
    RoleRepo roleRepo;

    @Override
    public void run(String... args) throws Exception {
    //    for (Role.RoleEnum roleEnum : Role.RoleEnum.values())   {
    //        roleRepo.save(new Role(null, roleEnum.name()));
    //    }
    }
}
