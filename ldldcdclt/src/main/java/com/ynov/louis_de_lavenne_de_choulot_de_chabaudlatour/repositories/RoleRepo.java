package com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.repositories;

import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String role);
}
