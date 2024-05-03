package com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.repositories;

import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long> {
    Admin save(Admin admin);
    Optional<Admin> findByEmail(String email);
}
